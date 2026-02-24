package GameSystem;

import Enums.OfferState;
import OOPGameCharacter.Player;

public interface CalculateNPC {

    boolean isBuyer();
    double getLimit();
    double getGreed();
    int getCurrentOffer();
    void setCurrentOffer(int price);

    default double getStartingOffer() {
        if (isBuyer()) {
            return getLimit() * 0.6;
        } else {
            return getLimit() * 1.5;
        }
    }

    default double calculateSuccessChance(double playerPrice) {
        //double current = getCurrentOffer();
        //double range = Math.abs(getLimit() - current);
        double limit = getLimit();
        double diff = Math.abs(playerPrice - limit);

        double tolerance = limit * 0.25;
        if (diff >= tolerance) return 0;

        double baseChance = 1 - (diff / tolerance);


        // greed ยิ่งสูง → chance ยิ่งลด
        double greedPenalty = 1 - (getGreed() * 0.4);
        double finalChance = baseChance * greedPenalty;


        return Math.max(0, Math.min(1, finalChance));
    }

    default void recalculatePrice(double playerPrice) {
        int current = getCurrentOffer();
        double limit = getLimit();

        double diffRatio = Math.abs(current - playerPrice) / current;

        double baseStep = 0.02 + (diffRatio * 0.06);

        double noise = GameRNG.getRandomInt(-10, 10) / 1000.0;
        
        double step = (baseStep + noise) / getGreed();
        step = Math.max(0.01, Math.min(step, 0.10)); 

        double newPriceDouble;

        if (isBuyer()) {
            newPriceDouble = current + (current * step);
            if (newPriceDouble > limit) newPriceDouble = limit;
        } else {
            newPriceDouble = current - (current * step);
            if (newPriceDouble < limit) newPriceDouble = limit;
        }

        int newPrice = (int) Math.round(newPriceDouble);
        newPrice = Math.max(1, newPrice);

        setCurrentOffer((int) Math.round(newPrice));
    }

    OfferState processOffer(double playerPrice, Player player);
}