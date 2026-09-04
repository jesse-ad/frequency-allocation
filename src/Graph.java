package FrequencyAllocation.src;
import java.util.*;
public class Graph {

    private final Map<Cell, List<Cell>> graph;
    public static double threshold = 250;

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
    
    
    public double distanceCalculator(Cell cell1, Cell cell2) {
        double eastdiff = cell2.easting - cell1.easting;
        double northdiff = cell2.northing - cell1.northing;

        return Math.sqrt((eastdiff * eastdiff) + (northdiff * northdiff));
    }

    public boolean cellsClose(double distance) {
        if (distance > threshold) {
            return false;
        }
        return true;
    }

    public Map<Cell, List<Cell>> createGraph(Cell[] cells) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = i + 1; j < cells.length; j++) {
                addCell(cells[i]);
                if (cellsClose(distanceCalculator(cells[i], cells[j]))) {
                    addConnection(cells[i], cells[j]);
                }
            }
        }
        return graph;
    }
}
