import java.util.ArrayList;
import java.util.List;

public class FishDatabase {
    public static List<Fish> getAllFish() {
        List<Fish> db = new ArrayList<>();

        // --- LEVEL 1 - Starter Waters ---
        db.add(new Fish(1, "Carp", "Common", 1.5, 3.0, 2.0, 1));
        db.add(new Fish(2, "Crucian Carp", "Common", 0.25, 0.5, 4.0, 1));
        db.add(new Fish(3, "Perch", "Common", 0.2, 0.5, 5.0, 1));
        db.add(new Fish(4, "Small Bream", "Common", 0.1, 0.2, 4.0, 1));
        db.add(new Fish(5, "Tench", "Rare", 0.5, 2.0, 10.0, 1));
        db.add(new Fish(6, "Pike", "Rare", 0.5, 1.5, 14.0, 1));
        db.add(new Fish(7, "Eel", "Rare", 1.0, 3.6, 18.0, 1));
        db.add(new Fish(8, "Wels Catfish", "Epic", 5.0, 20.0, 10.0, 1));
        db.add(new Fish(9, "Sturgeon", "Epic", 5.0, 15.0, 12.0, 1));
        db.add(new Fish(10, "Giant Goldfish", "Mythic", 1.0, 2.0, 150.0, 1));
        db.add(new Fish(11, "Electric Eel", "Mythic", 20.0, 40.0, 8.0, 1));
        db.add(new Fish(12, "Ancient Carp", "Legendary", 10.0, 15.0, 60.0, 1));

        // --- LEVEL 2 - Deeper Lakes ---
        db.add(new Fish(13, "Common Bream", "Common", 1.0, 1.5, 10.0, 2));
        db.add(new Fish(14, "Rudd", "Common", 0.2, 0.5, 30.0, 2));
        db.add(new Fish(15, "Roach", "Common", 0.05, 0.2, 70.0, 2));
        db.add(new Fish(16, "Small Zander", "Common", 1.0, 3.0, 6.0, 2));
        db.add(new Fish(17, "Zander", "Rare", 1.5, 2.0, 25.0, 2));
        db.add(new Fish(18, "Great Pike", "Rare", 3.0, 5.0, 12.0, 2));
        db.add(new Fish(19, "Largemouth Bass", "Rare", 1.0, 2.0, 30.0, 2));
        db.add(new Fish(20, "Giant Wels Catfish", "Epic", 5.0, 20.0, 10.0, 2));
        db.add(new Fish(21, "Albino Pike", "Epic", 6.0, 12.0, 18.0, 2));
        db.add(new Fish(22, "Shadow Fish", "Mythic", 4.0, 6.0, 80.0, 2));
        db.add(new Fish(23, "Lightning Fish", "Mythic", 1.0, 3.0, 150.0, 2));
        db.add(new Fish(24, "Deepsea Ghost Fish", "Legendary", 1.0, 2.0, 600.0, 2));

        // --- LEVEL 3 - Rivers ---
        db.add(new Fish(25, "Barbel", "Common", 1.0, 2.0, 7.0, 3));
        db.add(new Fish(26, "Common Nase", "Common", 0.5, 1.0, 14.0, 3));
        db.add(new Fish(27, "Chub", "Common", 0.5, 1.5, 10.0, 3));
        db.add(new Fish(28, "Bleak", "Common", 0.03, 0.05, 300.0, 3));

        db.add(new Fish(29, "Atlantic Salmon", "Rare", 3.0, 8.0, 12.0, 3));
        db.add(new Fish(30, "Brown Trout", "Rare", 1.0, 3.0, 32.0, 3));
        db.add(new Fish(31, "Large Barbel", "Rare", 3.0, 5.0, 18.0, 3));

        db.add(new Fish(32, "Golden Trout", "Epic", 0.5, 1.5, 180.0, 3));
        db.add(new Fish(33, "Giant Salmon", "Epic", 3.0, 6.0, 35.0, 3));

        db.add(new Fish(34, "Firefish", "Mythic", 0.4, 0.8, 750.0, 3));
        db.add(new Fish(35, "Icefish", "Mythic", 0.5, 1.0, 650.0, 3));

        db.add(new Fish(36, "River Guardian", "Legendary", 6.0, 12.0, 150.0, 3));
        return db;
    }
}
