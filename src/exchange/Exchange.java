package exchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import exchange.orders.FundsOrder;
import exchange.orders.PairOrder;

public class Exchange {

	private List<PairExchange> pairs = new ArrayList<PairExchange>();
	private Map<Currency, HashMap<User, Integer>> balances = new HashMap<Currency, HashMap<User, Integer>>();

	public Exchange() {
	}

	public int createPair(Currency first, Currency second) {
		int id = pairs.size();
		pairs.add(new PairExchange(this, first, second, id));
		return id;
	}

	public void updateChange(User user, Currency currency, int change) {
		int currentBalance = balance(user, currency);
		currentBalance += change;
		if (currentBalance > 0) {
			balances.get(currency).put(user, currentBalance);
		} else if (currentBalance == 0) {
			balances.get(currency).remove(user);
		} else {
			throw new RuntimeException();
		}
	}

	public int balance(User user, Currency currency) {
		HashMap<User, Integer> currencyMap = balances.get(currency);
		if (currencyMap == null) {
			currencyMap = new HashMap<User, Integer>();
			balances.put(currency, currencyMap);
		}
		Integer balance = currencyMap.get(user);
		if (balance == null) {
			return 0;
		} else {
			return balance;
		}
	}

	public void process(Order order) {
		if (order instanceof FundsOrder) {
			updateChange(((FundsOrder) order).getUser(),
					((FundsOrder) order).getCurrency(),
					((FundsOrder) order).getChange());
		} else if (order instanceof PairOrder) {
			pairs.get(((PairOrder) order).getPair()).process((PairOrder) order);

		}
	}
	
	public void printBalances() {
		for (Entry<Currency, HashMap<User, Integer>> e : balances.entrySet()) {
			for(Entry<User, Integer> i : e.getValue().entrySet()){
				System.out.println(i.getKey() + ": " + i.getValue() + " in " + e.getKey());
			}
			
		}
	}

	public void output(Order order) {
	//	System.out.println(order.getClass().toString() + ": " + order.toString());
	}
}
