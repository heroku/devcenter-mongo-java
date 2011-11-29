## Using Mongo from Java

MongoDB offers a standard Java client. In order to use this client in your project you have to declare the dependency in your build and initialize the connection from the environment variable that Heroku provides to your application.

### Add the Mongo Java driver to Your Pom.xml

Add the following dependency to your pom.xml:

    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongo-java-driver</artifactId>
        <version>2.7.2</version>
    </dependency>

### Use MongoDB in your application

    :::java
    URI mongoURI = new URI(System.getenv("MONGOHQ_URL"));
    
    Mongo mongo = new Mongo(mongoURI.getHost(), mongoURI.getPort());
    //substring is to remove leading slash in path
    db = mongo.getDB(mongoURI.getPath().substring(1));
    db.authenticate(mongoURI.getUserInfo().split(":",2)[0], mongoURI.getUserInfo().split(":",2)[1].toCharArray());    
    //Use the db object to talk to MongoDB
    Set<String> colls = db.getCollectionNames();

### Using MongoDB with Spring

Use the following Java Configuration class to set up a `DB` object as a singleton Spring bean:

    :::java
    @Configuration
    public class SpringConfig {
        @Bean
        public DB getDb() throws UnknownHostException, MongoException {
            DB db = null;
            try {
                URI mongoURI = new URI(System.getenv("MONGOHQ_URL"));
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

or the following XML configuration file:

    :::xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:context="http://www.springframework.org/schema/context"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
          http://www.springframework.org/schema/context
          http://www.springframework.org/schema/context/spring-context-3.0.xsd">
    
        <context:annotation-config/>
        <context:property-placeholder/>
      
        <bean id="mongoURI" class="java.net.URI">
          <constructor-arg value="${MONGOHQ_URL}"/>
        </bean>
    
         <bean id="mongo" class="com.mongodb.Mongo">
           <constructor-arg index="0" value="#{ @mongoURI.getHost() }"/>
           <constructor-arg index="1" value="#{ @mongoURI.getPort() }"/>
         </bean>
    
        <!-- create db object by calling getDB on mongo bean -->
        <bean id="db" class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
          <property name="targetObject"><ref local="mongo"/></property>
          <property name="targetMethod"><value>getDB</value></property>
          <property name="arguments">
            <list>
              <value>#{ @mongoURI.getPath().substring(1) }</value>
            </list>
          </property>
        </bean>  
     
        <!-- call authenticate on db object -->
        <bean id="authenticateDB"
            class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
            <property name="targetObject"><ref local="db" /></property>
            <property name="targetMethod">
                <value>authenticate</value>
            </property>
            <property name="arguments">
                <list>
                    <value>#{ @mongoURI.getUserInfo().split(':',2)[0] }</value>
                    <value>#{ @mongoURI.getUserInfo().split(':',2)[1] }</value>
                </list>
            </property>
        </bean>
    </beans>
    
<div>MethodInvokingFactoryBean is used to initialize beans using methods other than constructors and setters</div>

### Sample code

To see a complete, working example, check out the [sample code in github](https://github.com/heroku/devcenter-mongo-java). The [readme](https://github.com/heroku/devcenter-mongo-java/blob/master/README.md) explains more about the example.
