package GameSystem;

import java.util.ArrayList;
import java.util.List;

public class TimeManagement {
    // แก้เป็น singleton
    private static TimeManagement instance;
    private List<WeeklyListener> listenersObj;
    private int currentWeek;
    private int currentDay;

    private TimeManagement() {
        this.currentDay = 1;
        this.currentWeek = 1;
        this.listenersObj = new ArrayList<>();
    }

    public static TimeManagement getInstance(){
        if (instance == null) {
            instance = new TimeManagement();
        }
        return instance;
    }

    public void addListener(WeeklyListener listener){
        listenersObj.add(listener);
    }

    public void setListener(List<WeeklyListener> listeners){
        this.listenersObj = listeners;
    }

    protected void updateDay(){
        currentDay++;
    }
    
    protected  void updateWeek(){
        currentWeek++;
        weeklyUpdate();
    }

    public int getDay(){
        return currentDay;
    }

    public int getWeek(){
        return currentWeek;
    }
    
    public void weeklyUpdate(){
        for (WeeklyListener listener : listenersObj) {
            listener.weeklyAction();
        }
    }

    public void nextDay() {
        updateDay();

        if (currentDay > 7) {
            currentDay = 1;
            nextWeek();
        }
    }

    public void nextWeek() {
        updateWeek();
    }
}
