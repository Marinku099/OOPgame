package GUI;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameWindow extends JFrame{

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    public GameWindow(String GameName, String logoPath){
        
        this.setTitle(GameName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.setSize(screenSize);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(800, 600)); // not sure yet
        this.setLocationRelativeTo(null);

        this.setVisible(true);

        ImageIcon icon = new ImageIcon(logoPath);
        this.setIconImage(icon.getImage());
        // this.getContentPane().setBackground(new Color(255, 255, 255));
    }
}
