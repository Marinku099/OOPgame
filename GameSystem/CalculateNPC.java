package GameSystem;

import OOPGameCharacter.Player;

public interface CalculateNPC {
    void calculateLimit();
    double getStartingOffer();
    void processOffer(double playerOffer, Player player);
    void recalculatePrice(double playerOffer);
    double calculateSuccessChance(double playerOffer);
}