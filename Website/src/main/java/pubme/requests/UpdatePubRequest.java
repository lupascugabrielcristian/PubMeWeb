package pubme.requests;


import pubme.model.Pub;

public class UpdatePubRequest {
    private Pub pubToSave;

    public Pub getPubToSave() {
        return pubToSave;
    }

    public void setPubToSave(Pub pubToSave) {
        this.pubToSave = pubToSave;
    }
}
