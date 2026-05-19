import java.util.ArrayList;
import java.util.List;

public class Player {
    private int money;
    private int xp;
    private int level;
    private int xpToNextLevel;
    private List<CaughtFish> inventory;

    // --- NEW: Gear Levels (0 = basic/none, 1-5 = upgrades) ---
    private int bucketLevel = 0;
    private int lineLevel = 0;
    private int rodLevel = 0;
    private int reelLevel = 0;

    // --- NEW: Bait System (0 = none, 1-5 = current equipped bait type, and count) ---
    private int equippedBaitType = 0;
    private int baitCount = 0;

    public Player() {
        this.money = 0;
        this.xp = 0;
        this.level = 1;
        this.xpToNextLevel = 100;
        this.inventory = new ArrayList<>();
    }

    // --- PROGRESSION METHODS ---

    public void addMoney(int amount) { this.money += amount; }
    public void subtractMoney(int amount) { this.money -= amount; }

    public void addXp(int amount) {
        this.xp += amount;
        while (this.xp >= xpToNextLevel) {
            levelUp();
        }
    }

    private void levelUp() {
        this.xp -= xpToNextLevel;
        this.level++;


        this.xpToNextLevel = 100 + (level - 1) * 300;

        System.out.println("\n\u001B[33m⭐ LEVEL UP! You reached Level " + level + "! ⭐\u001B[0m");
    }

    // --- INVENTORY METHODS WITH MAX CAPACITY ---

    /**
     * Gets maximum inventory capacity. Base is 10, increases by 10 per Bucket Level.
     */
    public int getMaxInventorySize() {
        return 10 + (bucketLevel * 10);
    }

    public boolean isInventoryFull() {
        return inventory.size() >= getMaxInventorySize();
    }

    public void addFishToInventory(int fishId, double weight) {
        if (!isInventoryFull()) {
            inventory.add(new CaughtFish(fishId, weight));
        }
    }

    public void clearInventory() { inventory.clear(); }

    // --- NEW: BONUS CALCULATIONS FOR SHOP & FISHING ---

    /**
     * Calculates total selling bonus.
     * Maxed gear (All Lvl 5) now gives +100% profit. Lvl 5 bait adds +40%.
     */
    public double getPriceMultiplier() {
        double multiplier = 1.0;

        // TUNED UP: Lvl 1=+5%, Lvl 2=+10%, Lvl 3=+15%, Lvl 4=+20%, Lvl 5=+25%
        double[] gearPriceBonuses = {0.0, 0.05, 0.10, 0.15, 0.20, 0.25};

        multiplier += gearPriceBonuses[Math.min(5, Math.max(0, bucketLevel))];
        multiplier += gearPriceBonuses[Math.min(5, Math.max(0, lineLevel))];
        multiplier += gearPriceBonuses[Math.min(5, Math.max(0, rodLevel))];
        multiplier += gearPriceBonuses[Math.min(5, Math.max(0, reelLevel))];

        // Active Bait price bonuses (Lvl 1=5%, Lvl 2=10%, Lvl 3=20%, Lvl 4=30%, Lvl 5=40%)
        double[] baitPriceBonuses = {0.0, 0.05, 0.10, 0.20, 0.30, 0.40};

        if (baitCount > 0 && equippedBaitType >= 1 && equippedBaitType <= 5) {
            multiplier += baitPriceBonuses[equippedBaitType];
        }

        return multiplier;
    }

    /**
     * Calculates total rarity luck.
     * Maxed gear (All Lvl 5) now gives +15% passive luck. Lvl 5 bait adds +35%.
     */
    public double getRarityLuckBonus() {
        double totalLuck = 0.0;

        // TUNED UP: Lvl 1=+1%, Lvl 2=+2%, Lvl 3=+3%, Lvl 4=+4%, Lvl 5=+5%
        double[] gearLuckBonuses = {0.0, 0.01, 0.02, 0.03, 0.04, 0.05};

        totalLuck += gearLuckBonuses[Math.min(5, Math.max(0, rodLevel))];
        totalLuck += gearLuckBonuses[Math.min(5, Math.max(0, lineLevel))];
        totalLuck += gearLuckBonuses[Math.min(5, Math.max(0, reelLevel))];

        // Active Bait luck bonuses (Lvl 1=0%, Lvl 2=5%, Lvl 3=15%, Lvl 4=20%, Lvl 5=35%)
        double[] baitLuckBonuses = {0.0, 0.00, 0.05, 0.15, 0.20, 0.35};

        if (baitCount > 0 && equippedBaitType >= 1 && equippedBaitType <= 5) {
            totalLuck += baitLuckBonuses[equippedBaitType];
        }

        return totalLuck;
    }

    /**
     * Consumes 1 bait if available.
     */
    public void useBait() {
        if (baitCount > 0) {
            baitCount--;
            if (baitCount == 0) {
                equippedBaitType = 0;
                System.out.println("\u001B[31m[System] You used your last bait!\u001B[0m");
            }
        }
    }

    // --- SYSTEM METHODS (Save/Load/Stats) ---

    public void loadPlayerData(int xp, int money, int level, List<CaughtFish> loadedInventory,
                               int bucketLevel, int lineLevel, int rodLevel, int reelLevel,
                               int baitType, int baitCount) {
        this.xp = xp;
        this.money = money;
        this.level = level;
        this.xpToNextLevel = 100 + (level - 1) * 300;
        this.inventory = new ArrayList<>(loadedInventory);
        this.bucketLevel = bucketLevel;
        this.lineLevel = lineLevel;
        this.rodLevel = rodLevel;
        this.reelLevel = reelLevel;
        this.equippedBaitType = baitType;
        this.baitCount = baitCount;
    }

    public void resetStats() {
        this.xp = 0;
        this.money = 0;
        this.level = 1;
        this.xpToNextLevel = 100;
        this.inventory.clear();
        this.bucketLevel = 0;
        this.lineLevel = 0;
        this.rodLevel = 0;
        this.reelLevel = 0;
        this.equippedBaitType = 0;
        this.baitCount = 0;
    }

    public void showStats() {
        System.out.println("\n\u001B[34m=================== PLAYER PROFILE ===================\u001B[0m");
        System.out.println(" Level: " + level + " | XP: " + xp + " / " + xpToNextLevel);
        System.out.println(" Balance: " + money + " €");
        System.out.println(" Bucket: " + inventory.size() + " / " + getMaxInventorySize() + " fish");
        System.out.println("------------------------------------------------------");
        System.out.println(" \u001B[36m--- Equipment Levels ---\u001B[0m");
        System.out.println(" Rod: Lvl " + rodLevel + " | Line: Lvl " + lineLevel +
                " | Reel: Lvl " + reelLevel + " | Bucket: Lvl " + bucketLevel);
        System.out.println("------------------------------------------------------");

        double totalProfitPercent = (getPriceMultiplier() - 1.0) * 100;
        double totalLuckPercent = getRarityLuckBonus() * 100;

        System.out.printf(" 💰 Total Selling Bonus: +%.1f%%\n", totalProfitPercent);
        System.out.printf(" 🍀 Total Rarity Luck:   +%.1f%%\n", totalLuckPercent);

        if (baitCount > 0 && equippedBaitType >= 1) {
            System.out.println(" \u001B[35m[Active Bait]: Lvl " + equippedBaitType + " (" + baitCount + " uses left)\u001B[0m");
        } else {
            System.out.println(" [Active Bait]: None");
        }
        System.out.println("\u001B[34m======================================================\u001B[0m");
    }

    // --- GETTERS & SETTERS FOR UPGRADES ---

    public int getMoney() { return money; }
    public int getXp() { return xp; }
    public int getLevel() { return level; }
    public List<CaughtFish> getInventory() { return inventory; }

    public int getBucketLevel() { return bucketLevel; }
    public void setBucketLevel(int lvl) { this.bucketLevel = lvl; }

    public int getLineLevel() { return lineLevel; }
    public void setLineLevel(int lvl) { this.lineLevel = lvl; }

    public int getRodLevel() { return rodLevel; }
    public void setRodLevel(int lvl) { this.rodLevel = lvl; }

    public int getReelLevel() { return reelLevel; }
    public void setReelLevel(int lvl) { this.reelLevel = lvl; }

    public int getEquippedBaitType() { return equippedBaitType; }
    public void setEquippedBaitType(int type) { this.equippedBaitType = type; }

    public int getBaitCount() { return baitCount; }
    public void setBaitCount(int count) { this.baitCount = count; }
}
