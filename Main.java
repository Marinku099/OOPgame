import DataBase.Database;
import DataBase.Loader.ItemLoader;
import GameSystem.*;
import OOPGameCharacter.*;

public class Main {

    public static void main(String[] args) {

        // 1. สร้าง Database กลาง
        Database database = new Database();

        // 2. โหลด Item จาก CSV
        ItemLoader itemLoader = new ItemLoader(database);
        itemLoader.loadItems(); // ← ใช้ชื่อ method จริงของคุณ

        Player player = new Player("PlayerOne", 1000);
        ScoreManagement score = new ScoreManagement(player);

        System.out.println("=== ยินดีต้อนรับสู่ OOP Merchant Game ===");

        // 5 สัปดาห์
        while (TimeManagement.getWeek() <= 5 && player.getBalance() > 0) {

            System.out.println("\n===== สัปดาห์ที่ " + TimeManagement.getWeek() + " =====");

            // 7 วันต่อสัปดาห์
            for (int day = 1; day <= 7; day++) {
                System.out.println("\n--- วันที่ " + day + " ---");

                int npcToday = GameRNG.getRandomInt(2, 4);

                for (int i = 0; i < npcToday; i++) {
                    interactWithNPC(player, database);
                }

                score.scoreByDay();
                TimeManagement.updateDay();
            }

            System.out.println("\n[สรุปสัปดาห์]");
            System.out.println("เงินสะสม: " + score.getSummaryBalance());
            System.out.println("ขายไปทั้งหมด: " + score.getSummarySellAmount());
            System.out.println("ซื้อทั้งหมด: " + score.getSummaryBuyAmount());

            TimeManagement.updateWeek();
        }

        System.out.println("\n=== จบเกม ===");
    }

    private static void interactWithNPC(Player player, Database database) {
        NPC npc = createNPC(TimeManagement.getWeek());

        if (npc instanceof SellerNPC seller) {
            var item = GameRNG.pickRandomItem(database);
            player.buyItemFrom(seller, item);

        } else if (npc instanceof BuyerNPC buyer) {
            if (!player.getInventory().isEmpty()) {
                var item = player.getRandomItemFromInventory();
                player.sellItemTo(buyer, item);
            }
        }
    }

    private static NPC createNPC(int week) {
        int knowledge = GameRNG.genKnowledge(week);
        double greed = GameRNG.genGreed();
        int patience = GameRNG.genPatience();

        return GameRNG.getRandomBoolean()
                ? new BuyerNPC("Buyer_" + GameRNG.getRandomInt(10, 99), knowledge, greed, patience)
                : new SellerNPC("Seller_" + GameRNG.getRandomInt(10, 99), knowledge, greed, patience);
    }
}