
import java.util.ArrayList;
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
public class Graph {
     List<DirectedEdge> edges;
     List<Vertex> vertices;
     HashMap<AirDistanceUnit,Integer> airDistances;

    public Graph(List<DirectedEdge> edges, List<Vertex> vertices) {
        this.edges = edges;
        this.vertices = vertices;
        
    }

    public Graph(List<DirectedEdge> edges, List<Vertex> vertices, HashMap<AirDistanceUnit, Integer> airDistances) {
        this.edges = edges;
        this.vertices = vertices;
        this.airDistances = airDistances;
    }
    
    

    public List<DirectedEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<DirectedEdge> edges) {
        this.edges = edges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }
     
    public List<DirectedEdge> findAllOngoingEdges(Vertex v){
     List<DirectedEdge> vertex_to = new ArrayList<>();   
        for (int i = 0; i < edges.size(); i++) {
            if(v.equals(edges.get(i).from())){
               vertex_to.add(edges.get(i));
            }
        }
        return vertex_to;
    }
    
    public int getNumberOfVertices(){
     return this.vertices.size();
    }

    public HashMap<AirDistanceUnit, Integer> getAirDistances() {
        return airDistances;
    }

    public void setAirDistances(HashMap<AirDistanceUnit, Integer> airDistances) {
        this.airDistances = airDistances;
    }
    
    
    
    
     
}
