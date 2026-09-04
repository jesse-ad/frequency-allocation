

public class Cell {
    String id;
    int easting;
    int northing;
    double longitude;
    double latitude;
    int frequency;

    public Cell(String id, int easting, int northing, double longitude, double latitude, int frequency) {
        this.id = id;
        this.easting = easting;
        this.northing = northing;
        this.longitude = longitude;
        this.latitude = latitude;
        this.frequency = frequency;
    }
}