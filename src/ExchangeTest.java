import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.shptech.exchange.Currency;
import com.shptech.exchange.Exchange;
import com.shptech.exchange.PairExchange;
import com.shptech.exchange.User;
import com.shptech.exchange.orders.BidAskOrder;
import com.shptech.exchange.orders.FundsOrder;


public class ExchangeTest {

	@Test
	public void test() {

	   Exchange e = new Exchange();
	   Currency aud = new Currency("AUD");
	   Currency btc = new Currency("BTC");
	   int pairId = e.createPair(aud, btc);
	   
	   User bob = new User("bob");
	   User bill = new User("bill");
	   
	   e.process(new FundsOrder(bob, aud, 10));
	   e.process(new FundsOrder(bill, btc, 1));
	   e.process(new BidAskOrder(bob, pairId, true, 1, 10));
	   e.process(new BidAskOrder(bill, pairId, false, 1, 10));
	}
	
	@Test
	public void test1() {

	   Exchange e = new Exchange();
	   Currency aud = new Currency("AUD");
	   Currency btc = new Currency("BTC");
	   int pairId = e.createPair(aud, btc);
	   
	   User bob = new User("bob");
	   User bill = new User("bill");
	   
	   e.process(new FundsOrder(bob, aud, 10));
	   e.process(new FundsOrder(bill, btc, 1));
	   e.printBalances();
	   e.process(new BidAskOrder(bob, pairId, true, 1, 10));
	   e.printBalances();
	   e.process(new BidAskOrder(bill, pairId, false, 1, 10));
	   e.printBalances();
	   e.process(new BidAskOrder(bill, pairId, true, 1, 10));
	   e.process(new BidAskOrder(bob, pairId, false, 1, 10));
	   e.printBalances();
	   
	   e.process(new FundsOrder(bill, btc, 1));
	   e.process(new BidAskOrder(bob, pairId, true, 2, 5));
	   e.printBalances();
	   e.process(new BidAskOrder(bill, pairId, false, 1, 4));
	   e.printBalances();
	   e.process(new BidAskOrder(bill, pairId, false, 1, 5));
	   e.printBalances();
	   
	}

}
