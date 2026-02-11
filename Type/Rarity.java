package Type;
// --- 2. ระดับความหายาก (ตัวคูณราคา) ---
enum Rarity {
    COMMON(1.0),
    RARE(2.5),
    LEGENDARY(10.0);

    public final double multiplier;

    Rarity(double m) {
        this.multiplier = m;
    }
}
