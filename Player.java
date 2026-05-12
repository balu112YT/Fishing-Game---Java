public class Player {
    public void loadPlayerData(int xp, int money, int level) {
        this.xp = xp;
        this.money = money;
        this.level = level;
        this.xpToNextLevel = (int) (100 * Math.pow(1.5, level - 1));
    }
    private int money = 0;
    private int xp = 0;
    private int level = 1;
    private int xpToNextLevel = 100; // XP needed for a next level

    // Adding ,oney
    public void addMoney(int amount) {
        this.money += amount;
    }

    // Adding XP and checking level up
    public void addXp(int amount) {
        this.xp += amount;
        System.out.println("+" + amount + " XP");

        while (xp >= xpToNextLevel) {
            levelUp();
        }
    }

    private void levelUp() {
        xp -= xpToNextLevel;
        level++;
        xpToNextLevel = (int) (xpToNextLevel * 1.25); // With each level up you will need more XP
        System.out.println("\n\u001B[33m⭐ LEVEL UP! Now you are Level " + level + "! ⭐\u001B[0m");
        System.out.println("Next level at: " + xpToNextLevel + " XP");
    }

    // Getters (GameLogic will see the data)
    public int getMoney() { return money; }
    public int getLevel() { return level; }
    public int getXp() { return xp; }

    public void showStats() {
        System.out.println("\n--- PLAYER STATS ---");
        System.out.println("Level: " + level);
        System.out.println("XP: " + xp + "/" + xpToNextLevel);
        System.out.println("Balance: " + money + " €");
        System.out.println("--------------------");
    }
}
