package com.sb.mongo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Employee {

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
		
		ObjectId ids= new ObjectId("5ebd5a410dde2215dbf3c818");
		ObjectId ids1= new ObjectId("5ebd5a410dde2215dbf3c819");
		ObjectId ids2= new ObjectId("5ebd5a410dde2215dbf3c81a");

		Document document1 = new Document("name","kumar").append("email", "kumar@sb.com").append("company", ids).append("experience", 4);
		Document document2 = new Document("name","Akhil").append("email", "akhil@sb.com").append("company", ids).append("experience", 7);
		Document document3 = new Document("name","Subhi").append("email", "subhi@pieriandx.com").append("company", ids1).append("experience", 11);
		Document document4 = new Document("name","Gufran").append("email", "gufran@persistent.com").append("company", ids2).append("experience", 9);

		List<Document> documents = new ArrayList<>();
		documents.add(document1);
		documents.add(document2);
		documents.add(document3);
		documents.add(document4);

		collection.insertMany(documents);
		System.out.println("collection created successfully");
		
		
		}

}
