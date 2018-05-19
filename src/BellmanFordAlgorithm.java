
import java.util.Arrays;
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
public class BellmanFordAlgorithm implements Algorithm {

    private Graph graph;
    private Vertex startVertex;
    private HashMap<Vertex, Integer> sourceDistances;
    private HashMap<Vertex, Vertex> previousVertices;

    public BellmanFordAlgorithm(Graph graph, Vertex startVertex) {
        this.graph = graph;
        this.startVertex = startVertex;
        sourceDistances = new HashMap<>();
        previousVertices = new HashMap<>();

        int index = graph.vertices.indexOf(this.startVertex);
        graph.vertices.remove(index);
        graph.vertices.add(0, this.startVertex);

    }

    public void startAlgorithm(String cityofOrigin, String targetCity) {
        // Initialize
        sourceDistances.put(startVertex, 0);
        previousVertices.put(startVertex, null);
        for (int i = 1; i < this.graph.getNumberOfVertices(); i++) {
            sourceDistances.put(this.graph.vertices.get(i), 99999);
            previousVertices.put(this.graph.vertices.get(i), null);
        }

        for (int i = 0; i < this.graph.getNumberOfVertices() - 1; i++) {
            for (int j = 0; j < this.graph.vertices.size(); j++) {
                List<DirectedEdge> next_edges = this.graph.findAllOngoingEdges(graph.vertices.get(j));
                for (int k = 0; k < next_edges.size(); k++) {
                    Vertex toVertex = next_edges.get(k).to();
                    int newDistance = sourceDistances.get(graph.vertices.get(j)) + next_edges.get(k).weight();

                    updateDistance(graph.vertices.get(j), toVertex, newDistance);
                }
            }
        }

        printShortestPath(graph.vertices.get(findVertexByName(targetCity)));

    }

    private int findVertexByName(String cityName) {
        for (int i = 0; i < graph.vertices.size(); i++) {
            if (graph.vertices.get(i).equals(new Vertex(cityName))) {
                return i;
            }
        }
        return -1;
    }

//    public String printDistance(String source, String dest){
//      return ""+sourceDistances.get(new Vertex(dest));
//    }
//    public List<String> printPath(String source,String dest){
//   
//    }
    
    //This method prints out shortest path 
    private void printShortestPath(Vertex V) {
        System.out.println("Shortest Path from " + startVertex.getName() + " to " + V.getName() + " Distance :" + sourceDistances.get(V));
        Vertex newVertex = new Vertex(V.getName());
        System.out.print(V.getName() + " ");
        while (previousVertices.get(newVertex) != null) {
            System.out.print(previousVertices.get(newVertex).getName() + " ");
            newVertex = new Vertex(previousVertices.get(newVertex).getName());
        }

        System.out.println("");
    }

    private void updateDistance(Vertex currentVertex, Vertex toVertex, int newValue) {
        if (sourceDistances.get(toVertex) > newValue) {
            sourceDistances.put(toVertex, newValue);
            previousVertices.put(toVertex, currentVertex);
        }
    }

    @Override
    public String PrintDistance(String cityOfOrigin, String destination) {
        return "" + sourceDistances.get(new Vertex(destination));
    }

    @Override
    public List<String> PrintPath(String cityofOrigin, String targetCity) {
        Vertex newVertex = new Vertex(targetCity);
        String x = "";
        x = targetCity + " ";
        while (previousVertices.get(newVertex) != null) {
            x += previousVertices.get(newVertex).getName() + " ";
            newVertex = new Vertex(previousVertices.get(newVertex).getName());
        }
        List<String> y = Arrays.asList(x.split(" "));
        Collections.reverse(y);
        return y;
    }

    @Override
    public String getNameOfAlgorithm() {
        return "Bellman - Ford Alg.";
    }

}
