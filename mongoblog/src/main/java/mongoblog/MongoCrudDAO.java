package mongoblog;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class MongoCrudDAO {
	
	public static void main(String[] args) {
		
		// specify connection details
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		// specify database name
		MongoDatabase database = mongoClient.getDatabase("students");
		// specify collection name 
		MongoCollection<Document> collection = database.getCollection("grades"); 
		
//		sort by student and 
//		then by score, you can 
//		iterate through and find the lowest score for each student 
//		by noticing a change in student id. 
//		As you notice that change of student_id, remove the document. 
		
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
			if (i%2 == 0) {
				System.out.println(document.toJson());
				collection.deleteOne(document);
			}
			i++;
		}
		
		System.out.println(collection.count());
		
		


		
	}
}
