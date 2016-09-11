package com.java.util;

public class Cat {
	
	public String name;

	public Cat()
	{
		System.out.println("Cat created");
	}

	public String getName() {
		System.out.println("getter "+name);
		return name;
	}

	public void setName(String name) {
		System.out.println("setter "+name);
		this.name = name;
	}
	
	
}
