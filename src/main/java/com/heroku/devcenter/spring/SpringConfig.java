package com.heroku.devcenter.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@Configuration
public class SpringConfig {
	
	@Bean
	public DB getDb() throws UnknownHostException, MongoException {
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
        
        return db;
	}

}
