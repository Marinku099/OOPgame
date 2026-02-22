package DataBase;

import java.util.List;
import java.util.Map;

import DataBase.Loader.ItemLoader;
import DataBase.Loader.NameLoader;
import Enums.Rarity;
import GameItem.ClothingItem;

public class Database {
    
    private Map<Rarity, List<ItemData>> itemMap;
    private List<String> customerNames;

    public Database(ItemLoader itemLoader, NameLoader nameLoader) {
        this.itemMap = itemLoader.loadItems();
        System.out.println("Item Database Loaded Successfully");

        this.customerNames = nameLoader.loadNames();
        System.out.println("Customer Names Loaded Loaded Successfully");
    }

    //ดูเฉพาะ rarity นั้นๆ
    public List<ItemData> getItemsByRarity(Rarity rarity) {
        return itemMap.get(rarity);
    }
    
    //ดูทุก items
    public Map<Rarity, List<ItemData>> getItemDataByRarityMap() {
        return itemMap;
    }

    //ดูทุกชื่อ Customer names
    public List<String> getAllCustomerNames() {
        return customerNames;
    }

    public List<ClothingItem> getListClothingItems(){
        return itemMap.values().stream().flatMap(List::stream).map(ItemData::toClothingItem).toList();
    }
}