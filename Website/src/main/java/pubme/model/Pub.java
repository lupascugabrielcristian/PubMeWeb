package pubme.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Pub {
    @Id
    private String id;
    private String ownerId;
    private String name;
    private String description;
    private PubType type;
    private Location location;
    private List<Promotion> promotions;
    private List<String> images;

    public Pub() {
        id = ObjectId.get().toString();
        promotions = new ArrayList<>();
        type = PubType.UNKNOWN;
        images = new ArrayList<>();
    }

    public Pub(String name) {
        id = ObjectId.get().toString();
        this.name = name;
        promotions = new ArrayList<>();
        type = PubType.UNKNOWN;
        images = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PubType getType() {
        return type;
    }

    public void setType(PubType type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int addImage(String path){
        images.add(path);
        return images.size();
    }
}
