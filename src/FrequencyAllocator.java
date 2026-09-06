import java.util.*;

public class FrequencyAllocator {

    int bestCount;
    Map<String, Integer> bestAllocation = new HashMap<>();

    public FrequencyAllocator(int bestCount) {
        this.bestCount = bestCount;

    }

    public boolean isFrequencySafe(Cell currentCell, Map<Cell, List<Cell>> network, int currentFrequency) {
        List<Cell> closeCells = network.get(currentCell);

        for (Cell cell : closeCells) {
            if (cell.frequency == currentFrequency) {
                return false;
            }
        }
        return true;
    }

    public boolean allocateFrequencies(Cell[] cells, Map<Cell, List<Cell>> network, int currentIndex, int numCells, int[] frequencies, int maxFrequencies) {
        if (currentIndex == numCells) {
            return true;
        }

        for (int i = 0; i < maxFrequencies; i++) {
            if (isFrequencySafe(cells[currentIndex], network, frequencies[i])) {
                cells[currentIndex].frequency = frequencies[i];

                boolean success = allocateFrequencies(cells, network, currentIndex + 1, numCells, frequencies, maxFrequencies);
                
                if (success) {
                    return true;
                }
            
            cells[currentIndex].frequency = 0;
            }
        }
        return false;
    }
}



