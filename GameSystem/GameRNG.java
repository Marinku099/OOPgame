package GameSystem;

import java.util.List;
import java.util.Random;

import Type.Size;

public class GameRNG {
    // Singleton Logic
    private static GameRNG instance;
    private final Random random;

    private GameRNG() {
        this.random = new Random();
    }

    public static GameRNG getInstance() {
        if (instance == null) {
            instance = new GameRNG();
        }
        return instance;
    }

    // --- ส่วนที่เพิ่มใหม่สำหรับ ClothingItem ---

    public int genRange(int min, int max) {
        if (min > max)
            return min;
        return random.nextInt((max - min) + 1) + min;
    }

    public boolean succeedOnChance(double probability) {
        return random.nextDouble() < probability;
    }

    public <T> T pickRandomItem(List<T> list) {
        if (list == null || list.isEmpty())
            return null;
        return list.get(random.nextInt(list.size()));
    }

    // --- Game Logic (NPC Stats) ---

    public double genGreedFactor() {
        // สุ่มความงก 0.9 - 1.1
        return 0.9 + (random.nextDouble() * 0.2);
    }

    public int genPatience() {
        // สุ่มความอดทน 2 - 4 ครั้ง
        return genRange(2, 4);
    }

    public int genKnowledgeLevel(int week) {
        // สัปดาห์แรกๆ (1-3) สุ่ม 1-Week, หลัง Week 4 สุ่ม 2-4
        if (week < 4)
            return genRange(1, week);
        return genRange(2, 4);
    }

    public double genNegotiationStep() {
        return 0.10 + (random.nextDouble() * 0.40);
    }

    // --- ส่วนที่เพิ่มใหม่สำหรับ ClothingItem ---

    // สุ่มสภาพ (0.1 - 1.0)
    public double genCondition() {
        return 0.1 + (0.9 * random.nextDouble());
    }

    // สุ่มไซส์
    public Size genSize() {
        Size[] allSizes = Size.values();
        return allSizes[random.nextInt(allSizes.length)];
    }

    // สุ่มว่าเป็นของปลอมไหม (ตาม Week)
    public boolean genIsFake(int week) {
        int roll = random.nextInt(100);
        if (week == 1)
            return roll < 10; // 10%
        else if (week == 2)
            return roll < 30; // 30%
        else
            return roll < 40; // 40% (Week 3+)
    }

    // สุ่มความเนียนของของปลอม (ตาม Week)
    public double genFakeAuthenticity(int week) {
        if (week == 1) {
            // Week 1: ปลอมไม่เนียน (0.1 - 0.5)
            return 0.1 + (0.4 * random.nextDouble());
        } else if (week == 2) {
            // Week 2: ปลอมเกรด B (0.4 - 0.8)
            return 0.4 + (0.4 * random.nextDouble());
        } else {
            // Week 3+: ปลอมเกรด Mirror AAA (0.7 - 1.0)
            return 0.7 + (0.3 * random.nextDouble());
        }
    }
}