package sg.edu.nus.iss.app.day26workshop.repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.day26workshop.models.Game;

import static sg.edu.nus.iss.app.day26workshop.Constants.*;

@Repository
public class GameRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    // Method searches the MongoDB collection for games and returns a list of Game objects based on the given limit and offset values
    public List<Game> search(Integer limit, Integer offset) {
        // Create a Pageable object with the given offset and limit
        final Pageable pageableRequest = PageRequest.of(offset, limit);
        // Create a new Query object
        Query query = new Query();
        // Add the Pageable object to the Query object
        query.with(pageableRequest);
    
        // similiar as below lambda
        // Retrieve the games from the MongoDB collection using mongoTemplate and the Query object
        List<Document> documents = mongoTemplate.find(query, Document.class, GAMES);
    
        // Create a new list to hold the Game objects
        List<Game> games = new ArrayList<>();
    
        // Loop through the retrieved documents and create Game objects for each
        for (Document document : documents) {
            Game game = Game.create(document);
            games.add(game);
        }
        // Return the list of Game objects
        return games;
    }

    // Method retrieves games from the MongoDB collection sorted by ranking and returns a list of Game objects based on the given limit and offset values
    public List<Game> getGamesByRank(Integer limit, Integer offset) {
        // Create a Pageable object with the given offset and limit
        final Pageable pageableRequest = PageRequest.of(offset, limit);
        // Create a new Query object and add the Pageable object to it
        Query query = new Query().with(pageableRequest);
        // Add a sorting condition to the Query object based on the ranking field in ascending order
        query.with(Sort.by(Sort.Direction.ASC, "ranking"));
    
        // lambda to find all the mongo game collections sort by ranking (same as above)
        // Retrieve the games from the MongoDB collection using mongoTemplate and the Query object
        return mongoTemplate.find(query, Document.class, GAMES)
                        .stream()
                        // Map each retrieved document to a Game object using the create() method in the Game class
                        .map(d -> Game.create(d))
                        // Convert the stream of Game objects to a List and return it
                        .toList();
    }

    // Method retrieves the details of a game from the MongoDB collection based on the given ID
    public Game getGameDetailsById(ObjectId id) {
        // Create a new Query object and add a criteria to match the ID field with the given ID value
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        // Retrieve the matching game from the MongoDB collection using mongoTemplate and the Query object
        return mongoTemplate.findOne(query, Game.class, GAMES);
    }   
}
