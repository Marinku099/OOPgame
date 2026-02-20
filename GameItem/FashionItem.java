package GameItem;

import Enums.ClothingType;
import Enums.Rarity;
import Enums.Size;

public abstract class FashionItem {

    public FashionItem(String name, String description, ClothingType type, Rarity rarity,
                       Size size, double condition, boolean isFake, double fakeAuthenticity) {
    }
}