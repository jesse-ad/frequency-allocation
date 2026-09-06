/* Network class handles the creation of the network */

import java.util.*;
public class Network {

    private final Map<Cell, List<Cell>> network;
    public static double threshold = 250;

    public Network() {
        this.network = new HashMap<>();
    }

    // Adds cell to network.
    public void addCell(Cell cell) {
        network.putIfAbsent(cell, new ArrayList<>());
    }
    
    // Adds connection between two cells in network.
    public void addConnection(Cell source, Cell dest) {
        addCell(source);
        addCell(dest);

        network.get(source).add(dest);
        network.get(dest).add(source);
    }
    
    /*  Calculates the euclidean distance between two cells 
     using their Easting and Northing coordinates.
     */
    public double distanceCalculator(Cell cell1, Cell cell2) {
        double eastdiff = cell2.easting - cell1.easting;
        double northdiff = cell2.northing - cell1.northing;

        return Math.sqrt((eastdiff * eastdiff) + (northdiff * northdiff));
    }

    /* Checks if two cells are considered close based on the distance
        between them and the threshold.
    */
    public boolean close(double distance) {
        return distance <= threshold;
    }

    /* Creates network by comparing each unique pair of cells and
        creates an undirected connetion between cells that are considered
        too close.
    */
    public Map<Cell, List<Cell>> createNetwork(Cell[] cells) {

        // Add all cells to the network.
        for (Cell cell : cells) {
            addCell(cell); 
        }

        for (int i = 0; i < cells.length; i++) {
            for (int j = i + 1; j < cells.length; j++) {

                // Add an undirected connection if cells are too close.
                if (close(distanceCalculator(cells[i], cells[j]))) {
                    addConnection(cells[i], cells[j]);
                }
            }
        }
        return network;
    }
}
