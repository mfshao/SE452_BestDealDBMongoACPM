import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBDataStoreUtilities {
	static MongoCollection<Document> customerReviewsCollection;
	static MongoClient mongo = null;

	public static void getConnection() {
		if (mongo == null) {
			mongo = new MongoClient("localhost", 27017);

			MongoDatabase db = mongo.getDatabase("CustomerReviews");
			customerReviewsCollection = db.getCollection("customerReviewsCollection");
		}
	}

	public static void closeConnection() {
		if (mongo != null) {
			mongo.close();
			mongo = null;
		}
	}
	
	public static void initalizeDatabase() {
		getConnection();
		customerReviewsCollection.drop();
		customerReviewsCollection = mongo.getDatabase("CustomerReviews").getCollection("customerReviewsCollection");
		ArrayList<Product> productList = SaxParserDataStore.getAllProducts();
		Random rand = new Random(System.currentTimeMillis());
		for (Product p : productList) {
			int times = rand.nextInt(4);
			while (times > 0) {
				ItemReview ir = ItemReview.generateRandomReview(p);
				insertItemReview(ir);
				times--;
			}
		}
		closeConnection();
	}

	public static void insertItemReview(ItemReview itemReview) throws com.mongodb.MongoSocketOpenException {
		getConnection();
		Document doc = new Document(itemReview.getMap());
		customerReviewsCollection.insertOne(doc);
		closeConnection();
	}

	public static ArrayList<ItemReview> getItemReviewsByProduct(Product p) {
		ArrayList<ItemReview> itemReviewList = new ArrayList<>();

		getConnection();
		BasicDBObject query = new BasicDBObject();
		query.put("productID", new BasicDBObject("$eq", p.getId()));
		FindIterable<Document> documentList = customerReviewsCollection.find(query);
		documentList.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				String retailerZip = document.getString("retailerZip");
				String retailerCity = document.getString("retailerCity");
				String retailerState = document.getString("retailerState");
				String userName = document.getString("userName");
				int userAge = document.getInteger("userAge");
				String userGender = document.getString("userGender");
				String userOccupation = document.getString("userOccupation");
				int reviewRating = document.getInteger("reviewRating");
				String reviewDate = document.getString("reviewDate");
				String reviewText = document.getString("reviewText");
				
				itemReviewList.add(new ItemReview(p, retailerZip, retailerCity, retailerState, userName, userAge, userGender, userOccupation, reviewRating, reviewDate, reviewText));
			}
		});
		closeConnection();
		return itemReviewList;
	}
	
	public static ArrayList<Entry<String, Double>> getTopFiveMostLikedProducts() {
		getConnection();
		
		ArrayList<Entry<String, Double>> resultList = new ArrayList<>();
		
		BasicDBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("_id", "$productName");
		groupFields.put("average", new BasicDBObject( "$avg", "$reviewRating"));
		BasicDBObject group = new BasicDBObject("$group", groupFields);		
		BasicDBObject sort = new BasicDBObject();
		sort.put("average", -1);
		BasicDBObject orderby = new BasicDBObject("$sort", sort);
		BasicDBObject limit = new BasicDBObject("$limit", 5);
		
		List<BasicDBObject> operationList = new LinkedList<>();
		operationList.add(group);
		operationList.add(orderby);
		operationList.add(limit);
		
		AggregateIterable<Document> ao = customerReviewsCollection.aggregate(operationList);
		
		for (Document doc : ao ) {
			String productName = doc.getString("_id");
			Double averageRating = Double.parseDouble(new DecimalFormat("#.0").format(doc.getDouble("average")));
			
			Entry<String, Double> entry = new AbstractMap.SimpleEntry<String, Double>(productName, averageRating);
			resultList.add(entry);
		}
		
		closeConnection();
		return resultList;
	}

	public static ArrayList<Entry<LinkedList<String>, Integer>> getTopFiveMostSoldZipcodes() {
		getConnection();
		
		ArrayList<Entry<LinkedList<String>, Integer>> resultList = new ArrayList<>();
		
		BasicDBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("_id", "$retailerZip");
		groupFields.put("retailerCity", new BasicDBObject("$first", "$retailerCity"));
		groupFields.put("retailerState", new BasicDBObject("$first", "$retailerState"));
		groupFields.put("count",new BasicDBObject("$sum", 1));
		BasicDBObject group = new BasicDBObject("$group", groupFields);	
		BasicDBObject sort = new BasicDBObject();
		sort.put("count", -1);
		BasicDBObject orderby = new BasicDBObject("$sort", sort);	
		BasicDBObject limit = new BasicDBObject("$limit", 5);
		
		List<BasicDBObject> operationList = new LinkedList<>();
		operationList.add(group);	
		operationList.add(orderby);
		operationList.add(limit);
		
		AggregateIterable<Document> ao = customerReviewsCollection.aggregate(operationList);
		
		for (Document doc : ao ) {
			String retailerZip = doc.getString("_id");
			String retailerCity = doc.getString("retailerCity");
			String retailerState = doc.getString("retailerState");
			LinkedList<String> reatilerList = new LinkedList<>();
			reatilerList.add(retailerZip);
			reatilerList.add(retailerCity);
			reatilerList.add(retailerState);
			Integer count = doc.getInteger("count");
			
			Entry<LinkedList<String>, Integer> entry = new AbstractMap.SimpleEntry<LinkedList<String>, Integer>(reatilerList, count);
			resultList.add(entry);
		}
		
		closeConnection();
		return resultList;
	}
	
	public static ArrayList<Entry<String, Integer>> getTopFiveMostSoldProducts() {
		getConnection();
		
		ArrayList<Entry<String, Integer>> resultList = new ArrayList<>();
		
		BasicDBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("_id", "$productName");
		groupFields.put("count", new BasicDBObject( "$sum", 1));
		BasicDBObject group = new BasicDBObject("$group", groupFields);		
		BasicDBObject sort = new BasicDBObject();
		sort.put("count", -1);
		BasicDBObject orderby = new BasicDBObject("$sort", sort);
		BasicDBObject limit = new BasicDBObject("$limit", 5);
		
		List<BasicDBObject> operationList = new LinkedList<>();
		operationList.add(group);
		operationList.add(orderby);
		operationList.add(limit);
		
		AggregateIterable<Document> ao = customerReviewsCollection.aggregate(operationList);
		
		for (Document doc : ao ) {
			String productName = doc.getString("_id");
			Integer count = new Integer(doc.getInteger("count"));
			
			Entry<String, Integer> entry = new AbstractMap.SimpleEntry<String, Integer>(productName, count);
			resultList.add(entry);
		}
		
		closeConnection();
		return resultList;
	}
}
