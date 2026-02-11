package OOPGameCharacter;

import java.util.List;
import GameItem.ClothingItem;

public class BuyerNPC extends NPC {
    private ClothingItem wantItem;

    public BuyerNPC(String name) {
        super(name);
    }

    @Override
    public void setItem(List<ClothingItem> playerInventory) {
        if (playerInventory == null || playerInventory.isEmpty())
            return;

        // เลือกของจากกระเป๋าผู้เล่น
        // this.wantItem = Stock.random Get from Stock;

        evaluateItem(this.wantItem);
        calculateLimit();

        // เปิดราคาต่ำ (60% ของ Limit)
        this.currentNegotiationPrice = this.negotiationLimit * 0.6;
        // แสดงราคา
        // ผ่าน UI
    }

    @Override
    protected void calculateLimit() {
        // Limit คนซื้อ = ราคาสูงสุดที่จ่ายไหว
        this.negotiationLimit = this.perceivedValue / this.greedFactor;
    }

    @Override
    public void processOffer(double playerOffer, Player player) {
        // 1. ผู้เล่นยอมขายในราคาที่ NPC เสนอ (หรือถูกกว่า)
        if (playerOffer <= this.currentNegotiationPrice) {
            performTransaction(player, this.currentNegotiationPrice);
            return;
        }

        // 2. ผู้เล่นโก่งราคาเกินงบ
        if (playerOffer > this.negotiationLimit) {
            System.out.println(name + ": แพงไป ไม่มีเงินจ่าย!");
            this.patience = 0;
            return;
        }

        // 3. คำนวณความน่าจะเป็น (Gap ยิ่งน้อย ยิ่งมีโอกาสผ่าน)
        double gap = playerOffer - this.currentNegotiationPrice;
        double range = this.negotiationLimit - this.currentNegotiationPrice;
        double chance = (range > 0) ? (range - gap) / range : 0;

        if (rand.nextDouble() < chance) {
            // จ่ายเงิน
            performTransaction(player, playerOffer);
        } else {
            counterOffer(playerOffer);
        }
    }

    private void performTransaction(Player player, double finalPrice) {
        // ให้เงิน player
        // ลบของออกจาก Stock
        this.wantItem = null;
        this.patience = 0;
    }

    private void counterOffer(double playerOffer) {
        // เพิ่มราคาขึ้น 20% ของส่วนต่าง
        double increase = (playerOffer - this.currentNegotiationPrice) * 0.2;
        this.currentNegotiationPrice += increase;

        if (this.currentNegotiationPrice > this.negotiationLimit) {
            this.currentNegotiationPrice = this.negotiationLimit;
        }

        this.patience--;
        if (this.patience <= 0) {
            //ไม่ซื้อแล้ว
        } else {
            //Update UI เพิ่มราคา
            System.out.println(name + ": เพิ่มให้ได้แค่ " + (int) this.currentNegotiationPrice + " บาท ขายไหม?");
        }
    }
}