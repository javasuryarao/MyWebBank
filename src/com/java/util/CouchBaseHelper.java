package com.java.util;

import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.subdoc.DocumentFragment;

public class CouchBaseHelper {
	private static Bucket bucket;

	private static Bucket getBucket() {
		if (bucket == null) {
			try {
				CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder().kvTimeout(100000) // in
						// ms
						.connectTimeout(100000).socketConnectTimeout(100000).managementTimeout(100000)
						.queryTimeout(100000).searchTimeout(100000).viewTimeout(100000).build();

				// Initialize the Connection
				Cluster cluster = CouchbaseCluster.create(env, "localhost");
				bucket = cluster.openBucket("mywebbank");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bucket;
	}

	/**** Get collection / table from 'testdb' ****/
	// if collection doesn't exists, MongoDB will create it for you

	public static N1qlQueryResult getDBCollection() {
		return getDBCollection("testdb", "accounts");
	}

	public static N1qlQueryResult getDBCollection(String database, String collection) {
		Bucket bucket2 = getBucket();

		N1qlQueryResult result = bucket2.query(
				N1qlQuery.simple("SELECT * FROM mywebbank where id = 1"));

		// Print each found Row
		for (N1qlQueryRow row : result) {
			// Prints {"name":"Arthur"}
			System.out.println(row);
		}
		return result;
	}

	public static void close() {
		if (bucket != null) {
			bucket.close();
			bucket = null;
		}

	}
	
	public static void create(String id, JsonObject document) {
		// Store the Document
		getBucket().upsert(JsonDocument.create(id, document));

		// Load the Document and print it
		// Prints Content and Metadata of the stored Document
		System.out.println(bucket.get(id));

		// Create a N1QL Primary Index (but ignore if it exists)
		getBucket().bucketManager().createN1qlPrimaryIndex(true, false);
		CouchBaseHelper.close();

	}

	public static void update(String id, JsonObject document) {
		DocumentFragment<Lookup> resultLookup = bucket.lookupIn(id).get("name").execute();
		System.out.println(resultLookup.content("name", String.class));
		bucket.mutateIn(id).upsert("balance",document.get("balance"),false).execute();
		System.out.println(bucket.get(id));
	CouchBaseHelper.close();

	}

}
