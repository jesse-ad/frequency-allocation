import java.util.*;

public class FrequencyAllocator {

    public boolean isFrequencySafe(Cell currentCell, Map<Cell, List<Cell>> network, int currentFrequency) {
        List<Cell> closeCells = network.get(currentCell);

        for (Cell cell : closeCells) {
            if (cell.frequency == currentFrequency) {
                return false;
            }
        }
        return true;
    }

    
}
