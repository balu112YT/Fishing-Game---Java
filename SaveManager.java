import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveManager {
    //It will create a file with everything saved in it.
    private static final String SAVE_FILE = "fishing_save.txt";

    public static void saveGame(int xp, int money, int zone, List<Integer> caughtFishIds, List<Integer> caughtFishRarities) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
            writer.println(xp);
            writer.println(money);
            writer.println(zone);
            writer.println(caughtFishIds.size());

            for (int i = 0; i < caughtFishIds.size(); i++) {
                // Saving fish as "ID Rarity" on one line
                writer.println(caughtFishIds.get(i) + " " + caughtFishRarities.get(i));
            }
            System.out.println("Game saved to readable text file!");
        } catch (IOException e) {
            System.err.println("Error while saving: " + e.getMessage());
        }
    }

    // LOAD: Reading readable text back into the game
    public static void loadGame() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            System.out.println("No save found!");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNextInt()) return;

            int xp = scanner.nextInt();
            int money = scanner.nextInt();
            int zone = scanner.nextInt();
            int fishCount = scanner.nextInt();

            System.out.println("--- LOADED FROM TEXT ---");
            System.out.println("XP: " + xp + " | Money: " + money + " | Zone: " + zone);

            for (int i = 0; i < fishCount; i++) {
                int id = scanner.nextInt();
                int rarity = scanner.nextInt();
                System.out.println("Fish: ID " + id + ", Rarity " + rarity);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        }
    }

    public static void main(String[] args) {
        List<Integer> ids = List.of(1, 2);
        List<Integer> rarities = List.of(0, 3);

        saveGame(550, 1000, 2, ids, rarities);
        loadGame();
    }
}
