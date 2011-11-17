package com.heroku.devcenter.spring;

import java.net.UnknownHostException;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

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
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
		//ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext.xml");
		MongoConfig config = ctx.getBean(MongoConfig.class);		

		Mongo mongo = new Mongo(config.getHost(), config.getPort());
        DB db = mongo.getDB(config.getDbName());
        db.authenticate(config.getUsername(), config.getPassword().toCharArray());
        Set<String> colls = db.getCollectionNames();
        System.out.println("Collections found in DB (Spring Configuration): " + colls.toString());
	}

}
