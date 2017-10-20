package com.my.user.test;

import java.io.Serializable;

public class Person implements Cloneable ,Serializable{
	Integer id;
	String name;
	
	public Person() {
		
	}
	
	public Person(Integer id, String name) {
		this.id = id;
		this.name = name;
		System.out.println("instantiation complete");
	}
	
	{
		System.out.println("instantiation start");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Person clone() throws CloneNotSupportedException {
		return (Person) super.clone();
	}
}
