import Enums.OfferState;
import GameSystem.GameRNG;
import OOPGameCharacter.*;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        // ไม่ได้เรียก Database ให้มาทำงานในนี้?
        Player player = new Player("Player", 2, 1000, 0);
        // GameRNG rng = GameRNG.getInstance();

        int day = 1;
        int maxDay = 5;
        int maxWeek = 5;

        // ===== LOOP ใหญ่ : วันในเกม =====
        while (day <= maxDay && player.getBalance() > 0) {
            System.out.println("\n===== DAY " + day + " =====");

            // คำนวณจำนวน NPC วันนี้
            int npcLeftToday = GameRNG.getRandomInt(1, 5);
            // ไม่ได้ยึดตามดวงของ player?
            Queue<NPC> npcQueue = new LinkedList<>();
            // ถ้าไม่เพิ่มเข้าทีเดียวไม่ต้องใช้ queue ก็ได้?

            // เจน NPC ตัวแรก
            npcQueue.add(createNPC(player, day));

            // ===== LOOP รอง : คิว NPC =====
            while (!npcQueue.isEmpty()) {
                NPC currentNPC = npcQueue.poll();
                npcLeftToday--;

                // ให้ NPC เลือก item
                currentNPC.chooseItem(player.geStock().getItems());
                // ถ้าเป็น seller จะเลือกขายของที่ player มีอยู่แล้วหรอ?

                OfferState state = OfferState.PENDING;

                // ===== LOOP ต่อรอง =====
                while (state == OfferState.PENDING) {

                    double playerOffer = currentNPC.getCurrentOffer(); 
                    // (ถ้ามี input ผู้เล่น ค่อยเปลี่ยนตรงนี้)

                    if (currentNPC instanceof BuyerNPC buyer) {
                        state = player.sellItemTo(buyer, playerOffer);
                    } else if (currentNPC instanceof SellerNPC seller) {
                        state = player.buyItemFrom(seller, playerOffer);
                    }
                }

                System.out.println("ผลลัพธ์ดีล: " + state);

                // ===== จบดีล → เจน NPC ใหม่ =====
                if (npcLeftToday > 0) {
                    npcQueue.add(createNPC(player, day));
                }
            }
            // ===== สรุปผลรายวัน =====
            System.out.println("----- END DAY " + day + " -----");
            System.out.println("Balance: " + player.getBalance());
            // ไปเรียกมาจาก Scoremenagement
            System.out.println("Stock: " + player.geStock().getItems().size());
            day++;
        }
        System.out.println("\n=== GAME OVER ===");
    }
    // ===== สร้าง NPC ตามเงื่อนไขโจทย์ =====
    private static NPC createNPC(Player player, int week) {
        // GameRNG rng = GameRNG.getInstance();

        String name = "NPC";
        int knowledge = GameRNG.genKnowledge(week);
        double greed = GameRNG.genGreed();
        int patience = GameRNG.genPatience();
        boolean isBuyer = !player.geStock().getItems().isEmpty() && GameRNG.getRandomBoolean();
        return isBuyer
                ? new BuyerNPC(name, knowledge, greed, patience)
                : new SellerNPC(name, knowledge, greed, patience);
    }
}
