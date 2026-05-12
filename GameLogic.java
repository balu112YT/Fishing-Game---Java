import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameLogic {
    // Player data and game state
    private Player player = new Player();
    private HashMap<Integer, Fish> fishCatalog = new HashMap<>();
    private Random random = new Random();
    private int currentZone = 1; // Default starting zone
    private final String RESET = "\u001B[0m";

    // Constructor: Initializes the fish database
    public GameLogic() {
        FishDatabase.loadCatalog(fishCatalog);
    }

    // --- CORE FISHING LOGIC ---
    public void fishing() {
        // 1. Roll for rarity based on percentages
        double roll = random.nextDouble() * 100;
        String chosenRarity;

        if (roll < 60) chosenRarity = "Common";        // 60% chance
        else if (roll < 85) chosenRarity = "Rare";     // 25% chance
        else if (roll < 95) chosenRarity = "Epic";     // 10% chance
        else if (roll < 99) chosenRarity = "Mythic";   // 4% chance
        else chosenRarity = "Legendary";               // 1% chance

        // 2. Filter available fish based on current zone and rolled rarity
        List<Fish> possibleFish = new ArrayList<>();
        for (Fish f : fishCatalog.values()) {
            if (f.zone == currentZone && f.rarity.equals(chosenRarity)) {
                possibleFish.add(f);
            }
        }

        // Safety fallback: if no fish found for the rarity, default to Common
        if (possibleFish.isEmpty()) {
            for (Fish f : fishCatalog.values()) {
                if (f.zone == currentZone && f.rarity.equals("Common")) {
                    possibleFish.add(f);
                }
            }
        }

        // 3. Catch a random fish from the filtered list and calculate stats
        if (!possibleFish.isEmpty()) {
            Fish caughtFish = possibleFish.get(random.nextInt(possibleFish.size()));

            // Generate random weight between min and max
            double weight = caughtFish.minWeight + (caughtFish.maxWeight - caughtFish.minWeight) * random.nextDouble();
            int value = caughtFish.calculateValue(weight);

            // Calculate XP (Minimum 5 XP, or 1:1 ratio with value)
            int earnedXp = Math.max(5, value);

            // Reward the player
            player.addMoney(value);
            player.addXp(earnedXp);

            // Print the result to console
            System.out.println("\nCasting the line...");
            System.out.print("Something is biting... ");
            System.out.println(caughtFish.getColor() + "YOU CAUGHT A " + caughtFish.name.toUpperCase() + "!" + RESET);
            System.out.printf("Weight: %.2f kg | Value: %d € | XP: %d | Rarity: %s\n",
                    weight, value, earnedXp, caughtFish.rarity);
        }
    }

    // --- GETTERS AND SETTERS FOR SAVE/LOAD AND STATS ---

    public void displayPlayerStats() {
        player.showStats();
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getCurrentZone() {
        return this.currentZone;
    }

    public void setCurrentZone(int zone) {
        this.currentZone = zone;
    }
}
