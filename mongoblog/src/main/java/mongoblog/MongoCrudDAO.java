package mongoblog;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoCrudDAO {
	
	public static void main(String[] args) {
		// specify connection details
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		
		// specify database name
		MongoDatabase database = mongoClient.getDatabase("students");
		
		// specify collection name 
		MongoCollection<Document> collection = database.getCollection("xxxxtest");  //grades
		
		//Finds all documents in the collection
		Document doc = collection.find(eq("elementNo", 71)).first();
		System.out.println(doc.toJson());
		
		//Atomically find a document and remove it.
		collection.findOneAndDelete(eq("elementNo", 71));
		
		//close the connection
		mongoClient.close();
	}
}
