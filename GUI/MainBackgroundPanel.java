package GUI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MainBackgroundPanel extends JPanel{

    public MainBackgroundPanel(){
        this.setBackground(Color.BLUE);
        this.setLayout(new BorderLayout());
        // create margin ***change number later
        this.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }
}
