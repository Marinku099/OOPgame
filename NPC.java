import java.util.Random;

public abstract class NPC {
    protected String name;
    protected int knowledgeLevel;
    protected int patience;
    protected double greedFactor;
    protected double perceivedValue;
    protected double negotiationLimit;
    protected double currentNegotiationPrice;
    protected Random rand;

    public NPC(String name) {
        this.name = name;
        this.rand = new Random();
    }

    // --- Generate Stats ---
    public void generateStats(int week) {
        this.knowledgeLevel = calculateLevelByWeek(week);
        
        // ✂️ จุดที่ 2: ยุบ Method ความงกมาไว้ตรงนี้เลย
        this.greedFactor = 0.9 + (rand.nextDouble() * 0.2); 
        
        this.patience = 2 + rand.nextInt(3); 
    }

    private int calculateLevelByWeek(int week) {
        if (week == 1) return 1;
        if (week == 2) return rand.nextInt(2) + 1; // 1-2
        if (week == 3) return rand.nextInt(3) + 1; // 1-3
        return rand.nextInt(3) + 2;                // 2-4 (Week 4+)
    }

    // --- Core Logic ---
    
    // ✂️ จุดที่ 1: ตัด calculateLimit() ออกจากตรงนี้
    public void evaluateItem(ClothingItem item) {
        // แปลง Level int เป็น double (0.25 - 1.0) ตรงนี้เลยก็ได้ถ้า ClothingItem ต้องการ double
        // หรือถ้า ClothingItem รับ int แล้วไปหารเองข้างใน ก็ส่ง int ไป
        this.perceivedValue = item.getPerceivedPrice(this.knowledgeLevel);

        if (item.isFake && item.checkIfDetected(this.knowledgeLevel)) {
            System.out.println(name + ": Wait... this looks FAKE!");
            this.perceivedValue = 0;
        }
        // ❌ ลบ calculateLimit() บรรทัดนี้ทิ้ง!
    }

    // Template Method: คุม Flow การเตรียมเจรจา
    public void prepareNegotiation(ClothingItem item) {
        evaluateItem(item);       // 1. ประเมิน
        calculateLimit();         // 2. คำนวณขอบเขต (เรียก Abstract)
        
        // 3. ตั้งราคาเริ่ม
        this.currentNegotiationPrice = getStartingOffer(); 
        
        System.out.println(name + " (" + this.getClass().getSimpleName() + ") เปิดราคา: " + currentNegotiationPrice);
    }

    // --- Abstracts ---
    protected abstract void calculateLimit();
    public abstract double getStartingOffer();
    public abstract String checkOffer(double offer);

    // --- Getters ---
    public String getName() { return name; }
    // ตัด getKnowledgeLevel ออกก็ได้ ถ้าหน้าบ้านไม่ได้เอาไปโชว์
    public double getCurrentNegotiationPrice() { return currentNegotiationPrice; }
}