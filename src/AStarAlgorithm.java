
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alper
 */
public class AStarAlgorithm implements Algorithm{
    
    private Graph map;
    private List<EvaluatedVertex> priorityQueue;
    private List<Vertex> visited;
    private List<Vertex> closed;
    private Map<Vertex, Integer> distance;
    private Map<Vertex, Vertex> predecessors;

    public AStarAlgorithm(Graph map) {
        this.map = map;
        this.priorityQueue = new ArrayList<>();
        visited = new ArrayList<>();
        closed  = new ArrayList<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
    }
    
    @Override
    public void startAlgorithm(String cityofOrigin, String targetCity) {
        Vertex startCity = new Vertex(cityofOrigin);
        Vertex endCity = new Vertex(targetCity);
        distance.put(startCity,0);
        priorityQueue.add(new EvaluatedVertex(startCity, map.airDistances.get(new AirDistanceUnit(startCity, endCity))));
        visited.add(startCity);
        while (!priorityQueue.isEmpty()) {
            Vertex next = priorityQueue.get(0).getVertex();
            priorityQueue.remove(0);
            if (next.equals(endCity)) {
                break;
            }
            else{
             List<EvaluatedVertex> neighbours = getEvaluatedNeighbours(next, endCity);
                for (int i = 0; i < neighbours.size(); i++) {
                    if(!visited.contains(neighbours.get(i).getVertex())){
                      visited.add(neighbours.get(i).getVertex());
                      priorityQueue.add(neighbours.get(i));
                    }
                }
                closed.add(next);
                Collections.sort(priorityQueue);
                System.out.println("rgdfgdfdf");
               
    }
             
        }
        System.out.println("dsdfsdf");
       
    }
    public List<DirectedEdge> getNeighbouringEdges(Vertex start) {
        List<DirectedEdge> edges = new ArrayList<>();
        for (int i = 0; i < map.edges.size(); i++) {
            if (map.edges.get(i).from().equals(start) && !map.edges.get(i).to().equals(start)
                    && !visited.contains(map.getEdges().get(i).to())
                    && map.edges.get(i).weight() != 99999) {
                edges.add(map.edges.get(i));
            }
            else if (map.edges.get(i).from().equals(start) && !map.edges.get(i).to().equals(start)
                    && map.edges.get(i).weight() != 99999) {
//                edges.add(map.edges.get(i));
                  int cdist = distance.get(map.getEdges().get(i).to());
                  if(cdist > distance.get(start)+ map.edges.get(i).weight()){
                      edges.add(map.edges.get(i));
                      distance.put(map.edges.get(i).to(),distance.get(start)+ map.edges.get(i).weight());
                      predecessors.put(map.edges.get(i).to(),start);
                  }
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
                predecessors.put(NeighbouringEdges.get(i).to(),start);
                int res = distance.get(start)+NeighbouringEdges.get(i).weight();
                distance.put(NeighbouringEdges.get(i).to(),res);
            }
            for (int i = 0; i < vertices.size(); i++) {
                evalVertices.add(new EvaluatedVertex(vertices.get(i), calculateFitnessValue(vertices.get(i), destination)));
            }
        }
        return evalVertices;
    }

    private int calculateFitnessValue(Vertex i, Vertex destination) {
        AirDistanceUnit unit = new AirDistanceUnit(i, destination);
        
        int y = map.airDistances.get(unit);
        int x = y+ distance.get(i);
        return x;
    }

    private int findDistance( Vertex from, Vertex to) {
        for (int i = 0; i < map.edges.size(); i++) {
            if (map.edges.get(i).from().equals(from) && map.edges.get(i).to().equals(to)) {
                return map.edges.get(i).weight();
            }
        }
        return -2;
    }        
            
            

    @Override
    public String PrintDistance(String cityOfOrigin, String destination) {
        return distance.get(new Vertex(destination))+"";
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
        System.out.println("dfsdfs");
        return pathStr;
    }

    @Override
    public String getNameOfAlgorithm() {
        return "A* Algorithm";
    }
    
}
