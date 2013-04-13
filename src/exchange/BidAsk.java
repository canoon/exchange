package exchange;
public abstract class BidAsk {
	private final int price;
	private int amount;
	private final User user;
	private final long id;
	
	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public BidAsk(int price, int amount, User user, long id) {
		this.price = price;
		this.amount = amount;
		this.user = user;
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public int getAmount() {
		return amount;
	}
	

	public void spendAmount(int amount) {
		this.amount -= amount;
	}
	


	@Override
	public String toString() {
		return "BidAsk [price=" + price + ", amount=" + amount + ", user="
				+ user + "]";
	}
}
