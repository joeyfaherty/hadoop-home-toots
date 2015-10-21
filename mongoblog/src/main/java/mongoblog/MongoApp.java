package mongoblog;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoApp {
	
	public static void main(String[] args) {
		// specify connection details
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		
		// specify database name
		MongoDatabase database = mongoClient.getDatabase("students");
		
		// specify collection name 
		MongoCollection<Document> collection = database.getCollection("xxxxtest");  //grades
		
		//create a new document
		Document document = new Document("name", "Joey")
							.append("age", 28)
							.append("hair-color", "brown")
							.append("exam-results", new Document("maths", 93)
													.append("irish", 87));
		
		
		//TODO: Implement a Logger
		System.out.println("Now inserting \n" + document.toJson() + "\ninto collection " + collection.getNamespace());
		//insert this document into the collection
		collection.insertOne(document);
		
		//create list of n documents
		List<Document> documents = new ArrayList<Document>();
		for (int i = 0; i < 100; i++) {
		    documents.add(new Document("elementNo", i));
		}
		System.out.println("Now we have : " + collection.count() + "documents in collection: " + collection.getNamespace());
		System.out.println("Inserting into collection " + collection.getNamespace() );
		collection.insertMany(documents);
		System.out.println("Now we have : " + collection.count() + "documents in collection: " + collection.getNamespace());
		
		
		//print ALL documents in a collection
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        System.out.println(cursor.next().toJson());
		    }
		} finally {
		    cursor.close();
		}
		
		//close the connection
		mongoClient.close();
	}
	
	
	
	

}
