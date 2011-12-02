package com.heroku.devcenter;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

public class Main {

	/**
	 * @param args
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws MongoException, UnknownHostException {
		System.out.println("Launching MongoDB sample application.");
		
		MongoURI mongoURI = new MongoURI(System.getenv("MONGOHQ_URL"));
        DB db = mongoURI.connectDB();
        db.authenticate(mongoURI.getUsername(), mongoURI.getPassword());		

        Set<String> colls = db.getCollectionNames();
        System.out.println("Collections found in DB: " + colls.toString());
	}

}
