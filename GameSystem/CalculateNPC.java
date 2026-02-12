package GameSystem;

import OOPGameCharacter.Player;

public interface CalculateNPC {

    // --- Accessors: ต้องดึงค่าพวกนี้มาใช้ใน Default Method ---
    boolean isBuyer();
    double getLimit();
    double getGreedMultiplier();            // เพิ่ม: ดึงค่าความงก
    double getCurrentNegotiationPrice();    // เพิ่ม: ดึงราคาปัจจุบัน
    void setCurrentNegotiationPrice(double price); // เพิ่ม: สั่งแก้ราคาปัจจุบัน

    // --- 1. Default Method: ราคาเปิด ---
    default double getStartingOffer() {
        if (isBuyer()) {
            return getLimit() * 0.6;
        } else {
            return getLimit() * 1.5;
        }
    }

    // --- 2. Default Method: คำนวณโอกาสสำเร็จ ---
    default double calculateSuccessChance(double playerProposedPrice) {
        double current = getCurrentNegotiationPrice();
        double limit = getLimit();

        double negotiationRange = Math.abs(limit - current);
        

        double priceDifference = Math.abs(playerProposedPrice - current);

        if (negotiationRange <= 0) return 0;

        double chance = (negotiationRange - priceDifference) / negotiationRange;
        return Math.max(0, chance);
    }

    // --- 3. Default Method: คิดราคาใหม่ ---
    default void recalculatePrice(double playerProposedPrice) {
        // ใช้ Math.abs หาความต่างราคา
        double priceDifference = Math.abs(getCurrentNegotiationPrice() - playerProposedPrice);
        
        // คำนวณ Step การขยับราคา
        double negotiationStepPercent = GameRNG.getInstance().genNegotiationStep();
        double adjustmentFactor = negotiationStepPercent / getGreedMultiplier();
        double changeAmount = priceDifference * adjustmentFactor;

        double current = getCurrentNegotiationPrice();
        double limit = getLimit();
        double newPrice;

        if (isBuyer()) {
            // คนซื้อ: เพิ่มราคาขึ้น (แต่ไม่เกิน Limit)
            newPrice = current + changeAmount;
            if (newPrice > limit) newPrice = limit;
        } else {
            // คนขาย: ลดราคาลง (แต่ไม่ต่ำกว่า Limit)
            newPrice = current - changeAmount;
            if (newPrice < limit) newPrice = limit;
        }

        setCurrentNegotiationPrice(newPrice);
    }

    // --- Abstract Methods: ---
    void processOffer(double playerOffer, Player player);
}