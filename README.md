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

Now that it's built, you can run the plain Java example, or the Spring example:

* To run the plain Java example:

    $ sh target/bin/sample

* To run the Spring example:

    $ sh target/bin/spring-sample

<div class="callout" markdown="1">
Note: you can also use foreman to execute the Procfile. [Read more about foreman and procfiles](http://devcenter.heroku.com/articles/procfile).
</div>

You can switch between the Java and XML based configuration by commenting out one of the two lines in `Main.java` in the `spring` sub-package:

    :::java
    public class Main {

        public static void main(String[] args) throws Exception{

            // If you want Java based configuration:
            final ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        
            // If you want XML based configuration:
            //final ApplicationContext ctx = new GenericXmlApplicationContext("applicationContext.xml");
        
            ...
            
 # Test on Heroku

Assuming you already have a [Heroku account](http://heroku.com/signup) and have installed the [Heroku command line tool](http://devcenter.heroku.com/articles/java), you can test this sample on Heroku in a few steps.

## Create Heroku App

    $ heroku create -s cedar
    Creating quiet-waterfall-6274... done, stack is cedar
    http://quiet-waterfall-6274.herokuapp.com/ | git@heroku.com:quiet-waterfall-6274.git
    Git remote heroku added

## Add Mongo HQ Service

    $ heroku addons:add mongohq
    -----> Adding mongohq to quiet-waterfall-6274... done, v1 (free)

## Deploy Sample Using Git

    $ git push heroku master
    Counting objects: 62, done.
    Delta compression using up to 8 threads.
    Compressing objects: 100% (45/45), done.
    Writing objects: 100% (62/62), 11.52 KiB, done.
    Total 62 (delta 12), reused 0 (delta 0)
    
    -----> Heroku receiving push
    -----> Java app detected
    -----> Installing Maven 3.0.3..... done
    -----> Installing settings.xml..... done
    -----> executing /app/tmp/repo.git/.cache/.maven/bin/mvn -B -Duser.home=/tmp/build_31ktigwl5losz -Dmaven.repo.local=/app/tmp/repo.git/.cache/.m2/repository -s /app/tmp/repo.git/.cache/.m2/settings.xml -DskipTests=true clean install
           [INFO] Scanning for projects...
           [INFO]                                                                         
           [INFO] ------------------------------------------------------------------------
           [INFO] Building mongoSample 0.0.1-SNAPSHOT
           [INFO] ------------------------------------------------------------------------
           Downloading: http://s3pository.heroku.com/jvm/org/mongodb/mongo-java-driver/2.7.2/mongo-java-driver-2.7.2.pom
           Downloaded: http://s3pository.heroku.com/jvm/org/mongodb/mongo-java-driver/2.7.2/mongo-java-driver-2.7.2.pom (850 B at 2.1 KB/sec)
           Downloading: http://s3pository.heroku.com/jvm/org/springframework/spring-core/3.0.6.RELEASE/spring-core-3.0.6.RELEASE.pom
           Downloaded: http://s3pository.heroku.com/jvm/org/springframework/spring-core/3.0.6.RELEASE/spring-core-3.0.6.RELEASE.pom (3 KB at 17.6 KB/sec)
    ...

## Execute the Sample Code as One-Off Processes

The two sample apps are listed as two process types, "sample" and "springsample". They are designed to be executed as one-off processes, so you execute them with

    $ heroku run sample
    Running sample attached to terminal... up, run.1
    Launching MongoDB sample application.
    Collections found in DB: [system.indexes, system.users]

and

    $ heroku run springsample
    Running springsample attached to terminal... up, run.2
    Launching MongoDB sample application with Spring configuration.
    Dec 1, 2011 11:40:14 PM org.springframework.context.support.AbstractApplicationContext prepareRefresh
    INFO: Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@6345e044: startup date [Thu Dec 01 23:40:14 UTC 2011]; root of context hierarchy
    Dec 1, 2011 11:40:14 PM org.springframework.beans.factory.support.DefaultListableBeanFactory preInstantiateSingletons
    INFO: Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@32b0bad7: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,springConfig,getDb]; root of factory hierarchy
    Setting up new mongo client for connection mongodb://heroku:94dd57798aea1b3cc5943e28efd42895@staff.mongohq.com:10055/app1797069
    Collections found in DB (Spring Configuration): [system.indexes, system.users]
