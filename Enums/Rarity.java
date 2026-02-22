package Enums;

// --- 2. ระดับความหายาก (ตัวคูณราคา) ---
public enum Rarity {
    COMMON(1.0),
    UNCOMMON(1.2),
    RARE(2.0),
    VINTAGE(3.5),
    LEGENDARY(6.05);

    private final double multiplier;

    Rarity(double m) {
        this.multiplier = m;
    }

    public double getMultiplier() {
        return this.multiplier;
    }
}