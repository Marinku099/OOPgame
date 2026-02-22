package Enums;

// --- 3. ขนาด (ตัวคูณราคาตามความนิยม) ---
public enum Size {
    XS(0.9),   // เล็กไป ราคาตก 50%
    S(1.0),    // เล็กนิดหน่อย
    M(1.0),    // มาตรฐาน
    L(1.0),    // ไซส์นิยม ราคาบวกเพิ่ม 20%
    XL(1.1),   // มาตรฐาน
    XXL(1.2);  // ใหญ่ไป ราคาตก 40%

    private final double priceMultiplier;

    Size(double m) {
        this.priceMultiplier = m;
    }

    public double getMultiplier() {
        return this.priceMultiplier;
    }
}
