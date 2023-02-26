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
    
    public List<Game> search(Integer limit, Integer offset) {
        final Pageable pageableRequest = PageRequest.of(offset, limit);
        Query query = new Query();
        query.with(pageableRequest);
    
        // similiar as below lambda
        List<Document> documents = mongoTemplate.find(query, Document.class, GAMES);
    
        List<Game> games = new ArrayList<>();
    
        for (Document document : documents) {
            Game game = Game.create(document);
            games.add(game);
        }
    
        return games;
    }
    
    public List<Game> getGamesByRank(Integer limit, Integer offset) {
        final Pageable pageableRequest = PageRequest.of(offset, limit);
        Query query = new Query().with(pageableRequest);
        query.with(Sort.by(Sort.Direction.ASC, "ranking"));
    
        // lambda to find all the mongo game collections sort by ranking (same as above)
        return mongoTemplate.find(query, Document.class, GAMES)
                        .stream()
                        .map(d -> Game.create(d))
                        .toList();
    }

    public Game getGameDetailsById(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Game.class, GAMES);
    }   
}
