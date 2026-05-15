import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveManager {

    private static String getFileName(int slot) {
        return "fishing_save_" + slot + ".txt";
    }

    // Save player stats, zone AND inventory (ID + Weight)
    public static void saveGame(Player player, int zone, int slot) {
        String fileName = getFileName(slot);
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Basic stats
            writer.println(player.getXp());
            writer.println(player.getMoney());
            writer.println(player.getLevel());
            writer.println(zone);

            // Inventory saving
            List<CaughtFish> inv = player.getInventory();
            writer.println(inv.size()); // Number of fish

            for (CaughtFish cf : inv) {
                // Save ID and Weight separated by space
                writer.println(cf.fishId + " " + cf.weight);
            }

            System.out.println("\u001B[32m[System] Progress and Inventory saved to Slot " + slot + "!\u001B[0m");
        } catch (IOException e) {
            System.err.println("[Error] Save failed: " + e.getMessage());
        }
    }

    // Load everything back
    public static void loadGame(Player player, GameLogic logic, int slot) {
        File file = new File(getFileName(slot));

        if (!file.exists()) {
            System.out.println("[System] Slot " + slot + " is empty. Creating new save file...");
            player.resetStats();
            logic.setCurrentZone(1);
            saveGame(player, 1, slot);
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            // EZ A FONTOS SOR: Kényszerítjük a pont használatát tizedesjelként
            scanner.useLocale(java.util.Locale.US);

            if (scanner.hasNextInt()) {
                // Alapadatok beolvasása
                int xp = scanner.nextInt();
                int money = scanner.nextInt();
                int level = scanner.nextInt();
                int zone = scanner.nextInt();

                // Inventory beolvasása
                List<CaughtFish> loadedInv = new ArrayList<>();
                if (scanner.hasNextInt()) {
                    int invSize = scanner.nextInt();
                    for (int i = 0; i < invSize; i++) {
                        if (scanner.hasNextInt()) {
                            int id = scanner.nextInt();
                            // Most már nem fog elszállni a pont miatt!
                            double weight = scanner.nextDouble();
                            loadedInv.add(new CaughtFish(id, weight));
                        }
                    }
                }

                player.loadPlayerData(xp, money, level, loadedInv);
                logic.setCurrentZone(zone);

                System.out.println("\u001B[36m[System] Slot " + slot + " loaded successfully with " + loadedInv.size() + " fish!\u001B[0m");
            }
        } catch (Exception e) {
            System.err.println("[Error] Load failed. File might be corrupted.");
            e.printStackTrace(); // Itt fogod látni, ha még mindig hiba van
        }
    }
}
