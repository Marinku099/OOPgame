package GUI;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import Enums.SkillType;
import GameSystem.ScoreManagement;
import OOPGameCharacter.Player;
import GUI.GameWindow;

public class test {
    public static void main(String[] args) {
        GameWindow a = new GameWindow();
        a.setVisible(true);
        // Player player = new Player("player", 5000);
        // // GameWindow gameWindow = new GameWindow(player);
        // JFrame frame = new JFrame();
        // ScoreManagement sm = new ScoreManagement(player);

        //  player.setBalance(-100);
        //  sm.updateBalanceByPlayer();
        //  player.setBalance(+390);
        //  sm.updateBalanceByPlayer();

        //  Map<SkillType, Integer> skills = new HashMap<>();
        //  skills.put(SkillType.LUCK, 1);
        //  skills.put(SkillType.KNOWLEDGE, 3);

        //  MainBackgroundPanel mainPanel = new MainBackgroundPanel();
        //  SkillUpgradePanel skilPane = new SkillUpgradePanel(skills, player);
        //  mainPanel.add(skilPane);

        //  skilPane.setUpgradeListener(cost -> {
        //      sm.updateBuying(cost);
        //  });

        //  gameWindow.add(mainPanel);

        //  SummaryPanel sum = new SummaryPanel(sm);

<<<<<<< Updated upstream
        // // gameWindow.add(sum);
        // // gameWindow.setVisible(true);
        // // frame.add(sum);
        // // frame.setTitle("Play On Wear , Trade & Talk");
        // // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // // frame.setVisible(true);
=======
        // gameWindow.add(sum);
        // gameWindow.setVisible(true);
        // frame.add(sum);
        // frame.setTitle("Play On Wear , Trade & Talk");
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setVisible(true);

        // java.net.URL imgUrl = skilPane.getClass().getResource("/GameElement/Status/AddButton.png");
        // if (imgUrl != null) System.out.println("found it!");
        // else System.out.println("nah TT");

        // new GameWindow();
    }
}
