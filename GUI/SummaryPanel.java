package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GameSystem.FontManagement;
import GameSystem.ScoreManagement;
import GameSystem.StatTracker;

public class SummaryPanel extends JPanel {
    private ScoreManagement sm;
    private PageNavigator navigator;

    private Image bgImage = new ImageIcon("GameElement\\Summary\\Summarysi.png").getImage();

    public SummaryPanel(ScoreManagement sm /* , PageNavigator nav*/){
        this.sm = sm;
        this.navigator = null /*nav*/;
        this.setLayout(null);

        buildStatLabelByDay(sm.getBuyAmount(), 430, 245);
        buildStatLabelByDay(sm.getSellAmount(), 890, 245);
        buildStatLabelByDay(sm.getBalance(), 430, 465);

        JButton nextButton = new JButton("Next");
        nextButton.setBackground(Color.white);
        nextButton.setFont(FontManagement.getFont("GameSystem\\Acme-Regular.ttf", 30f));
        nextButton.setBounds(1100, 600, 100, 50);
        nextButton.setFocusable(false);
        nextButton.addActionListener(e -> {
            if (navigator != null) {
                //TODO: แก้ชื่อให้ถูก
                navigator.navigateTo("MENU_PAGE");
            } else {
                System.out.println("ยังไม่ได้เสียบปลั๊กรีโมทโว้ย!");
            }
        });

        this.add(nextButton);

    }

    private void buildStatLabelByDay(StatTracker st, int x, int y) {
        double curr = st.getCurrent();
        double best = st.getBest();

        JLabel mainLabel = new JLabel(String.valueOf(curr));
        mainLabel.setFont(FontManagement.getFont("GameSystem\\Acme-Regular.ttf", 40f));
        mainLabel.setForeground(Color.white);
        mainLabel.setBounds(x, y, 300, 50);

        JLabel bestLabel = new JLabel(String.valueOf(best));
        bestLabel.setFont(FontManagement.getFont("GameSystem\\Acme-Regular.ttf", 25f));
        bestLabel.setForeground(Color.white);
        bestLabel.setBounds(x, y + 40, 300, 50);

        this.add(mainLabel);
        this.add(bestLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}
