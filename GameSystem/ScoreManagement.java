package GameSystem;
import Enums.OfferState;
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
        updateSummaryScore();
        this.balance.updateByDay();
        this.sellAmount.updateByDay();
        this.buyAmount.updateByDay();
    }

    private void updateSummaryScore(){
        this.summaryBalance += balance.getCurrent();
        this.summarySellAmount += sellAmount.getCurrent();
        this.summaryBuyAmount += buyAmount.getCurrent();
    }

    public void updateDeal(OfferState state, NPC npc) {
        if (state != OfferState.SUCCESS) return ;

        if (npc instanceof BuyerNPC) sellAmount.updateCurr(1);
        else buyAmount.updateCurr(1);
        balance.updateCurr(npc.getCurrentOffer());
    }

    public void printDailySummary() {
    System.out.println("----- DAILY SUMMARY -----");
    System.out.println("Balance: " + player.getBalance());
    System.out.println("Sell Amount Today: " + sellAmount.getCurrent());
    System.out.println("Buy Amount Today: " + buyAmount.getCurrent());
    System.out.println("-------------------------");
    }

    public void printWeeklySummary() {
    System.out.println("===== WEEK SUMMARY =====");
    System.out.println("Total Balance Score: " + summaryBalance);
    System.out.println("Total Sell Amount: " + summarySellAmount);
    System.out.println("Total Buy Amount: " + summaryBuyAmount);
    System.out.println("========================");
    }
    
    public StatTracker getBalance() {
        return balance;
    }

    public StatTracker getSellAmount() {
        return sellAmount;
    }

    public StatTracker getBuyAmount() {
        return buyAmount;
    }

    public int getSummarySellAmount() {
        return summarySellAmount;
    }

    public int getSummaryBuyAmount() {
        return summaryBuyAmount;
    }

    public int getSummaryBalance() {
        return summaryBalance;
    }
 
}
