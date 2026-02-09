import java.util.Random;

// --- 1. ประเภทสินค้าและราคาฐาน ---
enum ClothingType {
    T_SHIRT(500.0),
    HOODIE(1200.0),
    JACKET(2500.0),
    SNEAKERS(4000.0),
    ACCESSORY(1500.0);

    private final double defaultBasePrice;

    ClothingType(double price) {
        this.defaultBasePrice = price;
    }

    public double getBasePrice() {
        return this.defaultBasePrice;
    }
}

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

// --- 3. ขนาด (ตัวคูณราคาตามความนิยม) ---
enum Size {
    XS(0.5),   // เล็กไป ราคาตก 50%
    S(0.8),    // เล็กนิดหน่อย
    M(1.0),    // มาตรฐาน
    L(1.2),    // ไซส์นิยม ราคาบวกเพิ่ม 20%
    XL(1.0),   // มาตรฐาน
    XXL(0.6);  // ใหญ่ไป ราคาตก 40%

    private final double priceMultiplier;

    Size(double m) {
        this.priceMultiplier = m;
    }

    public double getMultiplier() {
        return this.priceMultiplier;
    }
}

abstract class ClothingItem {
    protected static final Random rand = new Random();

    // Attributes ที่กำหนดตอนสร้าง (Fixed)
    protected String name;
    protected String description;
    protected ClothingType type;
    protected Rarity rarity;
    protected double basePrice;

    // Attributes ที่สุ่ม (Randomized)
    protected Size size;
    protected double condition;       // 0.0 - 1.0
    protected boolean isFake;         // ของปลอมไหม
    protected double fakeAuthenticity;// ความเนียน (0.0 - 1.0)

    // --- Constructor หลัก ---
    public ClothingItem(String name, String description, ClothingType type, Rarity rarity) {
        // 1. รับค่าที่กำหนด
        this.name = name;
        this.description = description;
        this.type = type;
        this.rarity = rarity;
        this.basePrice = type.getBasePrice();

        // 2. สุ่มค่าพื้นฐาน
        this.condition = 0.1 + (0.9 * rand.nextDouble()); // Min 0.1, Max 1.0
        this.size = Size.values()[rand.nextInt(Size.values().length)]; // สุ่มไซส์

        // 3. ดึง Week ปัจจุบันจาก DayManager เพื่อคำนวณของปลอม **รอสร้าง DayManager**
        int currentWeek = DayManager.getCurrentWeek();
        calculateFakeStatus(currentWeek);
    }

    // --- Logic การคำนวณของปลอม ---
    private void calculateFakeStatus(int week) {
        int roll = rand.nextInt(100); // 0-99

        // โอกาสเจอของปลอมตาม Week
        if (week == 1) this.isFake = (roll < 10);      // 10%
        else if (week == 2) this.isFake = (roll < 30); // 30%
        else this.isFake = (roll < 40);                // 40% (Week 3+)

        // ถ้าเป็นของปลอม -> คำนวณความเนียน (Authenticity)
        if (this.isFake) {
            if (week == 1) {
                // Week 1: ปลอมไม่เนียน (0.1 - 0.5)
                this.fakeAuthenticity = 0.1 + (0.4 * rand.nextDouble());
            } else if (week == 2) {
                // Week 2: ปลอมเกรด B (0.4 - 0.8)
                this.fakeAuthenticity = 0.4 + (0.4 * rand.nextDouble());
            } else {
                // Week 3+: ปลอมเกรด Mirror AAA (0.7 - 1.0)
                this.fakeAuthenticity = 0.7 + (0.3 * rand.nextDouble());
            }
        } else {
            this.fakeAuthenticity = 0.0; // ของแท้
        }
    }

    // --- Logic การคำนวณราคา ---

    // 1. ราคาประเมิน (Appraised Value) - ราคาที่ "ควรจะเป็น" ถ้าเป็นของแท้
    protected double calculateAppraisedValue() {
        // สูตร: ราคาฐาน * ความหายาก * ขนาด * สภาพ
        return basePrice 
               * rarity.multiplier 
               * size.getMultiplier() 
               * condition;
    }

    // 2. ราคาจริง (Real Value) - ถ้าปลอมราคาตก
    public double calculateRealValue() {
        double appraised = calculateAppraisedValue();
        
        if (!isFake) return appraised; // ของแท้ราคาเต็ม

        // สูตรของปลอม: 5% + (ความเนียน * 45%) ของราคาประเมิน
        double fakeFactor = 0.05 + (fakeAuthenticity * 0.45);
        return appraised * fakeFactor;
    }

    // 3. ตรวจสอบการมองเห็น (Detection)
    protected boolean checkIfDetected(int viewerLevel) {
        if (!isFake) return true; // ของแท้ ดูยังไงก็คือของแท้

        // Level 1=25%, 2=50%, 3=75%, 4=100% Detection Chance
        double detectionPower = viewerLevel * 0.25;
        
        // ถ้าพลังการมองเห็น >= ความเนียน -> จับได้
        return detectionPower >= this.fakeAuthenticity;
    }

    // --- Public API ---

    // ราคาที่คนคนหนึ่งมองเห็น (ขึ้นอยู่กับ Level)
    public double getPerceivedPrice(int viewerLevel) {
        boolean detected = checkIfDetected(viewerLevel);
        return detected ? calculateRealValue() : calculateAppraisedValue();
    }

    // ส่งข้อมูลไปแสดงบนหน้าจอ
    public InspectionResult inspectForUI(int viewerLevel) {
        boolean detected = checkIfDetected(viewerLevel);
        double priceToShow = detected ? calculateRealValue() : calculateAppraisedValue();
        
        String desc = detected && isFake ? "[FAKE DETECTED] " + description : description;
        
        return new InspectionResult(
            priceToShow, 
            this.name, 
            desc, 
            detected, 
            this.condition, 
            this.rarity,
            this.size
        );
    }
    
    // Debug Info มีไว้เช็ค
    @Override
    public String toString() {
        return String.format("[%s] %s (%s) | Size:%s (x%.1f) | Cond:%.2f | Fake:%b (Auth:%.2f)", 
            type, name, rarity, size, size.getMultiplier(), condition, isFake, fakeAuthenticity);
    }
}

// --- Concrete Class สำหรับใช้งานจริง ---
class FashionItem extends ClothingItem {
    public FashionItem(String name, String description, ClothingType type, Rarity rarity) {
        super(name, description, type, rarity);
    }
}