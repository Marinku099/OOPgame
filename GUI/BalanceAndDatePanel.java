package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import GameSystem.TimeManagement;
import OOPGameCharacter.Player;

public class BalanceAndDatePanel extends JPanel {
    private Player player;
    private JLabel lblBalance;
    private JLabel lblTitle;
    private JLabel lblDate;

    public BalanceAndDatePanel(Player player) {
        this.player = player;
        initUI();
    }

    private void initUI() {
        setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false); // ต้องใส่นะ ไม่งั้นวาดรูปทรงแปลกๆ แล้วพื้นหลังมันจะทึบเป็นสี่เหลี่ยม

        lblDate = new JLabel();
        lblDate.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblDate.setForeground(new Color(80, 80, 80)); // สีเทาเข้มๆ ให้ดูตัดกับเงิน
        lblDate.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ตัวเลขเงิน
        lblBalance = new JLabel();
        lblBalance.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblBalance.setForeground(new Color(46, 139, 87)); // สีเขียวเข้มแบบดูแพง
        lblBalance.setAlignmentX(Component.CENTER_ALIGNMENT);

        updateData(); 

        // add(lblTitle);
        add(lblDate);
        add(Box.createVerticalStrut(5));
        add(lblBalance);
    }

    // เมธอดนี้สำคัญมาก! เอาไว้ให้ระบบเรียกใช้เมื่อเงินมีการเปลี่ยนแปลง
    public void updateData() {
        if (player != null) {
            // ดึงค่า Balance จาก Player มาแสดงผล
            lblBalance.setText("Balance: $" + player.getBalance());
        }

        int week = TimeManagement.getInstance().getWeek();
        int day = TimeManagement.getInstance().getDay();
        lblDate.setText("Week " + week + " - Day " + day);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // [ข้อควรระวัง] ห้ามลืมบรรทัดนี้เด็ดขาด ไม่งั้น UI เละ
        super.paintComponent(g); 
        
        Graphics2D g2 = (Graphics2D) g.create();
        // เปิดโหมดลบรอยหยัก ให้เส้นโค้งและกราฟิกดูเนียนตาขึ้น
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // 1. วาดฐานปฏิทินด้านหลัง (สีเทาเข้ม/ดำ)
        g2.setColor(new Color(40, 40, 40)); 
        g2.fillRoundRect(5, 15, w - 10, h - 15, 15, 15);

        // 2. วาดกระดาษปฏิทิน (สีขาวครีม)
        g2.setColor(new Color(250, 250, 245)); 
        g2.fillRoundRect(5, 20, w - 10, h - 25, 10, 10);
        
        // ตีเส้นขอบกระดาษบางๆ ให้ดูมีมิติ
        g2.setColor(new Color(200, 200, 200));
        g2.drawRoundRect(5, 20, w - 10, h - 25, 10, 10);

        // // 3. วาดแถบหัวกระดาษ (สีแดงแบบปฏิทินจีน/ตั้งโต๊ะทั่วไป)
        // g2.setColor(new Color(220, 50, 50)); 
        // // วาดแค่ส่วนบนของกระดาษ
        // g2.fillRoundRect(6, 21, w - 12, 18, 8, 8);
        // g2.fillRect(6, 29, w - 12, 10); // ถมเหลี่ยมด้านล่างให้เนียนต่อกับกระดาษ

        // 4. วาดห่วงกระดูกงู (Spiral Rings)
        int ringCount = 7; // จำนวนห่วง
        int spacing = (w - 30) / (ringCount - 1); // คำนวณระยะห่างอัตโนมัติ
        
        for (int i = 0; i < ringCount; i++) {
            int rx = 15 + (i * spacing);
            
            // วาดตัวห่วงสีเทาเข้ม (เหมือนเหล็ก)
            g2.setColor(new Color(80, 80, 80)); 
            g2.fillRoundRect(rx, 10, 6, 16, 4, 4);
            
            // วาดแสงสะท้อนบนห่วงเหล็กให้ดูเงาๆ
            g2.setColor(new Color(200, 200, 200));
            g2.fillRoundRect(rx + 1, 11, 2, 14, 2, 2);
            
            // วาดรูเจาะกระดาษสีดำ
            g2.setColor(new Color(30, 30, 30));
            g2.fillOval(rx + 1, 24, 4, 4);
        }

        g2.dispose(); // คืนทรัพยากรการวาดให้ระบบ (สำคัญในการจัดการ Memory)
    }
}
