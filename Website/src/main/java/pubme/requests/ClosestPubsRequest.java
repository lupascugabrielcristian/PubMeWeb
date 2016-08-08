package pubme.requests;

public class ClosestPubsRequest {

    private double latitude;
    private double longitude;
    private int initalIndex;
    private int batchSize;

    public ClosestPubsRequest() {
    }

    public ClosestPubsRequest(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        initalIndex = 0;
        batchSize = 5;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getInitalIndex() {
        return initalIndex;
    }

    public void setInitalIndex(int initalIndex) {
        this.initalIndex = initalIndex;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
