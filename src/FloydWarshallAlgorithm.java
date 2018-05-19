
import java.util.ArrayList;
import java.util.Arrays;
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
public class FloydWarshallAlgorithm implements Algorithm {

    private Graph inputGraph;
    private int[][] distanceMatrix;
    private int[][] parentMatrix;

    public FloydWarshallAlgorithm(Graph inputGraph) {
        this.inputGraph = inputGraph;
        this.distanceMatrix = new int[inputGraph.getNumberOfVertices()][inputGraph.getNumberOfVertices()];
        this.parentMatrix = new int[inputGraph.getNumberOfVertices()][inputGraph.getNumberOfVertices()];
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                distanceMatrix[i][j] = 99999;
                parentMatrix[i][j] = 0;
            }
        }
    }

    public String obtainPath(int i, int j) {
        if (distanceMatrix[i][j] == 99999) {
            return " no path to ";
        }

        if (parentMatrix[i][j] == i) {
            return " ";
        } else {
            return obtainPath(i, parentMatrix[i][j]) + String.valueOf(parentMatrix[i][j] + 1) + obtainPath(parentMatrix[i][j], j);
        }
    }

    public String explainPath(int i, int j) {

        String[] newStr = obtainPath(i, j).split(" ");
        String explainStr = "";
        for (int k = 1; k < newStr.length; k++) {
            explainStr += inputGraph.vertices.get(Integer.parseInt(newStr[k].trim()) - 1) + " ";
        }
        return explainStr;
    }

    public void startAlgorithm(String cityofOrigin, String targetCity) {

        for (int i = 0; i < inputGraph.getNumberOfVertices(); i++) {
            distanceMatrix[i][i] = 0;
            parentMatrix[i][i] = i;
        }

        for (int i = 0; i < inputGraph.edges.size(); i++) {
            distanceMatrix[getCityFromIndex(i)][getCitytoIndex(i)] = inputGraph.edges.get(i).weight();
            parentMatrix[getCityFromIndex(i)][getCitytoIndex(i)] = getCityFromIndex(i);
        }

        for (int k = 0; k < inputGraph.getNumberOfVertices(); k++) {
            for (int i = 0; i < inputGraph.getNumberOfVertices(); i++) {
                for (int j = 0; j < inputGraph.getNumberOfVertices(); j++) {
                    if (distanceMatrix[i][j] > (distanceMatrix[i][k] + distanceMatrix[k][j])) {
                        distanceMatrix[i][j] = (distanceMatrix[i][k] + distanceMatrix[k][j]);
                        parentMatrix[i][j] = parentMatrix[k][j];
                    }
                }
            }
        }

        System.out.println("Distance Between " + cityofOrigin + " - " + targetCity + " is :" + distanceMatrix[findVertexByName(cityofOrigin)][findVertexByName(targetCity)]);
        System.out.println("Path :" + cityofOrigin + " " + explainPath(findVertexByName(cityofOrigin), findVertexByName(targetCity)) + " " + targetCity);

    }

    public String PrintDistance(String cityOfOrigin, String destination) {
        return "" + distanceMatrix[findVertexByName(cityOfOrigin)][findVertexByName(destination)];
    }

    public List<String> PrintPath(String cityofOrigin, String targetCity) {
        String resPath = cityofOrigin + " " + explainPath(findVertexByName(cityofOrigin), findVertexByName(targetCity)) + " " + targetCity;
        String[] ultPath = resPath.split(" ");
        List<String> endstr = new ArrayList<>(Arrays.asList(ultPath));
        if(endstr.contains("")){
         endstr.remove("");
        }
        return endstr;
    }

    private int findVertexByName(String cityName) {
        for (int i = 0; i < inputGraph.vertices.size(); i++) {
            if (inputGraph.vertices.get(i).equals(new Vertex(cityName))) {
                return i;
            }
        }
        return -1;
    }

    private int getCityFromIndex(int edgeIndex) {
        Vertex toEdge = inputGraph.edges.get(edgeIndex).from();
        for (int i = 0; i < inputGraph.vertices.size(); i++) {
            if (toEdge.equals(inputGraph.vertices.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private int getCitytoIndex(int edgeIndex) {
        Vertex toEdge = inputGraph.edges.get(edgeIndex).to();
        for (int i = 0; i < inputGraph.vertices.size(); i++) {
            if (toEdge.equals(inputGraph.vertices.get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String getNameOfAlgorithm() {
        return "Floyd - Warshall Alg.";
    }

}
