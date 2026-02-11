package Type;
// --- 1. ประเภทสินค้าและราคาฐาน ---

enum ClothingType {
    T_SHIRT(100.0),
    HOODIE(130.0),
    JACKET(150.0),
    Sweater(110.0),
    Polo(100),
    CropTop(80),
    Camisole(30),
    Shirt(100);

    private final double defaultBasePrice;

    ClothingType(double price) {
        this.defaultBasePrice = price;
    }

    public double getBasePrice() {
        return this.defaultBasePrice;
    }
}