package GameItem;
// --- Concrete Class สำหรับใช้งานจริง ---
class FashionItem extends ClothingItem {
    public FashionItem(String name, String description, ClothingType type, Rarity rarity) {
        super(name, description, type, rarity);
    }
}