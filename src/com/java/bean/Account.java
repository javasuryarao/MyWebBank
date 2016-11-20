package com.java.bean;

public class Account {
	
	private int hits;
	private int id;
	private String userName;
	private double accountBalance;
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", userName=" + userName + ", accountBalance=" + accountBalance + "]";
	}
	
	

}
