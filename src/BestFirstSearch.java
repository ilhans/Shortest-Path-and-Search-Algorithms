import java.util.ArrayList;
import java.util.Collection;
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
 * @author Alper
 */
public class BestFirstSearch implements Algorithm {

    private Graph map;
    private List<EvaluatedVertex> priorityQueue;
    private List<Vertex> visited;
    private List<Vertex> closed;
    private HashMap<Vertex,Vertex> predecessors;
    

    public BestFirstSearch(Graph map) {
        this.map = map;
        this.priorityQueue = new ArrayList<>();
        visited = new ArrayList<>();
        closed  = new ArrayList<>();
        predecessors = new HashMap<>();
    }

    @Override
    public void startAlgorithm(String cityofOrigin, String targetCity) {
        Vertex startCity = new Vertex(cityofOrigin);
        Vertex endCity = new Vertex(targetCity);
        priorityQueue.add(new EvaluatedVertex(startCity, map.airDistances.get(new AirDistanceUnit(startCity, endCity))));
        
        while (!priorityQueue.isEmpty()) {
            Vertex next = priorityQueue.get(0).getVertex();
            priorityQueue.remove(0);
            predecessors.put(findParent(closed, startCity, next), next);
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
              
            }
        }
        

    }

    @Override
    public String PrintDistance(String cityOfOrigin, String destination) {
        int totalCost = 0;
        List<Vertex> keyList = new ArrayList<>(predecessors.keySet());
        for (int i = 0; i < keyList.size(); i++) {
            if(!keyList.get(i).equals(new Vertex("-"))){
               totalCost+= findDistance(keyList.get(i),predecessors.get(keyList.get(i)));
            }
        }
        return totalCost+"";
    }

    @Override
    public List<String> PrintPath(String cityofOrigin, String targetCity) {
       ArrayList<String> pathStr = new ArrayList<>();
       
        List<Vertex> keyList = new ArrayList<>(predecessors.keySet());
        for (int i = 0; i < keyList.size(); i++) {
               pathStr.add(predecessors.get(keyList.get(i)).getName());
        }
        return pathStr;
    }

    @Override
    public String getNameOfAlgorithm() {
        return "Best First Search";
    }
    
    private Vertex findParent(List<Vertex> closed , Vertex startNode, Vertex cnode){
        
        if(cnode.equals(startNode)){
         return new Vertex("-");
        }
        for (int i = 0; i < map.edges.size(); i++) {
            if(map.edges.get(i).to().equals(cnode) && map.edges.get(i).weight() != 99999){
               if((map.edges.get(i).from().equals(startNode))){
                  return startNode;
               }
                for (int j = 0; j < closed.size(); j++) {
                if(map.edges.get(i).from().equals(closed.get(j))){
                 return closed.get(j);
                }
            }
        }
           
      }
      return null;  
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

    private int findDistance( Vertex from, Vertex to) {
        for (int i = 0; i < map.edges.size(); i++) {
            if (map.edges.get(i).from().equals(from) && map.edges.get(i).to().equals(to)) {
                return map.edges.get(i).weight();
            }
        }
        return -2;
    }

}
