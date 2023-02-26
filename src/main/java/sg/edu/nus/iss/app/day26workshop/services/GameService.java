package sg.edu.nus.iss.app.day26workshop.services;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.day26workshop.models.Game;
import sg.edu.nus.iss.app.day26workshop.repositories.GameRepository;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepo;
    
    public List<Game> searchGame(Integer limit, Integer offset) {
        return (List<Game>) gameRepo.search(limit, offset);
    }

    public List<Game> getGamesByRank(Integer limit, Integer offset) {
        return (List<Game>) gameRepo.getGamesByRank(limit, offset);
    }

    public Game getGameDetailsById(ObjectId id) {
        return gameRepo.getGameDetailsById(id);
    }
}
