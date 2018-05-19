/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alper
 */
public class EvaluatedVertex implements Comparable<EvaluatedVertex>{
    private Vertex vertex;
    private int FitnessValue;

    public EvaluatedVertex(Vertex vertex, int FitnessValue) {
        this.vertex = vertex;
        this.FitnessValue = FitnessValue;
    }
    

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public int getFitnessValue() {
        return FitnessValue;
    }

    public void setFitnessValue(int FitnessValue) {
        this.FitnessValue = FitnessValue;
    }

    @Override
    public int compareTo(EvaluatedVertex o) {
        return this.FitnessValue - o.FitnessValue;
    }
 
}
