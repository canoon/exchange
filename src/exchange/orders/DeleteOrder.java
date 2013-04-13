package exchange.orders;

import exchange.User;

public class DeleteOrder extends PairOrder {

	private final int id;

	public DeleteOrder(User user, int pair, int id) {
		super(user, pair);
		this.id = id;
	}

	public int getOrderId() {
		return id;
	}

}
