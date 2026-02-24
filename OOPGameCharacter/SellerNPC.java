package OOPGameCharacter;

import Enums.OfferState;
import GameItem.ClothingItem;
import GameSystem.GameRNG;
import java.util.List;

public class SellerNPC extends NPC {
    // private ClothingItem sellingItem; // เดิม: itemForSale

    public SellerNPC(List<ClothingItem> cloths, List<String> names) {
        super(cloths, names);
        this.greed = 1.0 + (GameRNG.genGreed() - 0.9);
    }

    @Override
    public double getStartingOffer() {
        // คนขาย -> ตั้งราคาเริ่ม "แพงกว่าราคาประเมิน"
        return estimatedValue * (1.3 + greed * 0.3);
        //return Math.max(1, estimatedValue * (1 + greed));
    }

    // จำเป็นต้องเช็กด้วยหรอ? บอกด้วยคลาสแล้วนะ
    @Override
    public boolean isBuyer() { return false; }

    @Override
    public void chooseItem() {
        if (this.item == null) {
            this.currentOffer = -1;
            this.limitPrice = -1;
            return;
        }

        // --- ส่วนการประเมินราคา ---
        inspectItem();

        if (this.estimatedValue <= 0) {
            this.estimatedValue = 10; // MIN_PRICE กลาง ๆ
        }   

        this.limitPrice = estimatedValue * (0.85 + greed * 0.1); // คำนวณลิมิต (คนขายเอาแพง -> คูณความงก)
        this.currentOffer = (int) Math.max(1, getStartingOffer()); // ตั้งราคาเริ่มต้น
    }

    @Override
    protected boolean isPriceAcceptable(double playerPrice){
        return playerPrice > 0
            && playerPrice >= this.limitPrice;
        //return playerPrice >= this.currentOffer * 0.8 && playerPrice >= this.limitPrice;
    }

    @Override
    protected boolean isPriceTooExploit(double playerPrice){
        return playerPrice <= this.limitPrice * 0.4;
    }

    @Override
    protected String OfferDialogue(){
        return ": ขาดทุน ไม่ขาย!";
    }

    @Override
    protected void makeDeal(Player player, double finalPrice) {
        System.out.println(">> ตกลงขายที่ราคา " + (int) finalPrice);
        player.completeBuy(this.item, finalPrice);
        // player.deductMoney(finalPrice);
        // player.addItem(this.sellingItem);
        this.item = null;
        this.currentOffer = 0;
        this.limitPrice = 0;
        this.patience = 0;
    }

    @Override
    protected OfferState reducePatience() {
        this.patience--;

        this.currentOffer = Math.max(1, this.currentOffer);

        if (this.patience <= 0) {
            System.out.println(name + ": ไม่ขายแล้ว รำคาญ (เดินหนี)");
            return OfferState.FAIL;
        } else {
            System.out.println(name + ": ลดให้เหลือ " + (int) this.currentOffer + " บาท เอาไหม?");
            return OfferState.PENDING;
        }
    }
}