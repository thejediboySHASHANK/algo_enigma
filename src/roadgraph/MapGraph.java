package roadgraph;

import geography.GeographicPoint;

import java.util.*;
import java.util.function.Consumer;

import util.GraphLoader;

/**
 * A class which represents a graph of geographic locations
 * Nodes in the graph are edges between
 */
public class MapGraph {
    /**
     * Nodes grouped by GeographicPoint.
     */
    private HashMap<GeographicPoint, MapNode> nodesByLocation = new HashMap<>();

    /**
     * Create a new empty MapGraph
     */
    public MapGraph() {
    }

    /**
     * Get the number of vertices (road edges) in the graph
     *
     * @return The number of vertices in the graph.
     */
    public int getNumVertices() {
        return nodesByLocation.size();
    }

    /**
     * Return the edges, which are the vertices in this graph.
     *
     * @return The vertices in this graph as GeographicPoints
     */
    public Set<GeographicPoint> getVertices() {
        Set<GeographicPoint> listOfVertices = new HashSet<>();
        for (GeographicPoint location : nodesByLocation.keySet()) {
            listOfVertices.add(nodesByLocation.get(location).getLocation());
        }

        return listOfVertices;
    }

    /**
     * Get the number of road segments in the graph
     *
     * @return The number of edges in the graph.
     */
    public int getNumEdges() {
        int numberOfEdges = 0;
        for (GeographicPoint hashKey : nodesByLocation.keySet()) {
            numberOfEdges += nodesByLocation.get(hashKey).getEdges().size();
        }

        return numberOfEdges;
    }


    /**
     * Add a node corresponding to an intersection at a Geographic Point
     * If the location is already in the graph or null, this method does
     * not change the graph.
     *
     * @param location The location of the intersection
     * @return true if a node was added, false if it was not (the node
     * was already in the graph, or the parameter is null).
     */
    public boolean addVertex(GeographicPoint location) {
        if (location == null) {
            return false;
        }
        if (nodesByLocation.keySet().contains(location)) {
            return false;
        }
        MapNode newVertex = new MapNode(location);
        nodesByLocation.put(location, newVertex);

        return true;
    }

    /**
     * Adds a directed edge to the graph from pt1 to pt2.
     * Precondition: Both GeographicPoints have already been added to the graph
     *
     * @param from     The starting point of the edge
     * @param to       The ending point of the edge
     * @param roadName The name of the road
     * @param roadType The type of the road
     * @param length   The length of the road, in km
     * @throws IllegalArgumentException If the points have not already been added as nodes to the graph, if any of the arguments is null, or if the length is less than 0.
     */
    public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length) throws IllegalArgumentException {
        if (from == null || to == null || roadName == null || roadType == null || length < 0) {
            throw new IllegalArgumentException("Arguments can't be null!");
        }
        if (!nodesByLocation.keySet().contains(from) || !nodesByLocation.keySet().contains(to)) {
            throw new IllegalArgumentException("Arguments can't be null!");
        }
        MapNode start = nodesByLocation.get(from);
        MapEdge edge = new MapEdge(from, to, roadName, length);
        start.addEdge(edge);
    }


    /**
     * Find the path from start to goal using breadth first search
     *
     * @param start The starting location
     * @param goal  The goal location
     * @return The list of edges that form the shortest (unweighted) path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return bfs(start, goal, temp);
    }

    /**
     * Find the path from start to goal using breadth first search
     *
     * @param start        The starting location
     * @param goal         The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of edges that form the shortest (unweighted) path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        Map<MapNode, MapNode> parentMap = new HashMap<>();
        Set<GeographicPoint> detected = new HashSet<>();
        LinkedList<GeographicPoint> queue = new LinkedList<>();
        boolean found = false;
        detected.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            GeographicPoint currPoint = queue.pop();
            if (currPoint.equals(goal)) {
                found = true;
                break;
            }

            MapNode currNode = nodesByLocation.get(currPoint);
            List<GeographicPoint> neighbors = currNode.getNeighbors();
            ListIterator<GeographicPoint> iterator = neighbors.listIterator(neighbors.size());
            while (iterator.hasPrevious()) {
                GeographicPoint next = iterator.previous();
                if (!detected.contains(next)) {
                    detected.add(next);
                    queue.add(next);
                    parentMap.put(nodesByLocation.get(next), currNode);
                    nodeSearched.accept(next);
                }
            }
        }

        if (found) {
            return constructPath(nodesByLocation.get(start), nodesByLocation.get(goal), parentMap);
        }

        return null;
    }


    /**
     * Find the path from start to goal using Dijkstra's algorithm
     *
     * @param start The starting location
     * @param goal  The goal location
     * @return The list of edges that form the shortest path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return dijkstra(start, goal, temp);
    }

    /**
     * Find the path from start to goal using Dijkstra's algorithm
     *
     * @param start        The starting location
     * @param goal         The goal location
     * @param nodeSearched A hook for visualization. See assignment instructions for how to use it.
     * @return The list of edges that form the shortest path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        boolean found = false;
        Map<MapNode, MapNode> parentMap = new HashMap<>();
        Set<MapNode> detected = new HashSet<>();
        PriorityQueue<MapNode> queue = new PriorityQueue<>(2000, MapNode::compareToByCurrentDistance);
        MapNode startNode = nodesByLocation.get(start);
        MapNode goalNode = nodesByLocation.get(goal);

        startNode.setCurrentDistance(0.0);
        queue.add(startNode);
        while (!queue.isEmpty()) {
            MapNode currNode = queue.poll();
            if (!detected.contains(currNode)) {
                detected.add(currNode);
                if (currNode.equals(goalNode)) {
                    found = true;
                    break;
                }
                List<MapEdge> edges = currNode.getEdges();
                ListIterator<MapEdge> edgeListIterator = edges.listIterator(edges.size());
                while (edgeListIterator.hasPrevious()) {
                    MapEdge edge = edgeListIterator.previous();
                    GeographicPoint next = edge.getEnd();
                    MapNode nextNode = nodesByLocation.get(next);
                    if (!detected.contains(nextNode)) {
                        double newDistance = currNode.getCurrentDistance() + edge.getDistance();
                        if (newDistance < nextNode.getCurrentDistance()) {
                            nextNode.setCurrentDistance(newDistance);
                            queue.add(nextNode);
                            parentMap.put(nextNode, currNode); // ??
                            nodeSearched.accept(next);
                        }
                    }
                }
            }
        }

        if (found) {
            return constructPath(startNode, goalNode, parentMap);
        }

        return null;
    }

    /**
     * Find the path from start to goal using A-Star search
     *
     * @param start The starting location
     * @param goal  The goal location
     * @return The list of edges that form the shortest path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return aStarSearch(start, goal, temp);
    }

    /**
     * Find the path from start to goal using A-Star search
     *
     * @param start        The starting location
     * @param goal         The goal location
     * @param nodeSearched A hook for visualization. See assignment instructions for how to use it.
     * @return The list of edges that form the shortest path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        boolean found = false;
        Map<MapNode, MapNode> parentMap = new HashMap<>();
        Set<MapNode> detected = new HashSet<>();
        PriorityQueue<MapNode> queue = new PriorityQueue<>(2000, MapNode::compareToByCurrentHeuristicDistance);
        MapNode startNode = nodesByLocation.get(start);
        MapNode goalNode = nodesByLocation.get(goal);

        startNode.setCurrentDistance(0.0);
        startNode.setCurrentHeuristicDistance(start.distance(goal));
        queue.add(startNode);
        while (!queue.isEmpty()) {
            MapNode currNode = queue.poll();
            if (!detected.contains(currNode)) {
                detected.add(currNode);
                if (currNode.equals(goalNode)) {
                    found = true;
                    break;
                }
                List<MapEdge> edges = currNode.getEdges();
                ListIterator<MapEdge> edgeListIterator = edges.listIterator(edges.size());
                while (edgeListIterator.hasPrevious()) {
                    MapEdge edge = edgeListIterator.previous();
                    GeographicPoint next = edge.getEnd();
                    MapNode nextNode = nodesByLocation.get(next);
                    if (!detected.contains(nextNode)) {
                        double newDistance = currNode.getCurrentDistance() + edge.getDistance();
                        double directDistanceFromNextToGoal = next.distance(goal);
                        double newHeuristicDistance = newDistance + directDistanceFromNextToGoal;
                        if (newHeuristicDistance < nextNode.getCurrentHeuristicDistance()) {
                            nextNode.setCurrentDistance(newDistance);
                            nextNode.setCurrentHeuristicDistance(newHeuristicDistance);
                            queue.add(nextNode);
                            parentMap.put(nextNode, currNode); // ??
                            nodeSearched.accept(next);
                        }
                    }
                }
            }
        }

        if (found) {
            return constructPath(startNode, goalNode, parentMap);
        }

        return null;
    }

    private List<GeographicPoint> constructPath(MapNode startNode, MapNode goalNode, Map<MapNode, MapNode> parentMap) {
        LinkedList<GeographicPoint> path = new LinkedList<>();
        MapNode currNode = goalNode;
        while (currNode != startNode) {
            path.addFirst(currNode.getLocation());
            currNode = parentMap.get(currNode);
        }
        path.addFirst(startNode.getLocation());

        return path;
    }

    public static void main(String[] args) {
        System.out.print("Making a new map...");
        MapGraph firstMap = new MapGraph();
        System.out.print("DONE. \nLoading the map...");
        GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
        System.out.println("DONE.");

        // You can use this method for testing.

        /* Here are some test cases you should try before you attempt
         * the Week 3 End of Week Quiz, EVEN IF you score 100% on the
         * programming assignment.
         */
//        MapGraph simpleTestMap = new MapGraph();
//        GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
//
//        GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
//        GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
//
//        List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart, testEnd);
//        List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart, testEnd);
//        System.out.println("Test 2 using utc: Dijkstra should be 9 (is " + testroute.size() + ") and AStar should be 5 (is " + testroute2.size() + ")");
//
//
//        MapGraph testMap = new MapGraph();
//        GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
//
//        // A very simple test using real data
//        testStart = new GeographicPoint(32.869423, -117.220917);
//        testEnd = new GeographicPoint(32.869255, -117.216927);
//        testroute = testMap.dijkstra(testStart, testEnd);
//        testroute2 = testMap.aStarSearch(testStart, testEnd);
//        System.out.println("Test 2 using utc: Dijkstra should be 13 (is " + testroute.size() + ") and AStar should be 5 (is " + testroute2.size() + ")");
//
//
//        // A slightly more complex test using real data
//        testStart = new GeographicPoint(32.8674388, -117.2190213);
//        testEnd = new GeographicPoint(32.8697828, -117.2244506);
//        testroute = testMap.dijkstra(testStart, testEnd);
//        testroute2 = testMap.aStarSearch(testStart, testEnd);
//        System.out.println("Test 2 using utc: Dijkstra should be 37 (is " + testroute.size() + ") and AStar should be 10 (is " + testroute2.size() + ")");

        /* Use this code in Week 3 End of Week Quiz */
        MapGraph theMap = new MapGraph();
        System.out.print("DONE. \nLoading the map...");
        GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
        System.out.println("DONE.");

        GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
        GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);


        List<GeographicPoint> route = theMap.dijkstra(start, end);
        List<GeographicPoint> route2 = theMap.aStarSearch(start, end);
        System.out.println("Test 2 using utc: Dijkstra is " + route.size() + " and AStar is " + route2.size() + "");
    }

}
