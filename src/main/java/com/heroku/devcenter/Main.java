package com.heroku.devcenter;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.regex.Pattern;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class Main {

	/**
	 * @param args
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, MongoException {
		
		DB db = null;
		try {
			URI mongoURI = new URI(System.getenv("MONGOHQ_URL"));
			System.out.println("Setting up new mongo client for connection " + mongoURI);
			
			Mongo mongo = new Mongo(mongoURI.getHost(), mongoURI.getPort());
			//substring is to remove leading slash in path
	        db = mongo.getDB(mongoURI.getPath().substring(1));
	        db.authenticate(mongoURI.getUserInfo().split(":",2)[0], mongoURI.getUserInfo().split(":",2)[1].toCharArray());			

		} catch (URISyntaxException e) {
			throw new RuntimeException("Mongo couldn't be configured from URL in MONGOHQ_URL env var: "+
										System.getenv("MONGOHQ_URL"));
		}

        Set<String> colls = db.getCollectionNames();
        System.out.println("Collections found in DB: " + colls.toString());
	}

}
