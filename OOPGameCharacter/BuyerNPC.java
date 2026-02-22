package OOPGameCharacter;

import GameItem.ClothingItem;
import java.util.List;

import Enums.OfferState;

public class BuyerNPC extends NPC {
    // private ClothingItem wantedItem;

    public BuyerNPC(List<ClothingItem> cloths, List<String> names) {
        super(cloths, names);
    }

    @Override
    public double getStartingOffer() {
        // คนซื้อ -> เริ่มจากราคาต่ำกว่าราคาประเมิน
        return estimatedValue * (1 - greed);
    }

    // ต้องเช็กด้วยหรอ?
    @Override
    public boolean isBuyer() { return true; }

    //ต้องแก้
    @Override
    public void chooseItem() {
        if (this.item == null) return;

        // 1. ประเมินราคา (Logic จากแม่)
        inspectItem();

        // 2. คำนวณลิมิต (คนซื้อเอาถูก -> หารด้วยความงก)
        this.limitPrice = this.estimatedValue / this.greed;
        
        // 3. ตั้งราคาเริ่มต้น (Logic จาก Interface)
        this.currentOffer = getStartingOffer();
    }

    @Override
    protected boolean isPriceAcceptable(double playerPrice){
        return playerPrice <= this.currentOffer * 1.2 && playerPrice <= this.limitPrice;
    }

    @Override
    protected boolean isPriceTooExploit(double playerPrice){
        return playerPrice >= this.limitPrice * 1.4;
    }

    @Override
    protected String OfferDialogue(){
        return ": แพงเกินไป ไม่มีเงินจ่าย!";
    }

    @Override
    protected void makeDeal(Player player, double finalPrice) {
        System.out.println(">> ตกลงรับซื้อที่ราคา " + (int) finalPrice);
        // player.addMoney(finalPrice);
        // player.removeItem(this.wantedItem);
        this.patience = 0;
    }

    @Override
    protected OfferState reducePatience() {
        this.patience--;
        if (this.patience <= 0) {
            System.out.println(name + ": ไม่เอาแล้ว (เดินหนี)");
            return OfferState.FAIL;
        }
        else {
            System.out.println(name + ": เพิ่มให้เป็น " + (int) this.currentOffer + " บาท เอาไหม?");
            return OfferState.PENDING;
        }
    }
}