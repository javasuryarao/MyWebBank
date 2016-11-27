package com.java.dao.couchDB;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.java.bean.Account;
import com.java.util.CouchBaseHelper;
import com.mongodb.BasicDBObject;

public class AccountDAO {

	public static void update(Account account) {
	JsonObject document = JsonObject.create()
			.put("id", account.getId()+"")     
			.put("balance", account.getAccountBalance()+"");
		CouchBaseHelper.update(account.getId()+"", document);

	}

	public static Account createAccount(Account account) {

		
		/**** Insert ****/
		// create a document to store key and value
		int accountId = getMaxId();
		JsonObject document = JsonObject.create()
	            .put("id", accountId)
	            .put("type", "account")
	            .put("name", account.getUserName())
	    		.put("balance", account.getAccountBalance())
	    		.put("createdDate", "2016-11-11")
	            .put("apps", JsonArray.from("MyWebBank"));
		
		CouchBaseHelper.create(accountId+"", document);

		return getAccount(account.getUserName());
	}

	public static Account getAccount(String name) {
		Account account = new Account();
		/**** Find and display ****/
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("type", "account");
		searchQuery.put("name", name);

		N1qlQueryResult cursor = CouchBaseHelper.getDBCollection();
		System.out.println("finding Account by name " + name);
		int accountId = 0;
		boolean found = false;
		 // Print each found Row
        for (N1qlQueryRow row : cursor) {
            // Prints {"name":"Arthur"}
            System.out.println(row);
            found = true;
            JsonObject record = row.value();
            Object object = record.get("mywebbank");
            System.out.println(object);
            record = (JsonObject)object;
			Object idObject = record.get("id");
			System.out.println("Account found! " + record);
			if (idObject != null) {

				accountId = Integer.parseInt(idObject.toString());
				account = new Account();
				account.setId(accountId);
				Object balanceObj = record.get("balance");
				if (balanceObj instanceof Integer) {
					Integer accountBalanceDouble = (Integer) balanceObj;
					account.setAccountBalance(accountBalanceDouble.doubleValue());
				} else if (balanceObj instanceof Double) {
					Double accountBalanceDouble = (Double) balanceObj;
					account.setAccountBalance(accountBalanceDouble.doubleValue());
				} else if (balanceObj instanceof String) {
					String balanceString = (String) balanceObj;
					account.setAccountBalance(Double.parseDouble(balanceString));
				}

				account.setUserName(name);

			}
           
        }
        
  
		
		if (!found) {
			account.setUserName(name);
			account.setAccountBalance(100.00);
			account = createAccount(account);
		}
		return account;
	}

	public static double deposit(Account account, double amount) {
		account.setAccountBalance(account.getAccountBalance() + amount);
		update(account);
		return account.getAccountBalance();
	}

	private static int getMaxId() {
		/**** Find and display ****/
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("type", "account");

		System.out.println("finding Accounts");
		int maxId = 0;
		int tmpId = 0;
		
		maxId++;
		System.out.println("Existing Max Id: " + maxId);
		return maxId;
	}

}
