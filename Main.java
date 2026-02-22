import DataBase.Database;
import DataBase.Loader.*;

import Enums.OfferState;

import GameSystem.*;

import OOPGameCharacter.*;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {

        /* ================= INIT DATABASE ================= */
        ItemLoader itemLoader = new CsvItemLoader("Csv/Item.csv");
        NameLoader nameLoader = new CsvNameLoader("Csv/NPC_name.csv");
        Database database = new Database(itemLoader, nameLoader);

        /* ================= INIT SYSTEM ================= */
        Player player = new Player("Player", 2, 1000, 0);
        TimeManagement time = TimeManagement.getInstance();
        ScoreManagement score = new ScoreManagement(player);

        System.out.println("=== OOP Merchant Game ===");

        /* ================= GAME LOOP : 5 WEEKS ================= */
        while (time.getWeek() <= 5 && player.getBalance() > 0) {

            System.out.println("\n===== WEEK " + time.getWeek() + " =====");

            /* ================= 7 DAYS / WEEK ================= */
           for (int day = 1; day <= 7; day++) {

                System.out.println("\n--- DAY " + day + " ---");

                int npcToday = GameRNG.getRandomInt(2, 5);
                Queue<NPC> npcQueue = new LinkedList<>();

                // npcQueue.add(createNPC(player, database, time.getWeek()));
                npcQueue.add(createNPC(player, database));

                /* ================= NPC LOOP ================= */
                while (!npcQueue.isEmpty()) {

                    NPC npc = npcQueue.poll();
                    npcToday--;

                    /* ===== NPC CHOOSE ITEM ===== */
                    npc.chooseItem();
                    // if (npc instanceof BuyerNPC) {
                    //     npc.chooseItem();
                    // } else if (npc instanceof SellerNPC) {
                    //     // // 1. สุ่ม rarity
                    //     // Rarity rarity = GameRNG.genRandomRarityByWeek();

                    //     // // 2. ดึง ItemData จาก Database
                    //     // List<ItemData> rawItems = database.getItemsByRarity(rarity);

                    //     // // 3. สุ่ม 1 ชิ้น
                    //     // ItemData raw = GameRNG.pickRandomItem(rawItems);
                    //     // if (raw == null) {
                    //     //     // ข้าม NPC ตัวนี้ไปเลย
                    //     //     continue;
                    //     // }
                    //     // // 4. แปลงเป็น ClothingItem
                    //     // ClothingItem item = new FashionItem(
                    //     //     raw.getName(),
                    //     //     raw.getDescription(),
                    //     //     raw.getType(),
                    //     //     raw.getRarity(),
                    //     //     GameRNG.genSize(),
                    //     //     GameRNG.genCondition(),
                    //     //     GameRNG.genIsFake(),
                    //     //     GameRNG.genFakeAuthenticity()
                    //     // );

                    //     // ClothingItem item = new ClothingItem(database);

                    //     // 5. ส่งให้ NPC
                    //     // List<ClothingItem> items = new ArrayList<>();
                    //     // items.add(item);

                    //     // ตอนสร้างมันสุ่มภายในตัวเองอยู่แล้ว
                    //     npc.chooseItem();
                    // }

                    OfferState state = OfferState.PENDING;

                    /* ===== DEAL LOOP ===== */
                    while (state == OfferState.PENDING) {
                        double offer = npc.getCurrentOffer();

                        if (npc instanceof BuyerNPC buyer) {
                            state = player.sellItemTo(buyer, offer);
                        } else if (npc instanceof SellerNPC seller) {
                            state = player.buyItemFrom(seller, offer);
                        }
                    }

                    score.updateDeal(state, npc);
                    System.out.println("ผลลัพธ์ดีล: " + state);

                    if (npcToday > 0) {
                        npcQueue.add(createNPC(player, database));
                    }
                }

                /* ================= END DAY ================= */
                score.scoreByDay();
                score.printDailySummary();
            }

            /* ================= END WEEK ================= */
            score.printWeeklySummary();
            time.nextWeek();
        }

        System.out.println("\n=== GAME OVER ===");
    }

    /* ================= CREATE NPC ================= */
    // private static NPC createNPC(Player player, Database database, int week) {

    //     int knowledge = GameRNG.genKnowledge();
    //     double greed = GameRNG.genGreed();
    //     int patience = GameRNG.genPatience();

    //     boolean isBuyer =
    //             !player.getStock().getItems().isEmpty()
    //             && GameRNG.getRandomBoolean();

    //     String name = GameRNG.pickRandomItem(database.getAllCustomerNames());

    //     return isBuyer
    //             ? new BuyerNPC(name, knowledge, greed, patience)
    //             : new SellerNPC(name, knowledge, greed, patience);
    // }

    private static NPC createNPC(Player player, Database database){
        boolean isBuyer = !player.getStock().getItems().isEmpty()
                            && GameRNG.getRandomBoolean();
        if (isBuyer) {
            return new BuyerNPC(player.getStock().getItems(), database.getAllCustomerNames());
        }
        else {
            return new SellerNPC(database.getListClothingItems(), database.getAllCustomerNames());
        }
    }
}