package OOPGameCharacter;

import java.util.ArrayList;
import GameItem.ClothingItem;

public class Stock {
    private ArrayList<ClothingItem> items;

    public Stock() {
        this.items = new ArrayList<>();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void addItem(ClothingItem item) {
        items.add(item);
    }

    public ClothingItem removeItem(int index) {
        if (items.isEmpty())
            return null;
            return items.remove(index);
    }

    public ArrayList<ClothingItem> getItems() {
        return items;
    }
}
