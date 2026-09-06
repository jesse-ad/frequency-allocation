
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph graph = new Graph();
        int[] frequencies = new int[]{110, 111, 112, 113, 114, 115};
        FrequencyAllocator fa = new FrequencyAllocator(Integer.MAX_VALUE);

        System.out.println("--- Welcome to the Freqeuncy Allocator! ---");
        System.out.println("Enter number of cells: ");
        int totalCells = sc.nextInt();
        sc.nextLine();

        Cell[] cells = new Cell[totalCells];

        System.out.println("Enter cell data (ID, Easting, Northing, Longitude, Latitude)");

        for (int i = 0; i < totalCells; i++) {
            String[] input = sc.nextLine().split(", ");

            String id = input[0];
            int easting = Integer.parseInt(input[1]);
            int northing = Integer.parseInt(input[2]);
            double longitude = Double.parseDouble(input[3]);
            double latitude = Double.parseDouble(input[4]);

            Cell cell = new Cell(id, easting, northing, longitude, latitude, 0);
            cells[i] = cell;
        }

        Map<Cell, List<Cell>> network = graph.createGraph(cells);
        fa.allocateFrequencies(cells, network, 0, totalCells, frequencies);
        sc.close();
      
        for (Map.Entry<Cell, List<Cell>> entry : network.entrySet()) {
        System.out.print(entry.getKey().id + " -> ");

            for (Cell neighbour : entry.getValue()) {
                System.out.print(neighbour.id + " ");
            }

            System.out.println();
    
        }

    }
}