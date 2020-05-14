package com.sb.mongo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Assignment {

public static void main(String[] args) {
		
		MongoClient mongo = new MongoClient("localhost",27017);
		
		MongoCredential credintial;
		credintial = MongoCredential.createCredential("sampleUser", "mydata","password".toCharArray());
		
		MongoDatabase db = mongo.getDatabase("mydata");
		System.out.println("Credential ::"+credintial);
		
		
		///creating collection
		//db.createCollection("sampleCollection");
		db.createCollection("employee");
		
		MongoCollection<Document> collection = db.getCollection("employee");
		
}
}
