import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveManager {

    private static String getFileName(int slot) {
        return "fishing_save_" + slot + ".txt";
    }

    /**
     * Saves player stats, upgrades, baits, current zone, map progression, and inventory data.
     */
    public static void saveGame(Player player, int zone, int slot, AreaManager areaManager) {
        String fileName = getFileName(slot);
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Save core progression stats
            writer.println(player.getXp());
            writer.println(player.getMoney());
            writer.println(player.getLevel());
            writer.println(zone);

            // NEW: Save Equipment Upgrades (Bucket, Line, Rod, Reel)
            writer.println(player.getBucketLevel() + " " + player.getLineLevel() + " " +
                    player.getRodLevel() + " " + player.getReelLevel());

            // NEW: Save Bait Data (Type and Count)
            writer.println(player.getEquippedBaitType() + " " + player.getBaitCount());

            // Save map unlock statuses
            for (Area a : areaManager.getAreas()) {
                writer.print(a.isUnlocked + " ");
            }
            writer.println();

            // Save inventory fish count and data
            List<CaughtFish> inv = player.getInventory();
            writer.println(inv.size());
            for (CaughtFish cf : inv) {
                writer.println(cf.fishId + " " + cf.weight);
            }

            System.out.println("\u001B[32m[System] Progress, Upgrades, Maps, and Inventory saved to Slot " + slot + "!\u001B[0m");
        } catch (IOException e) {
            System.err.println("[Error] Save failed: " + e.getMessage());
        }
    }

    /**
     * Loads player progress, gear levels, bait data, maps, and inventory from file.
     */
    public static void loadGame(Player player, GameLogic logic, int slot, AreaManager areaManager) {
        File file = new File(getFileName(slot));

        if (!file.exists()) {
            System.out.println("[System] Slot " + slot + " is empty. Creating new save file...");
            player.resetStats();
            logic.setCurrentZone(1);

            // Reset all map unlocks except the first one
            for (int i = 0; i < areaManager.getAreas().size(); i++) {
                areaManager.getAreas().get(i).isUnlocked = (i == 0);
            }
            saveGame(player, 1, slot, areaManager);
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            // Enforce US locale to support dots (.) as decimal points
            scanner.useLocale(java.util.Locale.US);

            if (scanner.hasNextInt()) {
                // Load core stats
                int xp = scanner.nextInt();
                int money = scanner.nextInt();
                int level = scanner.nextInt();
                int zone = scanner.nextInt();

                // NEW: Load Equipment Upgrades
                int bucketLevel = scanner.nextInt();
                int lineLevel = scanner.nextInt();
                int rodLevel = scanner.nextInt();
                int reelLevel = scanner.nextInt();

                // NEW: Load Bait Data
                int baitType = scanner.nextInt();
                int baitCount = scanner.nextInt();

                // Load map unlock statuses
                for (Area a : areaManager.getAreas()) {
                    if (scanner.hasNextBoolean()) {
                        a.isUnlocked = scanner.nextBoolean();
                    }
                }

                // Load inventory content
                List<CaughtFish> loadedInv = new ArrayList<>();
                if (scanner.hasNextInt()) {
                    int invSize = scanner.nextInt();
                    for (int i = 0; i < invSize; i++) {
                        if (scanner.hasNextInt()) {
                            int id = scanner.nextInt();
                            double weight = scanner.nextDouble();
                            loadedInv.add(new CaughtFish(id, weight));
                        }
                    }
                }

                // Apply loaded data to player (including new gear stats)
                player.loadPlayerData(xp, money, level, loadedInv, bucketLevel, lineLevel, rodLevel, reelLevel, baitType, baitCount);
                logic.setCurrentZone(zone);

                System.out.println("\u001B[36m[System] Slot " + slot + " loaded successfully!\u001B[0m");
            }
        } catch (Exception e) {
            System.err.println("[Error] Load failed. File might be corrupted.");
            e.printStackTrace();
        }
    }
}
