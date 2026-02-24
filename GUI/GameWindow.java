package GUI;

import java.awt.*;
import javax.swing.*;

import DataBase.Database;
import DataBase.Loader.CsvItemLoader;
import DataBase.Loader.CsvNameLoader;
import DataBase.Loader.ItemLoader;
import DataBase.Loader.NameLoader;

import java.util.HashMap;
import java.util.Map;

import Enums.SkillType;
import GameItem.ClothingItem;
import GameSystem.GameRNG;
import OOPGameCharacter.Player;

import Enums.ClothingType;
import Enums.Rarity;
import Enums.Size;

public class GameWindow extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private Player player;
    private Map<SkillType, Integer> skillsMap;

    public GameWindow() {

        setTitle("Play On Wear , Trade & Talk");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ==============================
        // 1️⃣ สร้าง Player
        // ==============================
        player = new Player("Tar", 1000, 1, 1);

        // ==============================
        // สร้างไอเทมทดสอบใส่ใน Stock
        // ==============================

        ItemLoader itemLoader = new CsvItemLoader("Csv/Item.csv");
        NameLoader nameLoader = new CsvNameLoader("Csv/NPC_name.csv");
        Database database = new Database(itemLoader, nameLoader);
        ClothingItem testItem = GameRNG.pickRandomCloth(database.getListClothingItems());


      //  ClothingItem testItem = new ClothingItem(
      //          "Vintage Hoodie",
      //          "90s oversized street hoodie in perfect condition.",
      //          ClothingType.HOODIE,   // ใช้ enum ที่มีจริง
      //          Rarity.VINTAGE,
      //          Size.L,
      //          0.95,                  // 95% condition
      //          false,                 // ไม่ใช่ของปลอม
      //          0.0
//
      //          
      //  );

        // เพิ่มเข้า inventory ผู้เล่น
        player.getStock().addItem(testItem);

        // ==============================
        // 3️⃣ เตรียม Skill Map
        // ==============================
        skillsMap = new HashMap<>();
        for (SkillType type : SkillType.values()) {
            skillsMap.put(type, 0);
        }

        // ==============================
        // 4️⃣ CardLayout
        // ==============================
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        GameOpeningPanel startGame = new GameOpeningPanel(this);
        ShopPanel shopPanel = new ShopPanel(this);
        StockPanel stockPanel = new StockPanel(this);
        SkillUpgradePanel skillPanel =
                new SkillUpgradePanel(this, skillsMap, player);

        mainPanel.add(startGame, "StartGame");
        mainPanel.add(shopPanel, "ShopPanel");
        mainPanel.add(stockPanel, "StockPanel");
        mainPanel.add(skillPanel, "SkillUpgradePanel");

        add(mainPanel);

        // ==============================
        // 5️⃣ เปิดหน้า Inventory ทันที
        // ==============================
        cardLayout.show(mainPanel, "StockPanel");

        // โหลดข้อมูลเข้าตาราง
        stockPanel.updateStockData();
    }

    // ให้ StockPanel เรียกใช้
    public Player getPlayer() {
        return player;
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}