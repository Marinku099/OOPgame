package OOPGameCharacter;

import java.util.List;
import GameItem.ClothingItem;
import GameSystem.CalculateNPC;

public abstract class NPC implements CalculateNPC {
    protected String characterName;
    
    // Stats (รับค่ามา ไม่สุ่มเอง)
    protected int appraisalLevel;
    protected int currentPatience;
    protected double greedMultiplier;

    // Variables (ค่าที่เปลี่ยนไประหว่างเล่น)
    protected double estimatedBaseValue;
    protected double absolutePriceLimit;
    protected double currentOfferPrice;

    // --- Constructor ใหม่: รับทุกอย่างเข้ามา ---
    public NPC(String name, int appraisalLevel, double greedMultiplier, int currentPatience) {
        this.characterName = name;
        this.appraisalLevel = appraisalLevel;
        this.greedMultiplier = greedMultiplier;
        this.currentPatience = currentPatience;
    }

    // --- Shared Logic ---
    protected void evaluateItem(ClothingItem item) {
        this.estimatedBaseValue = item.getPerceivedPrice(this.appraisalLevel);
    }

    public abstract void setItem(List<ClothingItem> items);

    // --- Implement Accessors ---
    @Override public double getLimit() { return this.absolutePriceLimit; }
    @Override public double getGreedMultiplier() { return this.greedMultiplier; }
    @Override public double getCurrentNegotiationPrice() { return this.currentOfferPrice; }
    @Override public void setCurrentNegotiationPrice(double price) { this.currentOfferPrice = price; }
    
    public String getName() { return characterName; }
}

/*
// อยู่ใน class ที่ใช้สร้าง NPC
public static NPC createRandomNPC(int week) {
        GameRNG rng = GameRNG.getInstance();

        // 1. สร้างชื่อ
        จาก csv

        // 2. สุ่ม Stats ข้างนอก Object
        int level = rng.genKnowledgeLevel(week);
        double greed = rng.genGreedFactor();
        int patience = rng.genPatience();
        
        // 3. สุ่มบทบาท (ซื้อ หรือ ขาย)
        if (playerInventory.isEmpty()) {  //เช็คว่า Stock player ว่างไหม
            // ถ้าของหมดเกลี้ยง -> บังคับเป็นคนขาย (Seller) เท่านั้น
            isBuyer = false; 
        } else {
            // ถ้ามีของ -> สุ่มตามปกติ (50/50 หรือแล้วแต่เรท)
            isBuyer = rng.genRandomBoolean(); 
        }

        // 4. สร้าง Object ที่สมบูรณ์แล้ว
        if (isBuyer) {
            return new BuyerNPC(name, level, greed, patience);
        } else {
            return new SellerNPC(name, level, greed, patience);
        }
    }
*/