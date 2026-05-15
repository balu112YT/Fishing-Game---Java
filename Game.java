import java.util.Scanner;

public class Game {
    private static int currentSlot = 1; // Default slot

    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== SUPER FISHING SIMULATOR 2026 ===");

        // Initial slot selection
        System.out.println("Select Save Slot (1, 2, or 3):");
        currentSlot = Integer.parseInt(scanner.nextLine());
        SaveManager.loadGame(gameLogic.getPlayer(), gameLogic, currentSlot);

        while (running) {
            System.out.println("\n----------- MAIN MENU -----------");
            System.out.println("1. Start Fishing");
            System.out.println("2. Shop (Coming Soon)");
            System.out.println("3. Areas (Coming Soon)");
            System.out.println("4. Player Stats");
            System.out.println("5. Manage Saves (Change Slot)");
            System.out.println("6. Save and Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            Shop shop = new Shop();

            switch (choice) {
                case "1":
                    System.out.println("Type 'cast' to fish or 'back' to return to menu.");
                    while (true) {
                        System.out.print("Fishing > ");
                        String fishInput = scanner.nextLine().toLowerCase();
                        if (fishInput.equals("cast")) gameLogic.fishing();
                        else if (fishInput.equals("back")) break;
                    }
                    break;
                case "2":
                    shop.openShop(gameLogic.getPlayer(), gameLogic.getFishCatalog());
                    break;
                case "3":
                    System.out.println("Current Area: " + gameLogic.getCurrentZone());
                    break;
                case "4":
                    gameLogic.displayPlayerStats();
                    break;
                case "5":
                    System.out.print("Switch to Slot (1, 2, 3): ");
                    currentSlot = Integer.parseInt(scanner.nextLine());
                    SaveManager.loadGame(gameLogic.getPlayer(), gameLogic, currentSlot);
                    break;
                case "6":
                    SaveManager.saveGame(gameLogic.getPlayer(), gameLogic.getCurrentZone(), currentSlot);
                    System.out.println("Exiting... Tight lines!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, try again!");
            }
        }
        scanner.close();
    }
}
