package com.my.user.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class SPITest {

//	@Test
//	public void test() {
//		ServiceLoader<java.sql.Driver> serviceLoader = ServiceLoader.load(java.sql.Driver.class);
//		Iterator<Driver> iter = serviceLoader.iterator();
//		while(iter.hasNext()) {
//			System.out.println(Driver.class.getCanonicalName() + "使用的类加载器是：" + Driver.class.getClassLoader());
//			Driver driver = iter.next();
//			System.out.println(driver.getClass() + "使用的类加载器是：" + driver.getClass().getClassLoader());
//			System.out.println(Driver.class.isAssignableFrom(driver.getClass()));
//			System.out.println(driver instanceof Driver);
//			System.out.println("===============");
////			System.out.println(driver);
//		}
//	}
//	
//	@Test
//	public void test2() {
//		System.out.println(Object.class.getClassLoader());
//		System.out.println(SPITest.class.getClassLoader());
//		System.out.println(Object.class.isAssignableFrom(SPITest.class));
//	}
//	
//	@Test
//	public void test3() {
//		int arr[] = new int[12];
//		System.out.println(arr.getClass());
//		System.out.println(ClassUtils.getShortName(arr.getClass()));
//		System.out.println(ClassUtils.getQualifiedName(arr.getClass()));
//	}
//	
	@Test
	public void test4() throws ClassNotFoundException, InstantiationException, IllegalAccessException, CloneNotSupportedException, IOException {
		ClassLoader loader = new MyClassLoader();
//		System.out.println(loader);
//		do {
//			System.out.println(loader.getParent());
//			loader = loader.getParent();
//		}while(loader.getParent() != null);
		
//		Class<?> clazz1 = Person.class;
//		Class<?> clazz2 = loader.loadClass(Person.class.getCanonicalName());
//		System.out.println(clazz1 == clazz2);
//		//System.out.println(loader.getParent().loadClass(Person.class.getCanonicalName()));
//		System.out.println(clazz1 ==  Thread.currentThread().getContextClassLoader().loadClass(Person.class.getCanonicalName()));
		
//		clazz2.newInstance();
		
		Person p1 = new Person(1, "chje");
//		Person p2 = p1.clone();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
		ObjectOutputStream writer = new ObjectOutputStream(out);
		writer.writeObject(p1);
		byte[] arr = out.toByteArray();
		ObjectInputStream reader = new ObjectInputStream(new ByteArrayInputStream(arr));
		Person p2 = (Person)reader.readObject();
		System.out.println(p2);
	}
}


class MyClassLoader extends ClassLoader{
	
	
	public MyClassLoader() {
		super(MyClassLoader.class.getClassLoader().getParent());
	}
	
	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		InputStream input = this.getClass().getResourceAsStream("/" + name.replace(".", "/") + ".class");
		byte[] arr = new byte[4096];
		int size;
		try {
			size = input.read(arr);
			return super.defineClass(name, arr, 0, size);
		} catch (IOException e) {
			throw new ClassNotFoundException("not found:" + name);
		}
	}
	
	static {
		System.out.println(1111);
	}
}

