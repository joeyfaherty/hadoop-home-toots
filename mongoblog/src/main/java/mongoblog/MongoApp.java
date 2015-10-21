package mongoblog;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
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
		
		//insert this document into the collection
		System.out.println("Now inserting \n" + document.toJson() + "\ninto collection " + collection.getNamespace());
		collection.insertOne(document);
		
		mongoClient.close();
	}
	
	
	
	

}
