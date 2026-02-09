public class SellerNPC extends NPC {
    // 🔥 Single Transaction: มีของขายแค่ชิ้นเดียว
    private ClothingItem currentStock;

    public SellerNPC(String name) {
        super(name);
        //ตอนนี้ขาดฟังชั่นว่าจะขายอะไร
        //evaluateItem(item จาก list) จะได้รู้ราคาก่อนเอาไปคำนวณ Limit
    }

    // --- 1. ส่งของให้ผู้เล่นดู (Inspect) ---
    public ClothingItem inspectStock() {
        return currentStock;
    }

    // --- 2. ฟังก์ชันขายสำเร็จ (Transaction) ---
    // เรียกเมื่อตกลงราคากันได้แล้ว
    public ClothingItem finalizeSale() {
        ClothingItem item = this.currentStock;
        this.currentStock = null; // ของหมด
        return item;
    }

    // ---  Abstract Implementation  ---

    @Override
    protected void calculateLimit() {
        // Seller Limit: ราคา "ต่ำสุด" ที่ยอมขาย
        // ยิ่ง Greed เยอะ Limit ยิ่งสูง (ขายแพง)
        this.negotiationLimit = this.perceivedValue * this.greedFactor;
    }

    @Override
    public double getStartingOffer() {
        // เปิดราคามา "แพงเวอร์" ไว้ก่อน (เช่น 150% ของที่อยากได้)
        return this.negotiationLimit * 1.5;
    }

    @Override
    public String checkOffer(double offer) {
        // คนขาย: ชอบราคา "มากกว่า หรือ เท่ากับ" ลิมิต
        if (offer >= this.negotiationLimit) {
            return "ACCEPT";
        }

        patience--;
        if (patience <= 0)
            return "LEAVE"; // รำคาญ ไม่ขายแล้ว

        return "REJECT"; // ถูกไป ขอเพิ่มอีก
    }
}