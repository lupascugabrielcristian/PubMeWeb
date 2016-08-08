package pubme.requests;

public class FavoritePubRequest {
    private String userId;
    private String pubId;

    public FavoritePubRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }
}
