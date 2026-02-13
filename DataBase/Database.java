package DataBase;

import java.util.List;
import java.util.Map;

import DataBase.Loader.ItemLoader;
import DataBase.Loader.NameLoader;
import Type.Rarity;

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
    public Map<Rarity, List<ItemData>> getAllDataItem() {
        return itemMap;
    }

    //ดูทุกชื่อ Customer names
    public List<String> getAllCustomerNames() {
        return customerNames;
    }
}

/*
// 1. ใส่ไฟล์ Path
DataLoader itemLoader = new CsvItemLoader("Csv/Game_OOP.csv");
NameLoader nameLoader = new CsvNameLoader("Csv/CustomerNames.csv");

// 2. สร้าง Database
Database db = new Database(itemLoader, nameLoader);

// 3. ใช้งานได้ (ตัวอย่าง)
List<String> names = db.getCustomerNames();
*/