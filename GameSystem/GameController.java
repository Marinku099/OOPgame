package GameSystem;

import DataBase.Database;
import GUI.GameWindow;
import OOPGameCharacter.*;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JOptionPane;

public class GameController {
    private static GameController instance;
    private Player player;
    private Database database;
    private GameWindow gameWindow;
    private Queue<NPC> npcQueue;
    private ScoreManagement scoreManager;

    private GameController() {
        npcQueue = new LinkedList<>();
    }

    public static GameController getInstance() {
        if (instance == null) instance = new GameController();
        return instance;
    }

    // เริ่มต้นระบบ
    public void init(Player player, Database db, GameWindow window) {
        this.player = player;
        this.database = db;
        this.gameWindow = window;
        this.scoreManager = new ScoreManagement(player);
        startNewDay();
    }

    public void startNewDay() {
        // สุ่มจำนวนลูกค้าวันนี้ 3-5 คน
        int customerCount = GameRNG.getRandomInt(3, 5);
        npcQueue.clear();
        for (int i = 0; i < customerCount; i++) {
            npcQueue.add(createRandomNPC());
        }
        nextCustomer();
    }

    public void nextCustomer() {
        if (!npcQueue.isEmpty()) {
            NPC nextNpc = npcQueue.poll();
            gameWindow.getShopPanel().setNPC(nextNpc);
        } else {
            //FIXME: logic  แปลก ๆ
            // จบวัน
            TimeManagement.getInstance().nextDay();
            
            JOptionPane.showMessageDialog(gameWindow, "End day!");
            startNewDay(); // ในอนาคตต้องเปลี่ยนไปหน้า SummaryPanel
        }
    }

    private NPC createRandomNPC() {
        boolean isBuyer = !player.getStock().isEmpty() && GameRNG.getRandomBoolean();
        if (isBuyer) {
            return new BuyerNPC(player.getStock().getItems(), database.getAllCustomerNames());
        } else {
            return new SellerNPC(database.getListClothingItems(), database.getAllCustomerNames());
        }
    }

    // Getters
    public Player getPlayer() { return player; }
    public ScoreManagement getScoreManager() { return scoreManager; }
}
