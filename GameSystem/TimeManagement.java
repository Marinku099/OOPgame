package GameSystem;

import OOPGameCharacter.GameCharacter;

public class TimeManagement {
    // แก้เป็น singleton
    private static int currentWeek = 1;
    private static int currentDay = 1;

    protected static void updateDay(){
        currentDay++;
    }
    
    protected static void updateWeek(){
        currentWeek++;
    }

    public static int getDay(){
        return currentDay;
    }

    public static int getWeek(){
        return currentWeek;
    }
    
    public void weeklyUpdate(GameCharacter chr){

    }
}
