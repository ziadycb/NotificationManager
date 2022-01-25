package Model;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


public class MongoConnection {

    private static MongoClient mongoClient = null;

    private MongoConnection()
    {
    }

    public static MongoClient getInstance()
    {
        if(mongoClient == null)
        {
                String connectionString = "mongodb://localhost:27017";
                mongoClient = MongoClients.create(connectionString);
        }

        return mongoClient;
    }

}
