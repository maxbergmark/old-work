/**
 **  Java Program to Implement Graph Coloring Algorithm
 **/
 
import java.util.Scanner;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.io.Console;
import java.io.File;
import java.io.PrintWriter;
 
/** Class Sudoku **/
public class Sudoku {    
    private int V, numOfColors;
    private int[] color; 
    private int[][] graph;
    private static int[] clues;
    private int easyplaced;
    private int totEasy;
    private long totTime = 0;
 
    /** Function to assign color **/
    public void graphColor(int[][] g, int noc, int[] board, int clue, PrintWriter p) {
        V = g.length;
        numOfColors = noc;
        color = new int[V];
        graph = g;
        color = board.clone();
   
//        System.out.println("Input:");
        long t1 = 0,t2 = 0;
//        display(board);
        t1 = System.nanoTime();
        easyplaced = 0;
        placeEasy();  
        totEasy += easyplaced;      
        //System.out.println("Easy placed: " + easyplaced);
        try {
            solve(0);
            System.out.println("No solution");
        } catch (Exception e) {
            t2 = System.nanoTime();
            totTime += t2-t1;
           System.out.println("Solution from " + clue + " clues, found in " + printTime(t1,t2));
//            String sb = getString(board);
//            String sc = getString(color);
//            p.println(sb + " " + sc);
//            p.flush();
            if (t2-t1 > 1000000000L) {
                display2(board, color);
                
            }
        }
    }

    public String getString(int[] board) {
        StringBuilder s = new StringBuilder();
        for (int i : board) {
            s.append(i+1);
        }
        return s.toString();
    }

    public long getTime() {
        return totTime;
    }

    public void placeEasy() {
        int tempPlaced;
        do {
            tempPlaced = easyplaced;
            for (int v = 0; v < 81; v++) {
                if (color[v] == -1) {
                    int temp = 0;
                    int tempcol = -1;
                    for (int c = 0; c < 9; c++) {

                        boolean b = isPossible(v,c);
                        temp += b ? 1:0;
                        tempcol = b ? c : tempcol;
                    }
                    if (temp == 1) {
                        color[v] = tempcol;
                        easyplaced++;
                    }
                }
            }
//            System.out.println(tempPlaced + "   " + easyplaced);
        } while(tempPlaced < easyplaced);
    }

    public static String printTime(long t1, long t2) {
        String unit = " ns";
        if (t2-t1 > 10000) {
            unit = " us";
            t1 /= 1000; t2 /= 1000;
        }
        if (t2-t1 > 10000) {
            unit = " ms";
            t1 /= 1000; t2 /= 1000;
        }
        if (t2-t1 > 10000) {
            unit = " seconds";
            t1 /= 1000; t2 /= 1000;
        }
        return (t2-t1) + unit;
    }

    public void solve(int v) throws Exception {
  
        while (v < 81 && color[v] >= 0) {v++;}
  
        if (v == V) {
            throw new Exception("Solution found");
        }

        for (int c = 0; c < numOfColors;c++) {
            if (isPossible(v, c)) {
                color[v] = c; solve(v + 1); color[v] = -1;
            }
        }    

    }

    public boolean isPossible(int v, int c) {

        for (int i = 0; i < V; i++) {
            if (graph[v][i] == 1 && c == color[i]) {return false;}
        }

        return true;

    }


    public void display(int[] color) {

        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                System.out.println("+-----+-----+-----+");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                if (color[i*9+j] != -1) {
                    System.out.print(color[i*9+j]+1);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("|");
        }
        System.out.println("+-----+-----+-----+");
    }

    public void display2(int[] board, int[] color) {

        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                System.out.println("+-----+-----+-----+     +-----+-----+-----+");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                if (board[i*9+j] != -1) {
                    System.out.print(board[i*9+j]+1);
                } else {
                    System.out.print(" ");
                }
            }

            System.out.print("|     ");

            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                if (color[i*9+j] != -1) {
                    System.out.print(color[i*9+j]+1);
                } else {
                    System.out.print(" ");
                }
            }

            System.out.println("|");
        }
        System.out.println("+-----+-----+-----+     +-----+-----+-----+");
    }

    public static int[][] connect() {
        int[][] conn = new int[81][81];
        int[][] cells = {{0 ,1 ,2 ,9 ,10,11,18,19,20},
                         {3 ,4 ,5 ,12,13,14,21,22,23},
                         {6 ,7 ,8 ,15,16,17,24,25,26},
                         {27,28,29,36,37,38,45,46,47},
                         {30,31,32,39,40,41,48,49,50},
                         {33,34,35,42,43,44,51,52,53},
                         {54,55,56,63,64,65,72,73,74},
                         {57,58,59,66,67,68,75,76,77},
                         {60,61,62,69,70,71,78,79,80}};

        HashMap<Integer,ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
 
        for (int[] c: cells) {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            for (int v : c) {
                temp.add(v);
            }
            for (int v : c) {
                map.put(v,temp);
            }
        }

        for (int i = 0; i < 81; i++) {
            for (int j = (i/9)*9; j < (i/9)*9 + 9; j++) {
                if (i != j) {
                    conn[i][j] = conn[j][i] = 1;
                }
            }
            for (int j = i%9; j < 81; j += 9) {
                if (i != j) {
                    conn[i][j] = conn[j][i] = 1;
                }
            }
            for (int j : map.get(i)) {
                if (i != j) {
                    conn[i][j] = conn[j][i] = 1;
                }
            }
        }
        return conn;
    }

    public static int[][] getInput() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[][] board = new int[n][81];
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < 81; i++) {
                int x = scan.nextInt()-1;
                board[k][i] = x;
            }
        }
        return board;
    }

    public static int[][] getInput2() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        clues = new int[n];
        int[][] board = new int[n][81];
        for (int k = 0; k < n; k++) {
            String line = scan.next();
            int clue = 0;
            for (int i = 0; i < 81; i++) {
                int x = Character.getNumericValue(line.charAt(i))-1;
                if (x != -1) {clue++;}
                board[k][i] = x;
            }
            clues[k] = clue;
        }
        return board;
    }

    private int getTotEasy() {
        return totEasy;
    }

    public static void main (String[] args) {
        Sudoku gc = new Sudoku();
        File f;
        PrintWriter p;
        try {
            f = new File("sudoku_output.txt");
            p = new PrintWriter(f);
        } catch (Exception e) {
            return;
        }
        long t0 = System.nanoTime();
        int[][] board = gc.getInput2();
        long tinp = System.nanoTime();
        int[][] graph = gc.connect();
        long t1 = System.nanoTime();
        for (int i = 0; i < board.length; i++) {
            System.out.print("(" + (i+1) + "/" + board.length + ") ");
            gc.graphColor(graph, 9, board[i], gc.clues[i], p);
        }
        long t2 = System.nanoTime();
        System.out.println("Total time (including prints): " + gc.printTime(t1,t2));
        System.out.println("Sudoku solving time: " + gc.printTime(0,gc.getTime()));
        System.out.println("Average time per board: " + gc.printTime(0,gc.getTime()/board.length));
        System.out.println("Number of one-choice digits per board: " + String.format("%.2f", gc.getTotEasy()/(double)board.length));  
        System.out.println("\nInput time: " + gc.printTime(t0,tinp));
        System.out.println("Connect time: " + gc.printTime(tinp,t1));
    }
}
