package GUI;

import Enums.OfferState;
import GameItem.ClothingItem;
import GameSystem.FontManagement;
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
    private JLabel lblNpcName, lblNpcDialogue, lblNpcImage, lblNpcOffer;
    private JLabel lblItemName, lblItemPrice, lblItemIcon;
    private JPanel npcMaskPanel;
    private JTextArea txtItemDetails;
    private JTextField txtPlayerOffer;
    private JButton btnAccept, btnCounter, btnReject;
    private JProgressBar patienceBar;
    private BalanceAndDatePanel balancePanel;

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

        npcMaskPanel = new JPanel(null);
        npcMaskPanel.setOpaque(false); // ทำให้ล่องหน
        npcMaskPanel.setBounds(400, 150, 400, 390);

        lblNpcImage = new JLabel();
        lblNpcImage.setBounds(0, 0, 400, 500); // ตำแหน่งตัวละครตรงกลาง
        
        npcMaskPanel.add(lblNpcImage);
        add(npcMaskPanel);

        lblNpcName = new JLabel("Customer", SwingConstants.CENTER);
        lblNpcName.setFont(new Font("Arial", Font.BOLD, 24));
        lblNpcName.setBounds(450, 100, 300, 40);
        add(lblNpcName);

        lblNpcDialogue = new JLabel("Hello! I want to trade.", SwingConstants.CENTER);
        lblNpcDialogue.setOpaque(true);
        lblNpcDialogue.setFont(FontManagement.getFont("GameSystem\\static\\NotoSansThai_Condensed-Bold.ttf", 25f));
        lblNpcDialogue.setForeground(Color.BLACK);
        lblNpcDialogue.setBackground(new Color(255, 255, 255, 200));
        lblNpcDialogue.setBounds(800, 250, 400, 80);
        add(lblNpcDialogue);

        patienceBar = new JProgressBar(0, 10); // สมมติความอดทนเต็ม 10
        patienceBar.setForeground(Color.RED);
        patienceBar.setBounds(500, 80, 200, 15);
        add(patienceBar);

        // --- ส่วนของไอเทม (ซ้าย) ---
        itemCardPanel();

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

        balancePanel = new BalanceAndDatePanel(player);
        balancePanel.setBounds(950, 500, 250, 90);
        add(balancePanel);
    }

    private void itemCardPanel() {
        JPanel itemCard = new JPanel();
        // พิกัดของกรอบใหญ่ยังคงใช้ setBounds ได้ ถ้า Panel แม่มันยังเป็น Absolute
        // (null) layout อยู่
        itemCard.setBounds(50, 120, 250, 500);
        itemCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // เปลี่ยนให้กรอบนี้จัดเรียงชิ้นส่วนแบบ บนลงล่าง (Y_AXIS)
        itemCard.setLayout(new BoxLayout(itemCard, BoxLayout.Y_AXIS));

        lblItemIcon = new JLabel();
        // บังคับขนาดของกล่องใส่รูป (เพราะ BoxLayout จะยึดตาม Preferred/Maximum Size)
        lblItemIcon.setPreferredSize(new Dimension(200, 250));
        lblItemIcon.setMaximumSize(new Dimension(200, 250));
        lblItemIcon.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblItemIcon.setAlignmentX(Component.CENTER_ALIGNMENT); // จัดให้อยู่กึ่งกลางแนวนอน

        // lblItemName = new JLabel("Item Name", SwingConstants.CENTER);
        // lblItemName.setAlignmentX(Component.CENTER_ALIGNMENT);

        // lblItemPrice = new JLabel("Price: $0", SwingConstants.CENTER);
        // lblItemPrice.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblNpcOffer = new JLabel("NPC Offer: $0", SwingConstants.CENTER);
        lblNpcOffer.setFont(FontManagement.getFont("GameSystem\\static\\NotoSansThai_Condensed-Bold.ttf", 18f)); // ฟอนต์ตัวหนา
                                                                                                                 // ขนาด
                                                                                                                 // 18
        lblNpcOffer.setForeground(new Color(220, 20, 60)); // ใช้สีแดงอมม่วง (Crimson) ให้สะดุดตา
        lblNpcOffer.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtItemDetails = new JTextArea();
        txtItemDetails.setEditable(false);
        txtItemDetails.setLineWrap(true);
        txtItemDetails.setWrapStyleWord(true);
        // ใช้ Font คล้ายกับ StockPanel เพื่อให้รองรับภาษาไทย
        txtItemDetails.setFont(FontManagement.getFont("GameSystem\\NotoSansThai-VariableFont_wdth,wght.ttf", 16f));
        txtItemDetails.setMargin(new Insets(10, 10, 10, 10));

        // เอา JTextArea ไปใส่ใน ScrollPane เผื่อข้อความยาวเกินกล่อง
        JScrollPane scrollPane = new JScrollPane(txtItemDetails);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ประกอบร่าง
        itemCard.add(Box.createVerticalStrut(20));
        itemCard.add(lblItemIcon);
        itemCard.add(Box.createVerticalStrut(10)); // ระยะห่างระหว่างรูปกับราคา
        itemCard.add(lblNpcOffer);
        itemCard.add(Box.createVerticalStrut(10));
        itemCard.add(scrollPane); // ยัด ScrollPane แทน Label เก่า

        add(itemCard);
    }

    // ฟังก์ชันรับ Data จาก Controller มาอัปเดตหน้าจอ
    public void setNPC(NPC npc) {
        this.currentNPC = npc;
        lblNpcName.setText(npc.getName());
        lblNpcDialogue.setText(npc instanceof BuyerNPC ? "I want to buy this!" : "I want to sell this!");
        ImageIcon npcIcon = new ImageIcon(npc.getimagePath());
        Image scaledNpcImage = npcIcon.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);

        lblNpcImage.setIcon(new ImageIcon(scaledNpcImage));
        npcMaskPanel.add(lblNpcImage);

        // อัปเดตไอเทมที่เขานำเสนอ
        ClothingItem item = npc.getItem();

        // lblItemPrice.setText("Price: $" + npc.getCurrentOffer());

        ImageIcon defaultIcon = new ImageIcon("Image\\item\\MaiMeeSua.png");
        Image defaultScaledImage = defaultIcon.getImage().getScaledInstance(200, 250, Image.SCALE_SMOOTH);

        if (item != null) {
            // lblItemName.setText(item.getName());
            // lblItemPrice.setText("Offer: $" + (int) npc.getCurrentOffer());

            // ดึงที่อยู่ไฟล์รูปภาพ
            String imagePath = item.getImagePath();

            // 1. ดัก Error: ตรวจสอบว่ามี path และไฟล์มีอยู่จริงหรือไม่
            if (imagePath != null && !imagePath.isEmpty()) {
                java.io.File imgFile = new java.io.File(imagePath);

                if (imgFile.exists()) {
                    // 2. โหลดและปรับขนาดรูปภาพให้พอดีกับกล่อง (200x200)
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image scaledImage = icon.getImage().getScaledInstance(200, 250, Image.SCALE_SMOOTH);

                    // ใช้รูปที่ย่อขนาดแล้ว (scaledImage) มาแสดงผล
                    lblItemIcon.setIcon(new ImageIcon(scaledImage));
                    lblItemIcon.setText(""); // ลบข้อความ Placeholder (เช่น "Item Image") ออก

                } else {
                    // กรณีหาไฟล์รูปลงท้ายด้วยชื่อนี้ไม่เจอในโฟลเดอร์
                    lblItemIcon.setIcon(new ImageIcon(defaultScaledImage));
                    lblItemIcon.setText("");
                }
            } else {
                // กรณีไอเทมชิ้นนี้ไม่มีข้อมูล Path
                lblItemIcon.setIcon(new ImageIcon(defaultScaledImage));
                lblItemIcon.setText("");
            }
        }

        StringBuilder details = new StringBuilder();

        // [สำคัญ] เพิ่มบรรทัดแสดงราคา Offer ของ NPC
        // details.append(">> Offer: $").append((int) npc.getCurrentOffer()).append("
        // <<\n\n");
        lblNpcOffer.setText("NPC Offer: $" + (int) npc.getCurrentOffer());

        details.append("Name: ").append(item.getName()).append("\n");
        details.append("Type: ").append(item.getType()).append("\n");
        details.append("Rarity: ").append(item.getRarity()).append("\n");
        details.append("Size: ").append(item.getSize()).append("\n");
        details.append("Condition: ").append(String.format("%.0f%%", item.getCondition() * 100)).append("\n");

        if (item.isFake()) {
            details.append("\n[!] Counterfeit Item\n(Authenticity: ")
                    .append(String.format("%.0f%%", item.getFakeAuthenticity() * 100)).append(")\n");
        }

        details.append("\n--- Description ---\n");
        details.append(item.getDescription() != null ? item.getDescription() : "No description available.");

        // โยนข้อความเข้า txtItemDetails
        txtItemDetails.setText(details.toString());
        txtItemDetails.setCaretPosition(0); // บังคับให้ scrollbar อยู่บนสุด

        // รีเซ็ตความอดทน (สมมติ patience ใน NPC คือจำนวนรอบ)
        patienceBar.setValue(10);
        repaint();
    }

    private void processTrade(boolean isAccept) {
        if (currentNPC == null)
            return;

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
                balancePanel.updateData();
                GameController.getInstance().nextCustomer();
            } else if (state == OfferState.FAIL) {
                JOptionPane.showMessageDialog(this, "Deal Failed! NPC Walked away.");
                GameController.getInstance().nextCustomer();
            } else {
                // กรณี PENDING (NPC ต่อราคาต่อ)
                lblNpcDialogue.setText("How about $" + (int) currentNPC.getCurrentOffer() + "?");
                lblItemPrice.setText("Offer: $" + (int) currentNPC.getCurrentOffer());
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