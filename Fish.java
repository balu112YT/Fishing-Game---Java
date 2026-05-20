package fishinggamejava.fishinggamejava;

public class Fish {
    int id;
    String name;
    String rarity;
    double minWeight, maxWeight;
    double pricePerKg;
    int zone;

    public Fish(int id, String name, String rarity, double minWeight, double maxWeight, double pricePerKg, int zone) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.pricePerKg = pricePerKg;
        this.zone = zone;
    }

    // Calculates the price for each fish.
    public int calculateValue(double weight) {
        return (int) (weight * pricePerKg);
    }

    public String getColor() {
        return switch (rarity) {
            case "Common" -> "\u001B[37m"; // White
            case "Rare" -> "\u001B[32m";   // Green
            case "Epic" -> "\u001B[35m";   // Purple
            case "Mythic" -> "\u001B[31m"; // Red
            case "Legendary" -> "\u001B[33m"; // Yellow
            default -> "\u001B[0m";        // Reset
        };
    }
}
