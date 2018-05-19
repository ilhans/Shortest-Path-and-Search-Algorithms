
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alper
 */
public class HillClimbing implements Algorithm {

    private Graph map;
    private List<Vertex> visited;
    private boolean targetReached;

    public HillClimbing(Graph map) {
        this.map = map;
        this.visited = new ArrayList<>();
        targetReached = false;
    }

    @Override
    public void startAlgorithm(String cityofOrigin, String targetCity) {
        Vertex nextNode = new Vertex(cityofOrigin);
        Vertex targetNode = new Vertex(targetCity);
        while (!nextNode.equals(targetNode)) {
            visited.add(nextNode);
            List<EvaluatedVertex> neighbours = getEvaluatedNeighbours(nextNode, targetNode);
            if (neighbours.isEmpty()) {
                break;
            }
            Collections.sort(neighbours);
            nextNode = neighbours.get(0).getVertex();
        }
        if (nextNode.equals(targetNode)) {
            targetReached = true;
            visited.add(targetNode);
        }

    }

    @Override
    public String PrintDistance(String cityOfOrigin, String destination) {
        int totalDistance = 0;
        if (visited.size() > 1) {
            for (int i = 0; i < visited.size() - 1; i++) {
                totalDistance += findDistance(map.edges, visited.get(i), visited.get(i + 1));
            }
        }
        return totalDistance+"";
    }

    @Override
    public List<String> PrintPath(String cityofOrigin, String targetCity) {
        List<String> cities = new ArrayList<>();
        for (int i = 0; i < visited.size(); i++) {
            cities.add(visited.get(i).getName());
        }
        return cities;
    }

    @Override
    public String getNameOfAlgorithm() {
        return "Hill Climbing Algorithm";
    }

    public List<DirectedEdge> getNeighbouringEdges(Vertex start) {
        List<DirectedEdge> edges = new ArrayList<>();
        for (int i = 0; i < map.edges.size(); i++) {
            if (map.edges.get(i).from().equals(start) && !map.edges.get(i).to().equals(start)
                    && !visited.contains(map.getEdges().get(i).to())
                    && map.edges.get(i).weight() != 99999) {
                edges.add(map.edges.get(i));
            }
        }
        return edges;
    }

    public List<EvaluatedVertex> getEvaluatedNeighbours(Vertex start, Vertex destination) {
        List<DirectedEdge> NeighbouringEdges = getNeighbouringEdges(start);
        List<Vertex> vertices = new ArrayList<>();
        List<EvaluatedVertex> evalVertices = new ArrayList<>();
        if (!NeighbouringEdges.isEmpty()) {
            for (int i = 0; i < NeighbouringEdges.size(); i++) {
                vertices.add(NeighbouringEdges.get(i).to());
            }
            for (int i = 0; i < vertices.size(); i++) {
                evalVertices.add(new EvaluatedVertex(vertices.get(i), calculateFitnessValue(vertices.get(i), destination)));
            }
        }
        return evalVertices;
    }

    private int calculateFitnessValue(Vertex i, Vertex destination) {
        AirDistanceUnit unit = new AirDistanceUnit(i, destination);
        return map.airDistances.get(unit);
    }

    private int findDistance(List<DirectedEdge> edges, Vertex from, Vertex to) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).from().equals(from) && edges.get(i).to().equals(to)) {
                return edges.get(i).weight();
            }
        }
        return -2;
    }

}
