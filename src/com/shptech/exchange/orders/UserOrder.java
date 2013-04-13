package com.shptech.exchange.orders;

import com.shptech.exchange.Order;
import com.shptech.exchange.User;

public abstract class UserOrder extends Order{
	
	private final User user;

	public User getUser() {
		return user;
	}

	public UserOrder(User user) {
		this.user = user;
	}

}
