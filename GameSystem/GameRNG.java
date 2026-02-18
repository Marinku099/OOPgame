package GameSystem;

import Enums.Size;
import java.util.List;
import java.util.Random;

public class GameRNG {
    private static GameRNG instance;
    private final Random random;
 
    private GameRNG() {
        this.random = new Random();
    }

    // คืนค่า Instance หลักของคลาส (Singleton Pattern)
    public static GameRNG getInstance() {
        if (instance == null) {
            instance = new GameRNG();
        }
        return instance;
    }

    // สุ่มตัวเลขจำนวนเต็มในช่วงที่กำหนด (รวมค่า min และ max)
    public int getRandomInt(int min, int max) {
        if (min > max) return min;
        return random.nextInt((max - min) + 1) + min;
    }

    // สุ่มค่าความจริง (True หรือ False)
    public boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    // คำนวณผลลัพธ์ว่าสำเร็จหรือไม่ ตามความน่าจะเป็นที่กำหนด (0.0 - 1.0)
    public boolean succeedOnChance(double probability) {
        return random.nextDouble() < probability;
    }

    // สุ่มเลือกวัตถุหนึ่งชิ้นจากรายการ (List) ที่ส่งเข้ามา
    public <T> T pickRandomItem(List<T> list) {
        if (list == null || list.isEmpty()) return null;
        return list.get(random.nextInt(list.size()));
    }

    // สุ่มค่าความโลภของ NPC (ส่งผลต่อลิมิตราคาที่รับได้)
    public double getGreed() {
        return 0.9 + (random.nextDouble() * 0.4);
    }

    // สุ่มค่าความอดทนของ NPC (จำนวนรอบที่ยอมให้ต่อรองราคา)
    public int getPatience() {
        return getRandomInt(2, 4);
    }

    // สุ่มระดับความรู้ของ NPC ในการประเมินราคาของ (ขึ้นอยู่กับสัปดาห์ในเกม)
    public int getKnowledge(int week) {
        if (week < 4) return getRandomInt(1, week);
        return getRandomInt(2, 4);
    }

    // สุ่มเปอร์เซ็นต์ช่วงราคาที่จะขยับในการต่อรองแต่ละครั้ง
    public double genNegotiationStep() {
        return 0.10 + (random.nextDouble() * 0.40);
    }

    // สุ่มค่าสภาพของสินค้า (10% - 100%)
    public double genCondition() {
        return 0.1 + (0.9 * random.nextDouble());
    }

    // สุ่มไซส์ของสินค้าจาก Enum Size
    public Size genSize() {
        Size[] allSizes = Size.values();
        return allSizes[random.nextInt(allSizes.length)];
    }

    // สุ่มโอกาสที่สินค้าจะเป็นของปลอม (โอกาสสูงขึ้นตามสัปดาห์ที่เล่น)
    public boolean genIsFake(int week) {
        int roll = random.nextInt(100);
        if (week == 1) 
            return roll < 10;
        else if (week == 2) 
            return roll < 30;
        else 
            return roll < 40;
    }

    // สุ่มค่าความเนียนของของปลอม (ยิ่งสัปดาห์มาก ยิ่งดูเหมือนของจริงมาก)
    public double genFakeAuthenticity(int week) {
        if (week == 1) {
            return 0.1 + (0.4 * random.nextDouble());
        } else if (week == 2) {
            return 0.4 + (0.4 * random.nextDouble());
        } else {
            return 0.7 + (0.3 * random.nextDouble());
        }
    }

    // เรียกใช้จากภายนอก (เพื่อเอาชื่อ Rarity ไป Query ใน Database)
    public String getRandomRarityByWeek() {
        int week = TimeManagement.getWeek(); // เรียกใช้ Singleton ของ TimeManagement (ตรวจสอบให้แน่ใจว่าคลาสนั้นมี getInstance())
        return determineRarity(week);
    }

    // คำนวณเปอร์เซ็นต์ Rarity
    private String determineRarity(int week) {
        double roll = random.nextDouble() * 100; // สุ่มเลข 0.0 - 100.0

        if (week == 1) {
            if (roll < 70) return "Common";
            if (roll < 95) return "Uncommon";
            return "Rare";
        } 
        else if (week == 2) {
            if (roll < 65) return "Common";
            if (roll < 93) return "Uncommon";
            if (roll < 99) return "Rare";
            return "Vintage";
        } 
        else if (week == 3) {
            if (roll < 65) return "Uncommon";
            if (roll < 90) return "Rare";
            return "Vintage";
        } 
        else if (week == 4) {
            if (roll < 50) return "Uncommon";
            if (roll < 85) return "Rare";
            if (roll < 98) return "Vintage";
            return "Legendary";
        } 
        else { // Week 5 เป็นต้นไป
            if (roll < 60) return "Rare";
            if (roll < 90) return "Vintage";
            return "Legendary";
        }
    } 
}