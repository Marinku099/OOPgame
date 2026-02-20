package GameSystem;

import java.util.List;

public class TimeManagement {
    // แก้เป็น singleton
    private static TimeManagement instance;
    private int currentWeek;
    private int currentDay;

    private TimeManagement() {
        this.currentDay = 1;
        this.currentWeek = 1;
    }

    public static TimeManagement getInstance(){
        if (instance == null) {
            instance = new TimeManagement();
        }
        return instance;
    }
    protected void updateDay(){
        currentDay++;
    }
    
    protected void updateWeek(){
        currentWeek++;
    }

    public int getDay(){
        return currentDay;
    }

    public int getWeek(){
        return currentWeek;
    }
    
    public void weeklyUpdate(List<WeeklyListener> objects){
        for (WeeklyListener listener : objects) {
            listener.weeklyAction();
        }
    }
}
