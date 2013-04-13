package exchange.orders;

import exchange.User;

public class Trade extends PairOrder {
	
	private final int amount;
	private final int price;
	private final User other;

	public int getAmount() {
		return amount;
	}

	public int getPrice() {
		return price;
	}

	public User getOther() {
		return other;
	}

	public Trade(User user, int pair, int amount, int price, User other) {
		super(user, pair);
		this.amount = amount;
		this.price = price;
		this.other = other;
	}

	@Override
	public String toString() {
		return "Trade [amount=" + amount + ", price=" + price + ", other="
				+ other + ", getPair()=" + getPair() + ", getUser()="
				+ getUser() + ", getId()=" + getId() + "]";
	}

}
