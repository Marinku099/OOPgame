package OOPGameCharacter;

import java.util.List;
import GameItem.ClothingItem;
import GameSystem.GameRNG;

public class SellerNPC extends NPC {
    private ClothingItem itemForSale;

    public SellerNPC(String name, int level, double greed, int patience) {
        super(name, level, greed, patience);
    }

    @Override
    public boolean isBuyer() {
        return false;
    }

    @Override
    public void setItem(List<ClothingItem> possibleItems) {
        this.itemForSale = GameRNG.getInstance().pickRandomItem(possibleItems);
        if (this.itemForSale == null)
            return;

        evaluateItem(this.itemForSale);

        // คำนวณ Limit (คนขายคูณความงก)
        this.absolutePriceLimit = this.estimatedBaseValue * this.greedMultiplier;

        // ใช้ Default Method
        this.currentOfferPrice = getStartingOffer();
    }

    @Override
    public void processOffer(double playerProposedPrice, Player player) {
        // 1. ถ้าราคาผู้เล่น OK
        if (playerProposedPrice >= this.currentOfferPrice * 0.8 && playerProposedPrice >= this.absolutePriceLimit) {
            performTransaction(player, playerProposedPrice);
            return;
        }

        // 2. ถ้าต่อโหดเกินไป
        if (playerProposedPrice <= this.absolutePriceLimit * 0.4) {
            System.out.println(characterName + ": ขาดทุน ไม่ขาย!");
            this.currentPatience = 0;
            return;
        }

        // 3. วัดดวง
        if (GameRNG.getInstance().succeedOnChance(calculateSuccessChance(playerProposedPrice))) {
            performTransaction(player, playerProposedPrice);
        } else {
            recalculatePrice(playerProposedPrice);
            decreasePatience();
        }
    }

    private void performTransaction(Player player, double finalAgreedPrice) {
        System.out.println(">> ตกลงขายที่ราคา " + (int) finalAgreedPrice);
        // player.deductMoney(finalAgreedPrice);
        // player.addItem(this.itemForSale);
        this.currentPatience = 0;
    }

    private void decreasePatience() {
        this.currentPatience--;
        if (this.currentPatience <= 0) {
            System.out.println(characterName + ": ไม่ขายแล้ว รำคาญ (เดินหนี)");
        } else {
            System.out.println(characterName + ": ลดให้เหลือ " + (int) this.currentOfferPrice + " บาท เอาไหม?");
        }
    }
}