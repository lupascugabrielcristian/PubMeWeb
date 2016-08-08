package pubme.requests;

public class DeletePubFromOwnerRequest {

    private String pubId;
    private String ownerId;

    public DeletePubFromOwnerRequest() {
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
