package OOPGameCharacter;

import GameItem.ClothingItem;
import GameSystem.CalculateNPC;
import java.util.List;

import Enums.OfferState;

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
    
    // ตัวรับการเริ่มเจรจา
    public void receiveOffer(Player player , ClothingItem item) {
        inspectItem(item); // ประเมินของ
        this.currentOffer = getStartingOffer(); // ตั้งราคาเปิด
    }
    
    protected void inspectItem(ClothingItem item) {
        this.estimatedValue = item.getPerceivedPrice(this.knowledge);
    }

    public abstract void chooseItem(List<ClothingItem> items);

    // ให้ Buyer / Seller กำหนดราคาเปิดเอง
    public abstract double getStartingOffer();

    // ต่อราคา
    public abstract OfferState processOffer(double price , Player player);

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