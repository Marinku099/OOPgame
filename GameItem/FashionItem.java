package GameItem;

import Enums.ClothingType;
import Enums.Rarity;
import Enums.Size;

public class FashionItem extends ClothingItem {

    public FashionItem(String name, String description, ClothingType type, Rarity rarity,
                       Size size, double condition, boolean isFake, double fakeAuthenticity) {
        
        super(name, description, type, rarity, size, condition, isFake, fakeAuthenticity);
    }
}