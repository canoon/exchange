package exchange;

public class Bid extends BidAsk implements Comparable<Bid> {

	@Override
	public String toString() {
		return "Bid [getId()=" + getId() + ", getUser()=" + getUser()
				+ ", getPrice()=" + getPrice() + ", getAmount()=" + getAmount()
				+ "]";
	}

	public Bid(int price, int amount, User user, long id) {
		super(price, amount, user, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(Bid o) {
	   int result = Integer.compare(o.getPrice(), getPrice());
	   if (result == 0) {
		   result = Long.compare(getId(), o.getId());
	   }
	   return result;
	}
	
}