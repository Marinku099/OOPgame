package GUI;

import java.util.HashMap;
import java.util.Map;

import Enums.SkillType;
import OOPGameCharacter.Player;

public class test {
    public static void main(String[] args) {
        Player player = new Player("player", 1, 1000, 1);
        GameWindow gameWindow = new GameWindow(player);
        Map<SkillType, Integer> skills = new HashMap<>();
        skills.put(SkillType.LUCK, 1);
        skills.put(SkillType.KNOWLEDGE, 3);

        MainBackgroundPanel mainPanel = new MainBackgroundPanel();
        SkillUpgradePanel skilPane = new SkillUpgradePanel(skills, player);
        mainPanel.add(skilPane);
        gameWindow.add(mainPanel);

        gameWindow.setVisible(true);

        // java.net.URL imgUrl = skilPane.getClass().getResource("/GameElement/Status/AddButton.png");
        // if (imgUrl != null) System.out.println("found it!");
        // else System.out.println("nah TT");
    }
}
