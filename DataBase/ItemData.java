package DataBase;

import Type.ClothingType;
import Type.Rarity;

public class ItemData {
    public String name;
    public ClothingType type;
    public Rarity rarity;
    public String description;
    public String imagepath;

    public ItemData(String name, ClothingType type, Rarity rarity, String description, String imagepath) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.description = description;
        this.imagepath = imagepath;
    }
}