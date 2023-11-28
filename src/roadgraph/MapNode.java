package roadgraph;

import geography.GeographicPoint;

import java.util.ArrayList;
import java.util.List;

public class MapNode {
    private GeographicPoint location;
    private double currentDistance;
    private double currentHeuristicDistance;
    private List<MapEdge> edges = new ArrayList<>();

    MapNode(GeographicPoint location) {
        this.location = location;
        this.currentDistance = Double.POSITIVE_INFINITY;
        this.currentHeuristicDistance = Double.POSITIVE_INFINITY;
    }

    List<GeographicPoint> getNeighbors() {
        List<GeographicPoint> neighbors = new ArrayList<>();
        for (MapEdge e : edges) {
            neighbors.add(e.getEnd());
        }

        return neighbors;
    }

    GeographicPoint getLocation() {
        return location;
    }

    double getCurrentDistance() {
        return currentDistance;
    }

    void setCurrentDistance(double currentDistance) {
        this.currentDistance = currentDistance;
    }

    double getCurrentHeuristicDistance() {
        return currentHeuristicDistance;
    }

    void setCurrentHeuristicDistance(double currentHeuristicDistance) {
        this.currentHeuristicDistance = currentHeuristicDistance;
    }

    public List<MapEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<MapEdge> edges) {
        this.edges = edges;
    }

    void addEdge(MapEdge edge) {
        this.edges.add(edge);
    }

    int compareToByCurrentDistance(Object o) {
        MapNode other = (MapNode) o;
        if (currentDistance < other.currentDistance) {
            return -1;
        } else if (currentDistance > other.currentDistance) {
            return 1;
        }

        return 0;
    }


    int compareToByCurrentHeuristicDistance(Object o) {
        MapNode other = (MapNode) o;
        if (currentHeuristicDistance < other.currentHeuristicDistance) {
            return -1;
        } else if (currentHeuristicDistance > other.currentHeuristicDistance) {
            return 1;
        }

        return 0;
    }

    public boolean equals(Object o) {
        if (o instanceof MapNode) {
            MapNode mapNode = (MapNode) o;
            return (getLocation().getX() == mapNode.getLocation().getX()) && (getLocation().getY() == mapNode.getLocation().getY());
        }

        return super.equals(o);
    }

    public String toString() {
        return location.toString();
    }
}
