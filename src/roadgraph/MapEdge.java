package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
    private GeographicPoint start;
    private GeographicPoint end;
    private String streetName;
    private double distance;

    MapEdge(GeographicPoint start, GeographicPoint end, String streetName, double distance) {
        this.start = start;
        this.end = end;
        this.streetName = streetName;
        this.distance = distance;
    }

    public GeographicPoint getStart() {
        return start;
    }

    public GeographicPoint getEnd() {
        return end;
    }

    public String getStreetName() {
        return streetName;
    }

    double getDistance() {
        return distance;
    }
}
