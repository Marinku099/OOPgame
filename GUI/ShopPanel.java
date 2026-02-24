package GUI;

import Enums.OfferState;
import GameItem.ClothingItem;
import GameSystem.GameController;
import OOPGameCharacter.NPC;
import OOPGameCharacter.Player;
import OOPGameCharacter.BuyerNPC;
import javax.swing.*;

import DataBase.Database;

import java.awt.*;

public class ShopPanel extends JPanel {

    private GameWindow mainFrame;
    private Image bgImage;

    private Player player;
    
    // UI Components
    private JLabel lblNpcName, lblNpcDialogue, lblNpcImage;
    private JLabel lblItemName, lblItemPrice, lblItemIcon;
    private JTextField txtPlayerOffer;
    private JButton btnAccept, btnCounter, btnReject;
    private JProgressBar patienceBar;

    private NPC currentNPC;

    public ShopPanel(GameWindow frame, Player player) {
        this.mainFrame = frame;
        this.player = player;
        setLayout(null); // ใช้ Absolute Layout เพื่อวาง Component ตามตำแหน่งในภาพพื้นหลัง
        
        // 1. โหลดพื้นหลัง
        bgImage = new ImageIcon("GameElement/HomePage/bg_homepage.png").getImage();

        initUI();
    }

    private void initUI() {
        // --- ส่วนของ NPC ---
        lblNpcImage = new JLabel();
        lblNpcImage.setBounds(400, 150, 400, 500); // ตำแหน่งตัวละครตรงกลาง
        add(lblNpcImage);

        lblNpcName = new JLabel("Customer", SwingConstants.CENTER);
        lblNpcName.setFont(new Font("Arial", Font.BOLD, 24));
        lblNpcName.setBounds(450, 100, 300, 40);
        add(lblNpcName);

        lblNpcDialogue = new JLabel("Hello! I want to trade.", SwingConstants.CENTER);
        lblNpcDialogue.setOpaque(true);
        lblNpcDialogue.setBackground(new Color(255, 255, 255, 200));
        lblNpcDialogue.setBounds(300, 650, 600, 80);
        add(lblNpcDialogue);

        patienceBar = new JProgressBar(0, 10); // สมมติความอดทนเต็ม 10
        patienceBar.setForeground(Color.RED);
        patienceBar.setBounds(500, 80, 200, 15);
        add(patienceBar);

        // --- ส่วนของไอเทม (ขวาบน) ---
        JPanel itemCard = new JPanel(null);
        itemCard.setBounds(950, 50, 250, 300);
        itemCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        lblItemIcon = new JLabel();
        lblItemIcon.setBounds(25, 20, 200, 200);
        itemCard.add(lblItemIcon);

        lblItemName = new JLabel("Item Name", SwingConstants.CENTER);
        lblItemName.setBounds(0, 230, 250, 30);
        itemCard.add(lblItemName);

        lblItemPrice = new JLabel("Price: $0", SwingConstants.CENTER);
        lblItemPrice.setBounds(0, 260, 250, 30);
        itemCard.add(lblItemPrice);
        add(itemCard);

        // --- แผงควบคุม (ล่างขวา) ---
        txtPlayerOffer = new JTextField();
        txtPlayerOffer.setBounds(950, 600, 250, 40);
        txtPlayerOffer.setFont(new Font("Arial", Font.PLAIN, 20));
        add(txtPlayerOffer);

        btnCounter = new JButton("Counter Offer");
        btnCounter.setBounds(950, 650, 250, 40);
        btnCounter.addActionListener(e -> processTrade(false));
        add(btnCounter);

        btnAccept = new JButton("Accept Offer");
        btnAccept.setBounds(950, 700, 250, 40);
        btnAccept.setBackground(Color.GREEN);
        btnAccept.addActionListener(e -> processTrade(true));
        add(btnAccept);

        btnReject = new JButton("Reject");
        btnReject.setBounds(950, 750, 250, 40);
        btnReject.addActionListener(e -> GameController.getInstance().nextCustomer());
        add(btnReject);
        
        // ปุ่มไปหน้าคลัง
        JButton btnStock = new JButton("Inventory");
        btnStock.setBounds(50, 50, 120, 40);
        btnStock.addActionListener(e -> mainFrame.showScreen("StockPanel"));
        add(btnStock);
    }

    // ฟังก์ชันรับ Data จาก Controller มาอัปเดตหน้าจอ
    public void setNPC(NPC npc) {
        this.currentNPC = npc;
        lblNpcName.setText(npc.getName());
        lblNpcDialogue.setText(npc instanceof BuyerNPC ? "I want to buy this!" : "I want to sell this!");
        
        // อัปเดตไอเทมที่เขานำเสนอ
        ClothingItem item = npc.getItem();
        if (item != null) {
            lblItemName.setText(item.getName());
            lblItemPrice.setText("Offer: $" + (int)npc.getCurrentOffer());
            // ในอนาคตใช้ lblItemIcon.setIcon(new ImageIcon(item.getImagePath()));
        }
        
        // รีเซ็ตความอดทน (สมมติ patience ใน NPC คือจำนวนรอบ)
        patienceBar.setValue(10); 
        repaint();
    }

    private void processTrade(boolean isAccept) {
        if (currentNPC == null) return;

        try {
            double playerPrice;
            if (isAccept) {
                playerPrice = currentNPC.getCurrentOffer();
            } else {
                playerPrice = Double.parseDouble(txtPlayerOffer.getText());
            }

            // ส่งราคาไปให้ Logic NPC ประมวลผล
            OfferState state = currentNPC.processOffer(playerPrice, GameController.getInstance().getPlayer());

            if (state == OfferState.SUCCESS) {
                JOptionPane.showMessageDialog(this, "Deal Success!");
                GameController.getInstance().getScoreManager().updateDeal(state, currentNPC);
                GameController.getInstance().nextCustomer();
            } else if (state == OfferState.FAIL) {
                JOptionPane.showMessageDialog(this, "Deal Failed! NPC Walked away.");
                GameController.getInstance().nextCustomer();
            } else {
                // กรณี PENDING (NPC ต่อราคาต่อ)
                lblNpcDialogue.setText("How about $" + (int)currentNPC.getCurrentOffer() + "?");
                lblItemPrice.setText("Offer: $" + (int)currentNPC.getCurrentOffer());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price!");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}