package com.heroku.devcenter.spring;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

	@Bean
	public MongoConfig getMongoConfig() {
		Pattern pattern = Pattern.compile("^mongodb://([^:]*):([^@]*)@([^:]*):([^/]*)/(.*)?");
		//Parse the configuration URL
        Matcher matcher = pattern.matcher(System.getenv("MONGOHQ_URL"));
        matcher.matches();
        
        MongoConfig config = new MongoConfig();
		config.setHost(matcher.group(3));
		config.setPort(Integer.parseInt(matcher.group(4)));
		config.setDbName(matcher.group(5));
		config.setUsername(matcher.group(1));
		config.setPassword(matcher.group(2));
		return config;		
	}
}
