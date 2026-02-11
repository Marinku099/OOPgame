package OOPGameCharacter;

import java.util.List;
import GameItem.ClothingItem;

public class SellerNPC extends NPC {
    private ClothingItem currentStock;

    public SellerNPC(String name) {
        super(name);
    }

    @Override
    public void setItem(List<ClothingItem> possibleItems) {
        if (possibleItems == null || possibleItems.isEmpty())
            return;

        // 1. สุ่มของมาขาย
        this.currentStock = possibleItems.get(rand.nextInt(possibleItems.size()));

        // 2. คำนวณราคาต่ำสุดที่รับได้ (ทุน + กำไรขั้นต่ำ)
        calculateLimit();

        // 3. เปิดราคาขายเริ่มต้น (โขกราคา 150% ของ Limit)
        this.currentNegotiationPrice = this.negotiationLimit * 1.5;

        System.out.println("[Seller] " + name + ": ขาย " + currentStock.getName() + " ราคา "
                + (int) this.currentNegotiationPrice + " บาท สนไหม?");
    }

    @Override
    protected void calculateLimit() {
        // Limit = ราคาต่ำสุดที่จะยอมขาย
        this.negotiationLimit = this.perceivedValue * this.greedFactor;
    }

    @Override
    public double getStartingOffer() {
        return this.currentNegotiationPrice;
    }

    // Process: ผู้เล่นเสนอราคาซื้อ (Bargain)
    public void processOffer(double playerOffer, Player player) {
        // เช็คเงินผู้เล่นก่อน กันไว้ก่อน
        // if (player.getMoney() < playerOffer) {
        // System.out.println(name + ": เงินไม่พอนี่นา!");
        // return;
        // }

        // กรณี 1: ผู้เล่นยอมซื้อราคาที่ NPC เสนอ (หรือให้มากกว่า) -> ขายเลย
        if (playerOffer >= this.currentNegotiationPrice) {
            performTransaction(player, this.currentNegotiationPrice);
            return;
        }

        // กรณี 2: ผู้เล่นต่อราคาต่ำกว่าทุน (Limit) -> ไม่ขาย
        if (playerOffer < this.negotiationLimit) {
            System.out.println(name + ": ราคานี้ขาดทุน ไม่ขาย! (จบการเจรจา)");
            this.patience = 0;
            return;
        }

        // กรณี 3: วัดดวง (ยิ่งขอลดเยอะ โอกาสยิ่งน้อย)
        double gap = this.currentNegotiationPrice - playerOffer; // ส่วนต่างที่ขอลด
        double range = this.currentNegotiationPrice - this.negotiationLimit; // ช่องว่างที่ลดได้
        double chance = (range - gap) / range; // ยิ่ง gap เยอะ chance ยิ่งน้อย

        if (rand.nextDouble() < chance) {
            // สำเร็จ: ยอมลดให้ตามที่ขอ
            performTransaction(player, playerOffer);
        } else {
            // ไม่สำเร็จ: ลดราคาลงมาให้หน่อย (Counter Offer)
            counterOffer(playerOffer);
        }
    }

    private void performTransaction(Player player, double finalPrice) {
        player.deductMoney(finalPrice);
        player.addItem(this.currentStock);
        // หักเงิน player
        // ลบของออกจาก currentStock

        this.currentStock = null;
        this.patience = 0;
    }

    private void counterOffer(double playerOffer) {
        // ลดราคาลงมาหาผู้เล่น 20% ของส่วนต่าง
        double decrease = (this.currentNegotiationPrice - playerOffer) * 0.2;
        this.currentNegotiationPrice -= decrease;

        // ห้ามต่ำกว่า Limit
        if (this.currentNegotiationPrice < this.negotiationLimit) {
            this.currentNegotiationPrice = this.negotiationLimit;
        }

        this.patience--;
        if (this.patience <= 0) {
            // ไม่ขายแล้ว
        } else {
            // Update UI ลดราคา
            System.out.println(name + ": ลดสุดๆ ได้แค่ " + (int) this.currentNegotiationPrice + " บาท เอาไหม?");
        }
    }
}