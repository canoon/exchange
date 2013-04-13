package exchange.orders;

import exchange.User;

public abstract class PairOrder extends UserOrder {
	private int pair;
	
	
	

	public int getPair() {
		return pair;
	}

	public PairOrder(User user, int pair) {
		super(user);
		this.pair = pair;
	}
}
