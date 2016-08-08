package pubme.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private List<String> favoritePubs;
    private String name;

    public User() {
        favoritePubs = new ArrayList<>();
    }

    public User(String id) {
        this.id = id;
        favoritePubs = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getFavoritePubs() {
        return favoritePubs;
    }

    public void setFavoritePubs(List<String> favoritePubs) {
        this.favoritePubs = favoritePubs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addNewFavoritePub(String pubId) {
        favoritePubs.add(pubId);
    }
}
