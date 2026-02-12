package GameSystem;

public class ScoreManagement {
    private StatTracker balance;
    private StatTracker sellAmount;
    private StatTracker buyAmount;

    public ScoreManagement(){
        this.balance = new StatTracker(0, 0, 0);
        this.buyAmount = new StatTracker(0, 0, 0);
        this.sellAmount = new StatTracker(0, 0, 0);
    }
}
