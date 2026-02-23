package GUI; // อยู่ใน package GUI

import java.awt.*;              // ใช้ Layout ต่าง ๆ เช่น CardLayout
import javax.swing.*;           // ใช้ JFrame, JPanel, SwingUtilities

// คลาสหน้าต่างหลักของเกม
public class GameWindow extends JFrame {

    private CardLayout cardLayout; // ระบบสลับหน้าแบบไพ่
    private JPanel mainPanel;      // กล่องใหญ่ที่เก็บทุกหน้าไว้ซ้อนกัน

    // Constructor
    public GameWindow() {

        // ===== ตั้งค่าหน้าต่างหลัก =====
        setTitle("Play On Wear , Trade & Talk"); // ชื่อบนแถบด้านบน
        setExtendedState(JFrame.MAXIMIZED_BOTH); // เปิดแบบเต็มจอ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // กด X แล้วปิดโปรแกรม

        // ===== ตั้งค่าระบบสลับหน้า =====
        cardLayout = new CardLayout();       // สร้างระบบสลับหน้า
        mainPanel = new JPanel(cardLayout);  // สร้างกล่องหลัก และกำหนดให้ใช้ CardLayout

        // ===== สร้างหน้าแรกของเกม =====
        GameOpeningPanel startGame = new GameOpeningPanel(this);
        ShopPanel shopPanel = new ShopPanel(this);

        // เพิ่มหน้าเข้าไปในระบบไพ่
        // รูปแบบ: add(หน้า, "ชื่อรหัส")
        mainPanel.add(startGame, "StartGame");
        mainPanel.add(shopPanel, "ShopPanel");

        // เอากล่องใหญ่ไปใส่ในหน้าต่างหลัก
        add(mainPanel);

        // สั่งให้แสดงหน้า MENU ตอนเปิดโปรแกรม
        cardLayout.show(mainPanel, "StartGame");
    }

    // เมธอดสำหรับสลับหน้า
    public void showScreen(String screenName) {

        // สั่ง CardLayout ให้แสดงหน้าที่มีชื่อรหัสตรงกับ screenName
        cardLayout.show(mainPanel, screenName);
    }

    // จุดเริ่มต้นของโปรแกรม
    public static void main(String[] args) {

        // สั่งให้ Swing ทำงานบน Event Dispatch Thread (ปลอดภัยกับ GUI)
        SwingUtilities.invokeLater(() -> {

            // สร้างหน้าต่างเกม
            GameWindow window = new GameWindow();

            // ทำให้หน้าต่างมองเห็น
            window.setVisible(true);
        });
    }
}
    









//    Dimension screenSize;
//    int screenWidth;
//    int screenHight;
//
//    public GameWindow(Player player){
//        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        this.screenWidth = screenSize.width;
//        this.screenHight = screenSize.height;
//
//        String GameName = "Geme name";
//        this.setTitle(GameName);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        settingLayout();
//
//        this.setResizable(true);
//        this.setSize(screenSize);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        this.setMinimumSize(new Dimension(800, 600)); // not sure yet
//        this.setLocationRelativeTo(null);
//
//        // String logoPath = "path";
//        // ImageIcon icon = new ImageIcon(logoPath);
//        // this.setIconImage(icon.getImage());
//        // this.getContentPane().setBackground(new Color(255, 255, 255));
//    }
//
//    private void settingLayout(){
//        this.setLayout(new BorderLayout());
//
//        // JPanel basePane = (JPanel) this.getContentPane();
//
//        // basePane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
//    }