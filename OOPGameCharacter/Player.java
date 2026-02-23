package OOPGameCharacter;

import java.util.HashMap;
import java.util.Map;

import Enums.OfferState;
import Enums.SkillType;

public class Player extends GameCharacter {
    private int balance;
    private int luck;
    private Stock stock;
    Map<SkillType, Integer> SkillLevel = new HashMap<>();

    public Player(String name, int knowledge, int balance, int luck) {
        super(name, knowledge);
        this.balance = balance;
        this.luck = luck;
        this.stock = new Stock();

        for (SkillType skill : SkillType.values()) {
            SkillLevel.put(skill, 0);
        }
    }

    public int getSkillLevel(SkillType skill){
        return SkillLevel.get(skill);
    }


    
    // เริ่มการเจรจา (เลือกของ)
    // public void sentOfferTo(NPC npc, ClothingItem item) {
    //     npc.receiveOffer(this,item);
    // }

    // ต่อราคา (ซื้อ)
    public OfferState buyItemFrom(SellerNPC seller , double price) {
        return seller.processOffer(price, this);
    }

    // ต่อราคา (ขาย)
    public OfferState sellItemTo(BuyerNPC buyer , double price) {
        return buyer.processOffer(price, this);   
    }

    // getter อาจจะไม่ต้อง public หมด, encapsulate ไว้บ้างก็ดีตามความเหมาะสม

    //Getter
    public int getBalance() {
        return balance;
    }

    public int getLuck() {
        return luck;
    }

    public Stock getStock() {
        return this.stock;
    }

    private void setBalance(double amount) {
        this.balance += amount;
    }

    private void addLuck() {
        this.luck += 1;
    }

    public void updateBalance(OfferState state, NPC npc){
        if (state != OfferState.SUCCESS) return;
        
        if (npc instanceof BuyerNPC) setBalance(npc.getCurrentOffer());
        else setBalance(-npc.getCurrentOffer());
    }
}
