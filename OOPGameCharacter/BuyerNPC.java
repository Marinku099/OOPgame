package OOPGameCharacter;

import GameItem.ClothingItem;
import GameSystem.GameRNG;
import java.util.List;

import Enums.OfferState;

public class BuyerNPC extends NPC {
    private ClothingItem wantedItem;

    public BuyerNPC(String name, int knowledge, double greed, int patience) {
        super(name, knowledge, greed, patience);
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
    public void chooseItem(List<ClothingItem> playerItems) {
        // เลือกของจากกระเป๋าผู้เล่น
        this.wantedItem = GameRNG.getInstance().pickRandomItem(playerItems);
        
        if (this.wantedItem == null) return;

        // 1. ประเมินราคา (Logic จากแม่)
        inspectItem(this.wantedItem);

        // 2. คำนวณลิมิต (คนซื้อเอาถูก -> หารด้วยความงก)
        this.limitPrice = this.estimatedValue / this.greed;
        
        // 3. ตั้งราคาเริ่มต้น (Logic จาก Interface)
        this.currentOffer = getStartingOffer();
    }

    @Override
    public OfferState processOffer(double playerPrice, Player player) {
        // 1. ถ้าราคาผู้เล่น OK (ไม่เกิน 1.2 เท่าของที่เสนอ และไม่เกินลิมิต)
        if (playerPrice <= this.currentOffer * 1.2 && playerPrice <= this.limitPrice) {
            makeDeal(player, playerPrice);
            return OfferState.SUCCESS;
        }

        // 2. ถ้าแพงเกินไปมาก (เกิน 1.4 เท่าของลิมิต)
        if (playerPrice >= this.limitPrice * 1.4) {
            System.out.println(name + ": แพงเกินไป ไม่มีเงินจ่าย!");
            this.patience = 0;
            return OfferState.FAIL;
        }

        // 3. วัดดวง (ใช้ Logic จาก Interface)
        if (GameRNG.getInstance().succeedOnChance(calculateSuccessChance(playerPrice))) {
            makeDeal(player, playerPrice);
            return OfferState.SUCCESS;
        } else {
            recalculatePrice(playerPrice); // คำนวณราคาเสนอใหม่
            return reducePatience();
        }
    }

    private void makeDeal(Player player, double finalPrice) {
        System.out.println(">> ตกลงรับซื้อที่ราคา " + (int) finalPrice);
        // player.addMoney(finalPrice);
        // player.removeItem(this.wantedItem);
        this.patience = 0;
    }

    private OfferState reducePatience() {
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