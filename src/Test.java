
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alper
 */
public class Test {

    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {
        List<Graph> graphs = preProcess();
        Algorithm astar = new AStarAlgorithm(graphs.get(0));
//        astar.startAlgorithm("Izmir","Sofia");
//        System.out.println(astar.PrintDistance("Izmir","Sofia"));
//        List<String> str = astar.PrintPath("Izmir", "Sofia");
//        for (int i = 0; i < str.size(); i++) {
//            System.out.print(str.get(i)+" ");
//        }
//        Algorithm climb = new BestFirstSearch(graphs.get(0));
//        climb.startAlgorithm("Izmir", "Sofia");
//        System.out.println(climb.PrintDistance("Izmir", "Sofia"));
//        List<String> str = climb.PrintPath("Izmir", "Sofia");
//        for (int i = 0; i < str.size(); i++) {
//            System.out.print(str.get(i)+" ");
//        }
        Algorithm aa = new IterativeDeepening(graphs.get(0));
        aa.startAlgorithm("Izmir","Sofia");
        String x= aa.PrintDistance("Izmir","Sofia");
        System.out.println(x);
        List<String> str = aa.PrintPath("Izmir","Sofia");
         for (int i = 0; i < str.size(); i++) {
            System.out.print(str.get(i)+" ");
        }
    }
    
 
    
   
     

    public static List<Graph> preProcess() {
        List<Graph> graphs = new ArrayList<>();

        int[][] distanceMatrix = ReadMeasuresFromFile(new File("drive_distancesv3.txt"));
        int[][] timeMatrix = ReadMeasuresFromFile(new File("drive_timesv2.txt"));
        String[] cities = ReadCitiesFromFile(new File("cities.txt"));
        HashMap<AirDistanceUnit,Integer> airdistances = ReadAirDistancesFromFile(new File("air_distances.txt"), cities);

        List<Vertex> vertices = createVertices(cities);
        List<DirectedEdge> distanceEdges = createDirectedEdges(vertices, distanceMatrix);
        List<DirectedEdge> timeEdges = createDirectedEdges(vertices, timeMatrix);

        graphs.add(new Graph(distanceEdges, vertices,airdistances));
        graphs.add(new Graph(timeEdges, vertices,airdistances));

        return graphs;
    }

    public static int[][] ReadMeasuresFromFile(File file) {
        int[][] distanceMatrix = new int[21][21];
        try {
            Scanner inputFile = new Scanner(file);
            int Rowcounter = 0;
            int Columncounter = 0;

            while (inputFile.hasNextInt()) {
                if (Columncounter == 21) {
                    Columncounter = 0;
                    Rowcounter++;
                }
                distanceMatrix[Rowcounter][Columncounter] = inputFile.nextInt();
                Columncounter++;
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Error when reading the file 1!");
        }

        return distanceMatrix;
    }

    public static String[] ReadCitiesFromFile(File file) {
        String[] cities = new String[21];
        try {
            Scanner inputFile = new Scanner(file);
            int counter = 0;
            while (inputFile.hasNextLine()) {
                cities[counter] = inputFile.nextLine().trim();
                counter++;
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Error when reading the file 2!");
        }

        return cities;
    }
    
    
     public static HashMap<AirDistanceUnit,Integer> ReadAirDistancesFromFile(File file , String[] cities) {
        HashMap<AirDistanceUnit,Integer> distances = new HashMap<>();
        int[][] distanceMatrix = new int[21][21];
           try {
            Scanner inputFile = new Scanner(file);
            int Rowcounter = 0;
            int Columncounter = 0;

            while (inputFile.hasNextInt()) {
                if (Columncounter == 21) {
                    Columncounter = 0;
                    Rowcounter++;
                }
                distanceMatrix[Rowcounter][Columncounter] = inputFile.nextInt();
                Columncounter++;
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Error when reading the file 3!");
        }
         for (int i = 0; i < distanceMatrix.length; i++) {
             for (int j = 0; j < distanceMatrix[i].length; j++) {
                 distances.put(new AirDistanceUnit(new Vertex(cities[i]),new Vertex(cities[j])),distanceMatrix[i][j]);
             }
         }
        return distances;
    }
    
    
    
    
    
    
    
    

    public static List<Vertex> createVertices(String cities[]) {
        List<Vertex> cts = new ArrayList<>(21);
        for (int i = 0; i < cities.length; i++) {
            cts.add(new Vertex(cities[i]));
        }
        return cts;
    }

    public static List<DirectedEdge> createDirectedEdges(List<Vertex> vertices, int[][] distanceMatrix) {
        List<DirectedEdge> dEdge = new ArrayList<>();
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                dEdge.add(new DirectedEdge(vertices.get(i), vertices.get(j), distanceMatrix[i][j]));
            }

        }
        return dEdge;
    }

    public static Graph createGraph(List<Vertex> vertices, List<DirectedEdge> edges) {
        return new Graph(edges, vertices);
    }

}
