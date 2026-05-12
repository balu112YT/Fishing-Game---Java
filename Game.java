import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic();
        Scanner scanner = new Scanner(System.in);

        // 1. LOAD GAME AT START
        SaveManager.loadGame(gameLogic.getPlayer(), gameLogic);

        System.out.println("=== SUPER FISHING SIMULATOR 2026 ===");
        System.out.println("Type 'fish', 'stats' or 'exit'");

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("fish")) {
                gameLogic.fishing();
            } else if (input.equals("stats")) {
                gameLogic.displayPlayerStats();
            } else if (input.equals("exit")) {
                // 2. SAVE GAME BEFORE EXITING
                SaveManager.saveGame(gameLogic.getPlayer(), gameLogic.getCurrentZone());
                System.out.println("Goodbye!");
                break;
            }else {
                System.out.println("Invalid input, use 'fish' or 'stats' or 'exit'");
            }
        }
        scanner.close();
    }
}
