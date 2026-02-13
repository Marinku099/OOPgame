package DataBase.Loader;

import java.util.List;
import java.util.Map;

import DataBase.ItemData;
import Type.Rarity;

public interface ItemLoader {
    Map<Rarity, List<ItemData>> loadItems();
}