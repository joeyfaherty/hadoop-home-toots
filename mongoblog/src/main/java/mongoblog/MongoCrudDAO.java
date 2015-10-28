package mongoblog;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

public class MongoCrudDAO {
	
	public static void main(String[] args) {
		
		// specify connection details
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		// specify database name
		MongoDatabase database = mongoClient.getDatabase("students");
		// specify collection name 
		MongoCollection<Document> collection = database.getCollection("grades"); 
		
		//filter collection using the statically imported Filters class
		Bson filter = eq("type", "homework") ;
		
		//sort using statically imported Sorts class
		Bson sort = Sorts.orderBy(Sorts.ascending("student_id"), Sorts.ascending("score"));
		
		//create new collection list
		List<Document> all = collection.find(filter)
										.sort(sort)
										.into(new ArrayList<Document>());
		int i = 0;
		//print new collection list
		for (Document document : all) {
			//collection is now sorted so we just delete every second document
			if (i%2 == 0) {
				collection.deleteOne(document);
			}
			i++;
		}
		
	}
}
