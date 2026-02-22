package GUI;

import java.util.HashMap;
import java.util.Map;

import Enums.SkillType;
import OOPGameCharacter.Player;

public class test {
    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow(new Player("playeBorderLayout.CENTERr", 1, 1000, 1));
        Map<SkillType, Integer> skills = new HashMap<>();
        skills.put(SkillType.LUCK, 1);
        skills.put(SkillType.KNOWLEDGE, 3);

        MainBackgroundPanel mainPanel = new MainBackgroundPanel();
        mainPanel.add(new SkillUpgradePanel(skills));
        gameWindow.add(mainPanel);

        gameWindow.setVisible(true);
    }
}
