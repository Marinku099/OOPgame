package OOPGameCharacter;

import java.util.List;
import GameItem.ClothingItem;
import GameSystem.CalculateNPC;

public abstract class NPC extends GameCharacter implements CalculateNPC {
    protected int patience;
    protected double greed;

    protected double estimatedValue;
    protected double limitPrice;
    protected double currentOffer;

    public NPC(String name, int knowledge, double greed, int patience) {
        super(name, knowledge);
        this.greed = greed;
        this.patience = patience;
    }

    protected void inspectItem(ClothingItem item) {
        this.estimatedValue = item.getPerceivedPrice(this.knowledge);
    }

    public abstract void chooseItem(List<ClothingItem> items);

    @Override 
    public double getLimit() { return this.limitPrice; }
    
    @Override 
    public double getGreed() { return this.greed; }

    @Override 
    public double getCurrentOffer() { return this.currentOffer; }
    
    @Override 
    public void setCurrentOffer(double price) { this.currentOffer = price; }
    
    public String getName() { return name; }
}

/*
public static NPC createRandomNPC(int week, List<ClothingItem> playerItems) {
    GameRNG rng = GameRNG.getInstance();

    String name = "Customer ";

    int knowledge = rng.getKnowledge(week);
    double greed = rng.getGreed();
    int patience = rng.getPatience();
    
    boolean isBuyer;

    เช็คว่า Stock player ว่างไหม
    if (playerItems.isEmpty()) {
        isBuyer = false; 
    } else {
        isBuyer = rng.getRandomBoolean(); 
    }

    if (isBuyer) {
        return new BuyerNPC(name, knowledge, greed, patience);
    } else {
        return new SellerNPC(name, knowledge, greed, patience);
    }
}

ถ้ามี Update Stat โดยไม่ new ต้องสร้าง func เพิ่มรูปแบบคล้ายๆกับ createRandomNPC
*/