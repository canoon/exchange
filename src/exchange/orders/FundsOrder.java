package exchange.orders;

import exchange.Currency;
import exchange.User;



public class FundsOrder extends UserOrder {
	private int change;
	private Currency currency;

	public FundsOrder(User user, Currency currency, int change) {
		super(user);
		this.change = change;
		this.currency = currency;
	}

	public Currency getCurrency() {
		return currency;
	}

	public int getChange() {
		return change;
	}

	@Override
	public String toString() {
		return "FundsOrder [change=" + change + ", currency=" + currency
				+ ", getUser()=" + getUser() + "]";
	}
}
