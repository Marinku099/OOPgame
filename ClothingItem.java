abstract class ClothingItem {
    // Attributes
    protected String name;
    protected String description;    // รับค่าตอน new แล้วเก็บไว้เลย
    protected double basePrice;
    protected double condition;
    protected Rarity rarity;
    protected Size size;
    protected boolean isFake;
    protected double fakeAuthenticity;

    public ClothingItem(String name, String description, double basePrice, 
                        double condition, Rarity rarity, Size size, 
                        boolean isFake, double fakeAuth) {
        this.name = name;
        this.description = description; // กำหนดค่าครั้งเดียวตอนสร้าง Object
        this.basePrice = basePrice;
        this.condition = condition;
        this.rarity = rarity;
        this.size = size;
        this.isFake = isFake;
        this.fakeAuthenticity = fakeAuth;
    }

    // --- Logic 1: ราคาประเมิน (ราคาหน้าป้าย) ---
    protected double calculateAppraisedValue() {
        return basePrice * rarity.multiplier * condition;
    }

    // --- Logic 2: ราคาจริง (สูตรตามภาพที่คุณส่งมา) ---
    public double calculateRealValue() {
        double marketPrice = calculateAppraisedValue();

        if (!this.isFake) {
            return marketPrice;
        }

        // สูตร: 5% + (ความเนียน * 45%)
        // fakeAuthenticity ควรมีค่า 0.0 - 1.0
        double fakeFactor = 0.05 + (this.fakeAuthenticity * 0.45);

        return marketPrice * fakeFactor;
    }

    // --- Logic 3: ตรวจสอบ (Detection) ---
    protected boolean checkIfDetected(int viewerLevel) {
        if (!isFake) return true;
        // ความเนียน 0.0-1.0 เทียบกับ Level 1-10 (คูณ 10 เพื่อปรับสเกล)
        return viewerLevel >= (this.fakeAuthenticity * 10);
    }

    // --- Public API 1: NPC ---
    public double getPerceivedPrice(int viewerLevel) {
        return checkIfDetected(viewerLevel) ? calculateRealValue() : calculateAppraisedValue();
    }

    // --- Public API 2: UI (แก้ไขตาม Request) ---
    public InspectionResult inspectForUI(int viewerLevel) {
        boolean detected = checkIfDetected(viewerLevel);
        double priceToShow = detected ? calculateRealValue() : calculateAppraisedValue();
        
        // ส่ง description ตัวเดิมที่รับมาตอน new Object ออกไปเลย
        // ไม่มีการแก้ไขข้อความ หรือต่อ String ใดๆ
        return new InspectionResult(
            priceToShow,
            this.name,
            this.description, // <--- ใช้ค่า Attribute ตรงๆ
            detected,
            this.condition,
            this.rarity
        );
    }
}

// Concrete Class
class Shirt extends ClothingItem {
    public Shirt(String name, String desc, double base, double cond, Rarity rare, Size size, boolean fake, double auth) {
        super(name, desc, base, cond, rare, size, fake, auth);
    }
}