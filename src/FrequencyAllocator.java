/* Frequency class handles the allocation of frequencies 
   to all cells in a network 
*/
import java.util.*;
public class FrequencyAllocator {

    Map<String, Integer> bestAllocation = new HashMap<>();

    // Checks that the current frequency can be assigned to the current cell.
    public boolean isFrequencySafe(Cell currentCell, Map<Cell, List<Cell>> network, int currentFrequency) {
        List<Cell> closeCells = network.get(currentCell);

        for (Cell cell : closeCells) {
            if (cell.frequency == currentFrequency) {
                return false;
            }
        }
        return true;
    }

    /* Uses backtracking to find a valid frequency allocations.
       maxFrequencies limits the amount of frequencies being used.
     */
    public boolean allocateFrequencies(Cell[] cells, Map<Cell, List<Cell>> network, int currentIndex, int numCells, int[] frequencies, int maxFrequencies) {

        // All cells have been successfully assigned a frequency.
        if (currentIndex == numCells) {
            return true;
        }

        // Continue assigning frequencies to the remaining cells.
        // If this choice eventually fails, backtrack and try another frequency.
        for (int i = 0; i < maxFrequencies; i++) {

            // Frequency is safe to use.
            if (isFrequencySafe(cells[currentIndex], network, frequencies[i])) {
                cells[currentIndex].frequency = frequencies[i];

                boolean success = allocateFrequencies(cells, network, currentIndex + 1, numCells, frequencies, maxFrequencies);
                
                if (success) {
                    return true;
                }
            // Backtrack.
            cells[currentIndex].frequency = 0;
            }
        }
        return false;
    }
}



