package com.my.user.test;

import org.junit.Before;
import org.junit.Test;

public class ThreadLocalTest {
	
	private static final  Counter counter = new Counter();
	private static final ThreadLocal<Counter> threadLocal = new InheritableThreadLocal<Counter>() {
		@Override
		protected Counter childValue(Counter parentValue) {
			return new Counter();
		}
	};
	
	@Before
	public void init() {
		threadLocal.set(counter);
	}
	
	@Test
	public void test() {
		System.out.println(threadLocal.get());
		for(int i=0;i<5;i++) {
			new Thread() {
				@Override
				public void run() {
					for(int j=0;j<3;j++) {
						System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
					}
				};
			}.start();
		}
		
	}
}


class Counter{
	int count = 0;

	public int increaseAndGet() {
		return ++count;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}