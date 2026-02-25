package Enums;
// --- 1. ประเภทสินค้าและราคาฐาน ---

public enum ClothingType {
    CAMISOLE(110),
    TOPS(180),
    BLOUSE(260),
    T_SHIRT(260),
    SHIRT(260),
    POLO(310),
    VEST(390),
    SWEATER(410),
    CARDIGAN(410),
    TRACKTOP(460),
    HOODIES(510),
    JACKET(610),
    DRESS(530),
    COAT(560);

    private final double defaultBasePrice;

    ClothingType(double price) {
        this.defaultBasePrice = price;
    }

    public double getBasePrice() {
        return this.defaultBasePrice;
    }
}