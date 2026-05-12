import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FishDatabase {
    public static List<Fish> getAllFish() {
        List<Fish> db = new ArrayList<>();

        // --- LEVEL 1 - Starter Waters (Alap árak) ---
        // Average Income: Common: ~5€ | Rare: ~15€ | Epic: ~75€ | Mythic: ~250€ | Legendary: ~600€
        db.add(new Fish(1, "Carp", "Common", 1.5, 3.0, 2.2, 1));
        db.add(new Fish(2, "Crucian Carp", "Common", 0.25, 0.5, 13.0, 1));
        db.add(new Fish(3, "Perch", "Common", 0.2, 0.5, 14.0, 1));
        db.add(new Fish(4, "Small Bream", "Common", 0.1, 0.2, 33.0, 1));

        db.add(new Fish(5, "Tench", "Rare", 0.5, 2.0, 12.0, 1));
        db.add(new Fish(6, "Pike", "Rare", 0.5, 1.5, 15.0, 1));
        db.add(new Fish(7, "Eel", "Rare", 1.0, 3.6, 6.5, 1));

        db.add(new Fish(8, "Wels Catfish", "Epic", 5.0, 20.0, 6.0, 1));
        db.add(new Fish(9, "Sturgeon", "Epic", 5.0, 15.0, 7.5, 1));

        db.add(new Fish(10, "Giant Goldfish", "Mythic", 1.0, 2.0, 165.0, 1));
        db.add(new Fish(11, "Electric Eel", "Mythic", 20.0, 40.0, 8.3, 1));

        db.add(new Fish(12, "Ancient Carp", "Legendary", 10.0, 15.0, 48.0, 1));

        // --- LEVEL 2 - Deeper Lakes (+25% C | +30% R/E | +35% M | +40% L) ---
        // Average Income: Common: ~6.5€ | Rare: ~20€ | Epic: ~98€ | Mythic: ~340€ | Legendary: ~840€
        db.add(new Fish(13, "Common Bream", "Common", 1.0, 1.5, 5.2, 2));
        db.add(new Fish(14, "Rudd", "Common", 0.2, 0.5, 18.5, 2));
        db.add(new Fish(15, "Roach", "Common", 0.05, 0.2, 52.0, 2));
        db.add(new Fish(16, "Small Zander", "Common", 1.0, 3.0, 3.2, 2));

        db.add(new Fish(17, "Zander", "Rare", 1.5, 2.0, 11.5, 2));
        db.add(new Fish(18, "Great Pike", "Rare", 3.0, 5.0, 5.0, 2));
        db.add(new Fish(19, "Largemouth Bass", "Rare", 1.0, 2.0, 13.3, 2));

        db.add(new Fish(20, "Giant Wels Catfish", "Epic", 5.0, 20.0, 7.8, 2));
        db.add(new Fish(21, "Albino Pike", "Epic", 6.0, 12.0, 10.9, 2));

        db.add(new Fish(22, "Shadow Fish", "Mythic", 4.0, 6.0, 68.0, 2));
        db.add(new Fish(23, "Lightning Fish", "Mythic", 1.0, 3.0, 170.0, 2));

        db.add(new Fish(24, "Deepsea Ghost Fish", "Legendary", 1.0, 2.0, 560.0, 2));

        // --- LEVEL 3 - Rivers (+25% C | +30% R/E | +35% M | +40% L) ---
        // Average Income: Common: ~8.2€ | Rare: ~26€ | Epic: ~127€ | Mythic: ~460€ | Legendary: ~1170€
        db.add(new Fish(25, "Barbel", "Common", 1.0, 2.0, 5.5, 3));
        db.add(new Fish(26, "Common Nase", "Common", 0.5, 1.0, 10.9, 3));
        db.add(new Fish(27, "Chub", "Common", 0.5, 1.5, 8.2, 3));
        db.add(new Fish(28, "Bleak", "Common", 0.03, 0.05, 205.0, 3));

        db.add(new Fish(29, "Atlantic Salmon", "Rare", 3.0, 8.0, 4.7, 3));
        db.add(new Fish(30, "Brown Trout", "Rare", 1.0, 3.0, 13.0, 3));
        db.add(new Fish(31, "Large Barbel", "Rare", 3.0, 5.0, 6.5, 3));

        db.add(new Fish(32, "Golden Trout", "Epic", 0.5, 1.5, 127.0, 3));
        db.add(new Fish(33, "Giant Salmon", "Epic", 3.0, 6.0, 28.2, 3));

        db.add(new Fish(34, "Firefish", "Mythic", 0.4, 0.8, 760.0, 3));
        db.add(new Fish(35, "Icefish", "Mythic", 0.5, 1.0, 610.0, 3));

        db.add(new Fish(36, "River Guardian", "Legendary", 6.0, 12.0, 130.0, 3));

        // --- LEVEL 4 - Oceans (+25% C | +30% R/E | +35% M | +40% L) ---
        // Average Income: Common: ~10.3€ | Rare: ~34€ | Epic: ~165€ | Mythic: ~620€ | Legendary: ~1630€
        db.add(new Fish(37, "Sardine", "Common", 0.1, 0.4, 41.2, 4));
        db.add(new Fish(38, "Mackerel", "Common", 0.5, 1.0, 13.7, 4));
        db.add(new Fish(39, "Small Tuna", "Common", 2.0, 3.0, 4.1, 4));
        db.add(new Fish(40, "Flounder", "Common", 1.0, 2.0, 6.8, 4));

        db.add(new Fish(41, "Barracuda", "Rare", 2.0, 8.0, 6.8, 4));
        db.add(new Fish(42, "Swordfish", "Rare", 60.0, 125.0, 0.36, 4));
        db.add(new Fish(43, "Squid", "Rare", 0.5, 1.0, 45.0, 4));

        db.add(new Fish(44, "Great White Shark", "Epic", 600.0, 1600.0, 0.15, 4));
        db.add(new Fish(45, "Giant Squid", "Epic", 150.0, 225.0, 0.88, 4));

        db.add(new Fish(46, "Abyssal Demonfish", "Mythic", 1.0, 2.0, 413.0, 4));
        db.add(new Fish(47, "Anglerfish", "Mythic", 150.0, 300.0, 2.75, 4));

        db.add(new Fish(48, "Lesser Leviathan", "Legendary", 250.0, 500.0, 4.35, 4));

        // --- LEVEL 5 - Ancient Waters (+25% C | +30% R/E | +35% M | +40% L) ---
        // Average Income: Common: ~12.8€ | Rare: ~44€ | Epic: ~215€ | Mythic: ~830€ | Legendary: ~2280€
        db.add(new Fish(49, "Mist Fish", "Common", 1.0, 2.0, 8.5, 5));
        db.add(new Fish(50, "Crystal Fish", "Common", 1.5, 3.0, 5.7, 5));
        db.add(new Fish(51, "Shadow Bream", "Common", 1.0, 1.5, 10.2, 5));
        db.add(new Fish(52, "Floating Fish", "Common", 0.5, 1.0, 17.0, 5));

        db.add(new Fish(53, "Lava Fish", "Rare", 2.5, 5.0, 11.7, 5));
        db.add(new Fish(54, "Frost Fish", "Rare", 2.0, 4.0, 14.6, 5));
        db.add(new Fish(55, "Toxic Spiritfish", "Rare", 1.5, 3.0, 19.5, 5));

        db.add(new Fish(56, "Dragon Fish", "Epic", 10.0, 15.0, 17.2, 5));
        db.add(new Fish(57, "Time Fish", "Epic", 7.0, 10.0, 25.3, 5));

        db.add(new Fish(58, "Dimension Fish", "Mythic", 7.0, 14.0, 79.0, 5));
        db.add(new Fish(59, "Cosmic Eel", "Mythic", 20.0, 30.0, 33.2, 5));

        db.add(new Fish(60, "Ancient Leviathan", "Legendary", 1600.0, 3500.0, 0.89, 5));

        return db;
    }

    public static void loadCatalog(HashMap<Integer, Fish> fishCatalog) {
        for (Fish f : getAllFish()) {
            fishCatalog.put(f.id, f);
        }
    }
}
