package OOPGameCharacter;

import GameItem.ClothingItem;
import GameSystem.CalculateNPC;
import GameSystem.GameRNG;

import java.util.List;

import Enums.OfferState;

public abstract class NPC extends GameCharacter implements CalculateNPC {
    protected int patience;
    protected double greed;

    protected double estimatedValue;
    protected double limitPrice;
    protected double currentOffer;
    protected ClothingItem item;

    public NPC(String name, int knowledge, double greed, int patience, List<ClothingItem> cloths) {
        super(name, knowledge);
        this.greed = greed;
        this.patience = patience;
        this.item = GameRNG.pickRandomCloth(cloths);
    }

    public NPC(List<ClothingItem> cloths, List<String> names) {
        super(GameRNG.pickRandomNPCName(names), GameRNG.genKnowledge());
        this.greed = GameRNG.genGreed();
        this.patience = GameRNG.genPatience();
        this.item = GameRNG.pickRandomCloth(cloths);
    }

    // public NPC(Database database){
    //     super(GameRNG.pickRandomNPCName(database), GameRNG.genKnowledge());
    //     this.greed = GameRNG.genGreed();
    //     this.patience = GameRNG.genPatience();
    //     this.item = GameRNG.pickRandomCloth(database.getListClothingItems());
    // }
    
    // ตัวรับการเริ่มเจรจา
    public void receiveOffer(Player player) {
        inspectItem(); // ประเมินของ
        this.currentOffer = getStartingOffer(); // ตั้งราคาเปิด
    }
    
    protected void inspectItem() {
        this.estimatedValue = item.getPerceivedPrice(this.knowledge);
    }

    public abstract void chooseItem();

    // ให้ Buyer / Seller กำหนดราคาเปิดเอง
    public abstract double getStartingOffer();

    protected abstract void makeDeal(Player player, double finalPrice);
    protected abstract boolean isPriceAcceptable(double playerPrice);
    protected abstract boolean isPriceTooExploit(double playerPrice);
    protected abstract String OfferDialogue();

    // ต่อราคา
    public OfferState processOffer(double playerPrice, Player player) {
        if (isPriceAcceptable(playerPrice)) {
            makeDeal(player, playerPrice);
            return OfferState.SUCCESS;
        }

        if (isPriceTooExploit(playerPrice)) {
            System.out.println(name + OfferDialogue());
            this.patience = 0;
            return OfferState.FAIL;
        }

        // 3. วัดดวง
        if (GameRNG.succeedOnChance(calculateSuccessChance(playerPrice))) {
            makeDeal(player, playerPrice);
            return OfferState.SUCCESS;
        } else {
            recalculatePrice(playerPrice);
            return reducePatience();
        }
    }

    protected abstract OfferState reducePatience();

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