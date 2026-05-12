import java.io.*;
import java.util.Scanner;

public class SaveManager {
    private static final String SAVE_FILE = "fishing_save.txt";

    // Save player stats and current zone
    public static void saveGame(Player player, int zone) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
            writer.println(player.getXp());
            writer.println(player.getMoney());
            writer.println(player.getLevel());
            writer.println(zone);
            System.out.println("\u001B[32m[System] Game progress saved to " + SAVE_FILE + "\u001B[0m");
        } catch (IOException e) {
            System.err.println("[Error] Save failed: " + e.getMessage());
        }
    }

    // Load player stats and current zone
    public static void loadGame(Player player, GameLogic logic) {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            System.out.println("[System] No save file found. Starting a new journey!");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextInt()) {
                int xp = scanner.nextInt();
                int money = scanner.nextInt();
                int level = scanner.nextInt();
                int zone = scanner.nextInt();

                player.loadPlayerData(xp, money, level);
                logic.setCurrentZone(zone);
                System.out.println("\u001B[36m[System] Save file loaded successfully!\u001B[0m");
            }
        } catch (Exception e) {
            System.err.println("[Error] Load failed: " + e.getMessage());
        }
    }
}
