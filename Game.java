import java.util.Scanner;

public class Game {
    private static int currentSlot = 1; // Default slot

    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic();
        AreaManager areaManager = new AreaManager(); // Initialize the AreaManager
        Shop shop = new Shop(); // Initialize the Shop once outside the loop
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== SUPER FISHING SIMULATOR 2026 ===");

        // Initial slot selection
        System.out.println("Select Save Slot (1, 2, or 3):");
        try {
            currentSlot = Integer.parseInt(scanner.nextLine());
            if (currentSlot < 1 || currentSlot > 3) currentSlot = 1; // Default to 1 if out of bounds
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, defaulting to Slot 1.");
            currentSlot = 1;
        }

        // Load game with the required areaManager parameter
        SaveManager.loadGame(gameLogic.getPlayer(), gameLogic, currentSlot, areaManager);

        while (running) {
            System.out.println("\n----------- MAIN MENU -----------");
            System.out.println("1. Start Fishing");
            System.out.println("2. Shop");
            System.out.println("3. Areas & Travel");
            System.out.println("4. Player Stats");
            System.out.println("5. Manage Saves (Change Slot)");
            System.out.println("6. Save and Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

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
                    // Open shop with player inventory and fish catalog
                    shop.openShop(gameLogic.getPlayer(), gameLogic.getFishCatalog());
                    break;
                case "3":
                    // Open the area travel menu
                    areaManager.openAreaMenu(gameLogic.getPlayer(), gameLogic);
                    break;
                case "4":
                    // Show current player profile
                    gameLogic.getPlayer().showStats();
                    break;
                case "5":
                    System.out.print("Switch to Slot (1, 2, 3): ");
                    try {
                        currentSlot = Integer.parseInt(scanner.nextLine());
                        if (currentSlot < 1 || currentSlot > 3) currentSlot = 1;
                    } catch (NumberFormatException e) {
                        currentSlot = 1;
                    }
                    // Load the newly selected slot
                    SaveManager.loadGame(gameLogic.getPlayer(), gameLogic, currentSlot, areaManager);
                    break;
                case "6":
                    // Save the game with the required areaManager parameter before exiting
                    SaveManager.saveGame(gameLogic.getPlayer(), gameLogic.getCurrentZone(), currentSlot, areaManager);
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
