import java.util.Random;

public abstract class NPC {
    // --- Attributes (ตรงตาม UML) ---
    protected String name;
    protected int knowledgeLevel;    // Level 1, 2, 3, 4
    protected int patience;          // ความอดทน
    protected double greedFactor;    // ความงก
    protected double perceivedValue; // ราคาที่ประเมินได้
    protected double negotiationLimit; // ราคาสูงสุด/ต่ำสุดที่รับได้
    protected Random rand;

    // --- Constructor ---
    public NPC(String name) {
        this.name = name;
        this.rand = new Random();

        //รอ DayManage
        generateStats(DayManager.getCurrentWeek());
    }

    // --- Method 1: คำนวณ Stats ตามสัปดาห์ ---
    // week รอ DayManage
    public void generateStats(int week) {
        this.knowledgeLevel = calculateLevelByWeek(week);
        this.greedFactor = calculateGreedByWeek(week);
        
        // สุ่มความอดทน (เช่น 2++ ครั้ง)
        this.patience = 2 + rand.nextInt(3); 
    }

    // 🔥 Method 2: Logic สุ่ม Level ตามเงื่อนไขใหม่ 🔥
    private int calculateLevelByWeek(int week) {
        if (week == 1) {
            // Week 1: ได้ Level 1 แน่นอน
            return 1;
        } 
        else if (week == 2) {
            // Week 2: สุ่ม Level 1 - 2
            // nextInt(2) ได้ 0,1 -> +1 กลายเป็น 1,2
            return rand.nextInt(2) + 1;
        } 
        else if (week == 3) {
            // Week 3: สุ่ม Level 1 - 3
            // nextInt(3) ได้ 0,1,2 -> +1 กลายเป็น 1,2,3
            return rand.nextInt(3) + 1;
        } 
        else {
            // Week 4+: สุ่ม Level 2 - 4
            // nextInt(3) ได้ 0,1,2 -> +2 กลายเป็น 2,3,4
            return rand.nextInt(3) + 2;
        }
    }

    private double calculateGreedByWeek(int week) {
        // สุ่ม Factor ความงกในช่วง 0.9 - 1.1
        return 0.9 + (rand.nextDouble() * 0.2);
    }

    // --- Method 3: ประเมินสินค้า (Main Logic) ---
    public void evaluateItem(ClothingItem item) {

        // 1. ถามราคาประเมินจาก Item (ส่งพลังการมองเห็นไป)
        this.perceivedValue = item.getPerceivedPrice(this.knowledgeLevel);

        // 2. เช็คของปลอม
        // ถ้าเป็นของปลอม AND ความเนียนสู้พลังเราไม่ได้ -> จับได้!
        if (item.isFake && item.checkIfDetected(this.knowledgeLevel)) {
            System.out.println(name + ": Wait... this looks FAKE!");
            this.perceivedValue = 0; // ราคาเหลือ 0 ทันที
        }

        // 3. คำนวณขอบเขตราคา (ให้ Class ลูกไปทำต่อ)
        calculateLimit();
    }

    // --- Abstract Methods (ต้องไปเขียนใน Seller/Buyer) ---
    protected abstract void calculateLimit();
    public abstract double getStartingOffer();
    public abstract String checkOffer(double offer);

    // --- Getters ---
    public String getName() { return name; }
    public int getKnowledgeLevel() { return knowledgeLevel; }
}