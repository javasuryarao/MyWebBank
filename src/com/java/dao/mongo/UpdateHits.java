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

public class UpdateHits {
	public static void update() {

		try {

			/**** Connect to MongoDB ****/
			// Since 2.10.0, uses MongoClient
			MongoClient mongo = new MongoClient("localhost", 27017);

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("testdb");

			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("hits");
			
			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("type", "hits");

			DBCursor cursor = table.find(searchQuery);
			System.out.println("finding hits");
			int hitsInt = 0;
			while (cursor.hasNext()) {
				DBObject next = cursor.next();
				Object object = next.get("count");
				System.out.println(next);
				if(object !=null)
				{
					hitsInt = Integer.parseInt(object.toString());
				}
				
			}
			System.out.println("Existing hits: "+hitsInt);
			

			/**** Insert ****/
			// create a document to store key and value
			if(hitsInt == 0)
			{
			BasicDBObject document = new BasicDBObject();
			document.put("type", "hits");
			document.put("count", ++hitsInt);
			document.put("createdDate", new Date());
			table.insert(document);
			System.out.println("Insert succesful.");
			}

			

			/**** Update ****/
			// search document where name="mkyong" and update it with new values
			BasicDBObject query = new BasicDBObject();
			query.put("type", "hits");

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("count", ++hitsInt);

			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);

			table.update(query, updateObj);

			/**** Find and display ****/
			BasicDBObject searchQuery2 = new BasicDBObject().append("type", "hits");

			DBCursor cursor2 = table.find(searchQuery2);

			System.out.println("Finding after update:");
			while (cursor2.hasNext()) {
				System.out.println(cursor2.next());
			}

			/**** Done ****/
			System.out.println("Done");
			mongo.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			if(e.getMessage().contains("Connection refused"))
			{
				System.out.println("Surya connection issue.");
			}
			e.printStackTrace();
		}
		catch (Throwable e) {
			if(e.getMessage().contains("Connection refused"))
			{
				System.out.println("Surya connection issue.");
			}
			e.printStackTrace();
		}

	}

}
