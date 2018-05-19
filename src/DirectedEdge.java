/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alper
 */
public class DirectedEdge {
        
    private Vertex fromVertex;
    private Vertex toVertex;
    private int weight;
    
    
    public DirectedEdge(Vertex fromVertex, Vertex toVertex, int weight){
            this.fromVertex = fromVertex;
            this.toVertex = toVertex;
            this.weight = weight;
        }
        
        public Vertex from(){
            return this.fromVertex;
        }
        
        public Vertex to(){
            return this.toVertex;
        }
        
        public int weight(){
            return weight;
        }
        
        
        public String toString(){
            return this.fromVertex.getName() + "->" + this.toVertex.getName() + " " + String.format("%d", weight);
        }
        
    }

