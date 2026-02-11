package OOPGameCharacter;
import java.util.List;

import GameItem.ClothingItem;

public class SellerNPC extends NPC {
    private ClothingItem currentStock; // ของที่ถืออยู่

    public SellerNPC(String name) {
        super(name);
    }

    // --- 1. ขั้นตอนรับของ (ถูกเรียกโดย Game System) ---
    public void setStock(List<ClothingItem> possibleItems) {
        if (possibleItems == null || possibleItems.isEmpty()) {
            System.out.println("Error: ไม่มีของให้ NPC ขาย");
            return;
        }
        
        // สุ่มหยิบของ 1 ชิ้นจากกอง
        this.currentStock = possibleItems.get(rand.nextInt(possibleItems.size()));
        
        // สั่งคำนวณราคาเตรียมขายทันที (เรียก Method แม่)
        prepareNegotiation(this.currentStock);
    }

    // --- 2. การขายและการส่งของ ---
    public ClothingItem inspectStock() {
        return currentStock; // ให้ผู้เล่นส่องดูของ
    }

    public ClothingItem finalizeSale() {
        ClothingItem item = this.currentStock;
        this.currentStock = null; // ขายไปแล้ว ของหมด
        return item; // ยื่นของให้ผู้เล่น
    }

    // --- 3. Logic การคำนวณ (Override จากแม่) ---

    @Override
    protected void calculateLimit() {
        // Limit ของคนขาย = ราคา "ต่ำสุด" ที่ยอมขาย
        // สูตร: ราคาที่มันเห็น * ความงก
        // (ถ้างกมาก Limit จะสูง เกือบเท่าราคาเต็ม)
        this.negotiationLimit = this.perceivedValue * this.greedFactor;
    }

    @Override
    public double getStartingOffer() {
        // เปิดราคาขาย "แพงเวอร์" ไว้ก่อน (เช่น 150% ของ Limit)
        // เพื่อเผื่อให้ผู้เล่นต่อรอง
        return this.negotiationLimit * 1.5;
    }

    @Override
    public String checkOffer(double playerOffer) {
        // 1. ถ้าต่ำกว่า Limit -> ปัดทิ้งทันที
        if (playerOffer < this.negotiationLimit) {
            patience--;
            return (patience <= 0) ? "LEAVE" : "TOO_LOW";
        }

        // 2. ถ้ามากกว่าราคาที่เสนอขายอยู่ -> ขายเลย (กำไรเห็นๆ)
        if (playerOffer >= this.currentNegotiationPrice) {
            return "ACCEPT";
        }

        // 3. วัดใจ (Dealer's Life Style)
        // ดูช่วงว่างระหว่าง (ราคาเสนอ - ลิมิต)
        double range = this.currentNegotiationPrice - this.negotiationLimit;
        if (range <= 0) return "ACCEPT"; // ป้องกัน Error

        // ดูว่าผู้เล่นเสนอมาใกล้ความจริงแค่ไหน
        double gap = playerOffer - this.negotiationLimit; 
        double chance = gap / range; // ยิ่งใกล้ราคาเสนอ ยิ่งมีโอกาสรับสูง

        // สุ่มดวง
        if (rand.nextDouble() < chance) {
            return "ACCEPT"; 
        } else {
            // ไม่รับ -> ลดราคาลงมาหา (Counter Offer)
            // ลดลง 20% ของส่วนต่าง
            double dropAmount = (this.currentNegotiationPrice - playerOffer) * 0.20;
            this.currentNegotiationPrice -= dropAmount;

            // กันไม่ให้ต่ำกว่า Limit
            if (this.currentNegotiationPrice < this.negotiationLimit) {
                this.currentNegotiationPrice = this.negotiationLimit;
            }

            patience--;
            return (patience <= 0) ? "LEAVE" : "COUNTER"; 
        }
    }
}