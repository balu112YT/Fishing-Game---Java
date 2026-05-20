package fishinggamejava.fishinggamejava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameLogic {
    // Player data and game state variables
    private Player player = new Player();
    private HashMap<Integer, Fish> fishCatalog = new HashMap<>();
    private Random random = new Random();
    private int currentZone = 1; // Default starting zone
    private final String RESET = "\u001B[0m";

    // Quest System Initialization
    private QuestManager questManager = new QuestManager();
    public QuestManager getQuestManager() { return questManager; }

    // Constructor: Automatically initializes and loads the fish database
    public GameLogic() {
        FishDatabase.loadCatalog(fishCatalog);
    }

    // --- CORE FISHING LOGIC ---
    public void fishing() {
        if (player.isInventoryFull()) {
            System.out.println("\n\u001B[31m[Inventory Full] Your bucket is full (" +
                    player.getInventory().size() + "/" + player.getMaxInventorySize() +
                    ")! Go to the Shop to sell your fish.\u001B[0m");
            return;
        }

        List<Fish> possibleFish = new ArrayList<>();
        double luckBonus = player.getRarityLuckBonus();

        // Check if a REAL bait is active (Count > 0 AND Type is between 1 and 5)
        boolean isBaitTrulyActive = player.getBaitCount() > 0 && player.getEquippedBaitType() >= 1;

        for (Fish f : fishCatalog.values()) {
            if (f.zone == currentZone) {
                double roll = random.nextDouble();

                // Base drop rates from our rarity logic
                double finalChance = 0.50; // Common
                if (f.rarity.equalsIgnoreCase("Uncommon")) finalChance = 0.35;
                else if (f.rarity.equalsIgnoreCase("Rare")) finalChance = 0.20;
                else if (f.rarity.equalsIgnoreCase("Epic")) finalChance = 0.10;
                else if (f.rarity.equalsIgnoreCase("Legendary")) finalChance = 0.04;
                else if (f.rarity.equalsIgnoreCase("Mythic")) finalChance = 0.01;

                // Apply luck bonus ONLY to high rarities
                if (luckBonus > 0 && (f.rarity.equalsIgnoreCase("Epic") ||
                        f.rarity.equalsIgnoreCase("Legendary") ||
                        f.rarity.equalsIgnoreCase("Mythic"))) {
                    finalChance += luckBonus;
                }

                if (roll <= finalChance) {
                    possibleFish.add(f);
                }
            }
        }

        System.out.println("\nCasting the line...");
        // ONLY print bait message if the bait is actually valid and equipped
        if (isBaitTrulyActive) {
            System.out.println("\u001B[35m[Bait Active] The fish are attracted by your high-quality bait!\u001B[0m");
        }

        if (!possibleFish.isEmpty()) {
            Fish caughtFish = possibleFish.get(random.nextInt(possibleFish.size()));

            double weight = caughtFish.minWeight + (caughtFish.maxWeight - caughtFish.minWeight) * random.nextDouble();
            int baseValue = caughtFish.calculateValue(weight);
            int earnedXp = Math.max(5, baseValue);

            // ONLY consume bait if it is truly active
            if (isBaitTrulyActive) {
                player.useBait();
            }

            player.addXp(earnedXp);
            player.addFishToInventory(caughtFish.id, weight);

            System.out.print("Something is biting... ");
            System.out.println(caughtFish.getColor() + "YOU CAUGHT A " + caughtFish.name.toUpperCase() + "!" + RESET);
            System.out.printf("Weight: %.2f kg | Rarity: %s | XP: %d\n", weight, caughtFish.rarity, earnedXp);
            System.out.println("The fish has been added to your bucket (" +
                    player.getInventory().size() + "/" + player.getMaxInventorySize() + ").");

            // ==================== NEW FEATURES IMPLEMENTED HERE ====================

            // 1. RECORD SYSTEM CHECK
            // Verifies if the caught fish is a new personal best weight record
            boolean isNewRecord = player.checkAndSaveRecord(caughtFish.name, weight);
            if (isNewRecord) {
                System.out.println("\u001B[33m🎉 NEW PERSONAL RECORD for " + caughtFish.name.toUpperCase() + "!\u001B[0m");
            }

            // 2. QUEST SYSTEM TRACKING
            // Updates progress: Type 1 = total fish caught, Type 2 = total value/xp tracker
            questManager.updateAllProgress(1, 1, caughtFish.rarity, player);
            questManager.updateAllProgress(2, baseValue, caughtFish.rarity, player);

            // =======================================================================

        } else {
            if (isBaitTrulyActive) {
                player.useBait();
            }
            System.out.println("The fish got away... Try again!");
        }
    }

    // --- SYSTEM GETTERS & SETTERS (Required by Shop, Game, and SaveManager) ---

    public Player getPlayer() {
        return this.player;
    }

    public HashMap<Integer, Fish> getFishCatalog() {
        return this.fishCatalog;
    }

    public int getCurrentZone() {
        return this.currentZone;
    }

    public void setCurrentZone(int zone) {
        this.currentZone = zone;
    }

    /**
     * Legacy method bridge to display player profile through GameLogic if needed.
     */
    public void displayPlayerStats() {
        player.showStats();
    }
}
