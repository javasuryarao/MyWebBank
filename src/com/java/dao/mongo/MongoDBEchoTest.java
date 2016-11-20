package com.java.dao.mongo;

import java.net.UnknownHostException;
import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class MongoDBEchoTest {
	public static void main(String[] args) {

		try {

			/**** Connect to MongoDB ****/
			// Since 2.10.0, uses MongoClient
			MongoClient mongo = new MongoClient("localhost", 27017);
			System.out.println("Connection established.");

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("testdb");
			System.out.println("Database connected.");
			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("hits");
			System.out.println("fetched a collection.");
			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("type", "hits");

			DBCursor cursor = table.find(searchQuery);
			while (cursor.hasNext()) {
				cursor.next();
			}
			System.out.println("navigated through the collection.");
			cursor.close();
			System.out.println("Echo Test Successful.");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
