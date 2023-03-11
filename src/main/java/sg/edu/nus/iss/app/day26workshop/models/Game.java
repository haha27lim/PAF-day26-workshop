package sg.edu.nus.iss.app.day26workshop.models;

import org.bson.Document;
import org.bson.types.ObjectId;


public class Game {
    
    private ObjectId id;
    private Integer gid;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer usersRated;
    private String url;
    private String image;

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getRanking() {
        return ranking;
    }
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    public Integer getUsersRated() {
        return usersRated;
    }
    public void setUsersRated(Integer usersRated) {
        this.usersRated = usersRated;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Game [id=" + id + ", gid=" + gid + ", name=" + name + ", year=" + year + ", ranking=" + ranking
                + ", usersRated=" + usersRated + ", url=" + url + ", image=" + image + "]";
    }

    // Method takes a Document object as input and returns a Game object
    public static Game create(Document d) {
        // Create a new instance of Game
        Game g = new Game();
        // Set the gid of the Game object to the integer value of the "gid" field in the Document
        g.setGid(d.getInteger("gid"));
        g.setName(d.getString("name"));
        g.setYear(d.getInteger("year"));
        g.setRanking(d.getInteger("ranking"));
        g.setUsersRated(d.getInteger("users_rated"));
        g.setUrl(d.getString("url"));
        g.setImage(d.getString("image"));
        // Return the Game object
        return g;
    }

}
