package GameSystem;
import OOPGameCharacter.*;

public class ScoreManagement {
    private Player player;
    private StatTracker balance;
    private StatTracker sellAmount;
    private StatTracker buyAmount;
    private int summarySellAmount;
    private int summaryBuyAmount;
    private int summaryBalance;

    public ScoreManagement(Player player){
        this.player = player;
        this.balance = new StatTracker(0, 0, 0);
        this.buyAmount = new StatTracker(0, 0, 0);
        this.sellAmount = new StatTracker(0, 0, 0);
    }

    public void scoreByDay(){
        int newBalance = player.getBalance();
        int newSellAmount = player.;
        int newBuyAmount = ;
    }
}
