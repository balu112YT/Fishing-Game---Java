import java.util.ArrayList;
import java.util.List;

public class Player {
    private int money;
    private int xp;
    private int level;
    private int xpToNextLevel;

    // The inventory now stores CaughtFish objects (ID + weight)
    private List<CaughtFish> inventory;

    public Player() {
        this.money = 0;
        this.xp = 0;
        this.level = 1;
        this.xpToNextLevel = 100;
        this.inventory = new ArrayList<>();
    }

    // --- PROGRESSION METHODS ---

    /**
     * Adds money to the player's balance.
     */
    public void addMoney(int amount) {
        this.money += amount;
    }

    /**
     * Subtracts money (e.g., for shopping).
     * Note: Should be used after checking if the player has enough!
     */
    public void subtractMoney(int amount) {
        this.money -= amount;
    }

    /**
     * Adds XP and handles level-up logic.
     */
    public void addXp(int amount) {
        this.xp += amount;
        while (this.xp >= xpToNextLevel) {
            levelUp();
        }
    }

    private void levelUp() {
        this.xp -= xpToNextLevel;
        this.level++;
        // Leveling curve: each level requires 50% more XP than the last
        this.xpToNextLevel = (int) (100 * Math.pow(1.5, level - 1));

        System.out.println("\n\u001B[33m⭐ LEVEL UP! You reached Level " + level + "! ⭐\u001B[0m");
        System.out.println("Next level at: " + xpToNextLevel + " XP");
    }

    // --- INVENTORY METHODS ---

    /**
     * Adds a new caught fish to the inventory with its unique weight.
     */
    public void addFishToInventory(int fishId, double weight) {
        inventory.add(new CaughtFish(fishId, weight));
    }

    /**
     * Clears all fish from the inventory (e.g., after selling them).
     */
    public void clearInventory() {
        inventory.clear();
    }

    // --- SYSTEM METHODS (Save/Load/Stats) ---

    /**
     * Updates all player data at once (used by SaveManager).
     */
    public void loadPlayerData(int xp, int money, int level, List<CaughtFish> loadedInventory) {
        this.xp = xp;
        this.money = money;
        this.level = level;
        this.xpToNextLevel = (int) (100 * Math.pow(1.5, level - 1));
        this.inventory = new ArrayList<>(loadedInventory);
    }

    /**
     * Resets the player to default values (New Game).
     */
    public void resetStats() {
        this.xp = 0;
        this.money = 0;
        this.level = 1;
        this.xpToNextLevel = 100;
        this.inventory.clear();
    }

    /**
     * Prints the player's current status in a formatted block.
     */
    public void showStats() {
        System.out.println("\n\u001B[36m--- PLAYER PROFILE ---\u001B[0m");
        System.out.println("Level: " + level);
        System.out.println("XP: " + xp + " / " + xpToNextLevel);
        System.out.println("Balance: " + money + " €");
        System.out.println("Inventory: " + inventory.size() + " fish in the bucket");
        System.out.println("\u001B[36m----------------------\u001B[0m");
    }

    // --- GETTERS ---

    public int getMoney() { return money; }
    public int getXp() { return xp; }
    public int getLevel() { return level; }
    public List<CaughtFish> getInventory() { return inventory; }
}
