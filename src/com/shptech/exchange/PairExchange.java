package com.shptech.exchange;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.shptech.exchange.orders.BidAskOrder;
import com.shptech.exchange.orders.PairOrder;
import com.shptech.exchange.orders.Trade;

public class PairExchange {

	private final Currency currency;
	private final Currency commodity;

	private SortedSet<Bid> bids = new TreeSet<Bid>();
	private SortedSet<Ask> asks = new TreeSet<Ask>();

	private final Exchange exchange;
	private final int id;
	private long count = 0;

	public PairExchange(Exchange exchange, Currency first, Currency second,
			int id) {
		this.exchange = exchange;
		this.currency = first;
		this.commodity = second;
		this.id = id;
	}

	private void executeTrade(User bidUser, User askUser, int price, int amount) {
		exchange.updateChange(bidUser, commodity, amount);
		exchange.updateChange(askUser, currency, price * amount);
		exchange.output(new Trade(bidUser, id, amount, price, askUser));
	}

	

	private void processPurchase(User user, boolean isBid, int price, int amount) {
		if (isBid) {
			if (amount > 0) {
				int balance = exchange.balance(user, currency);
				if (balance < price * amount) {
					amount = balance / price;
				}
				if (amount == 0) {
					return;
				}

				exchange.updateChange(user, currency, -(amount * price));

				exchange.output(new BidAskOrder(user, id, isBid, amount, price));
				Iterator<Ask> it = asks.iterator();
				while (it.hasNext() && amount > 0) {
					Ask ask = it.next();
					if (ask.getPrice() > price) {
						break;
					}
					it.remove();
					int tradeAmount;
					if (ask.getAmount() > amount) {
						tradeAmount = amount;
						ask.spendAmount(tradeAmount);
						asks.add(ask);
					} else {
						tradeAmount = ask.getAmount();
					}
					executeTrade(user, ask.getUser(), price, tradeAmount);
					amount -= tradeAmount;
				}
				if (amount > 0) {
					bids.add(new Bid(price, amount, user, count++));
				}
			} else if (amount < 0) {
				int balance = exchange.balance(user, commodity);
				if (balance < amount) {
					amount = balance;
				}
				exchange.updateChange(user, commodity, -amount);
				Iterator<Bid> it = bids.iterator();
				while (it.hasNext() && amount < 0) {
					BidAsk bid = it.next();
					if (bid.getUser() == user && bid.getPrice() == price) {
						int removeAmount;
						if (bid.getAmount() > -amount) {
							bid.spendAmount(-amount);
							removeAmount = -amount;
						} else {
							it.remove();
							removeAmount = bid.getAmount();
						}
						amount -= removeAmount;
						exchange.output(new BidAskOrder(user, id, isBid,
								-removeAmount, price));

						exchange.updateChange(user, currency, price
								* removeAmount);
					}
				}
			}
		} else {
			if (amount > 0) {
				int balance = exchange.balance(user, commodity);
				if (balance < amount) {
					amount = balance / price;
				}
				if (amount == 0) {
					return;
				}
				exchange.updateChange(user, commodity, -amount);
				exchange.output(new BidAskOrder(user, id, isBid, amount, price));
				Iterator<Bid> it = bids.iterator();
				while (it.hasNext() && amount > 0) {
					BidAsk bid = it.next();
					if (bid.getPrice() < price) {
						break;
					}

					int tradeAmount;
					if (bid.getAmount() > amount) {
						tradeAmount = amount;
						bid.spendAmount(tradeAmount);
					} else {
						it.remove();
						tradeAmount = bid.getAmount();
					}
					amount -= tradeAmount;
					executeTrade(bid.getUser(), user, price, tradeAmount);
				}
				if (amount > 0) {
					asks.add(new Ask(price, amount, user, count++));
				}
			} else if (amount < 0) {
				Iterator<Ask> it = asks.iterator();
				while (it.hasNext() && amount < 0) {
					BidAsk ask = it.next();
					if (ask.getUser() == user && ask.getPrice() == price) {
						int removeAmount;
						if (ask.getAmount() > -amount) {
							ask.spendAmount(-amount);
							removeAmount = -amount;
						} else {
							it.remove();
							removeAmount = ask.getAmount();
						}
						amount -= removeAmount;

						exchange.output(new BidAskOrder(user, id, isBid,
								-removeAmount, price));
						exchange.updateChange(user, commodity, removeAmount);
					}
				}
			}
		}
	}

	public void process(PairOrder pairOrder) {
		if (pairOrder instanceof BidAskOrder) {
			processPurchase(pairOrder.getUser(),
					((BidAskOrder) pairOrder).isBid(),
					((BidAskOrder) pairOrder).getPrice(),
					((BidAskOrder) pairOrder).getAmount());
		}
	}

	public Currency getFirst() {
		return currency;
	}

	public Currency getSecond() {
		return commodity;
	}

}
