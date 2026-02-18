package OOPGameCharacter;

import GameItem.ClothingItem;
import GameSystem.GameRNG;
import java.util.List;

import Enums.OfferState;

public class SellerNPC extends NPC {
    private ClothingItem sellingItem; // เดิม: itemForSale

    public SellerNPC(String name, int knowledge, double greed, int patience) {
        super(name, knowledge, greed, patience);
    }

    @Override
    public double getStartingOffer() {
        // คนขาย -> ตั้งราคาเริ่ม "แพงกว่าราคาประเมิน"
        return estimatedValue * (1 + greed);
    }

    // จำเป็นต้องเช็กด้วยหรอ? บอกด้วยคลาสแล้วนะ
    @Override
    public boolean isBuyer() { return false; }

    @Override
    public void chooseItem(List<ClothingItem> allItems) {
        
        String selectedRarity = GameRNG.getInstance().getRandomRarityByWeek(); //สัปดาห์นี้ NPC ควรจะเอาของระดับไหนมาขาย?
        List<ClothingItem> filteredItems = allItems.stream().filter(item -> item.getRarity().toString().equalsIgnoreCase(selectedRarity)).toList();//กรองเฉพาะไอเทมที่มี Rarity ตรงกับที่สุ่มได้

        if (filteredItems.isEmpty()) { // ถ้าไม่มีของในระดับนั้นเลย(กันพลาด) ให้ใช้ของทั้งหมดที่มี
            filteredItems = allItems;
        }
        
        this.sellingItem = GameRNG.getInstance().pickRandomItem(filteredItems); // สุ่มเลือกมา 1 ชิ้นจากรายการที่กรองแล้ว
        if (this.sellingItem == null) return;

        // --- ส่วนการประเมินราคา ---
        inspectItem(this.sellingItem);
        this.limitPrice = this.estimatedValue * this.greed; // คำนวณลิมิต (คนขายเอาแพง -> คูณความงก)
        this.currentOffer = getStartingOffer(); // ตั้งราคาเริ่มต้น
    }

    @Override
    public OfferState processOffer(double playerPrice, Player player) {
        // 1. ถ้าราคาผู้เล่น OK (ไม่ต่ำกว่า 0.8 ของที่เสนอ และไม่ต่ำกว่าลิมิต)
        if (playerPrice >= this.currentOffer * 0.8 && playerPrice >= this.limitPrice) {
            makeDeal(player, playerPrice);
            return OfferState.SUCCESS;
        }

        // 2. ถ้าต่อราคาโหดเกินไป (ต่ำกว่า 40% ของลิมิต)
        if (playerPrice <= this.limitPrice * 0.4) {
            System.out.println(name + ": ขาดทุน ไม่ขาย!");
            this.patience = 0;
            return OfferState.FAIL;
        }

        // 3. วัดดวง
        if (GameRNG.getInstance().succeedOnChance(calculateSuccessChance(playerPrice))) {
            makeDeal(player, playerPrice);
            return OfferState.SUCCESS;
        } else {
            recalculatePrice(playerPrice);
            return reducePatience();
        }
    }

    private void makeDeal(Player player, double finalPrice) {
        System.out.println(">> ตกลงขายที่ราคา " + (int) finalPrice);
        // player.deductMoney(finalPrice);
        // player.addItem(this.sellingItem);
        this.patience = 0;
    }

    private OfferState reducePatience() {
        this.patience--;
        if (this.patience <= 0) {
            System.out.println(name + ": ไม่ขายแล้ว รำคาญ (เดินหนี)");
            return OfferState.FAIL;
        } else {
            System.out.println(name + ": ลดให้เหลือ " + (int) this.currentOffer + " บาท เอาไหม?");
            return OfferState.PENDING;
        }
    }
}