import java.util.HashMap;

public class GameLogic {
    private HashMap<Integer, Fish> fishCatalog = new HashMap<>();

    public GameLogic() {
        // Amikor elindul a játék, betöltjük a halakat a katalógusba
        FishDatabase.loadCatalog(fishCatalog);
    }

    // Ide jön majd a horgászat metódus, ami használja a katalógust
}
