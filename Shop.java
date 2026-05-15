import java.util.Scanner;
import java.util.List;
import java.util.HashMap;

public class Shop {
    private Scanner scanner = new Scanner(System.in);

    public void openShop(Player player, HashMap<Integer, Fish> fishCatalog) {
        boolean inShop = true;

        while (inShop) {
            System.out.println("\n\u001B[33m---------- FISH MARKET ----------\u001B[0m");
            System.out.println("Balance: " + player.getMoney() + " €");
            System.out.println("Bucket: " + player.getInventory().size() + " fish");
            System.out.println("---------------------------------");
            System.out.println("1. Sell All Fish");
            System.out.println("2. View Inventory");
            System.out.println("3. Back to Main Menu");
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
            System.out.println("Your bucket is empty!");
            return;
        }

        int totalMoney = 0;
        for (CaughtFish cf : inv) {
            Fish f = fishCatalog.get(cf.fishId);
            if (f != null) {
                // Now using the actual weight stored in CaughtFish
                totalMoney += f.calculateValue(cf.weight);
            }
        }

        player.addMoney(totalMoney);
        player.clearInventory();
        System.out.println("\u001B[32m✔ Sold everything for " + totalMoney + " €!\u001B[0m");
    }

    private void viewInventory(Player player, HashMap<Integer, Fish> fishCatalog) {
        List<CaughtFish> inv = player.getInventory();
        if (inv.isEmpty()) {
            System.out.println("Your bucket is empty.");
            return;
        }

        System.out.println("\n--- Your Bucket Content ---");
        for (int i = 0; i < inv.size(); i++) {
            // FIX: We need to get the CaughtFish object first, then its ID
            CaughtFish cf = inv.get(i);
            Fish f = fishCatalog.get(cf.fishId);

            if (f != null) {
                System.out.printf("%d. %-15s | Weight: %5.2f kg | Rarity: %s\n",
                        (i + 1), f.name, cf.weight, f.rarity);
            }
        }
    }
}
