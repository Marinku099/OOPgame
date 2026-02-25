package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import GameItem.ClothingItem;
import GameSystem.FontManagement;
import OOPGameCharacter.Player;

public class StockPanel extends JPanel {

    private GameWindow mainFrame;
    private Image bgImage;

    private Player player;
    
    // Components สำหรับตาราง (Master View)
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private ArrayList<ClothingItem> currentInventoryList;

    // Components สำหรับส่วนแสดงรายละเอียด (Detail View)
    private JLabel lblItemImage;
    private JTextArea txtItemDescription;
    private JPanel detailPanel;

    public StockPanel(GameWindow frame, Player player) {
        this.mainFrame = frame;
        this.player = player;

        setLayout(new BorderLayout());

        setOpaque(true);

        //สร้างส่วนหัว (Header) และปุ่มย้อนกลับ
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Inventory Storage", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JButton btnBack = new JButton("Back to Shop");
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.addActionListener(e -> mainFrame.showScreen("ShopPanel"));
        topPanel.add(btnBack, BorderLayout.WEST);
        
        add(topPanel, BorderLayout.NORTH);

        // 3. สร้างส่วนตาราง (Master View) - ด้านซ้าย
        initTable();
        JScrollPane tableScrollPane = new JScrollPane(itemTable);
        // ทำให้ ScrollPane โปร่งใสเห็นพื้นหลัง
        tableScrollPane.setOpaque(false);
        tableScrollPane.getViewport().setOpaque(false);

        // 4. สร้างส่วนรายละเอียด (Detail View) - ด้านขวา
        initDetailPanel();

        // 5. ใช้ JSplitPane แบ่งครึ่ง ซ้าย(ตาราง) / ขวา(รายละเอียด)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, detailPanel);
        splitPane.setDividerLocation(550); // กำหนดจุดแบ่งเริ่มต้น (ปรับได้ตามความกว้างจอ)
        splitPane.setOpaque(false);
        splitPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        add(splitPane, BorderLayout.CENTER);
    }

    // --- เริ่มต้นส่วนตาราง ---
    private void initTable() {
        String[] columnNames = {"Name", "Type", "Rarity", "Size"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ห้ามแก้ไขข้อมูลในตารางโดยตรง
            }
        };

        itemTable = new JTable(tableModel);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemTable.setRowHeight(35); // เพิ่มความสูงให้กดง่ายขึ้น
        itemTable.setFont(new Font("SansSerif", Font.PLAIN, 16));
        itemTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));

        // [เพิ่มใหม่] เปิดใช้งานระบบกดที่หัวตารางเพื่อเรียงข้อมูล (Sorting)
        itemTable.setAutoCreateRowSorter(true);

        // [เพิ่มใหม่] ทำให้ตัวตารางและเซลล์มีสี
        setBackground(new Color(20, 30, 40));
        itemTable.setOpaque(true);
        itemTable.setBackground(new Color(245, 245, 245));
        itemTable.setForeground(Color.BLACK);
        itemTable.setSelectionBackground(new Color(100, 149, 237)); // สีตอนเลือก
        itemTable.setSelectionForeground(Color.WHITE);
        itemTable.getTableHeader().setBackground(new Color(60, 63, 65));
        itemTable.getTableHeader().setForeground(Color.WHITE);

        // เพิ่ม Listener เมื่อมีการคลิกเลือกแถวในตาราง
        itemTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && itemTable.getSelectedRow() != -1) {
                    // [สำคัญ] ต้องแปลง Index เพราะถ้าผู้เล่น Sort ตาราง Index หน้าจอจะไม่ตรงกับ List
                    int viewRowIndex = itemTable.getSelectedRow();
                    int modelRowIndex = itemTable.convertRowIndexToModel(viewRowIndex);
                    
                    ClothingItem selectedItem = currentInventoryList.get(modelRowIndex);
                    showItemDetails(selectedItem);
                }
            }
        });
    }

    // --- เริ่มต้นส่วนแสดงรายละเอียด ---
    private void initDetailPanel() {
        detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        // ตั้งพื้นหลังให้เป็นสีขาวโปร่งแสง (Alpha 200) เพื่อให้อ่านง่ายแต่ยังเห็นฉากหลังรำไร
        detailPanel.setBackground(new Color(255, 255, 255)); 
        detailPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2), "Item Details"));

        // พื้นที่แสดงรูปภาพ
        lblItemImage = new JLabel("Select an item", SwingConstants.CENTER);
        lblItemImage.setPreferredSize(new Dimension(250, 250));
        lblItemImage.setMaximumSize(new Dimension(250, 250));
        lblItemImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblItemImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // พื้นที่แสดงข้อความรายละเอียด
        txtItemDescription = new JTextArea(10, 20);
        txtItemDescription.setEditable(false);
        txtItemDescription.setLineWrap(true);
        txtItemDescription.setWrapStyleWord(true);
        // txtItemDescription.setFont(new Font("SansSerif", Font.PLAIN, 16)); // ปรับฟอนต์ให้ใหญ่ขึ้นนิดหน่อย
        txtItemDescription.setFont(FontManagement.getFont("GameSystem\\NotoSansThai-VariableFont_wdth,wght.ttf", 16f));
        txtItemDescription.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane descScrollPane = new JScrollPane(txtItemDescription);
        descScrollPane.setOpaque(false);
        
        detailPanel.add(Box.createVerticalStrut(20));
        detailPanel.add(lblItemImage);
        detailPanel.add(Box.createVerticalStrut(20));
        detailPanel.add(descScrollPane);
    }

    // --- Method สำหรับอัปเดตข้อมูลในตาราง (ต้องเรียกก่อนโชว์หน้านี้เสมอ) ---
    public void updateStockData() {
        Player player = mainFrame.getPlayer();
        if (player == null || player.getStock() == null) return;

        this.currentInventoryList = player.getStock().getItems();

        // ล้างข้อมูลเก่า
        tableModel.setRowCount(0);

        // โหลดข้อมูลใหม่ลงตาราง
        for (ClothingItem item : currentInventoryList) {
            Object[] row = {
                item.getName(),
                item.getType(),
                item.getRarity(),
                item.getSize()
            };
            tableModel.addRow(row);
        }
        
        clearDetails(); // ล้างหน้าจอฝั่งขวา
    }

    // --- Method แสดงรายละเอียดและรูปภาพของไอเทม ---
    private void showItemDetails(ClothingItem item) {
        // 1. จัดการรูปภาพ (แบบปลอดภัย ป้องกันบั๊กรูปหาย)
        ImageIcon defaultIcon = new ImageIcon("Image\\item\\MaiMeeSua.png");
        Image defaultScaledImage = defaultIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        if (item.getImagePath() != null && !item.getImagePath().isEmpty()) {
            File imgFile = new File(item.getImagePath());
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(item.getImagePath());
                Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                lblItemImage.setIcon(new ImageIcon(scaledImage));
                lblItemImage.setText("");
            } else {
                lblItemImage.setIcon(new ImageIcon(defaultScaledImage));
                // lblItemImage.setText("Image File Missing");
            }
        } else {
            lblItemImage.setIcon(new ImageIcon(defaultScaledImage));
            // lblItemImage.setText("No Image Data");
        }

        // 2. จัดการข้อความรายละเอียด
        StringBuilder details = new StringBuilder();
        details.append("Name: ").append(item.getName()).append("\n");
        details.append("Type: ").append(item.getType()).append("\n");
        details.append("Rarity: ").append(item.getRarity()).append("\n");
        details.append("Size: ").append(item.getSize()).append("\n");
        details.append("Condition: ").append(String.format("%.0f%%", item.getCondition() * 100)).append("\n");
        details.append("Base Price: $").append(String.format("%.2f", item.getBasePrice())).append("\n");
        
        // ถ้าเป็นของปลอม (Optional: อาจจะซ่อนไว้ถ้าไม่อยากให้ผู้เล่นรู้ทันที)
        if (item.isFake()) {
            details.append("[!] Counterfeit Item (Authenticity: ")
                   .append(String.format("%.0f%%", item.getFakeAuthenticity() * 100)).append(")\n");
        }

        details.append("\n--- Description ---\n");
        details.append(item.getDescription() != null ? item.getDescription() : "No description available.");

        txtItemDescription.setText(details.toString());
        txtItemDescription.setCaretPosition(0); // บังคับให้ Scrollbar อยู่บนสุดเสมอ
    }
    
    // ล้างข้อมูลฝั่งขวาเมื่อไม่มีการเลือกไอเทม
    private void clearDetails() {
        lblItemImage.setIcon(null);
        lblItemImage.setText("Select an item");
        txtItemDescription.setText("");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}