package DataBase;

import Enums.ClothingType;
import Enums.Rarity;

public class ItemData {
    private String name;
    private ClothingType type;
    private Rarity rarity;
    private String description;
    private String imagepath;

    public ItemData(String name, ClothingType type, Rarity rarity, String description, String imagepath) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.description = description;
        this.imagepath = imagepath;
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

    public String getImagepath() {
        return imagepath;
    }
}