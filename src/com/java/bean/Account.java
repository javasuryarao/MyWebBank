package com.java.bean;

public class Account {
	
	private int hits;
	private String userName;
	private String accountBalance;
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
	public String getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	@Override
	public String toString() {
		return "Account [hits=" + hits + ", userName=" + userName + ", accountBalance=" + accountBalance + "]";
	}
	
	

}
