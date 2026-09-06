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

    public void allocateFrequencies(Cell[] cells, Map<Cell, List<Cell>> network, int currentIndex, int numCells, int[] frequencies) {
        if (currentIndex == numCells) {
            currentIndex = 0;
            System.out.println("Complete allocation found");

            HashSet<Integer> frequenciesUsed = new HashSet<>();
            for (Cell cell: cells) {
                frequenciesUsed.add(cell.frequency);
            }
            if (bestCount >= frequenciesUsed.size()) {
                bestCount = frequenciesUsed.size();

                for (Cell cell : cells) {
                    bestAllocation.put(cell.id, cell.frequency);
                }

            }
            return;
        }

        for (int i = 0; i < frequencies.length; i++) {
            if (isFrequencySafe(cells[currentIndex], network, frequencies[i])) {
                cells[currentIndex].frequency = frequencies[i];

                allocateFrequencies(cells, network, currentIndex + 1, numCells, frequencies);
            }
            cells[currentIndex].frequency = 0;
        }
    }
}



