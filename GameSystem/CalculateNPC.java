package GameSystem;

import OOPGameCharacter.Player;

public interface CalculateNPC {

    boolean isBuyer();
    double getLimit();
    double getGreed();
    double getCurrentOffer();
    void setCurrentOffer(double price);

    default double getStartingOffer() {
        if (isBuyer()) {
            return getLimit() * 0.6;
        } else {
            return getLimit() * 1.5;
        }
    }

    default double calculateSuccessChance(double playerPrice) {
        double current = getCurrentOffer();
        double range = Math.abs(getLimit() - current);
        double diff = Math.abs(playerPrice - current);

        if (range <= 0) return 0;

        double chance = (range - diff) / range;
        return Math.max(0, chance);
    }

    default void recalculatePrice(double playerPrice) {
        double current = getCurrentOffer();
        double diff = Math.abs(current - playerPrice);
        
        double step = GameRNG.getInstance().genNegotiationStep();
        double change = (diff * step) / getGreed();

        double newPrice;
        double limit = getLimit();

        if (isBuyer()) {
            newPrice = current + change;
            if (newPrice > limit) newPrice = limit;
        } else {
            newPrice = current - change;
            if (newPrice < limit) newPrice = limit;
        }

        setCurrentOffer(newPrice);
    }

    void processOffer(double playerPrice, Player player);
}