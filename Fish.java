public class Fish {
    int id; //To find fish easier down the line
    String name;
    String rarity;
    double minWeight, maxWeight;
    double pricePerKg;
    int zone; //The zone the fish found in

    public Fish(int id, String name, String rarity, double minWeight, double maxWeight, double pricePerKg, int zone) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.pricePerKg = pricePerKg;
        this.zone = zone;
    }
}
