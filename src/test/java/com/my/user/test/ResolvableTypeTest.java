package com.my.user.test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.core.ResolvableType;

public class ResolvableTypeTest extends A{
	
	@Test
	public void test() {
		System.out.println(B.i);
	}
	
	static {
		System.out.println("ResolvableType static");
	}
	static int j=100;
}

class A{
	static int i=10;
	static {
		System.out.println("A.static");
	}
	
	{
		System.out.println("A instance");
	}
}

class B extends A{
	static {
		System.out.println("B staitc");
		
	}
	
	{
		System.out.println("B instance ");
	}
}