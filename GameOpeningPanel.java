package GUI; // อยู่ใน package GUI

import javax.swing.*; // ใช้ JPanel, JButton, ImageIcon
import java.awt.*;    // ใช้ Layout, Graphics, Cursor

// คลาสหน้าเปิดเกม (หน้า MENU)
public class GameOpeningPanel extends JPanel {

    private GameWindow mainFrame; // เก็บ reference ของหน้าต่างหลัก
    private Image bgImage;        // ตัวแปรเก็บรูปพื้นหลัง

    // Constructor รับ GameWindow เข้ามา
    public GameOpeningPanel(GameWindow frame) {

        this.mainFrame = frame; // จำหน้าต่างหลักไว้ เพื่อใช้สั่งเปลี่ยนหน้า

        // ใช้ GridBagLayout เพื่อจัดปุ่มให้อยู่ตรงกลาง
        setLayout(new GridBagLayout());

        // โหลดรูปพื้นหลัง
        bgImage = new ImageIcon("home/tarwws/OOPgame/GameElement/StartGame/bg_start.png").getImage();

        // โหลดรูปปุ่ม Start
        ImageIcon startIcon = new ImageIcon("home/tarwws/OOPgame/GameElement/StartGame/button_start.png");

        // กำหนดสเกล (0.6 = 60%)
        double scale = 0.3;

        int newWidth = (int)(startIcon.getIconWidth() * scale);
        int newHeight = (int)(startIcon.getIconHeight() * scale);

        // ย่อภาพ
        Image scaledImage = startIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // สร้างปุ่มโดยใช้รูปแทนข้อความ
        JButton btnStart = new JButton(scaledIcon);

        // ปิดทุกอย่างที่ทำให้ดูเป็นปุ่ม Swing ปกติ
        btnStart.setBorderPainted(false);
        btnStart.setContentAreaFilled(false);
        btnStart.setFocusPainted(false);
        btnStart.setOpaque(false);

        // เปลี่ยน cursor เป็นมือ
        btnStart.setCursor(new Cursor(Cursor.HAND_CURSOR));

       // ใช้ขนาดจริงของรูปเท่านั้น
        btnStart.setPreferredSize(new Dimension(
        startIcon.getIconWidth(),
        startIcon.getIconHeight()
        ));

        // เปลี่ยนเมาส์เป็นรูปมือเวลาชี้
        btnStart.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // เมื่อกดปุ่ม ให้สั่ง GameWindow เปลี่ยนไปหน้า GAME
        btnStart.addActionListener(e -> mainFrame.showScreen("ShopPanel"));


        GridBagConstraints gbc = new GridBagConstraints();

        // กำหนดตำแหน่งแกน X (0 = ตรงกลางเพราะมีแค่ชิ้นเดียว)
        gbc.gridx = 0;

        // กำหนดตำแหน่งแกน Y (เลขมากขึ้น = ลงล่าง)
        gbc.gridy = 1;

        // ดัน component ลงด้านล่าง
        gbc.anchor = GridBagConstraints.SOUTH;

        // เพิ่มช่องว่างด้านบน (ยิ่งเลขมาก ปุ่มยิ่งลงล่าง)
        gbc.insets = new Insets(200, 0, 0, 0);

        add(btnStart, gbc);
    }

    // เมธอดนี้ถูกเรียกอัตโนมัติทุกครั้งที่ panel ต้องวาดใหม่
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g); // ล้างพื้นหลังเก่าก่อนวาดใหม่ (สำคัญมาก)

        // วาดรูปพื้นหลังให้เต็ม panel
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}