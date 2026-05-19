public class Area {
    public int id;
    public String name;
    public int reqLevel;
    public int price;
    public boolean isUnlocked;

    public Area(int id, String name, int reqLevel, int price, boolean isUnlocked) {
        this.id = id;
        this.name = name;
        this.reqLevel = reqLevel;
        this.price = price;
        this.isUnlocked = isUnlocked;
    }
}
