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
import GameSystem.GameController;
import GameSystem.GameRNG;
import GameSystem.ScoreManagement;
import GameSystem.TimeManagement;
import OOPGameCharacter.NPC;
import OOPGameCharacter.Player;
import OOPGameCharacter.SellerNPC;
import OOPGameCharacter.Stock;
import Enums.ClothingType;
import Enums.Rarity;
import Enums.Size;

public class GameWindow extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private Player player;
    private Database database;
    private ShopPanel shopPanel;
    private StockPanel stockPanel;
    private SkillUpgradePanel skillPanel;
    private Map<SkillType, Integer> skillsMap;

    public GameWindow() {

        setTitle("Play On Wear , Trade & Talk");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ==============================
        // 1️⃣ สร้าง Player
        // ==============================
        this.player = new Player("Tar", 1, 10000, 1);

        // ==============================
        // สร้างไอเทมทดสอบใส่ใน Stock
        // ==============================

        ItemLoader itemLoader = new CsvItemLoader("Csv/Item.csv");
        NameLoader nameLoader = new CsvNameLoader("Csv/NPC_name.csv");
        this.database = new Database(itemLoader, nameLoader);
        ClothingItem testItem = GameRNG.pickRandomCloth(database.getListClothingItems());

        // เพิ่มเข้า inventory ผู้เล่น
        player.getStock().addItem(testItem);
        player.getStock().addItem(GameRNG.pickRandomCloth(database.getListClothingItems()));
        player.getStock().addItem(GameRNG.pickRandomCloth(database.getListClothingItems()));

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
        shopPanel = new ShopPanel(this, player);
        stockPanel = new StockPanel(this, player);
        skillPanel = new SkillUpgradePanel(this, skillsMap, player);

        mainPanel.add(startGame, "StartGame");
        mainPanel.add(shopPanel, "ShopPanel");
        mainPanel.add(stockPanel, "StockPanel");
        mainPanel.add(skillPanel, "SkillUpgradePanel");

        add(mainPanel);

        // shopPanel.setNPC(new SellerNPC(database.getListClothingItems(), database.getAllCustomerNames()));

        // ==============================
        // 5️⃣ เปิดหน้า Inventory ทันที
        // ==============================
        cardLayout.show(mainPanel, "StartGame");

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
            GameController.getInstance().init(window.getPlayer(), window.getDatabase(), window);
        });
    }

    public ShopPanel getShopPanel() {
        return this.shopPanel;
    }

    public Database getDatabase() {
        return database;
    }

    public StockPanel getStockPanel() {
        return this.stockPanel;
    }

    public SkillUpgradePanel getSkillUpgradePanel(){
        return this.skillPanel;
    }
}