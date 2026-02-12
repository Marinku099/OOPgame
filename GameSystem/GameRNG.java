package GameSystem;

import java.util.List;
import java.util.Random;

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

    // --- Core Tools ---
    
    public int genRange(int min, int max) {
        if (min > max) return min;
        return random.nextInt((max - min) + 1) + min;
    }

    public boolean succeedOnChance(double probability) {
        return random.nextDouble() < probability;
    }

    public <T> T pickRandomItem(List<T> list) {
        if (list == null || list.isEmpty()) return null;
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
        if (week < 4) return genRange(1, week);
        return genRange(2, 4);
    }

    public double genNegotiationStep() {
        return 0.10 + (random.nextDouble() * 0.40); 
    }
}