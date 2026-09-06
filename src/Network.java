
import java.util.*;
public class Network {

    private final Map<Cell, List<Cell>> network;
    public static double threshold = 250;

    public Network() {
        this.network = new HashMap<>();
    }

    public void addCell(Cell cell) {
        network.putIfAbsent(cell, new ArrayList<>());
    }
    
    public void addConnection(Cell source, Cell dest) {
        addCell(source);
        addCell(dest);

        network.get(source).add(dest);
        network.get(dest).add(source);
    }
    
    
    public double distanceCalculator(Cell cell1, Cell cell2) {
        double eastdiff = cell2.easting - cell1.easting;
        double northdiff = cell2.northing - cell1.northing;

        return Math.sqrt((eastdiff * eastdiff) + (northdiff * northdiff));
    }

    public boolean close(double distance) {
        return distance <= threshold;

    }

    public Map<Cell, List<Cell>> createNetwork(Cell[] cells) {

        for (Cell cell : cells) {
            addCell(cell);
        }

        for (int i = 0; i < cells.length; i++) {
            for (int j = i + 1; j < cells.length; j++) {
                if (close(distanceCalculator(cells[i], cells[j]))) {
                    addConnection(cells[i], cells[j]);
                }
            }
        }
        return network;
    }
}
