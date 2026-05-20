package fishinggamejava.fishinggamejava;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AreaManager {
    private List<Area> areas = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public AreaManager() {
        // Default maps (ID, Name, Required Level, Price, Initially Unlocked)
        areas.add(new Area(1, "Starter Pond", 1, 0, true));
        areas.add(new Area(2, "Crystal Lake", 10, 1500, false));
        areas.add(new Area(3, "Amazon River", 20, 3000, false));
        areas.add(new Area(4, "Deep Ocean", 30, 6000, false));
        areas.add(new Area(5, "Volcanic Crater", 40, 12500, false));
    }

    /**
     * Opens the Area Selection and Unlock Menu.
     */
    public void openAreaMenu(Player player, GameLogic logic) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n\u001B[34m---------- MAPS & AREAS ----------\u001B[0m");
            System.out.println("Current Level: " + player.getLevel() + " | Balance: " + player.getMoney() + " €");
            System.out.println("Current Active Area: " + getAreaNameById(logic.getCurrentZone()));
            System.out.println("----------------------------------");

            // Display all maps and their availability status
            for (int i = 0; i < areas.size(); i++) {
                Area a = areas.get(i);
                String status = a.isUnlocked ? "\u001B[32m[UNLOCKED]\u001B[0m" : "\u001B[31m[LOCKED - " + a.price + " € & Lvl " + a.reqLevel + "]\u001B[0m";
                System.out.println((i + 1) + ". " + a.name + " " + status);
            }
            System.out.println((areas.size() + 1) + ". Back to Main Menu");
            System.out.print("Select a map to Travel/Unlock: ");

            String choice = scanner.nextLine();
            int index;
            try {
                index = Integer.parseInt(choice) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
                continue;
            }

            if (index == areas.size()) {
                inMenu = false;
            } else if (index >= 0 && index < areas.size()) {
                handleAreaSelection(areas.get(index), player, logic);
            } else {
                System.out.println("Invalid option!");
            }
        }
    }

    /**
     * Validates and handles traveling to an unlocked area or purchasing a locked one.
     */
    private void handleAreaSelection(Area area, Player player, GameLogic logic) {
        if (area.isUnlocked) {
            // Fast travel if already purchased
            logic.setCurrentZone(area.id);
            System.out.println("\u001B[32mTravelling to " + area.name + "...\u001B[0m");
        } else {
            // Attempt to purchase the locked area
            if (player.getLevel() < area.reqLevel) {
                System.out.println("\u001B[31m[Error] Your level (" + player.getLevel() + ") is too low! Required: " + area.reqLevel + "\u001B[0m");
            } else if (player.getMoney() < area.price) {
                System.out.println("\u001B[31m[Error] Not enough money! You need " + area.price + " €\u001B[0m");
            } else {
                // Successful purchase deducts money and changes zone
                player.subtractMoney(area.price);
                area.isUnlocked = true;
                logic.setCurrentZone(area.id);
                System.out.println("\u001B[33m⭐ SUCCESS! You unlocked and travelled to " + area.name + "! ⭐\u001B[0m");
            }
        }
    }

    public String getAreaNameById(int id) {
        for (Area a : areas) {
            if (a.id == id) return a.name;
        }
        return "Unknown";
    }

    public List<Area> getAreas() { return areas; }
}
