package FrequencyAllocation.src;
import java.util.*;
public class Graph {

    private final Map<Cell, List<Cell>> graph;

    public Graph() {
        this.graph = new HashMap<>();
    }

    public void addCell(Cell cell) {
        graph.putIfAbsent(cell, new ArrayList<>());
    }
    
    public void addConnection(Cell source, Cell dest) {
        addCell(source);
        addCell(dest);

        graph.get(source).add(dest);
        graph.get(dest).add(source);
    }
    

    public static double threshold = 250;
    
    public double distanceCalculator(Cell cell1, Cell cell2) {
        double eastdiff = cell2.easting - cell1.easting;
        double northdiff = cell2.northing - cell1.northing;

        return Math.sqrt((eastdiff * eastdiff) + (northdiff * northdiff));
    }

    public boolean tooClose(double distance) {
        if (distance > threshold) {
            return false;
        }
        return true;
    }

    
}
