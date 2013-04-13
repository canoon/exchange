package exchange.orders;

import exchange.User;

public class EnterOrder extends PairOrder {
	private final int amount;
	private final boolean bid;

	private final int price;
	private final int id;
	

	public int getId() {
		return id;
	}

	public EnterOrder(User user, int pair, boolean bid, int amount, int price, int id) {
		super(user, pair);
		this.bid = bid;
		this.amount = amount;
		this.price = price;
		this.id = id;
	}

	@Override
	public String toString() {
		return "BidAskOrder [amount=" + amount + ", bid=" + bid + ", price="
				+ price + ", getPair()=" + getPair() + ", getUser()="
				+ getUser() + ", getId()=" + getId() + "]";
	}

	public int getAmount() {
		return amount;
	}
	


	public int getPrice() {
		return price;
	}

	public boolean isBid() {
		return bid;
	}

}
