
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
 * @author alper
 */
public class IterativeDeepening implements Algorithm {

    private Graph map;
    private List<Vertex> closed;
    private HashMap<Vertex, Vertex> predecessors;

    public IterativeDeepening(Graph map) {
        this.map = map;
        closed = new ArrayList<>();
        predecessors = new HashMap<>();
    }

    @Override
    public void startAlgorithm(String cityofOrigin, String targetCity) {
        Vertex startCity = new Vertex(cityofOrigin);
        Vertex endCity = new Vertex(targetCity);
        int step = -1;
        boolean result = false;
        while (true) {
            step++;
            result = backTrack(startCity, endCity, step);
            
            if (result) {
                break;
            }
            else{
            closed = new ArrayList<>();
            predecessors = new HashMap<>();
            }
        }
        
    }

    public boolean backTrack(Vertex X, Vertex finalV, int depthLimit) {
        if (depthLimit != -1) {
            closed.add(X);
            if (X.equals(finalV)) {
                return true;
            }
            List<Vertex> neighbours = getNeighbours(X);
            while (!neighbours.isEmpty()) {
                Vertex child = neighbours.get(0);
                predecessors.put(child, X);
                if (!closed.contains(child)) {
                    boolean res = backTrack(child, finalV, depthLimit - 1);
                    if (res) {
                        return true;
                    }
                }
                neighbours.remove(0);
            }
            return false;
        } else {
            return false;
        }
    }

    public List<DirectedEdge> getNeighbouringEdges(Vertex start) {
        List<DirectedEdge> edges = new ArrayList<>();
        for (int i = 0; i < map.edges.size(); i++) {
            if (map.edges.get(i).from().equals(start) && !map.edges.get(i).to().equals(start)
                    && !closed.contains(map.edges.get(i).to())
                    && map.edges.get(i).weight() != 99999) {
                edges.add(map.edges.get(i));
            }
        }
        return edges;
    }

    public List<Vertex> getNeighbours(Vertex start) {
        List<Vertex> vertices = new ArrayList<>();
        List<DirectedEdge> edges = getNeighbouringEdges(start);
        if (!edges.isEmpty()) {
            for (int i = 0; i < edges.size(); i++) {
                vertices.add(edges.get(i).to());
            }
        }
        return vertices;
    }

    @Override
    public String PrintDistance(String cityOfOrigin, String destination) {
       List<String> pathStr = PrintPath(cityOfOrigin, destination);
       int totalDistance = 0; 
       for (int i = 0; i < pathStr.size()-1; i++) {
            totalDistance += findDistance(new Vertex(pathStr.get(i)),new Vertex(pathStr.get(i+1)));
        }
       return totalDistance+"";
    }

    @Override
    public List<String> PrintPath(String cityofOrigin, String targetCity) {
        ArrayList<Vertex> path = new ArrayList<>();
        Vertex v = new Vertex(targetCity);
        path.add(v);
        while (true) {            
            path.add(predecessors.get(v));
            v = predecessors.get(v);
            if(v == null){
              break;
            }
        }
        path.remove(path.size()-1);
        ArrayList<String> pathStr = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            pathStr.add(path.get(i).getName());
        }
        Collections.reverse(pathStr);
        return pathStr;
    }

    @Override
    public String getNameOfAlgorithm() {
        return "Iterative Deepening";
    }
    
    private int findDistance( Vertex from, Vertex to) {
        for (int i = 0; i < map.edges.size(); i++) {
            if (map.edges.get(i).from().equals(from) && map.edges.get(i).to().equals(to)) {
                return map.edges.get(i).weight();
            }
        }
        return -2;
    }  

}
