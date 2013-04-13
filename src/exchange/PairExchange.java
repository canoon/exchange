package exchange;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import exchange.orders.EnterOrder;
import exchange.orders.PairOrder;
import exchange.orders.Trade;

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
			}
		} else {
			if (amount > 0) {
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
			}
		}
	}

	public void process(PairOrder pairOrder) {
		if (pairOrder instanceof EnterOrder) {
			exchange.output(pairOrder);
			processPurchase(pairOrder.getUser(),
					((EnterOrder) pairOrder).isBid(),
					((EnterOrder) pairOrder).getPrice(),
					((EnterOrder) pairOrder).getAmount());
		}
	}

	public Currency getFirst() {
		return currency;
	}

	public Currency getSecond() {
		return commodity;
	}

}
