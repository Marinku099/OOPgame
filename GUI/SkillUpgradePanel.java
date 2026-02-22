package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import java.util.Map;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Enums.SkillType;
import GameSystem.FontManagement;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class SkillUpgradePanel extends JPanel {
    private Border innerPadding;
    private Border lineBorder;
    private Border compoundBorder;
    Map<SkillType, Integer> skills;

    public SkillUpgradePanel(Map<SkillType, Integer> skills){
        this.setBackground(Color.white);
        this.skills = skills;
        this.innerPadding = BorderFactory.createEmptyBorder(120, 50, 70, 50);
        this.lineBorder = BorderFactory.createLineBorder(Color.black, 30);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        drawAllSkillBox();
        drawHeader("Skill Upgrade");

        // this.setBorder(this.compoundBorder = BorderFactory.createCompoundBorder(compoundBorder, this.innerPadding));
    }

    private void drawAllSkillBox(){
        // this.removeAll();

        for (Map.Entry<SkillType, Integer> entry : skills.entrySet()) {
            drawSkillBox(entry.getKey().getName());
            this.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        this.add(Box.createVerticalGlue());
        this.revalidate();
        this.repaint();
    }

    private void drawSkillBox(String name){
        RoundBox skillBox = new RoundBox(30);

        skillBox.setLayout(new BorderLayout());
        skillBox.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
        // skillBox.setMaximumSize(new Dimension(1000, 100));
        skillBox.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        skillBox.setBackground(Color.decode("#e9e8df"));

        JLabel skillName = new JLabel(name);
        skillName.setFont(FontManagement.getFont("GameSystem\\Acme-Regular.ttf", 50f));
        skillName.setForeground(Color.BLACK);
        skillBox.add(skillName, BorderLayout.WEST);

        this.add(skillBox);
    }

    private void drawHeader(String name){
        TitledBorder headerLabel = BorderFactory.createTitledBorder(
            innerPadding,
            name,
            TitledBorder.CENTER,
            TitledBorder.TOP
        );

        headerLabel.setTitleFont(FontManagement.getFont("GameSystem\\Acme-Regular.ttf", 70f));
        this.setBorder(this.compoundBorder = BorderFactory.createCompoundBorder(this.lineBorder, headerLabel));
        // this.setBorder(headerLabel);
    }
}