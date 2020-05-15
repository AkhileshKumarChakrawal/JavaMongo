package com.sb.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class Assignment {

public static void main(String[] args) {
		
		MongoClient mongo = new MongoClient("localhost",27017);
		
		MongoCredential credintial;
		credintial = MongoCredential.createCredential("sampleUser", "mydata","password".toCharArray());
		
		MongoDatabase db = mongo.getDatabase("mydata");
		System.out.println("Credential ::"+credintial);
		
		
		MongoCollection<Document> collection = db.getCollection("company");
		MongoCollection<Document> collection1 = db.getCollection("employee");
		
		// current mail assignment
		//1) Create index on name in company
		collection.createIndex(Indexes.ascending("name"));


		// previous mail assignment
		//1. Count no of companies
		System.out.println("count number of company ::"+collection.count());

		
		// previous mail assignment
		//2. count no of employees
		System.out.println("count number of employee ::"+collection1.count());
		
		
		// previous mail assignment
		//3. Find companies which are less than 10 years
		BasicDBObject inquery = new BasicDBObject();
		inquery.put("since", new BasicDBObject("$lt",10));
		FindIterable<Document> cursor = collection.find(inquery);
		int i = 1;
		// Getting the iterator
		Iterator it = cursor.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
			i++;
		}
		
		
		//previous mail assignment
		//4. Join companies & employees. Display details of employee 'kumar'
		Bson lookup = new Document("$lookup",
				new Document("from" , "company")
				.append("localField","company")
				.append("foreignField", "_id")
				.append("as", "EmployeeData"));
		
		Bson match = new Document("$match",
				new Document("name","kumar"));
		
		List<Bson> filters = new ArrayList<Bson>();
		
		filters.add(lookup);
		filters.add(match);
		
		AggregateIterable<Document> agg = db.getCollection("employee").aggregate(filters);
		for (Document row : agg) {
		    System.out.println(row.toJson());
		}
		
		
		// previous mail assignment 
		//5. Calculate average experience of employee by company wise
				collection1.aggregate(Arrays.asList(
						Aggregates.group("$company", Accumulators.avg("experience","$experience"))
						)).forEach((Block<Document>) System.out ::println);
		
		
		
		//current mail assignment
		//4) allow regex search on email in employee table.
		//5) create a aggregate pipeline to find "um" string in email and populate their experience in ascending order

		AggregateIterable<Document> documents1 = collection1.aggregate(Arrays.asList(

			    Aggregates.match(Filters.regex("email", "um")),
			    Aggregates.project(Projections.fields(Projections.excludeId(),Projections.include("experience","experience"))),
			    Aggregates.sort(Sorts.ascending("experience"))

			));
		
		for (Document document : documents1) {
		    System.out.println(document.toJson());
		}
		
		
}
}
