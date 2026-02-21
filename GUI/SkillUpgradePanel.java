package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JPanel;

import Enums.SkillType;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class SkillUpgradePanel extends JPanel {

    Map<SkillType, Integer> skills;
    public SkillUpgradePanel(Map<SkillType, Integer> skills){
        this.setBackground(Color.gray);
        this.skills = skills;

        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setLayout(new GridLayout(0, 1, 10, 10));
        drawSkillLabels();
    }

    private void drawSkillLabels(){
        this.removeAll();

        for (Map.Entry<SkillType, Integer> entry : skills.entrySet()) {
            JLabel skillName = new JLabel(entry.getKey().getName());
            this.add(skillName);
        }
    }
}