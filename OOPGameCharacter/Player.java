package OOPGameCharacter;
import java.util.ArrayList;

import GameItem.ClothingItem;

public class Player extends GameCharacter {
    private int balance;
    private int luck;
    private ArrayList<ClothingItem> items;

    public Player(String name, int knowledge, int balance, int luck) {
        super(name, knowledge);
        this.balance = balance;
        this.luck = luck;
        this.items = new ArrayList<>();
    }
    
    //ส่งข้อเสนอไปให้ item
    public void sentOfferTo(NPC npc, ClothingItem item) {
        npc.receiveOffer(this,item);
    }

    //ซื้อของจาก Seller
    public void buyItemFrom(Seller seller) {
        ClothingItem item = seller.sellItem();

        if (item != null) {
           int price = (int) item.calculateRealValue();
           if (balance >= price) {
            balance -= price;
            items.add(item);
           }
        }
    }

    //ขายของให้ Buyer
    public void sellItemTo(Buyer buyer) {
        if (!items.isEmpty()) {
            ClothingItem item = items.remove(0);
            balance += buyer.buyItem(item);
        }
    }

    private void setBalance(int n) {
        this.balance = n;
    }

    private void setLuck(int n) {
        this.luck = n;
    }
}
