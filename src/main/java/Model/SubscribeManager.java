package Model;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubscribeManager {
    String username;
    String password;
    MongoClient mongoClient;

    public SubscribeManager(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public List<String> returnSub() {
        List<String> sub = null;
        mongoClient = MongoConnection.getInstance();
        MongoDatabase database = mongoClient.getDatabase("Crypto");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("username", username);
        whereQuery.put("password", password);

        FindIterable<Document> cursor = database.getCollection("User").find(whereQuery);

        for (Document c : cursor) {
            sub = c.getList("subscription", String.class);
        }

        return sub;
    }

    public List<String> returnArticles(String coin) {
        List<String> sub = null;
        mongoClient = MongoConnection.getInstance();
        MongoDatabase database = mongoClient.getDatabase("Crypto");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("username", username);
        whereQuery.put("password", password);

        FindIterable<Document> cursor = database.getCollection("User").find(whereQuery);

        for (Document c : cursor) {
            sub = c.getList("Articles"+coin, String.class);
        }

        return sub;
    }

    public void insertArticle(String newSub,String article)
    {
        MongoDatabase database = mongoClient.getDatabase("Crypto");

        database.getCollection("User").updateOne(
                Filters.eq("username", username),
                new Document().append(
                        "$push",
                        new Document("Articles"+newSub, article)
                )
        );
        ;
    }

    public void removeArticle(String newSub)
    {
        MongoDatabase database = mongoClient.getDatabase("Crypto");

        database.getCollection("User").updateOne(
                Filters.eq("username", username),
                new Document().append(
                        "$pop",
                        new Document("Articles"+newSub, -1)
                )
        );
        ;
    }

    public void manageSub(String newSub, boolean add) {
        MongoDatabase database = mongoClient.getDatabase("Crypto");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("username", username);
        whereQuery.put("password", password);
        List<String> sub = new ArrayList<String>();

        if (add) {
            database.getCollection("User").updateOne(
                    Filters.eq("username", username),
                    new Document().append(
                            "$push",
                            new Document("subscription", newSub)
                    )

            );
            ;
            database.getCollection("User").updateOne(
                    Filters.eq("username", username),
                    new Document().append(
                            "$set",
                            new Document("Articles"+newSub, sub)
                    )
            );
            ;
        } else {
            database.getCollection("User").updateOne(
                    Filters.eq("username", username),
                    new Document().append(
                            "$pull",
                            new Document("subscription", newSub)
                    )
            );
            ;
            database.getCollection("User").updateOne(
                    Filters.eq("username", username),
                    new Document().append(
                            "$unset",
                            new Document("Articles"+newSub, 1)
                    )
            );
            ;
        }
    }
}

