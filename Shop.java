package fishinggamejava.fishinggamejava;

import java.util.Scanner;
import java.util.List;
import java.util.HashMap;

public class Shop {
    private Scanner scanner = new Scanner(System.in);

    // Price arrays for Upgrades (Lvl 1 to Lvl 5)
    private final int[] upgradePrices = {500, 1250, 2500, 4500, 10000};

    // Price arrays for Baits (Lvl 1 to Lvl 5)
    private final int[] baitPrices = {50, 125, 250, 350, 650};

    public void openShop(Player player, HashMap<Integer, Fish> fishCatalog) {
        boolean inShop = true;

        while (inShop) {
            System.out.println("\n\u001B[33m================= THE FISHING SHOP =================\u001B[0m");
            System.out.println("Balance: " + player.getMoney() + " € | Level: " + player.getLevel());
            System.out.println("Bucket: " + player.getInventory().size() + " / " + player.getMaxInventorySize() + " fish");
            System.out.println("----------------------------------------------------");
            System.out.println("1. 💰 Sell All Caught Fish");
            System.out.println("2. 🔍 View Bucket Content");
            System.out.println("3. 📦 Buy Equipment Upgrades (Requires Level 3)");
            System.out.println("4. 🪱 Buy Baits / Lures     (Requires Level 3)");
            System.out.println("5. 🚪 Exit Shop");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    sellAllFish(player, fishCatalog);
                    break;
                case "2":
                    viewInventory(player, fishCatalog);
                    break;
                case "3":
                    if (player.getLevel() < 3) {
                        System.out.println("\u001B[31m[Locked] You must reach Level 3 to buy Equipment Upgrades!\u001B[0m");
                    } else {
                        openUpgradeMenu(player);
                    }
                    break;
                case "4":
                    if (player.getLevel() < 3) {
                        System.out.println("\u001B[31m[Locked] You must reach Level 3 to buy Baits!\u001B[0m");
                    } else {
                        openBaitMenu(player);
                    }
                    break;
                case "5":
                    inShop = false;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void sellAllFish(Player player, HashMap<Integer, Fish> fishCatalog) {
        List<CaughtFish> inv = player.getInventory();
        if (inv.isEmpty()) {
            System.out.println("\u001B[31mYour bucket is empty!\u001B[0m");
            return;
        }

        int totalMoney = 0;
        double multiplier = player.getPriceMultiplier();

        for (CaughtFish cf : inv) {
            Fish f = fishCatalog.get(cf.fishId);
            if (f != null) {
                // Base value from fish math multiplied by gear bonus
                int baseValue = f.calculateValue(cf.weight);
                totalMoney += (int) (baseValue * multiplier);
            }
        }

        player.addMoney(totalMoney);
        player.clearInventory();
        System.out.printf("\u001B[32m✔ Sold everything for %d €! (Multiplier: %.1f%% applied)\u001B[0m\n",
                totalMoney, multiplier * 100);
    }

    private void viewInventory(Player player, HashMap<Integer, Fish> fishCatalog) {
        List<CaughtFish> inv = player.getInventory();
        if (inv.isEmpty()) {
            System.out.println("Your bucket is empty.");
            return;
        }

        System.out.println("\n--- Your Bucket Content ---");
        for (int i = 0; i < inv.size(); i++) {
            CaughtFish cf = inv.get(i);
            Fish f = fishCatalog.get(cf.fishId);
            if (f != null) {
                System.out.printf("%d. %-15s | Weight: %5.2f kg | Rarity: %s\n",
                        (i + 1), f.name, cf.weight, f.rarity);
            }
        }
    }

    private void openUpgradeMenu(Player player) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n\u001B[36m--- EQUIPMENT UPGRADES (Max Lvl 5) ---\u001B[0m");
            System.out.println("Balance: " + player.getMoney() + " €");

            // Display options with current levels and prices
            System.out.println("1. Bucket Upgrade (Current: Lvl " + player.getBucketLevel() + ") -> " + getPriceText(player.getBucketLevel(), upgradePrices));
            System.out.println("2. Fishing Line   (Current: Lvl " + player.getLineLevel() + ") -> " + getPriceText(player.getLineLevel(), upgradePrices));
            System.out.println("3. Fishing Rod    (Current: Lvl " + player.getRodLevel() + ") -> " + getPriceText(player.getRodLevel(), upgradePrices));
            System.out.println("4. Fishing Reel   (Current: Lvl " + player.getReelLevel() + ") -> " + getPriceText(player.getReelLevel(), upgradePrices));
            System.out.println("5. Back");
            System.out.print("Select an item to upgrade: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    if (buyUpgrade(player, player.getBucketLevel())) player.setBucketLevel(player.getBucketLevel() + 1);
                    break;
                case "2":
                    if (buyUpgrade(player, player.getLineLevel())) player.setLineLevel(player.getLineLevel() + 1);
                    break;
                case "3":
                    if (buyUpgrade(player, player.getRodLevel())) player.setRodLevel(player.getRodLevel() + 1);
                    break;
                case "4":
                    if (buyUpgrade(player, player.getReelLevel())) player.setReelLevel(player.getReelLevel() + 1);
                    break;
                case "5":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void openBaitMenu(Player player) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n\u001B[35m--- BAIT MARKET (Single Use items) ---\u001B[0m");
            System.out.println("Active Bait: Lvl " + player.getEquippedBaitType() + " (" + player.getBaitCount() + " left)");
            System.out.println("Balance: " + player.getMoney() + " €");
            System.out.println("----------------------------------------");
            System.out.println("Higher level baits give better luck and selling prices!");
            System.out.println("1. Buy Level 1 Bait ( +0% Luck /  +5% Price) -> " + baitPrices[0] + " €");
            System.out.println("2. Buy Level 2 Bait ( +5% Luck / +10% Price) -> " + baitPrices[1] + " €");
            System.out.println("3. Buy Level 3 Bait (+15% Luck / +20% Price) -> " + baitPrices[2] + " €");
            System.out.println("4. Buy Level 4 Bait (+20% Luck / +30% Price) -> " + baitPrices[3] + " €");
            System.out.println("5. Buy Level 5 Bait (+35% Luck / +40% Price) -> " + baitPrices[4] + " €");
            System.out.println("6. Back");
            System.out.print("Select a bait level to purchase: ");

            String choice = scanner.nextLine();
            int choiceInt;
            try {
                choiceInt = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
                continue;
            }

            if (choiceInt == 6) {
                inMenu = false;
            } else if (choiceInt >= 1 && choiceInt <= 5) {
                buyBait(player, choiceInt);
            } else {
                System.out.println("Invalid option!");
            }
        }
    }

    // Helper to get text or standard cost
    private String getPriceText(int currentLvl, int[] prices) {
        if (currentLvl >= 5) return "\u001B[33m[MAXED OUT]\u001B[0m";
        return prices[currentLvl] + " €";
    }

    // Process upgrade transaction
    private boolean buyUpgrade(Player player, int currentLvl) {
        if (currentLvl >= 5) {
            System.out.println("\u001B[31mThis item is already at maximum level!\u001B[0m");
            return false;
        }
        int cost = upgradePrices[currentLvl];
        if (player.getMoney() < cost) {
            System.out.println("\u001B[31mNot enough money! You need " + cost + " €\u001B[0m");
            return false;
        }
        player.subtractMoney(cost);
        System.out.println("\u001B[32m✔ Upgrade purchased successfully!\u001B[0m");
        return true;
    }

    // Process bait purchase transaction
    private void buyBait(Player player, int baitTier) {
        int cost = baitPrices[baitTier - 1];
        if (player.getMoney() < cost) {
            System.out.println("\u001B[31mNot enough money!\u001B[0m");
            return;
        }

        // If player switches bait tier, erase previous remaining baits
        if (player.getEquippedBaitType() != baitTier && player.getBaitCount() > 0) {
            System.out.println("\u001B[33m[Warning] You replaced your old bait tier. Remaining uses were lost.\u001B[0m");
            player.setBaitCount(0);
        }

        player.subtractMoney(cost);
        player.setEquippedBaitType(baitTier);
        player.setBaitCount(player.getBaitCount() + 1); // +1 use per purchase
        System.out.println("\u001B[32m✔ Bought 1x Lvl " + baitTier + " Bait! Total charges: " + player.getBaitCount() + "\u001B[0m");
    }
}
