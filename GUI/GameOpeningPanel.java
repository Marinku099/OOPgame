package GUI;

import javax.swing.*;
import java.awt.*;

// หน้าเปิดเกม (MENU)
public class GameOpeningPanel extends JPanel {

    private GameWindow mainFrame;
    private Image bgImage;

    public GameOpeningPanel(GameWindow frame) {

        this.mainFrame = frame;

        setLayout(new GridBagLayout());

        // =========================
        // ===== โหลดพื้นหลัง =====
        // =========================
        bgImage = new ImageIcon("GameElement/StartGame/bg_start.png").getImage();

        // =========================
        // ===== โหลดปุ่ม Start =====
        // =========================
        ImageIcon startIcon =
                new ImageIcon("GameElement/StartGame/button_start.png");

        // ===== ปรับขนาดปุ่ม (30%) =====
        double scale = 0.3;

        int newWidth  = (int) (startIcon.getIconWidth() * scale);
        int newHeight = (int) (startIcon.getIconHeight() * scale);

        Image scaledImage = startIcon.getImage()
                .getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // =========================
        // ===== สร้างปุ่ม =====
        // =========================
        JButton btnStart = new JButton(scaledIcon);

        btnStart.setBorderPainted(false);
        btnStart.setContentAreaFilled(false);
        btnStart.setFocusPainted(false);
        btnStart.setOpaque(false);
        btnStart.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ใช้ขนาด "หลัง scale"
        btnStart.setPreferredSize(new Dimension(newWidth, newHeight));

        // กดแล้วไปหน้า Shop
        btnStart.addActionListener(e ->
                mainFrame.showScreen("ShopPanel")
        );

        // =========================
        // ===== จัดตำแหน่ง =====
        // =========================
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(250, 0, 0, 0); // ปรับเลขนี้เพื่อเลื่อนลง

        add(btnStart, gbc);
    }

    // =========================
    // ===== วาดพื้นหลัง =====
    // =========================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0,
                    getWidth(), getHeight(), this);
        }
    }
}