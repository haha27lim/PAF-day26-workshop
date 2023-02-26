package sg.edu.nus.iss.app.day26workshop.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.app.day26workshop.models.Game;
import sg.edu.nus.iss.app.day26workshop.services.GameService;


@RestController
public class GameController {
    
    @Autowired
    private GameService gameService;

    @GetMapping(path="/games", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> listGamesByName(@RequestParam(required=false, defaultValue = "25") int limit,
            @RequestParam(required=false, defaultValue = "0") int offset) {

    List<Game> results = gameService.searchGame(limit, offset);

    // Build the response object
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();

    // Add games array
    JsonArrayBuilder gamesBuilder = Json.createArrayBuilder();
    for (Game game : results) {
        JsonObjectBuilder gameBuilder = Json.createObjectBuilder();
        gameBuilder.add("game_id", game.getGid().toString());
        gameBuilder.add("name", game.getName());
        gamesBuilder.add(gameBuilder.build());
    }
    objBuilder.add("games", gamesBuilder.build());

    // Add metadata
    objBuilder.add("offset", offset);
    objBuilder.add("limit", limit);
    objBuilder.add("total", results.size());
    objBuilder.add("timestamp", Instant.now().toString());

    JsonObject result = objBuilder.build();

    return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }


    @GetMapping(path="/games/rank", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> listGamesByRank(@RequestParam(required=false, defaultValue = "25") int limit,
            @RequestParam(required=false, defaultValue = "0") int offset) {

    List<Game> results = gameService.getGamesByRank(limit, offset);
    
    // Build the response object
    JsonObjectBuilder objBuilder = Json.createObjectBuilder();

    // Add games array
    JsonArrayBuilder gamesBuilder = Json.createArrayBuilder();
    for (Game game : results) {
        JsonObjectBuilder gameBuilder = Json.createObjectBuilder();
        gameBuilder.add("game_id", game.getGid().toString());
        gameBuilder.add("name", game.getName());
        gameBuilder.add("ranking", game.getRanking());
        gamesBuilder.add(gameBuilder.build());
    }
    objBuilder.add("games", gamesBuilder.build());

    // Add metadata
    objBuilder.add("offset", offset);
    objBuilder.add("limit", limit);
    objBuilder.add("total", results.size());
    objBuilder.add("timestamp", Instant.now().toString());

    JsonObject result = objBuilder.build();

    return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }


    @GetMapping(path = "/game/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getGameDetailsById(@PathVariable ObjectId id) {
        
        JsonObject result = null;
        Game gameDetails = gameService.getGameDetailsById(id);
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();

        objBuilder.add("game_id", gameDetails.getGid().toString());
        objBuilder.add("name", gameDetails.getName());
        objBuilder.add("year", gameDetails.getYear());
        objBuilder.add("ranking", gameDetails.getRanking());
        objBuilder.add("users_rated", gameDetails.getUsersRated() != null ? gameDetails.getUsersRated() : 0);
        objBuilder.add("url", gameDetails.getUrl());
        objBuilder.add("thumbnail", gameDetails.getImage());
        objBuilder.add("timestamp", Instant.now().toString());
        result = objBuilder.build();

        if (result == null) {
                // return a 404 Not Found response if the Game_Id does not exist
                return new ResponseEntity<>("Game ID " + id + " not found", HttpStatus.NOT_FOUND);
            } // return the customer with a 200 OK response
            return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }
    
}

