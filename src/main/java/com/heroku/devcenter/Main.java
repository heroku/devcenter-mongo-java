package com.heroku.devcenter;

import java.net.UnknownHostException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class Main {

    protected static final Pattern HEROKU_MONGO_URL_PATTERN = Pattern.compile("^mongodb://([^:]*):([^@]*)@([^:]*):([^/]*)/(.*)?");
    
	/**
	 * @param args
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, MongoException {
		
		//Parse the configuration URL
        Matcher matcher = HEROKU_MONGO_URL_PATTERN.matcher(System.getenv("MONGOHQ_URL"));
        matcher.matches();
        
        Mongo mongo = new Mongo(matcher.group(3), Integer.valueOf(matcher.group(4)));
        DB db = mongo.getDB(matcher.group(5));
        db.authenticate(matcher.group(1), matcher.group(2).toCharArray());
        Set<String> colls = db.getCollectionNames();
        System.out.println("Collections found in DB: " + colls.toString());
	}

}
