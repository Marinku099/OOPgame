public class BuyerNPC extends NPC {
    private ClothingItem wantItem;

    public BuyerNPC(String name) {
        super(name);
    }

    // คนซื้อไม่ต้องมี setStock เพราะรอซื้อจากผู้เล่น

    public void finalizePurchase(ClothingItem item) {
        this.wantItem = item;
        System.out.println(name + ": ดีล! ขอบคุณครับ");
    }

    // --- Logic การคำนวณ ---

    @Override
    protected void calculateLimit() {
        // Limit ของคนซื้อ = ราคา "สูงสุด" ที่ยอมจ่าย
        // สูตร: ราคาที่มันเห็น / ความงก
        // (ถ้างกมาก ตัวหารเยอะ Limit จะต่ำเตี้ยเรี่ยดิน)
        this.negotiationLimit = this.perceivedValue / this.greedFactor;
    }

    @Override
    public double getStartingOffer() {
        // เปิดราคารับซื้อ "ถูกๆ" (เช่น 60% ของ Limit)
        // กดราคาผู้เล่นก่อน
        return this.negotiationLimit * 0.6;
    }

    @Override
    public String checkOffer(double playerOffer) {
        // 1. ถ้าแพงกว่า Limit -> ปัดทิ้ง (จ่ายไม่ไหว/ไม่คุ้ม)
        if (playerOffer > this.negotiationLimit * (this.greedFactor * 10)) {
            patience--;
            return (patience <= 0) ? "LEAVE" : "TOO_HIGH";
        }

        // 2. ถ้าถูกกว่าราคาที่เสนอซื้อ -> ซื้อเลย (ของดีราคาถูก)
        if (playerOffer <= this.currentNegotiationPrice) {
            return "ACCEPT";
        }

        // 3. วัดใจ
        double range = this.negotiationLimit - this.currentNegotiationPrice;
        if (range <= 0)
            return "ACCEPT";

        // ยิ่งผู้เล่นลดราคาลงมาใกล้ Limit ยิ่งมีโอกาสขายออก
        double gap = this.negotiationLimit - playerOffer;
        double chance = gap / range;

        if (rand.nextDouble() < chance) {
            return "ACCEPT";
        } else {
            // ไม่รับ -> เพิ่มราคาขึ้นไปหา (Counter Offer)
            // เพิ่มให้ 20% ของส่วนต่าง
            double increaseAmount = (playerOffer - this.currentNegotiationPrice) * 0.20;
            this.currentNegotiationPrice += increaseAmount;

            // ห้ามเกิน Limit
            if (this.currentNegotiationPrice > this.negotiationLimit) {
                this.currentNegotiationPrice = this.negotiationLimit;
            }

            patience--;
            return (patience <= 0) ? "LEAVE" : "COUNTER";
        }
    }

    @Override
    public void setItem() {
        // 1. ดึงของจาก Stock
        //ClothingItem item = Stock.get;
        
        // 2. ตั้งค่าและคำนวณราคาขายทันที
        //this.wantItem = item;
        prepareNegotiation(this.wantItem);
    }
}