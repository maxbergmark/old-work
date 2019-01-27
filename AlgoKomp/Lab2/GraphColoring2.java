/**
 **  Java Program to Implement Graph Coloring Algorithm
 **/
 
import java.util.Scanner;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
 
/** Class GraphColoring **/
public class GraphColoring2
{    
    private int V, numOfColors;
    private double[] color; 
    private int[][] graph;
    private double[] divaColor = {1.0, 1.5, 2, 3, 4, 5, 6, 7, 8, 9};
    private int edges;
    private HashSet<Integer> allVertices;
    private ArrayList<ArrayList<Integer>> scenes;
    private HashSet<ArrayList<Integer>> allEdges;
    private String[] edg;
 
    /** Function to assign color **/
    public void graphColor(int[][] g, int noc, int edgeNum, String[] edgArr)
    {
        V = g.length;
        numOfColors = noc;
        color = new double[V];
        for (int c = 0; c < V; c++) {color[c] = -2;}
        graph = g;
        edges = edgeNum;
        allVertices = new HashSet<Integer>();
        allEdges = new HashSet<ArrayList<Integer>>();
        edg = edgArr;
        
        divaColor = new double[noc];
        divaColor[0] = 1.0;
        divaColor[1] = 1.5;
        for (int i = 2; i < noc; i++) {
            divaColor[i] = i;
        }
        for (int i = 0; i < V; i++) {
            allVertices.add(i);
        }
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < i; j++) {
                if (graph[i][j] == 1) {
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.add(Math.min(i,j));temp.add(Math.max(i,j));
                    allEdges.add(temp);
                }
            }
        }
//        System.out.println("\n\nAll edges: \n" + allEdges + "\n\n\n");
 
        try
        {
            solve(0);
            System.out.println("No solution");
        }
        catch (Exception e)
        {
//            System.out.println(e);
//            System.out.println("\nSolution exists ");
            display();
        }
    }
    /** function to assign colors recursively **/
    public void solve(int v) throws Exception
     {
        /** base case - solution found **/

        if (v == V) {
            
            boolean firstDiva = false;
            boolean secondDiva = false;
            for (int i = 0; i < V; i++) {
                //System.out.println(color[i] + "   " + (color[i] == 1.5) + "   " + (color[i] == 0.5));
                if (color[i] == divaColor[0]) {

                    firstDiva = true;
                }
                if (color[i] == divaColor[1]) {
                    secondDiva = true;
                }
            }
            if (firstDiva & secondDiva) {

                throw new Exception("Solution found");
            } else {
                return;
            }
            
        }
        /** try all colours **/
        double c = divaColor[0];
        for (int i = 0; i <= numOfColors;c = divaColor[i++])
        {
            if (isPossible(v, c))
            {
                /** assign and proceed with next vertex **/
                color[v] = c;
                solve(v + 1);
                /** wrong assignement **/
                color[v] = 0;
            }
        }    
    }

    private int numUnique(double[] arr) {
        HashSet<Double> h = new HashSet<Double>();
        for (double d : arr) {
            h.add(d);
        }
        return h.size();
    }

    /** function to check if it is valid to allot that color to vertex **/
    public boolean isPossible(int v, double c)
    {
        for (int i = 0; i < V; i++)
            if (graph[v][i] == 1 && (((int) c) == ((int) color[i])))
                return false;
        return true;
    }
    /** display solution **/
    public void display()
    {
//        System.out.print("\nColors : ");
//        for (int i = 0; i < V; i++)
//            System.out.print(color[i] +" ");
//        System.out.println();
    }

    public void findScenes() {
        ArrayList<Integer> subSet = new ArrayList<Integer>();
        HashSet<ArrayList<Integer>> edgesUnused = allEdges;

        HashSet<Integer> verticesUnused = allVertices;
        scenes = new ArrayList<ArrayList<Integer>>();
        HashSet<ArrayList<Integer>> allECopy = new HashSet<ArrayList<Integer>>();
        int c = 0;
        while (edgesUnused.size() > 0) {
//        while (verticesUsed.size() < V) {
            c++;

//            System.out.println();
//            System.out.println(scenes);
//            System.out.println(edgesUsed);
            //allVCopy.clear();
            long t1 = 0;
            long t2 = 0;
            
            subSet = new ArrayList<Integer>();
            //long t2 = System.nanoTime();
            
                
            if (verticesUnused.size() > 0) {
                for (Integer i : verticesUnused) {
                    subSet.add(i);
                    break;
                }
                
            } else {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                for (ArrayList<Integer> a : edgesUnused) {
                    temp = a;
                    break;
                }
                for (Integer i : temp) {
                    subSet.add(i);
                }

//                System.out.println("Special case: " + subSet);

            }
            //t2 = System.nanoTime();
                //            System.out.println();
            t1 = System.nanoTime();

            boolean found;
            
            //long t2 = System.nanoTime();
            do {
                found = false;
                for (int i = 0; i < V; i++) {
                    int tempSum = 0;
                    for (Integer j : subSet) {
                        tempSum += graph[i][j];
                    }
                    if (tempSum == subSet.size()) {
                        subSet.add(i);
                        found = true;
                        break;
                    }
                }

            } while (found);
            
            

            scenes.add(subSet);
            for (Integer i : subSet) {
                for (Integer j : subSet) {
                    if (j > i) {
                        ArrayList<Integer> tempEdge = new ArrayList<Integer>();
                        tempEdge.add(Math.min(i,j)); tempEdge.add(Math.max(i,j));
                        edgesUnused.remove(tempEdge);
                    }
                }
            }
            for (Integer i : subSet) {
                verticesUnused.remove(i);
            }

            t2 = System.nanoTime();
            //long t2 = System.nanoTime();

            //System.out.println((t2-t1) + "   " + 806*(t2-t1)/1000000 + "   " + subSet.size());

        }
        //System.out.println("Iterations: " + c);
//        System.out.println(scenes);
    }

    public void printOutput() {
        HashSet<Double> h = new HashSet<Double>();
        for (double d : color) {
            h.add(d);
        }
        String output = "";
        output += V + " ";
        output += scenes.size() + " ";
        output += h.size() + "";
        System.out.println(output);
        for (int i = 0; i < V; i++) {
            String temp = "";
            
            if (color[i] == 1) {
                temp = "1";
            } else if (color[i] == 1.5) {
                temp = "2";
            } else {
                temp = Integer.toString(((int) color[i]) + 1);
            }
            /*
            for (int j = 0; j < h.size(); j++) {
                temp += " " + (j+1);
            }
            */
            System.out.println("1 " + temp);
//            output += "1 " + temp + "\n";
        }
        for (ArrayList<Integer> i : scenes) {
            String temp = i.size() + "";
            for (Integer j : i) {
                temp += " " + (j+1);
            }
            temp += "";
            System.out.println(temp);
//            output += temp;
        }

    }
    public void findScenes2(int numOfColors, int V, int edges, String[] edg) {
        int neededActors = numOfColors + 2;
        int neededRoles = V + 2;
        int neededScenes = edges + 2;
        int numOfDivas = 2;

        System.out.println((neededRoles) + " " + (neededScenes) + " " + (neededActors));
        
        String temp = "";
        for (int i = numOfDivas+1; i < neededActors; i++) {
            
            temp += i + " ";
        }
        
        temp += neededActors + "";
        
        for (int r = 0; r < V; r++) {
            System.out.println((neededActors-2) + " " + temp);
        }
        

        System.out.println(1 + " " + 1 + "\n" + 1 + " " + 2);
//        System.out.println(1 + " " + 2);

        
        for (int e = 0; e < edges; e++) {
            System.out.println(2 + " " + edg[e]);
        }
        
        System.out.println(2 + " " + V + " " + (V+1));
        System.out.println(2 + " " + V + " " + (V+2));

        
    }


    

    public static void main (String[] args) 
    {
        Scanner scan = new Scanner(System.in);
//        System.out.println("Graph Coloring Algorithm Test\n");
        /** Make an object of GraphColoring class **/
        GraphColoring2 gc = new GraphColoring2();
 
        /** Accept number of vertices **/
//        System.out.println("Enter number of vertices\n");
        int V = scan.nextInt();
 
        /** Accept number of edges **/
//        System.out.println("Enter number of edges\n");
        int E = scan.nextInt();

//        System.out.println("\nEnter number of colors");
        int c = scan.nextInt();

        /** get graph **/
//        System.out.println("\nEnter edges\n");
        String[] edg = new String[E];
        int[][] graph = new int[V][V];
        for (int i = 0; i < E; i++) {
            int x = scan.nextInt()-1;
            int y = scan.nextInt()-1;
            graph[x][y] = graph[y][x] = 1;
            edg[i] = (x+1) + " " + (y+1);
        }
        long t0 = System.nanoTime();
        //gc.graphColor(graph, c+5, E, edg);
        long t1 = System.nanoTime();
        gc.findScenes2(c, V, E, edg);
        long t2 = System.nanoTime();
//        System.out.println((t1-t0)/1000000);
//        System.out.println((t2-t1)/1000000);
//        System.out.println(((t2-t1)/(double)((t1-t0))));
        //gc.printOutput();
        //System.out.println();
        //gc.reduce();
    }
}