package GUI;
import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import OOPGameCharacter.Player;

public class GameWindow extends JFrame{

    Dimension screenSize;
    int screenWidth;
    int screenHight;

    public GameWindow(Player player){
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = screenSize.width;
        this.screenHight = screenSize.height;

        String GameName = "Geme name";
        this.setTitle(GameName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settingLayout();

        this.setResizable(false);
        this.setSize(screenSize);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(800, 600)); // not sure yet
        this.setLocationRelativeTo(null);

        this.setVisible(true);

        // String logoPath = "path";
        // ImageIcon icon = new ImageIcon(logoPath);
        // this.setIconImage(icon.getImage());
        // this.getContentPane().setBackground(new Color(255, 255, 255));
    }

    private void settingLayout(){
        this.setLayout(new BorderLayout());

        // JPanel basePane = (JPanel) this.getContentPane();

        // basePane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }
}
