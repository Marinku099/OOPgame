package Type;
// --- 1. ประเภทสินค้าและราคาฐาน ---

public enum ClothingType {
    Camisole(50),
    Tops(120),
    Blouse(200),
    T_SHIRT(200),
    Shirt(200),
    Polo(250),
    Vest(330),
    Sweater(350),
    Cardigan(350),
    Tracktop(400),
    HOODIE(450),
    JACKET(550),
    Dress(470),
    Coat(500);

    private final double defaultBasePrice;

    ClothingType(double price) {
        this.defaultBasePrice = price;
    }

    public double getBasePrice() {
        return this.defaultBasePrice;
    }
}