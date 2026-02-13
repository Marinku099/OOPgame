package Type;
// --- 1. ประเภทสินค้าและราคาฐาน ---

public enum ClothingType {
    Camisole(50),
    CropTop(120),
    T_SHIRT(200),
    Shirt(200),
    Polo(250),
    Sweater(350),
    HOODIE(450),
    JACKET(550);

    private final double defaultBasePrice;

    ClothingType(double price) {
        this.defaultBasePrice = price;
    }

    public double getBasePrice() {
        return this.defaultBasePrice;
    }
}