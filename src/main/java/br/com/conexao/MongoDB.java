package br.com.conexao;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.MongoClient;

@SuppressWarnings({ "deprecation" })
public class MongoDB {
	
	private static DB database;
	private static MongoClient mongoClient;
	
	static {
		mongoClient = new MongoClient("localhost", 27017); 
		database = mongoClient.getDB("comanda");
	}
	
	public static MongoCollection getCollection(String nameOfCollection) {
		return new Jongo(MongoDB.database).getCollection(nameOfCollection);
	}
	
	public static DB getDB() {
		return MongoDB.database;
	}
	
	public static MongoClient getMongoClient() {
		return MongoDB.mongoClient;
	}
	
}
