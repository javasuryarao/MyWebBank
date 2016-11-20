package com.java.util;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoHelper {
	private static MongoClient mongo;

	private static MongoClient getMongoClient() {
		if (mongo == null) {
			try {
				mongo = new MongoClient("localhost", 27017);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return mongo;
	}

	/**** Get collection / table from 'testdb' ****/
	// if collection doesn't exists, MongoDB will create it for you

	public static DBCollection getDBCollection() {
		return getDBCollection("testdb", "accounts");
	}

	public static DBCollection getDBCollection(String database, String collection) {
		/**** Connect to MongoDB ****/
		// Since 2.10.0, uses MongoClient
		MongoClient mongo = getMongoClient();

		/**** Get database ****/
		// if database doesn't exists, MongoDB will create it for you
		DB db = mongo.getDB(database);

		/**** Get collection / table from 'testdb' ****/
		// if collection doesn't exists, MongoDB will create it for you
		DBCollection table = db.getCollection(collection);
		return table;
	}

	public static void close() {
		if(mongo!=null)
		{
			mongo.close();
			mongo = null;
		}
		
	}

	public static void update(BasicDBObject query, BasicDBObject updateObj) {
		getDBCollection().update(query, updateObj);
		MongoHelper.close();
		
	}

	public static void create(BasicDBObject document) {
		getDBCollection().insert(document);
		System.out.println("document created. " + document);
		MongoHelper.close();
		
	}

	public static DBCursor find(BasicDBObject searchQuery) {
		return getDBCollection().find(searchQuery);
	}

}
