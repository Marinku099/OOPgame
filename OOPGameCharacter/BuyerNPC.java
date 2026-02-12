package OOPGameCharacter;

import java.util.List;
import GameItem.ClothingItem;
import GameSystem.GameRNG;

public class BuyerNPC extends NPC {
    private ClothingItem desiredItem;

    public BuyerNPC(String name, int level, double greed, int patience) {
        super(name, level, greed, patience);
    }

    @Override
    public boolean isBuyer() {
        return true;
    }

    @Override
    public void setItem(List<ClothingItem> playerInventory) {
        this.desiredItem = GameRNG.getInstance().pickRandomItem(playerInventory);
        if (this.desiredItem == null)
            return;

        evaluateItem(this.desiredItem);

        this.absolutePriceLimit = this.estimatedBaseValue / this.greedMultiplier;
        this.currentOfferPrice = getStartingOffer();
    }

    @Override
    public void processOffer(double playerProposedPrice, Player player) {
        // 1. ถ้าราคาผู้เล่น OK
        if (playerProposedPrice <= this.currentOfferPrice * 1.2 && playerProposedPrice <= this.absolutePriceLimit) {
            performTransaction(player, playerProposedPrice);
            return;
        }

        // 2. ถ้าโก่งราคาเกินไป
        if (playerProposedPrice >= this.absolutePriceLimit * 1.4) {
            System.out.println(characterName + ": แพงไป ไม่มีเงินจ่าย!");
            this.currentPatience = 0;
            return;
        }

        // 3. วัดดวง (เรียกใช้ Default Method)
        if (GameRNG.getInstance().succeedOnChance(calculateSuccessChance(playerProposedPrice))) {
            performTransaction(player, playerProposedPrice);
        } else {
            // เรียกใช้ Default Method
            recalculatePrice(playerProposedPrice);
            decreasePatience();
        }
    }

    private void performTransaction(Player player, double finalAgreedPrice) {
        System.out.println(">> ตกลงรับซื้อที่ราคา " + (int) finalAgreedPrice);
        // player.addMoney(finalAgreedPrice);
        // player.removeItem(this.desiredItem);
        this.currentPatience = 0;
    }

    private void decreasePatience() {
        this.currentPatience--;
        if (this.currentPatience <= 0)
            System.out.println(characterName + ": ไม่เอาแล้ว (เดินหนี)");
        else
            System.out.println(characterName + ": เพิ่มให้เป็น " + (int) this.currentOfferPrice + " บาท เอาไหม?");
    }
}