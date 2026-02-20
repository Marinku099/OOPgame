package GameSystem;

import DataBase.Database;
import DataBase.ItemData;
import Enums.Rarity;
import Enums.Size;
import java.util.List;
import java.util.Random;

public class GameRNG implements WeeklyListener{
    private static final Random random = new Random();
    private static int week = TimeManagement.getInstance().getWeek();
 
    private GameRNG() { }

    // สุ่มตัวเลขจำนวนเต็มในช่วงที่กำหนด (รวมค่า min และ max)
    public static int getRandomInt(int min, int max) {
        if (min > max) return min;
        return random.nextInt((max - min) + 1) + min;
    }

    // สุ่มค่าความจริง (True หรือ False)
    public static boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    // คำนวณผลลัพธ์ว่าสำเร็จหรือไม่ ตามความน่าจะเป็นที่กำหนด (0.0 - 1.0)
    public static boolean succeedOnChance(double probability) {
        return random.nextDouble() < probability;
    }

    // สุ่มเลือกวัตถุหนึ่งชิ้นจากรายการ (List) ที่ส่งเข้ามา
    public static <T> T pickRandomItem(List<T> list) {
        if (list == null || list.isEmpty()) return null;
        return list.get(random.nextInt(list.size()));
    }

    public static ItemData pickRandomCloth(Database database){
        Rarity rarity = genRandomRarityByWeek();
        return pickRandomItem(database.getItemsByRarity(rarity));
    }

    public static String pickRandomNPCName(Database database){
        return pickRandomItem(database.getAllCustomerNames());
    }

    // สุ่มค่าความโลภของ NPC (ส่งผลต่อลิมิตราคาที่รับได้)
    public static double genGreed() {
        return 0.9 + (random.nextDouble() * 0.4);
    }

    // สุ่มค่าความอดทนของ NPC (จำนวนรอบที่ยอมให้ต่อรองราคา)
    public static int genPatience() {
        return getRandomInt(2, 4);
    }

    // สุ่มระดับความรู้ของ NPC ในการประเมินราคาของ (ขึ้นอยู่กับสัปดาห์ในเกม)
    public static int genKnowledge() {
        //int week = TimeManagement.getInstance().getWeek();
        if (week < 4) return getRandomInt(1, week);
        return getRandomInt(2, 4);
    }

    // สุ่มเปอร์เซ็นต์ช่วงราคาที่จะขยับในการต่อรองแต่ละครั้ง
    public static double genNegotiationStep() {
        return 0.10 + (random.nextDouble() * 0.40);
    }

    // สุ่มค่าสภาพของสินค้า (10% - 100%)
    public static double genCondition() {
        return 0.1 + (0.9 * random.nextDouble());
    }

    // สุ่มไซส์ของสินค้าจาก Enum Size
    public static Size genSize() {
        Size[] allSizes = Size.values();
        return allSizes[random.nextInt(allSizes.length)];
    }

    // สุ่มโอกาสที่สินค้าจะเป็นของปลอม (โอกาสสูงขึ้นตามสัปดาห์ที่เล่น)
    public static boolean genIsFake() {
        //int week = TimeManagement.getInstance().getWeek();
        int roll = random.nextInt(100);

        if (week == 1) 
            return roll < 10;
        else if (week == 2) 
            return roll < 30;
        else 
            return roll < 40;
    }

    // สุ่มค่าความเนียนของของปลอม (ยิ่งสัปดาห์มาก ยิ่งดูเหมือนของจริงมาก)
    public static double genFakeAuthenticity() {
        //int week = TimeManagement.getInstance().getWeek(); 
        if (week == 1) {
            return 0.1 + (0.4 * random.nextDouble());
        } else if (week == 2) {
            return 0.4 + (0.4 * random.nextDouble());
        } else {
            return 0.7 + (0.3 * random.nextDouble());
        }
    }

    // เรียกใช้จากภายนอก (เพื่อเอาชื่อ Rarity ไป Query ใน Database)
    public static Rarity genRandomRarityByWeek() {
        // int week = TimeManagement.getWeek(); // เรียกใช้ Singleton ของ TimeManagement (ตรวจสอบให้แน่ใจว่าคลาสนั้นมี getInstance())
        return determineRarity();
    }

    // คำนวณเปอร์เซ็นต์ Rarity
    private static Rarity determineRarity() {
        double roll = random.nextDouble() * 100; // สุ่มเลข 0.0 - 100.0

        if (week == 1) {
            if (roll < 70) return Rarity.COMMON /*"Common"*/;
            if (roll < 95) return Rarity.UNCOMMON /*"Uncommon"*/;
            return Rarity.RARE /*"Rare"*/;
        } 
        else if (week == 2) {
            if (roll < 65) return Rarity.COMMON /*"Common"*/;
            if (roll < 93) return Rarity.UNCOMMON/*"Uncommon"*/;
            if (roll < 99) return Rarity.RARE /*"Rare"*/;
            return Rarity.VINTAGE /*"Vintage"*/;
        } 
        else if (week == 3) {
            if (roll < 65) return Rarity.UNCOMMON /*"Uncommon"*/;
            if (roll < 90) return Rarity.RARE /*"Rare"*/;
            return Rarity.VINTAGE /*"Vintage"*/;
        } 
        else if (week == 4) {
            if (roll < 50) return Rarity.UNCOMMON /*"Uncommon"*/;
            if (roll < 85) return Rarity.RARE /*"Rare"*/;
            if (roll < 98) return Rarity.VINTAGE /*"Vintage"*/;
            return Rarity.LEGENDARY /*"Legendary"*/;
        } 
        else { // Week 5 เป็นต้นไป
            if (roll < 60) return Rarity.RARE /*"Rare"*/;
            if (roll < 90) return Rarity.VINTAGE /*"Vintage"*/;
            return Rarity.LEGENDARY /*"Legendary"*/;
        }
    } 

    @Override
    public void weeklyAction(){
        week = TimeManagement.getInstance().getWeek();
    }
}