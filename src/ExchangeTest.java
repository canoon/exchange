import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import exchange.Currency;
import exchange.Exchange;
import exchange.PairExchange;
import exchange.User;
import exchange.orders.EnterOrder;
import exchange.orders.FundsOrder;
import static org.hamcrest.CoreMatchers.*;

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
	   assertThat(e.balance(bob, aud), is(10));
	   assertThat(e.balance(bob, btc), is(0));
	   assertThat(e.balance(bill, aud), is(0));
	   assertThat(e.balance(bill, btc), is(1));
	   e.process(new EnterOrder(bob, pairId, true, 1, 10));
	   e.process(new EnterOrder(bill, pairId, false, 1, 10));
	   assertThat(e.balance(bob, aud), is(0));
	   assertThat(e.balance(bob, btc), is(1));
	   assertThat(e.balance(bill, aud), is(10));
	   assertThat(e.balance(bill, btc), is(0));

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
	   e.process(new EnterOrder(bob, pairId, true, 1, 10));
	   e.process(new EnterOrder(bill, pairId, false, 1, 10));
	   e.process(new EnterOrder(bill, pairId, true, 1, 10));
	   e.process(new EnterOrder(bob, pairId, false, 1, 10));
	   
	   e.process(new FundsOrder(bill, btc, 1));
	   e.process(new EnterOrder(bob, pairId, true, 2, 5));
	   e.process(new EnterOrder(bill, pairId, false, 1, 4));
	   e.process(new EnterOrder(bill, pairId, false, 1, 5));
	   
	   assertThat(e.balance(bob, aud), is(0));
	   assertThat(e.balance(bob, btc), is(2));
	   assertThat(e.balance(bill, aud), is(9));
	   assertThat(e.balance(bill, btc), is(0));
	}
	
	@Test
	public void performance() {
		   Exchange e = new Exchange();
		   Currency aud = new Currency("AUD");
		   Currency btc = new Currency("BTC");
		   int pairId = e.createPair(aud, btc);
		   
		   User bob = new User("bob");
		   User bill = new User("bill");
		   
		   e.process(new FundsOrder(bob, aud, 1000000));
		   e.process(new FundsOrder(bill, btc, 100000000));
		   e.process(new FundsOrder(bob, btc, 1000000));
		   e.process(new FundsOrder(bill, aud, 100000000));
		   Random r = new Random();
		   long start = System.currentTimeMillis();
		   for (int i = 0; i < 10000000; i++) {
			   e.process(new EnterOrder(r.nextBoolean() ? bob : bill, pairId, r.nextBoolean(), r.nextInt(100), r.nextInt(100) + 1));
		   }
		   long time = System.currentTimeMillis() - start;
		   System.out.println(10000000 * 1000 / time);
	}

}
