
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
public class NearestNeighbour implements Algorithm {

    private Graph map;
    private HashMap<Vertex, Vertex> previousVertices;
    int TotalDistance;
    boolean targetReached;
    private List<Vertex> visited;

    public NearestNeighbour(Graph map) {
        this.map = map;
        previousVertices = new HashMap<>();
        TotalDistance = 0;
        targetReached = false;
        visited = new ArrayList<>();
    }

    @Override
    public void startAlgorithm(String cityofOrigin, String targetCity) {
        Vertex next = new Vertex(cityofOrigin);
        visited.add(next);
        Vertex destination = new Vertex(targetCity);
        
        List<DirectedEdge> neighbours;
        do {
            neighbours = getNeighbouringEdges(next);
            if(neighbours.isEmpty()){
             break;
            }
            Vertex t = findNextVertex(neighbours);
            previousVertices.put(next, t);
            TotalDistance += findDistance(neighbours, next, t);
            visited.add(next);
            next = t;
        } while (!destination.equals(next));
        if(destination.equals(next)){
          targetReached = true;
        }
    }

    @Override
    public String PrintDistance(String cityOfOrigin, String destination) {
        return this.TotalDistance + "";
    }

    @Override
    public List<String> PrintPath(String cityofOrigin, String targetCity) {

ArrayList<Vertex> path = new ArrayList<>();
        Vertex v = new Vertex(cityofOrigin);
        path.add(v);
        while (true) {            
            path.add(previousVertices.get(v));
            v = previousVertices.get(v);
            if(v == null){
              break;
            }
        }
        path.remove(path.size()-1);
        ArrayList<String> pathStr = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            pathStr.add(path.get(i).getName());
        }
        return pathStr;
    }

    @Override
    public String getNameOfAlgorithm() {
        return "Nearest Neighbour Algorithm";
    }

    public List<DirectedEdge> getNeighbouringEdges(Vertex start) {
        List<DirectedEdge> edges = new ArrayList<>();
        for (int i = 0; i < map.edges.size(); i++) {
            if (map.edges.get(i).from().equals(start) && !map.edges.get(i).to().equals(start)
                    && !visited.contains(map.getEdges().get(i).to())
                    && map.edges.get(i).weight()!=99999) {
                edges.add(map.edges.get(i));
            }
        }
        return edges;
    }

    public Vertex findNextVertex(List<DirectedEdge> edges) {
        int minimumDistance = edges.get(0).weight();
        Vertex nextVertex = edges.get(0).to();
        for (int i = 1; i < edges.size(); i++) {
            if (edges.get(i).weight() < minimumDistance) {
                minimumDistance = edges.get(i).weight();
                nextVertex = edges.get(i).to();
            }
        }

        return nextVertex;
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
