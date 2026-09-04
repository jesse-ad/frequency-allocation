package FrequencyAllocation;

public class Cell {
    String id;
    int easting;
    int northing;
    int frequency;

    public Cell(String id, int easting, int northing, int frequency) {
        this.id = id;
        this.easting = easting;
        this.northing = northing;
        this.frequency = frequency;
    }

}