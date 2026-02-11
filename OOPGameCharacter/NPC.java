package OOPGameCharacter;

import java.util.List;
import java.util.Random;
import GameItem.ClothingItem;

public abstract class NPC {
    protected String name;
    protected int knowledgeLevel;
    protected int patience;
    protected double greedFactor;
    protected double perceivedValue;
    protected double negotiationLimit;        // ราคาสิ้นสุด (Limit)
    protected double currentNegotiationPrice; // ราคาปัจจุบันที่คุยกัน
    protected Random rand;

    public NPC(String name) {
        this.name = name;
        this.rand = new Random();
    }

    public void generateStats(int week) {
        this.knowledgeLevel = (week < 4) ? rand.nextInt(week) + 1 : rand.nextInt(3) + 2;
        this.greedFactor = 0.9 + (rand.nextDouble() * 0.2); // 0.9 - 1.1
        this.patience = 2 + rand.nextInt(3); 
    }

    protected void evaluateItem(ClothingItem item) {
        this.perceivedValue = item.getPerceivedPrice(this.knowledgeLevel);
    }

    // --- Abstract Methods (ต้องมีเพื่อให้ Game Loop เรียกใช้ได้) ---
    public abstract void setItem(List<ClothingItem> items); // Setup ของ
    protected abstract void calculateLimit();               // คำนวณเพดานราคา
    public abstract void processOffer(double playerOffer, Player player); // ประมวลผลดีล

    // --- Getters ---
    public String getName() { return name; }
    public double getCurrentNegotiationPrice() { return currentNegotiationPrice; }
}