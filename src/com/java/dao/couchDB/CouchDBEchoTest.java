package com.java.dao.couchDB;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;

public class CouchDBEchoTest {

	public static void main(String... args) {
		Cluster cluster = null;
		Bucket bucket = null;
		try {

			CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder().kvTimeout(100000) // in
																								// ms
					.connectTimeout(100000).socketConnectTimeout(100000).managementTimeout(100000).queryTimeout(100000)
					.searchTimeout(100000).viewTimeout(100000).build();

			// Initialize the Connection
			cluster = CouchbaseCluster.create(env, "localhost");
			// Opens the "default" bucket
			bucket = cluster.openBucket("mywebbank");

			// Opens the "travel-sample" bucket without password
			// Bucket tsBucket = cluster.openBucket("travel-sample");

			//// Opens a bucket with password
			// Bucket secureBucket = cluster.openBucket("mybucket",
			//// "mypassword");

			// Bucket bucket = cluster.openBucket("default");

			// Create a JSON Document
			JsonObject arthur = JsonObject.create().put("name", "Arthur").put("email", "kingarthur@couchbase.com")
					.put("interests", JsonArray.from("Holy Grail", "African Swallows"));

			// Store the Document
			bucket.upsert(JsonDocument.create("u:king_arthur", arthur));

			// Load the Document and print it
			// Prints Content and Metadata of the stored Document
			System.out.println(bucket.get("u:king_arthur"));

			// Create a N1QL Primary Index (but ignore if it exists)
			bucket.bucketManager().createN1qlPrimaryIndex(true, false);

			// Perform a N1QL Query
			N1qlQueryResult result = bucket.query(N1qlQuery.parameterized(
					"SELECT name FROM default WHERE $1 IN interests", JsonArray.from("African Swallows")));

			// Print each found Row
			for (N1qlQueryRow row : result) {
				// Prints {"name":"Arthur"}
				System.out.println(row);
			}
			bucket.close();
			cluster.disconnect();
		}catch (Exception ex) {
			bucket.close();
			cluster.disconnect();
		}

	}
}