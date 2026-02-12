package OOPGameCharacter;

import java.util.List;
import GameItem.ClothingItem;
import GameSystem.CalculateNPC;
import GameSystem.GameRNG;
import GameSystem.StatsGenerator;

public abstract class NPC implements StatsGenerator, CalculateNPC {
    protected String characterName;
    protected int appraisalLevel;          
    protected int currentPatience;
    protected double greedMultiplier;
    
    // ตัวแปรสำหรับการเจรจา
    protected double estimatedBaseValue;   
    protected double absolutePriceLimit;   
    protected double currentOfferPrice;    

    public NPC(String name) {
        this.characterName = name;
    }

    // --- Implement: StatsGenerator ---
    @Override
    public void generateStats(int week) {
        GameRNG rng = GameRNG.getInstance();
        this.appraisalLevel = rng.genKnowledgeLevel(week);
        this.greedMultiplier = rng.genGreedFactor();
        this.currentPatience = rng.genPatience();
    }

    // --- Shared Logic ---
    protected void evaluateItem(ClothingItem item) {
        this.estimatedBaseValue = item.getPerceivedPrice(this.appraisalLevel);
    }

    public abstract void setItem(List<ClothingItem> items);

    public String getName() { return characterName; }
    public double getCurrentNegotiationPrice() { return currentOfferPrice; }
}