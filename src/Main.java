/* Main class handles:
    - Reading of cells
    - Creating frequency array
    - Initialising the network
    - Runs the allocation of frequencies
 */
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Network nw = new Network();
        int[] frequencies = new int[]{110, 111, 112, 113, 114, 115};
        FrequencyAllocator fa = new FrequencyAllocator(Integer.MAX_VALUE);
        Set<String> cellIDs = new HashSet<>();

        System.out.println("--- Welcome to the Freqeuncy Allocator! ---");
        System.out.println("Enter number of cells: ");

        if (!sc.hasNextInt()) {
            System.out.println("Please enter a valid number of cells.");
            return;
        }
       
        int totalCells = sc.nextInt(); 
{}
        if (totalCells <= 0) {
            System.out.println("Number of cells must be greater than 0.");
            return;
        }
        sc.nextLine();

        Cell[] cells = new Cell[totalCells];

        System.out.println("Enter cell data (ID Easting Northing Longitude Latitude)");

        for (int i = 0; i < totalCells; i++) {
            String[] input = sc.nextLine().split(" ");
            if (input.length != 5) {
                System.out.println("Invalid cell data. Expected: ID Easting Nothing Longitude Latitude");
                return;
            }
            String id = input[0];
            if (!cellIDs.add(id)) {
                System.out.println("Cell ID already exists: " + id);
                return;
            }
        
            try {
                int easting = Integer.parseInt(input[1]);
                int northing = Integer.parseInt(input[2]);
                double longitude = Double.parseDouble(input[3]);
                double latitude = Double.parseDouble(input[4]);

                Cell cell = new Cell(id, easting, northing, longitude, latitude, 0);
                cells[i] = cell;
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid numeric value in cell data.");
                return;
            }  
        }

        Map<Cell, List<Cell>> network = nw.createNetwork(cells);

        boolean allocationFound = false;
        for (int i = 1; i < frequencies.length + 1; i++) {
            boolean success = fa.allocateFrequencies(cells, network, 0, totalCells, frequencies, i);

            if (success) {
                allocationFound = true;

                for (Cell cell : cells) {
                    fa.bestAllocation.put(cell.id, cell.frequency);
                }
                break;
            }
        }

        if (!allocationFound) {
            System.out.println("No valid frequency allocation could be found.");
        }

        sc.close();
      
        /*for (Map.Entry<Cell, List<Cell>> entry : network.entrySet()) {
        System.out.print(entry.getKey().id + " -> ");

            for (Cell neighbour : entry.getValue()) {
                System.out.print(neighbour.id + " ");
            }

            System.out.println();
    
        }*/

            System.out.println(fa.bestAllocation);


    }
}