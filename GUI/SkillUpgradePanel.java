package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import Enums.SkillType;
import GameSystem.FontManagement;
import OOPGameCharacter.Player;

public class SkillUpgradePanel extends JPanel {
    private Border innerPadding;
    private Border lineBorder;
    // private Border compoundBorder;
    private Map<SkillType, Integer> skills;
    // private Map<String, JTextField> skillLevelInp;
    private Player player;

    public SkillUpgradePanel(Map<SkillType, Integer> skills, Player player){
        this.setBackground(Color.white);
        this.skills = skills;
        this.player = player;
        this.innerPadding = BorderFactory.createEmptyBorder(120, 50, 70, 50);
        this.lineBorder = BorderFactory.createLineBorder(Color.black, 30);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        drawAllSkillBox();
        drawHeader("Skill Upgrade");

        
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

        skillBox.setLayout(new GridBagLayout());
        skillBox.setPreferredSize(new Dimension(Integer.MAX_VALUE, 200));
        skillBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        skillBox.setBackground(Color.decode("#e9e8df"));

        drawLabel(skillBox, name);
        drawNumberScanner(skillBox, name);

        this.add(skillBox);
    }

    //TODO: สร้างปุ่ม Submit ว่าซื้อแล้วนะ + อัปเดตเลเวลผู้เล่น

    private void updateCostLabel(JPanel box, String name){
        //TODO: รองรับการสร้างปุ่ม costLabel แต่ตอนนี้ยัดใส่ drawNumberScanner ไปก่อน
    }

    private void drawNumberScanner(JPanel box, String name){

        SkillType skill = SkillType.getByName(name);
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 0));
        spinnerPanel.setOpaque(false);

        //TODO: ตกแต่ง costLabel
        JLabel costLabel = new JLabel("0");


        // ==== TextField ====
        JTextField inp = new JTextField("0", 2);
        inp.setFont(FontManagement.getFont("GameSystem\\Acme-Regular.ttf", 30f));
        inp.setHorizontalAlignment(JTextField.CENTER);
        inp.setPreferredSize(new Dimension(50, 50));

        AbstractDocument doc = (AbstractDocument) inp.getDocument();
        doc.setDocumentFilter(new IntLimitFilter(0, 99));

        inp.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateSkillCost();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateSkillCost();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateSkillCost();
            }

            private void calculateSkillCost() {
                //TODO: ดัก maximum level
                String text = inp.getText().trim();

                if (text.isEmpty()) {
                    costLabel.setText("0");
                    return ;
                }

                int inputLv = Integer.parseInt(text);
                int currentLv = player.getSkillLevel(skill);
                int lvToUpgrade = currentLv + inputLv;

                if (inputLv == 0) {
                    costLabel.setText("0");
                }
                else {
                    long currentLvCost = costFromOneLevel(currentLv);
                    long lvToUpgradeCost = costFromOneLevel(lvToUpgrade);
                    costLabel.setText(String.valueOf(lvToUpgradeCost - currentLvCost));
                }
            }

            private long costFromOneLevel(long level){
                long squares = (level * (level + 1) * (2 * level + 1)) / 6;
                long integer = (level * (level + 1)) / 2;
                long constant = level;

                int A = skill.getCost();
                int B = 1;
                int C = 67;
                return (A * squares) + (B * integer) + (C * constant);
            }
        });

        // ==== plusButton ====
        JButton plusButton = new JButton();
        plusButton.setFocusable(false);
        plusButton.setPreferredSize(new Dimension(50, 50));

        ImageIcon BottonIcon = new ImageIcon(this.getClass().getResource("/GameElement/Status/AddButton.png"));
        Image IconScaled = BottonIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        plusButton.setIcon(new ImageIcon(IconScaled));

        plusButton.setBorderPainted(false);
        plusButton.setContentAreaFilled(false);
        plusButton.setFocusPainted(false);
        plusButton.addActionListener(e -> {
            int val = inp.getText().isEmpty() ? 0 : Integer.parseInt(inp.getText());
            if (val < 99) inp.setText(String.valueOf(val + 1));
        });


        // ==== minusButton ====
        JButton minusButton = new JButton();
        minusButton.setFocusable(false);
        minusButton.setPreferredSize(new Dimension(50, 50));

        BottonIcon = new ImageIcon(this.getClass().getResource("/GameElement/Status/MinusButton.png"));
        IconScaled = BottonIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        minusButton.setIcon(new ImageIcon(IconScaled));

        minusButton.setBorderPainted(false);
        minusButton.setContentAreaFilled(false);
        minusButton.addActionListener(e -> {
            int val = inp.getText().isEmpty() ? 0 : Integer.parseInt(inp.getText());
            if (val > 0) inp.setText(String.valueOf(val - 1));
        });
        

        // ==== merge components ====
        spinnerPanel.add(minusButton);
        spinnerPanel.add(inp);
        spinnerPanel.add(plusButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 30, 0, 30);
        gbc.anchor = GridBagConstraints.EAST;

        box.add(spinnerPanel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        box.add(costLabel, gbc);
    }

    private void drawLabel(JPanel box, String name){
        JLabel label = new JLabel(name);

        label.setFont(FontManagement.getFont("GameSystem\\Acme-Regular.ttf", 50f));
        label.setForeground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 30, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;

        box.add(label, gbc);
    }

    private void drawHeader(String name){
        TitledBorder headerLabel = BorderFactory.createTitledBorder(
            innerPadding,
            name,
            TitledBorder.CENTER,
            TitledBorder.TOP
        );

        headerLabel.setTitleFont(FontManagement.getFont("GameSystem\\Acme-Regular.ttf", 70f));
        this.setBorder(/* this.compoundBorder = */ BorderFactory.createCompoundBorder(this.lineBorder, headerLabel));
    }
}