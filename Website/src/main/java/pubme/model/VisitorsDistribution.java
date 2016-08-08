package pubme.model;


import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VisitorsDistribution {
    @Id
    private String id;
    private String pubId;
    private int totalVisitors;
    private int visitorsToday;
    private int visitorsThisWeek;
    private int visitorsThisMonth;
    private HashMap<Integer, Integer> weekDistribution;
    private HashMap<Integer, Integer> monthDistribution;
    private int lastMonthDay;
    private int lastWeekDay;
    private List<String> visitorsTodayIds;

    public VisitorsDistribution() {
        weekDistribution = new HashMap<>();
        for (int i = 1; i < 8; i++){
            weekDistribution.put(i, 0);
        }

        monthDistribution = new HashMap<>();
        for (int i = 1; i < 30; i++){
            monthDistribution.put(i, 0);
        }

        visitorsTodayIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public int getTotalVisitors() {
        return totalVisitors;
    }

    public void setTotalVisitors(int totalVisitors) {
        this.totalVisitors = totalVisitors;
    }

    public int getVisitorsToday() {
        return visitorsToday;
    }

    public void setVisitorsToday(int visitorsToday) {
        this.visitorsToday = visitorsToday;
    }

    public int getVisitorsThisWeek() {
        return visitorsThisWeek;
    }

    public void setVisitorsThisWeek(int visitorsThisWeek) {
        this.visitorsThisWeek = visitorsThisWeek;
    }

    public int getVisitorsThisMonth() {
        return visitorsThisMonth;
    }

    public void setVisitorsThisMonth(int visitorsThisMonth) {
        this.visitorsThisMonth = visitorsThisMonth;
    }

    public int getLastMonthDay() {
        return lastMonthDay;
    }

    public void setLastMonthDay(int lastMonthDay) {
        this.lastMonthDay = lastMonthDay;
    }

    public int getLastWeekDay() {
        return lastWeekDay;
    }

    public void setLastWeekDay(int lastWeekDay) {
        this.lastWeekDay = lastWeekDay;
    }

    public HashMap<Integer, Integer> getWeekDistribution() {
        return weekDistribution;
    }

    public HashMap<Integer, Integer> getMonthDistribution() {
        return monthDistribution;
    }

    public List<String> getVisitorsTodayIds() {
        return visitorsTodayIds;
    }

    public void addVisitor(int dayOfWeek, int dayOfMonth, String userId){
        if (dayOfWeek == 0){
            dayOfWeek = 7;
        }

        if (dayOfMonth < lastMonthDay){
            resetMonth();
        }

        else if (dayOfMonth != lastMonthDay){
            resetDay();
        }

        else if (dayOfWeek < lastWeekDay) {
            resetWeek();
        }

        lastWeekDay = dayOfWeek;
        lastMonthDay = dayOfMonth;
        this.totalVisitors++;
        this.visitorsToday++;
        this.visitorsThisWeek++;
        this.visitorsThisMonth++;

        int weekDistributionToIncrease = weekDistribution.get(dayOfWeek);
        weekDistribution.put(dayOfWeek, weekDistributionToIncrease + 1);

        int monthDistributionToIncrease = monthDistribution.get(dayOfMonth);
        monthDistribution.put(dayOfMonth, monthDistributionToIncrease + 1);

        visitorsTodayIds.add(userId);
    }

    public void resetDay() {
        visitorsToday = 0;
        visitorsTodayIds = new ArrayList<>();
    }

    public void resetWeek() {
        visitorsToday = 0;
        visitorsThisWeek = 0;
        for (int i = 1; i < 8; i++){
            weekDistribution.put(i, 0);
        }
    }

    public void resetMonth() {
        resetDay();
        resetWeek();
        visitorsThisMonth = 0;

        for (int i = 1; i < 8; i++){
            weekDistribution.put(i, 0);
        }

        for (int i = 1; i < 30; i++){
            monthDistribution.put(i, 0);
        }
    }
}
