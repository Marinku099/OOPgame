package GameItem;

import Enums.Rarity;
import Enums.Size;

public interface CalculateClothPrice {

    // --- Accessor Methods ---
    double getBasePrice();
    Rarity getRarity();
    Size getSize();
    double getCondition();
    boolean isFake();
    double getFakeAuthenticity();

    // --- Logic Methods (Default) ---
    // 1. ราคาประเมิน (ของแท้)
    default double calculateAppraisedValue() {
        return getBasePrice() 
                * getRarity().getMultiplier()
                * getSize().getMultiplier() 
                * getCondition();
    }

    // 2. ราคาจริง (คิดเรื่องของปลอม)
    default double calculateRealValue() {
        double appraised = calculateAppraisedValue();
        
        if (!isFake()) return appraised; 

        double fakeFactor = 0.05 + (getFakeAuthenticity() * 0.45);
        return appraised * fakeFactor;
    }

    // 3. ตรวจสอบว่ามองออกไหม (Detection)
    // เอาไปไว้ interface อื่น
    default boolean checkIfDetected(int viewerLevel) {
        if (!isFake()) return true; 

        // Level 1=25%, 2=50%, 3=75%, 4=100%
        double detectionPower = viewerLevel * 0.25;
        
        return detectionPower >= getFakeAuthenticity();
    }

    // 4. ราคาที่คนมองเห็น (Perceived Price)
    default double getPerceivedPrice(int viewerLevel) {
        boolean detected = checkIfDetected(viewerLevel);
        return detected ? calculateRealValue() : calculateAppraisedValue();
    }
}