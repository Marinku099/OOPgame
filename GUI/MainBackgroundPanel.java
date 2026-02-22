package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class MainBackgroundPanel extends JPanel{

    Border innerPadding;
    public MainBackgroundPanel(){
        this.setBackground(Color.cyan);
        this.setLayout(new BorderLayout());
        this.innerPadding = BorderFactory.createEmptyBorder(70,70,70,70);
        // create margin ***change number later
        this.setBorder(innerPadding);
        // drawHeader("Skill Upgrade");
    }

    private void drawHeader(String name){
        TitledBorder headerLabel = BorderFactory.createTitledBorder(
            innerPadding,
            name,
            TitledBorder.CENTER,
            TitledBorder.TOP
        );

        headerLabel.setTitleFont(new Font("Times New Roman", Font.BOLD, 50));
        this.setBorder(headerLabel);
    }
}
