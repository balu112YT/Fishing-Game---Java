package fishinggamejava.fishinggamejava;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SaveManager {

    private static String getFileName(int slot) {
        return "fishing_save_" + slot + ".txt";
    }

    /**
     * Saves player stats, upgrades, baits, current zone, map progression, inventory, records, and ALL 3 active quests.
     */
    public static void saveGame(Player player, GameLogic logic, int slot, AreaManager areaManager) {
        String fileName = getFileName(slot);
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Save core progression stats
            writer.println(player.getXp());
            writer.println(player.getMoney());
            writer.println(player.getLevel());
            writer.println(logic.getCurrentZone());

            // Save Equipment Upgrades (Bucket, Line, Rod, Reel)
            writer.println(player.getBucketLevel() + " " + player.getLineLevel() + " " +
                    player.getRodLevel() + " " + player.getReelLevel());

            // Save Bait Data (Type and Count)
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

            // Save Fish Journal Records (Map)
            Map<String, Double> records = player.getFishRecords();
            writer.println(records.size());
            for (Map.Entry<String, Double> entry : records.entrySet()) {
                String safeName = entry.getKey().replace(" ", "_");
                writer.println(safeName + " " + entry.getValue());
            }

            // FIXED: Loops through all 3 Quest slots and saves their specific parameters
            QuestManager qm = logic.getQuestManager();
            for (int i = 0; i < 3; i++) {
                QuestManager.Quest q = qm.getActiveQuests()[i];
                writer.println(q.type + " " + q.target + " " + q.progress + " " + q.completed);
            }

            System.out.println("\u001B[32m[System] Game successfully saved to Slot " + slot + "!\u001B[0m");
        } catch (IOException e) {
            System.err.println("[Error] Save failed: " + e.getMessage());
        }
    }

    /**
     * Loads player progress, gear levels, bait data, maps, inventory, records, and ALL 3 active quests from file.
     */
    public static void loadGame(Player player, GameLogic logic, int slot, AreaManager areaManager) {
        File file = new File(getFileName(slot));

        if (!file.exists()) {
            System.out.println("[System] Slot " + slot + " is empty. Creating new save file...");
            player.resetStats();
            logic.setCurrentZone(1);

            // Populates all 3 slots with fresh balanced tasks instantly
            logic.getQuestManager().generateAllQuests(player.getLevel(), logic.getCurrentZone());

            // Reset all map unlocks except the first one
            for (int i = 0; i < areaManager.getAreas().size(); i++) {
                areaManager.getAreas().get(i).isUnlocked = (i == 0);
            }
            saveGame(player, logic, slot, areaManager);
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            scanner.useLocale(java.util.Locale.US);

            if (scanner.hasNextInt()) {
                // Load core stats
                int xp = scanner.nextInt();
                int money = scanner.nextInt();
                int level = scanner.nextInt();
                int zone = scanner.nextInt();

                // Load Equipment Upgrades
                int bucketLevel = scanner.nextInt();
                int lineLevel = scanner.nextInt();
                int rodLevel = scanner.nextInt();
                int reelLevel = scanner.nextInt();

                // Load Bait Data
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

                // Load Fish Journal Records
                Map<String, Double> loadedRecords = new HashMap<>();
                if (scanner.hasNextInt()) {
                    int recordsSize = scanner.nextInt();
                    for (int i = 0; i < recordsSize; i++) {
                        if (scanner.hasNext()) {
                            String fishName = scanner.next().replace("_", " ");
                            double bestWeight = scanner.nextDouble();
                            loadedRecords.put(fishName, bestWeight);
                        }
                    }
                }
                player.setFishRecords(loadedRecords);

                // FIXED: Processes exactly 3 active quest rows sequentially
                QuestManager qm = logic.getQuestManager();
                for (int i = 0; i < 3; i++) {
                    if (scanner.hasNextInt()) {
                        int qType = scanner.nextInt();
                        int qTarget = scanner.nextInt();
                        int qProgress = scanner.nextInt();
                        boolean qCompleted = scanner.nextBoolean();

                        QuestManager.Quest q = qm.getActiveQuests()[i];
                        if (qType == 0) {
                            qm.generateScaledQuest(i, level, zone); // Generate fresh if corrupted/blank
                        } else {
                            q.loadQuestProperties(qType, qTarget, qProgress, qCompleted);
                        }
                    }
                }

                // Apply loaded data to player setup
                player.loadPlayerData(xp, money, level, loadedInv, bucketLevel, lineLevel, rodLevel, reelLevel, baitType, baitCount);
                logic.setCurrentZone(zone);

                System.out.println("\u001B[36m[System] Slot " + slot + " loaded successfully!\u001B[0m");
            }
        } catch (Exception e) {
            System.err.println("[Error] Load failed. File might be corrupted or in an old single-quest format.");
            System.out.println("[System] Tip: Delete old 'fishing_save_X.txt' files so the engine can rebuild them clean!");
            e.printStackTrace();
        }
    }
}
