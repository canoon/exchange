package exchange.orders;

import exchange.Order;
import exchange.User;

public abstract class UserOrder extends Order{
	
	private final User user;

	public User getUser() {
		return user;
	}

	public UserOrder(User user) {
		this.user = user;
	}

}
