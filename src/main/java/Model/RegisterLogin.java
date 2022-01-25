package Model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class RegisterLogin {
    String username;
    String password;

    public RegisterLogin(String username ,String password)
    {
        this.password=password;
        this.username=username;
    }

    public void Register()
    {
        MongoClient mongoClient = MongoConnection.getInstance();
        MongoDatabase database = mongoClient.getDatabase("Crypto");
        List<String> topics = new ArrayList<String>();
        List<String> articles = new ArrayList<String>();
        topics.add("BTC");
        topics.add("ETH");

        Document document = new Document();
        document.append("username", username);
        document.append("password", password);
        document.append("subscription", topics);
        document.append("ArticlesBTC", articles);
        document.append("ArticlesETH", articles);
        database.getCollection("User").insertOne(document);
    }

    public boolean Login()
    {
        boolean auth = false;
        MongoClient mongoClient = MongoConnection.getInstance();
        MongoDatabase database = mongoClient.getDatabase("Crypto");


        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("username", username);
        whereQuery.put("password", password);

        FindIterable<Document> cursor =  database.getCollection("User").find(whereQuery);

        for(Document c : cursor)
        {
            System.out.println(c.getString("username"));
            if(c.getString("username").equals(username))
                auth = true;
        }

        if(auth)
            return auth;

        return false;
    }
}
