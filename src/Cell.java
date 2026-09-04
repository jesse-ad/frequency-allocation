package FrequencyAllocation.src;

public class Cell {
    int id;
    int easting;
    int northing;
    int longitude;
    int latitude;
    int frequency;

    public Cell(int id, int easting, int northing, int longitude, int latitude, int frequency) {
        this.id = id;
        this.easting = easting;
        this.northing = northing;
        this.longitude = longitude;
        this.latitude = latitude;
        this.frequency = frequency;
    }
}