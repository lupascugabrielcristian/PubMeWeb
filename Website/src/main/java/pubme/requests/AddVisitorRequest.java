package pubme.requests;

public class AddVisitorRequest {

    private String pubId;
    private String userId;
    private int weekDayNumber;
    private int monthDayNumber;

    public AddVisitorRequest() {
    }

    public AddVisitorRequest(String pubId, int weekDayNumber, int monthDayNumber) {
        this.pubId = pubId;
        this.weekDayNumber = weekDayNumber;
        this.monthDayNumber = monthDayNumber;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWeekDayNumber() {
        return weekDayNumber;
    }

    public void setWeekDayNumber(int weekDayNumber) {
        this.weekDayNumber = weekDayNumber;
    }

    public int getMonthDayNumber() {
        return monthDayNumber;
    }

    public void setMonthDayNumber(int monthDayNumber) {
        this.monthDayNumber = monthDayNumber;
    }
}
