package com.my.user.test;

import org.junit.Test;

public class StringTest {

	@Test
	public void test() {
		int i=2;
		switch(i) {

			default:System.out.println("default");
			case 1:
				System.out.println("1");
			case (2 | 4):
				System.out.println("2");
			case 3:
				System.out.println("3");
		}
	}
}
