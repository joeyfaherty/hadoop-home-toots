package mongoblog;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.FindIterable.*;
import static com.mongodb.client.model.Projections.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.print.Doc;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static java.util.Arrays.asList;

public class MongoCrudDAO {
	
	public static void main(String[] args) {
		
		// specify connection details
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		// specify database name
		MongoDatabase database = mongoClient.getDatabase("students");
		// specify collection name 
		MongoCollection<Document> collection = database.getCollection("newTestSort"); 
		
		//drop collection
		collection.drop();
		
		//create documents in collection
		for (int key = 0; key < 10; key++) {
			for (int value = 0; value < 10; value++) {
				collection.insertOne(new Document().append("key", key).append("value", value));
			}
		}
		
//		sort by student and 
//		then by score, you can 
//		iterate through and find the lowest score for each student 
//		by noticing a change in student id. 
//		As you notice that change of student_id, remove the document. 
		
		//filter collection using raw Documents
		//Bson filter = new Document("key", 0).append("value", new Document("$gt", 6).append("$lt", 8));
				
		//filter collection using the statically imported Filters class
		Bson filter = and (eq("key", 0), gt("value", 1), lt("value" ,9)) ;
		
		//projections using raw Documents
		//project the field you want in the result. 0 excludes the field. 1 includes the field
		//Bson projection = new Document("key",0);
		
		//projections using the statically imported Projections class
		Bson projection = fields(Projections.include("value"), excludeId()) ;
		
		//sort using raw Documents
		//Bson sort = new Document("value", -1);
		
		//sort using statically imported Sorts class
		Bson sort = Sorts.orderBy(Sorts.descending("value"), Sorts.descending("key"));
		
		//create new collection list
		List<Document> all = collection.find(filter)
										.projection(projection)
										.sort(sort)
										.into(new ArrayList<Document>());
		//print new collection list
		for (Document document : all) {
			System.out.println(document.toJson());
		}
		
		
//		Bson sort = new Document("key", 1);
//		List<Document> all = collection.find()
//										.projection(projection)
//										.sort()
//										.
		
//		//Finds all documents in the collection
//		Document doc = collection.find(eq("elementNo", 71)).first();
//		System.out.println(doc.toJson());
//		
//		//Atomically find a document and remove it.
//		List<Document> docs = collection.find(eq("elementNo", "123"));
		
	}
}
