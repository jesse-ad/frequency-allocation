import java.util.*;

public class FrequencyAllocator {

    public boolean isFrequencySafe(Cell currentCell, Map<Cell, List<Cell>> network, int[] frequencies, int frequency, int numCells) {
        List<Cell> closeCells = network.get(currentCell);

        for (Cell cell : closeCells) {
            if (cell.frequency == currentCell.frequency) {
                return false;
            }
        }
        return true;
    }
}
