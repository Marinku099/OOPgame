package OOPGameCharacter;

import java.util.List;
import GameItem.ClothingItem;
import GameSystem.GameRNG;

public class SellerNPC extends NPC {
    private ClothingItem sellingItem; // เดิม: itemForSale

    public SellerNPC(String name, int knowledge, double greed, int patience) {
        super(name, knowledge, greed, patience);
    }

    @Override
    public boolean isBuyer() { return false; }

    //ต้องแก้
    @Override
    public void chooseItem(List<ClothingItem> possibleItems) {
        // สุ่มของที่จะมาขาย
        this.sellingItem = GameRNG.getInstance().pickRandomItem(possibleItems);
        
        if (this.sellingItem == null) return;

        // 1. ประเมินราคา
        inspectItem(this.sellingItem);

        // 2. คำนวณลิมิต (คนขายเอาแพง -> คูณความงก)
        this.limitPrice = this.estimatedValue * this.greed;

        // 3. ตั้งราคาเริ่มต้น
        this.currentOffer = getStartingOffer();
    }

    @Override
    public void processOffer(double playerPrice, Player player) {
        // 1. ถ้าราคาผู้เล่น OK (ไม่ต่ำกว่า 0.8 ของที่เสนอ และไม่ต่ำกว่าลิมิต)
        if (playerPrice >= this.currentOffer * 0.8 && playerPrice >= this.limitPrice) {
            makeDeal(player, playerPrice);
            return;
        }

        // 2. ถ้าต่อราคาโหดเกินไป (ต่ำกว่า 40% ของลิมิต)
        if (playerPrice <= this.limitPrice * 0.4) {
            System.out.println(name + ": ขาดทุน ไม่ขาย!");
            this.patience = 0;
            return;
        }

        // 3. วัดดวง
        if (GameRNG.getInstance().succeedOnChance(calculateSuccessChance(playerPrice))) {
            makeDeal(player, playerPrice);
        } else {
            recalculatePrice(playerPrice);
            reducePatience();
        }
    }

    private void makeDeal(Player player, double finalPrice) {
        System.out.println(">> ตกลงขายที่ราคา " + (int) finalPrice);
        // player.deductMoney(finalPrice);
        // player.addItem(this.sellingItem);
        this.patience = 0;
    }

    private void reducePatience() {
        this.patience--;
        if (this.patience <= 0) {
            System.out.println(name + ": ไม่ขายแล้ว รำคาญ (เดินหนี)");
        } else {
            System.out.println(name + ": ลดให้เหลือ " + (int) this.currentOffer + " บาท เอาไหม?");
        }
    }
}