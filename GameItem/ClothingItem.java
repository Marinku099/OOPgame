package GameItem;

import Type.ClothingType;
import Type.Rarity;
import Type.Size;

// ลบ FakeStatsGeneratorByWeek และ UI ออก
public abstract class ClothingItem implements CalculateClothPrice {
    
    // --- Attributes ---
    protected String name;
    protected String description;
    protected ClothingType type;
    protected Rarity rarity;
    protected double basePrice;

    protected Size size;
    protected double condition;
    protected boolean isFake;
    protected double fakeAuthenticity;

    // --- Constructor ---
    public ClothingItem(String name, String description, ClothingType type, Rarity rarity, 
                        Size size, double condition, boolean isFake, double fakeAuthenticity) {
        
        this.name = name;
        this.description = description;
        this.type = type;
        this.rarity = rarity;
        this.basePrice = type.getBasePrice();

        // ค่าพวกนี้ควรสุ่มมาจากข้างนอก แล้วส่งเข้ามา
        this.size = size;
        this.condition = condition;
        this.isFake = isFake;
        this.fakeAuthenticity = fakeAuthenticity;
    }

    // --- Implement: CalculateClothPrice (Getters) ---
    @Override public double getBasePrice() { return basePrice; }
    @Override public Rarity getRarity() { return rarity; }
    @Override public Size getSize() { return size; }
    @Override public double getCondition() { return condition; }
    @Override public boolean isFake() { return isFake; }
    @Override public double getFakeAuthenticity() { return fakeAuthenticity; }

    // --- Getters ทั่วไป ---
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ClothingType getType() { return type; }
}

/*
อาจจะอยู่ใน คลาสที่ใช้สร้าง Item
public void spawnDailyItems(int week) {
    GameRNG rng = GameRNG.getInstance();
    
    // 1. สุ่มค่าต่างๆ ข้างนอก Item
    boolean isFake = rng.genIsFake(week);
    double authenticity = isFake ? rng.genFakeAuthenticity(week) : 0.0;
    Size size = rng.genSize();
    double condition = rng.genCondition();

    // 2. สร้าง Item ที่สมบูรณ์แล้ว (ไม่ต้องไปสั่ง generate อีก)
    ClothingItem newItem = new FashionItem(ชื่อ จาก csv, Type จาก csv, Rarity จาก csv, Description จาก csv
    , size สุ่ม, condition สุ่ม, isFake สุ่ม, authenticity สุ่ม);
}
*/