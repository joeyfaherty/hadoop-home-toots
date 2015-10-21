package mongoblog;

import static com.mongodb.client.model.Filters.*;

import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
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
		MongoCollection<Document> collection = database.getCollection("grades"); 
		
//		//Finds all documents in the collection
//		Document doc = collection.find(eq("elementNo", 71)).first();
//		System.out.println(doc.toJson());
//		
//		//Atomically find a document and remove it.
//		List<Document> docs = collection.find(eq("elementNo", "123"));
		
		// now use a range query to get a larger subset
		Block<Document> printBlock = new Block<Document>() {
		     public void apply(final Document document) {
		         System.out.println(document.toJson());
		     }
		};
		collection.find(eq("type", "homework")).forEach(printBlock);
		
		//close the connection
		mongoClient.close();
	}
}
