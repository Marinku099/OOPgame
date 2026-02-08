public abstract class NPC {
    protected String name;
    protected int knowledgeLevel; // Level ของ NPC (ยิ่งสูง ยิ่งดูของปลอมออก)
    protected int patience = 3;   // ความอดทนในการต่อรอง (จำนวนครั้ง)
    protected double greedFactor; // ตัวคูณความงก (เช่น 1.2)

    // ข้อมูลลับที่ NPC คิดในใจ (ผู้เล่นมองไม่เห็น)
    protected double perceivedValue;   // ราคาที่ NPC คิดว่าของชิ้นนี้มีค่าเท่าไหร่
    protected double negotiationLimit; // จุดลิมิตที่ NPC ยอมรับได้ (Floor/Ceiling)

    public NPC(String name, int level) {
        this.name = name;
        this.knowledgeLevel = level;
    }

    // ฟังก์ชันหลัก: ส่องของ -> ประเมินราคา -> ตั้งธงในใจ
    public void evaluateItem(ClothingItem item) {
        // NPC ใช้ Level ตัวเองไปส่องของ (ถ้า Level ถึงจะเห็นราคาจริง ถ้าไม่ถึงจะเห็นราคาหลอก)
        this.perceivedValue = item.getPerceivedPrice(this.knowledgeLevel);
        
        // คำนวณขอบเขตราคาที่รับได้ทันที
        calculateLimit(); 
    }

    public String getName() {
        return name;
    }

    // Abstract Methods ให้ลูกหลานไปกำหนดพฤติกรรมเอง
    protected abstract void calculateLimit();        // คำนวณจุดที่ยอมรับข้อเสนอได้
    public abstract double getStartingOffer();       // ราคาเปิด
    public abstract String checkOffer(double offer); // ตรวจสอบข้อเสนอผู้เล่น
}