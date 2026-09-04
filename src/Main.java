package FrequencyAllocation.src;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph graph = new Graph();

        System.out.println("--- Welcome to the Freqeuncy Allocator! ---");
        System.out.println("*** Enter 'Run' to run allocator ***");
        System.out.println("Enter number of cells: ");
        int totalCells = sc.nextInt();
        Cell[] cells = new Cell[totalCells];
        System.out.println(" Enter cell data (ID, Easting, Northing, Longitude, Latitude)");
        
        

        while (sc.hasNextLine()) {
            String[] input = sc.nextLine().split(", ");
            String id = input[0];
            int easting = Integer.parseInt(input[1]);
            int northing = Integer.parseInt(input[2]);
            int longitude = Integer.parseInt(input[3]);
            int latitude = Integer.parseInt(input[4]);

            Cell cell = new Cell(id, easting, northing, longitude, latitude, 0);
        }

    }
}