package com.shptech.exchange;


public class Ask extends BidAsk  implements Comparable<Ask> {

	@Override
	public String toString() {
		return "Ask [getId()=" + getId() + ", getUser()=" + getUser()
				+ ", getPrice()=" + getPrice() + ", getAmount()=" + getAmount()
				+ "]";
	}

	public Ask(int price, int amount, User user, long id) {
		super(price, amount, user, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(Ask o) {
	   int result = Integer.compare(getPrice(), o.getPrice());
	   if (result == 0) {
		   result = Long.compare(getId(), o.getId());
	   }
	   return result;
	}
	
}