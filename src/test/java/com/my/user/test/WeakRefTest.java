package com.my.user.test;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

import org.junit.Test;

public class WeakRefTest {

	@Test
	public void test() {
		BigObject bigObj = new BigObject();
		Reference<BigObject> ref = new SoftReference<BigObject>(bigObj);
		bigObj = null;
		int i=0;
		while(true) {
			System.out.println(ref.get());
//			if(i>Integer.MAX_VALUE) {
//				break;
//			}
		}
//		System.out.println(bigObj);
	}
	
}

class BigObject{
	
}

