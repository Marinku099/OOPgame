package GUI;

import java.util.HashMap;
import java.util.Map;

import Enums.SkillType;
import GameSystem.ScoreManagement;
import OOPGameCharacter.Player;

public class test {
    public static void main(String[] args) {
        // Player player = new Player("player", 1000);
        // GameWindow gameWindow = new GameWindow(player);
        // ScoreManagement sm = new ScoreManagement(player);

        // player.setBalance(-100);
        // sm.updateBalanceByPlayer();
        // player.setBalance(+390);
        // sm.updateBalanceByPlayer();

        // Map<SkillType, Integer> skills = new HashMap<>();
        // skills.put(SkillType.LUCK, 1);
        // skills.put(SkillType.KNOWLEDGE, 3);

        // MainBackgroundPanel mainPanel = new MainBackgroundPanel();
        // SkillUpgradePanel skilPane = new SkillUpgradePanel(skills, player);
        // mainPanel.add(skilPane);

        // skilPane.setUpgradeListener(cost -> {
        //     sm.updateBuying(cost);
        // });

        // gameWindow.add(mainPanel);

        // SummaryPanel sum = new SummaryPanel(sm);

        // gameWindow.add(sum);
        // gameWindow.setVisible(true);

        // java.net.URL imgUrl = skilPane.getClass().getResource("/GameElement/Status/AddButton.png");
        // if (imgUrl != null) System.out.println("found it!");
        // else System.out.println("nah TT");

        new GameWindow();
    }
}
