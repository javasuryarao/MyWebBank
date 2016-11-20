package com.java.dao.mongo;

import java.util.Date;

import com.java.bean.Account;
import com.java.util.MongoHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class AccountDAO {

	public static void update(Account account) {
		DBCollection table = MongoHelper.getDBCollection();
		Account foundAccount = getAccount(account.getUserName());

		/**** Update ****/
		// search document where name="mkyong" and update it with new values
		BasicDBObject query = new BasicDBObject();
		query.put("type", "account");
		query.put("id", foundAccount.getId());

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("balance", account.getAccountBalance());

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);
		MongoHelper.update(query, updateObj);

	}

	public static Account createAccount(Account account) {

		DBCollection table = MongoHelper.getDBCollection();

		/**** Insert ****/
		// create a document to store key and value
		BasicDBObject document = new BasicDBObject();
		int accountId = getMaxId(table);
		document.put("id", accountId);
		document.put("type", "account");
		document.put("name", account.getUserName());
		document.put("balance", account.getAccountBalance());
		document.put("createdDate", new Date());
		MongoHelper.create(document);

		return getAccount(account.getUserName());
	}

	public static Account getAccount(String name) {
		Account account = new Account();
		/**** Find and display ****/
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("type", "account");
		searchQuery.put("name", name);

		DBCursor cursor = MongoHelper.find(searchQuery);
		System.out.println("finding Account by name " + name);
		int accountId = 0;
		boolean found = false;
		while (cursor.hasNext()) {
			DBObject record = cursor.next();
			found = true;
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
		cursor.close();
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

	private static int getMaxId(DBCollection table) {
		/**** Find and display ****/
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("type", "account");

		DBCursor cursor = table.find(searchQuery);
		System.out.println("finding Accounts");
		int maxId = 0;
		int tmpId = 0;
		while (cursor.hasNext()) {
			DBObject next = cursor.next();
			Object object = next.get("id");
			System.out.println(next);
			if (object != null) {
				tmpId = Integer.parseInt(object.toString());
				if (tmpId >= maxId) {
					maxId = tmpId;
				}
			}

		}
		maxId++;
		System.out.println("Existing Max Id: " + maxId);
		return maxId;
	}

}
