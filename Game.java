package fishinggamejava.fishinggamejava;

import java.util.Scanner;

public class Game {
    private static int currentSlot = 1; // Default slot

    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic();
        AreaManager areaManager = new AreaManager(); // Initialize the AreaManager
        Shop shop = new Shop(); // Initialize the Shop once outside the loop
        Investment investment = new Investment(); // Initialize the Investment desk

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

        // Load game with the required parameters
        SaveManager.loadGame(gameLogic.getPlayer(), gameLogic, currentSlot, areaManager);

        while (running) {
            System.out.println("\n----------- MAIN MENU -----------");
            System.out.println("1. Start Fishing");
            System.out.println("2. Shop");
            System.out.println("3. Areas & Travel");
            System.out.println("4. Player Stats");
            System.out.println("5. Investment Center");
            System.out.println("6. Fish Journal");
            System.out.println("7. Quests");
            System.out.println("8. Manage Saves or change slot");
            System.out.println("9. Save and Exit");
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
                    // Open the investment and market predictor desk
                    investment.openInvestmentMenu(gameLogic.getPlayer());
                    break;

                case "6":
                    // Show personal best fish records
                    gameLogic.getPlayer().showJournal();
                    break;

                case "7":
                    // Dedicated sub-menu loop for Quests (Stays inside until user chooses to exit)
                    boolean insideQuests = true;
                    while (insideQuests) {
                        gameLogic.getQuestManager().showQuestStatus();

                        // Dynamically calculate reroll cost based on current area/zone
                        // Area 1 = 200, Area 2 = 300, Area 3 = 450, Area 4 = 600, Area 5 = 750
                        int zone = gameLogic.getCurrentZone();
                        int rerollCost = 200;
                        if (zone == 2) rerollCost = 300;
                        else if (zone == 3) rerollCost = 450;
                        else if (zone == 4) rerollCost = 600;
                        else if (zone >= 5) rerollCost = 750;

                        System.out.println("\n Options:");
                        System.out.println(" 1-3. Claim/Reroll corresponding Slot (Completed: FREE | Active: " + rerollCost + " €)");
                        System.out.println(" 4.    Return to Main Menu");
                        System.out.print(" Choose an operation: ");

                        String questChoice = scanner.nextLine().trim();

                        if (questChoice.equals("4")) {
                            insideQuests = false; // Gracefully breaks loop, returns to main menu
                        } else if (questChoice.equals("1") || questChoice.equals("2") || questChoice.equals("3")) {
                            int targetSlot = Integer.parseInt(questChoice) - 1; // Convert to array index (0-2)
                            QuestManager.Quest selectedQuest = gameLogic.getQuestManager().getActiveQuests()[targetSlot];

                            // Check if the quest is already completed
                            if (selectedQuest.completed) {
                                gameLogic.getQuestManager().generateScaledQuest(targetSlot, gameLogic.getPlayer().getLevel(), zone);
                                System.out.println("\n\u001B[32m[System] Contract fulfilled! Next quest generated for Slot " + questChoice + " (FREE)!\u001B[0m");
                            } else {
                                if (gameLogic.getPlayer().getMoney() >= rerollCost) {
                                    gameLogic.getPlayer().subtractMoney(rerollCost);
                                    gameLogic.getQuestManager().generateScaledQuest(targetSlot, gameLogic.getPlayer().getLevel(), zone);
                                    System.out.println("\n\u001B[32m[System] Active contract broken. Slot " + questChoice + " replaced! (-" + rerollCost + " €)\u001B[0m");
                                } else {
                                    System.out.println("\n\u001B[31m[System] Insufficient funds to break this contract! (Needs " + rerollCost + " €)\u001B[0m");
                                }
                            }
                        } else {
                            System.out.println("\nInvalid menu option inside logistics.");
                        }
                    }
                    break;

                case "8":
                    System.out.print("Switch to any Slot (1, 2, 3...): ");
                    try {
                        currentSlot = Integer.parseInt(scanner.nextLine());
                        if (currentSlot < 1 || currentSlot > 3) currentSlot = 1;
                    } catch (NumberFormatException e) {
                        currentSlot = 1;
                    }
                    // Load the newly selected slot
                    SaveManager.loadGame(gameLogic.getPlayer(), gameLogic, currentSlot, areaManager);
                    break;

                case "9":
                    // Save the game directly passing gameLogic for full component tracking
                    SaveManager.saveGame(gameLogic.getPlayer(), gameLogic, currentSlot, areaManager);
                    System.out.println("Exiting game... Tight lines!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option, try again!");
            }
        }
        scanner.close();
    }
}
