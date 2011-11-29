## Using MongoDB from Java

This is an example of using Jedis to connect to the MongoHQ service from both a generic Java application and a Spring configured application on Heroku. Read more about how to use MongoHQ in the [add-on article](http://devcenter.heroku.com/articles/mongohq).

# Using The Sample Code

Clone the repo with:

    $ git clone https://github.com/heroku/devcenter-mongo-java.git

Start up MongoDB locally and set the `MONGOHQ_URL` environment variable:

    $ export MONGOHQ_URL="mongodb://:@localhost:6379/dbname"

Build the sample:

    $ mvn package
    [INFO] Scanning for projects...
    [INFO]                                                                         
    [INFO] ------------------------------------------------------------------------
    [INFO] Building mongoSample 0.0.1-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    ...

Run it with foreman:

    $ foreman start
    18:37:42 sample.1        | started with pid 77461
    18:37:42 springsample.1  | started with pid 77462
    18:37:43 sample.1        | Setting up new mongo client for connection mongodb://heroku:94dd57798aea1b3cc5943e28efd42895@staff.mongohq.com:10055/app1797069
    18:37:43 springsample.1  | Nov 28, 2011 6:37:43 PM org.springframework.context.support.AbstractApplicationContext prepareRefresh
    18:37:43 springsample.1  | INFO: Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@182d9c06: startup date [Mon Nov 28 18:37:43 PST 2011]; root of context hierarchy
    18:37:43 springsample.1  | Nov 28, 2011 6:37:43 PM org.springframework.beans.factory.support.DefaultListableBeanFactory preInstantiateSingletons
    18:37:43 springsample.1  | INFO: Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@ac980c9: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,springConfig,getDb]; root of factory hierarchy
    18:37:43 springsample.1  | Setting up new mongo client for connection mongodb://heroku:94dd57798aea1b3cc5943e28efd42895@staff.mongohq.com:10055/app1797069
    18:37:44 springsample.1  | Collections found in DB (Spring Configuration): [system.indexes, system.users]
    18:37:44 springsample.1  | process exiting
    18:37:44 sample.1        | Collections found in DB: [system.indexes, system.users]
    18:37:44 springsample.1  | process terminated
    18:37:44 system          | sending SIGTERM to all processes


You can switch between the Java and XML based configuration by commenting out one of the two lines in `Main.java` in the `spring` sub-package:

    :::java
    public class Main {

        public static void main(String[] args) throws Exception{

            // If you want Java based configuration:
            final ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        
            // If you want XML based configuration:
            //final ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext.xml");
        
            ...

