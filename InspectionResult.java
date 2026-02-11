import Type.Rarity;

public class InspectionResult {
    // ใช้ public final เพื่อให้อ่านค่าได้อย่างเดียว (Immutable) ปลอดภัยและเขียนสั้น
    public final double displayPrice;   // ราคาที่ผู้เล่นเห็น (อาจเป็นราคาหลอกถ้าดูไม่ออก)
    public final String name;           // ชื่อไอเทม
    public final String description;    // คำอธิบาย (อาจมีข้อความเตือนถ้าจับโป๊ะได้)
    public final boolean isFakeDetected;// จับได้ไหมว่าเป็นของปลอม (True = จับได้)
    public final double condition;      // สภาพสินค้า (0.0 - 1.0)
    public final Rarity rarity;         // ระดับความหายาก
    public final Size size;             // ขนาด

    // Constructor รับค่าทุกอย่างมาเก็บไว้
    public InspectionResult(double displayPrice, 
                            String name, 
                            String description, 
                            boolean isFakeDetected, 
                            double condition, 
                            Rarity rarity, 
                            Size size) {
        this.displayPrice = displayPrice;
        this.name = name;
        this.description = description;
        this.isFakeDetected = isFakeDetected;
        this.condition = condition;
        this.rarity = rarity;
        this.size = size;
    }

    // (Optional) toString สำหรับ Debug ใน Console
    @Override
    public String toString() {
        return "InspectionResult {" +
                "Price=" + (int)displayPrice +
                ", Name='" + name + '\'' +
                ", FakeDetected=" + isFakeDetected +
                ", Rarity=" + rarity +
                ", Size=" + size +
                '}';
    }
}