package DataBase;

import Enums.ClothingType;
import Enums.Rarity;
import GameItem.ClothingItem;

public class ItemData {
    private String name;
    private ClothingType type;
    private Rarity rarity;
    private String description;
    private String imagePath;

    public ItemData(String name, ClothingType type, Rarity rarity, String description, String imagepath) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.description = description;
        this.imagePath = imagepath;
    }

    public String getName() {
        return name;
    }

    public ClothingType getType() {
        return type;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ClothingItem toClothingItem(){
        return new ClothingItem(this);
    }
}