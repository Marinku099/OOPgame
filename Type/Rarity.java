package Type;

// --- 2. ระดับความหายาก (ตัวคูณราคา) ---
public enum Rarity {
    COMMON(1.0),
    RARE(2.5),
    LEGENDARY(10.0);

    private final double multiplier;

    Rarity(double m) {
        this.multiplier = m;
    }

    public double getMultiplier() {
        return this.multiplier;
    }
}