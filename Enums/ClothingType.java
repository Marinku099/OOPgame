package Enums;
// --- 1. ประเภทสินค้าและราคาฐาน ---

public enum ClothingType {
    Camisole(110),
    Tops(180),
    Blouse(260),
    T_SHIRT(260),
    Shirt(260),
    Polo(310),
    Vest(390),
    Sweater(410),
    Cardigan(410),
    Tracktop(460),
    HOODIE(510),
    JACKET(610),
    Dress(530),
    Coat(560);

    private final double defaultBasePrice;

    ClothingType(double price) {
        this.defaultBasePrice = price;
    }

    public double getBasePrice() {
        return this.defaultBasePrice;
    }
}