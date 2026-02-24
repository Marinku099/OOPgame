package OOPGameCharacter;

import Enums.OfferState;
import Enums.SkillType;
import GameItem.ClothingItem;
import java.util.HashMap;
import java.util.Map;

public class Player extends GameCharacter {
    private static final int MAX_LV = 5;
    private int balance;
    private int luck = 1;
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

    public Player(String name, int balance) {
        super(name, 0);
        this.balance = balance;
        this.stock = new Stock();

        for (SkillType skill : SkillType.values()) {
            SkillLevel.put(skill, 0);
        }
    }

    public int getSkillLevel(SkillType skill){
        return SkillLevel.get(skill);
    }

    public boolean updateSkillLevel(SkillType skill, int updateValue){
        if (getSkillLevel(skill) + updateValue > MAX_LV) return false;
        SkillLevel.computeIfPresent(skill, (k, v) -> v += updateValue);
        return true;
    }

    public static int getMaxLevel(){
        return Player.MAX_LV;
    }
    
    // เริ่มการเจรจา (เลือกของ)
    // public void sentOfferTo(NPC npc, ClothingItem item) {
    //     npc.receiveOffer(this,item);
    // }

    // ต่อราคา (ซื้อ)
    public OfferState buyItemFrom(SellerNPC seller , double price) {
        if (price <= 0) {
            System.out.println("ราคาต้องมากกว่า 0");
            return OfferState.FAIL;
        }
        seller.receiveOffer(this);
        return seller.processOffer(price, this);
    }

    // ต่อราคา (ขาย)
    public OfferState sellItemTo(BuyerNPC buyer , double price) {
        if (price <= 0) {
            System.out.println("ราคาต้องมากกว่า 0");
            return OfferState.FAIL;
        }
        buyer.receiveOffer(this);
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

    public Map<SkillType, Integer> getSkillLevel() {
        return SkillLevel;
    }

    private void setBalance(double amount) {
        this.balance += amount;
    }

    private void addLuck() {
        this.luck += 1;
    }

    /*public void updateBalance(OfferState state, NPC npc, double finalPrice){
        if (state != OfferState.SUCCESS) return;
        
        if (npc instanceof BuyerNPC) balance += finalPrice;
        else balance -= finalPrice;
    }

    public void levelUpSkill(SkillType skill , int level) {
        if (level <= 0) return;

        int current = SkillLevel.getOrDefault(skill, 0);
        SkillLevel.put(skill, current + level);
    }*/

    public void completeBuy(ClothingItem item, double price) {
        if (balance < price) return;

        this.balance -= price;
        this.stock.addItem(item);
    }

    public void completeSell(ClothingItem item, double price) {
        this.balance += price;
        this.stock.removeItem(item);
    }

    public void buyItem(long sumCost) {
        setBalance(-sumCost);

    }

    public void updateSkillLevel(Map<SkillType,Integer> skillsLevel) {
        for (Map.Entry<SkillType, Integer> entry : skillsLevel.entrySet()) {
            SkillLevel.computeIfPresent(entry.getKey(), (k, v) -> v += entry.getValue());
        }
    }
}
