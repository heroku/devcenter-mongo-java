## Using Mongo from Java

MongoDB offers a standard Java client. In order to use this client in your project you have to declare the dependency in your build and initialize the connection from the environment variable that Heroku provides to your application.

### Add the Mongo Java driver to Your Pom.xml

Add the following dependency to your pom.xml:

    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongo-java-driver</artifactId>
        <version>2.7.2</version>
    </dependency>

### Use MongoDB in Your Application

    :::java
    Pattern pattern = Pattern.compile("^mongodb://([^:]*):([^@]*)@([^:]*):([^/]*)/(.*)?");
    //Parse the configuration URL
    Matcher matcher = pattern.matcher(System.getenv("MONGOHQ_URL"));
    matcher.matches();
    Mongo mongo = new Mongo(matcher.group(3), Integer.valueOf(matcher.group(4)));
    DB db = mongo.getDB(matcher.group(5));
    db.authenticate(matcher.group(1), matcher.group(2).toCharArray());
    //Use the db object to talk to MongoDB
    Set<String> colls = db.getCollectionNames();

### Using MongoDB with Spring

When using MongoDB with Spring you can create a bean that will hold your MongoDB configuration and then use Spring to initialize that bean:

MongoDB Configuration Bean:

    public class MongoConfig {
        private String host;
        private int port;
        private String dbName;
        private String username;
        private String password;

        //getters and setters ommitted
    }

This bean can be initialized with either Java or XML based spring configuration:

Java Configuration:

    :::java
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

or XML Configuration:

    <bean class="com.heroku.devcenter.spring.MongoConfig">
        <property name="host" value="#{systemEnvironment['MONGOHQ_URL'].replaceAll('^mongodb://([^:]*):([^@]*)@([^:]*):([^/]*)/(.*)?','$3') }"/>
        <property name="port" value="#{systemEnvironment['MONGOHQ_URL'].replaceAll('^mongodb://([^:]*):([^@]*)@([^:]*):([^/]*)/(.*)?','$4') }"/>
        <property name="dbName" value="#{systemEnvironment['MONGOHQ_URL'].replaceAll('^mongodb://([^:]*):([^@]*)@([^:]*):([^/]*)/(.*)?','$5') }"/>
        <property name="username" value="#{systemEnvironment['MONGOHQ_URL'].replaceAll('^mongodb://([^:]*):([^@]*)@([^:]*):([^/]*)/(.*)?','$1') }"/>
        <property name="password" value="#{systemEnvironment['MONGOHQ_URL'].replaceAll('^mongodb://([^:]*):([^@]*)@([^:]*):([^/]*)/(.*)?','$2') }"/>
    </bean>

DB Object Creation:

    :::java
    //for XML confiugration use GenericXmlApplicationContext
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
    MongoConfig config = ctx.getBean(MongoConfig.class);		

    Mongo mongo = new Mongo(config.getHost(), config.getPort());
    DB db = mongo.getDB(config.getDbName());
    db.authenticate(config.getUsername(), config.getPassword().toCharArray());

You can also download the [sample code](http://github.com/heroku/devcenter-mongo-java)
