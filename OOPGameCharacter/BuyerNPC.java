package OOPGameCharacter;

import java.util.List;
import GameItem.ClothingItem;
import GameSystem.GameRNG;

public class BuyerNPC extends NPC {
    private ClothingItem desiredItem;

    public BuyerNPC(String name) {
        super(name);
    }

    @Override
    public void setItem(List<ClothingItem> playerInventory) {
        // Player: ขอ List ของที่มีในตัว (Inventory) เพื่อเอามาสุ่มเลือก
        /*
        this.desiredItem = GameRNG.getInstance().pickRandomItem(playerInventory);
        if (this.desiredItem == null) return;

        evaluateItem(this.desiredItem);
        calculateLimit();

        this.currentOfferPrice = getStartingOffer();
        */
    }

    // --- ส่วนคำนวณ ---
    @Override
    public void calculateLimit() {
        this.absolutePriceLimit = this.estimatedBaseValue / this.greedMultiplier;
    }

    @Override
    public double getStartingOffer() {
        return this.absolutePriceLimit * 0.6;
    }

    @Override
    public void recalculatePrice(double playerProposedPrice) {
        double negotiationStepPercent = GameRNG.getInstance().genNegotiationStep();
        double adjustmentFactor = negotiationStepPercent / this.greedMultiplier;

        double priceDifference = playerProposedPrice - this.currentOfferPrice;
        double priceIncreaseAmount = priceDifference * adjustmentFactor;
        
        this.currentOfferPrice += priceIncreaseAmount;

        if (this.currentOfferPrice > this.absolutePriceLimit) {
            this.currentOfferPrice = this.absolutePriceLimit;
        }
    }

    @Override
    public double calculateSuccessChance(double playerProposedPrice) {
        double priceDifference = playerProposedPrice - this.currentOfferPrice;
        double negotiationRange = this.absolutePriceLimit - this.currentOfferPrice;

        if (negotiationRange <= 0) return 0;
        
        double successProbability = (negotiationRange - priceDifference) / negotiationRange;

        return Math.max(0, successProbability);
    }

    @Override
    public void processOffer(double playerProposedPrice, Player player) {
        // 1. ถ้าผู้เล่นยอมขาย (ราคาเสนอ <= 120% ของที่ NPC ให้ และไม่เกิน Limit)
        if (playerProposedPrice <= this.currentOfferPrice * 1.2 && playerProposedPrice <= this.absolutePriceLimit) {
            performTransaction(player, playerProposedPrice);
            return;
        }

        // 2. ถ้าผู้เล่นโก่งราคาเกินงบ (เกิน 140% ของ Limit)
        if (playerProposedPrice >= this.absolutePriceLimit * 1.4) {
            System.out.println(characterName + ": แพงไป ไม่มีเงินจ่าย!");
            this.currentPatience = 0;
            return;
        }

        // 3. คำนวณโอกาสสำเร็จ
        if (GameRNG.getInstance().succeedOnChance(calculateSuccessChance(playerProposedPrice))) {
            performTransaction(player, playerProposedPrice);
        } else {
            recalculatePrice(playerProposedPrice);
            decreasePatience();
        }
    }

    private void performTransaction(Player player, double finalAgreedPrice) {
        System.out.println(">> ตกลงขายที่ราคา " + (int) finalAgreedPrice);
        // player.addMoney(finalAgreedPrice);
        // player.removeItem(this.desiredItem);
        // this.desiredItem = null;
        this.currentPatience = 0;
    }

    private void decreasePatience() {
        this.currentPatience--;
        if (this.currentPatience <= 0)
            System.out.println(characterName + ": ไม่เอาแล้ว (เดินหนี)");
        else
            System.out.println(characterName + ": เพิ่มให้เป็น " + (int) this.currentOfferPrice + " บาท เอาไหม?");
    }
}