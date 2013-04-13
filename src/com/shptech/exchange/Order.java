package com.shptech.exchange;

public abstract class Order {

	
	private int id;
	@Override
	public String toString() {
		return "Order []";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
