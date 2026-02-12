package OOPGameCharacter;

import java.util.List;
import GameItem.ClothingItem;
import GameSystem.GameRNG;

public class SellerNPC extends NPC {
    private ClothingItem itemForSale;

    public SellerNPC(String name) {
        super(name);
    }

    @Override
    public void setItem(List<ClothingItem> possibleItems) {
        /*
        this.itemForSale = GameRNG.getInstance().pickRandomItem(possibleItems);
        if (this.itemForSale == null) return;

        evaluateItem(this.itemForSale);
        calculateLimit();

        this.currentOfferPrice = getStartingOffer();
        */
    }

    // --- Implement: CalculateNPC ---
    @Override
    public void calculateLimit() {
        this.absolutePriceLimit = this.estimatedBaseValue * this.greedMultiplier;
    }

    @Override
    public double getStartingOffer() {
        return this.absolutePriceLimit * 1.5;
    }

    @Override
    public void recalculatePrice(double playerProposedPrice) {
        double negotiationStepPercent = GameRNG.getInstance().genNegotiationStep();
        double adjustmentFactor = negotiationStepPercent / this.greedMultiplier;

        double priceDifference = this.currentOfferPrice - playerProposedPrice;
        double priceDecreaseAmount = priceDifference * adjustmentFactor;
        
        this.currentOfferPrice -= priceDecreaseAmount;

        if (this.currentOfferPrice < this.absolutePriceLimit) {
            this.currentOfferPrice = this.absolutePriceLimit;
        }
    }

    @Override
    public double calculateSuccessChance(double playerProposedPrice) {
        double priceDifference = this.currentOfferPrice - playerProposedPrice;
        double negotiationRange = this.currentOfferPrice - this.absolutePriceLimit;

        if (negotiationRange <= 0) return 0;
        
        double successProbability = (negotiationRange - priceDifference) / negotiationRange;

        return Math.max(0, successProbability);
    }

    @Override
    public void processOffer(double playerProposedPrice, Player player) {
        // 1. ถ้าราคาผู้เล่น OK (>= 80% ของราคาป้าย และไม่ต่ำกว่าทุน)
        if (playerProposedPrice >= this.currentOfferPrice * 0.8 && playerProposedPrice >= this.absolutePriceLimit) {
            performTransaction(player, playerProposedPrice);
            return;
        }

        // 2. ถ้าผู้เล่นต่อราคาโหดเกิน (ต่ำกว่า 40% ของทุน)
        if (playerProposedPrice <= this.absolutePriceLimit * 0.4) {
            System.out.println(characterName + ": ขาดทุน ไม่ขาย!");
            this.currentPatience = 0;
            return;
        }

        // 3. วัดดวง
        if (GameRNG.getInstance().succeedOnChance(calculateSuccessChance(playerProposedPrice))) {
            performTransaction(player, playerProposedPrice);
        } else {
            recalculatePrice(playerProposedPrice);
            decreasePatience();
        }
    }

    private void performTransaction(Player player, double finalAgreedPrice) {
        System.out.println(">> ตกลงซื้อที่ราคา " + (int) finalAgreedPrice);
        // player.deductMoney(finalAgreedPrice);
        // player.addItem(this.itemForSale);
        // this.itemForSale = null;
        this.currentPatience = 0;
    }

    private void decreasePatience() {
        this.currentPatience--;
        if (this.currentPatience <= 0) {
            System.out.println(characterName + ": ไม่ขายแล้ว รำคาญ (เดินหนี)");
        } else {
            System.out.println(characterName + ": ลดให้เหลือ " + (int) this.currentOfferPrice + " บาท เอาไหม?");
        }
    }
}