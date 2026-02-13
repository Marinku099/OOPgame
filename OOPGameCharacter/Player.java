package OOPGameCharacter;

import GameItem.ClothingItem;

public class Player extends GameCharacter {
    private int balance;
    private int luck;
    private Stock stock;

    public Player(String name, int knowledge, int balance, int luck) {
        super(name, knowledge);
        this.balance = balance;
        this.luck = luck;
        this.stock = new Stock();
    }
    
    // เริ่มการเจรจา (เลือกของ)
    public void sentOfferTo(NPC npc, ClothingItem item) {
        npc.receiveOffer(this,item);
    }

    // ต่อราคา (ซื้อ)
    public void buyItemFrom(SellerNPC seller , double price) {
        seller.processOffer(price, this);
    }

    // ต่อราคา (ขาย)
    public void sellItemTo(BuyerNPC buyer , double price) {
        buyer.processOffer(price, this);   
    }

    // Stock
    public Stock geStock() {
        return stock;
    }

    //Getter
    public int getBalance() {
        return balance;
    }

    public int getLuck() {
        return luck;
    }

    private void setBalance(int amount) {
        this.balance += amount;
    }

    private void addLuck() {
        this.luck += 1;
    }
}
