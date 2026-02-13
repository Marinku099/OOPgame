package GameSystem;

public class TimeManagement {
    private static int currentWeek = 1;
    private static int currentDay = 1;

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
}
