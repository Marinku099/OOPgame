package DataBase.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataBase.ItemData;
import Enums.ClothingType;
import Enums.Rarity;

public class CsvItemLoader extends CsvLoader<Map<Rarity, List<ItemData>>> implements ItemLoader {

    public CsvItemLoader(String filePath) {
        super(filePath);
    }

    @Override
    public Map<Rarity, List<ItemData>> loadItems() {
        return loadFromFile();
    }

    @Override
    protected Map<Rarity, List<ItemData>> initResult() {
        Map<Rarity, List<ItemData>> map = new HashMap<>();
        for (Rarity r : Rarity.values()) {
            map.put(r, new ArrayList<>());
        }
        return map;
    }

    @Override
    protected void processLine(String line, Map<Rarity, List<ItemData>> result) {
        String[] row = line.split(",");
        if (row.length >= 4) {
            try {
                String name = row[0].trim();
                ClothingType type = ClothingType.valueOf(row[1].trim().toUpperCase());
                Rarity rarity = Rarity.valueOf(row[2].trim().toUpperCase());
                String description = row[3].trim();
                String imagepath = "Image/item/" + name + ".png";

                result.get(rarity).add(new ItemData(name, type, rarity,description,imagepath));
            } catch (IllegalArgumentException e) {
                System.out.println("Skip invalid line: " + line);
            }
        }
    }
}