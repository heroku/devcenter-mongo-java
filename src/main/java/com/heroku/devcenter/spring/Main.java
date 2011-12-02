package com.heroku.devcenter.spring;

import java.net.UnknownHostException;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.mongodb.DB;
import com.mongodb.MongoException;

public class Main {

	/**
	 * @param args
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, MongoException {
		System.out.println("Launching MongoDB sample application with Spring configuration.");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
		//ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext.xml");

        DB db = ctx.getBean(DB.class);
        Set<String> colls = db.getCollectionNames();
        System.out.println("Collections found in DB (Spring Configuration): " + colls.toString());
	}

}
