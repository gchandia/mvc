package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class RandomTest {
  public static void main(String[] args) {
    int iter = 3; // number of iterations
    
    int n;
    float p;
    String[] algorithms = {"TwoApp", "MaxGrade", "BetterTwoApp"};
    Integer[] vertexCover = null;
    
    int[][] rndGraph;
    long time;
    long initTime;
    long endTime;
    String filename;
    
    for (int i = 13; i <= 13; i++) {
      System.out.println("Proccessing n = 2^" + i);
      n = (int) Math.pow(2, i);
      
      for (int j = 0; j < 5; j++) { // iter on p
        p = 1f/n + (j*9f)/(4*n); // linear distribution
        System.out.println("  P = " + p);
        rndGraph = randomGraph(n, p);
        
        for (String alg: algorithms) {
          time = 0;
          System.out.println("    " + alg);
          for (int k = 0; k < iter; k++) {
            initTime = System.currentTimeMillis();
            vertexCover = computeVertexCover(rndGraph, alg);
            endTime = System.currentTimeMillis();
            time += endTime - initTime;
          }
          time /= iter;
          filename = String.format("%s-%d-%d.txt", alg, i, (j+1));
          writeResults(filename, n, p, time, vertexCover);
        }
      }
    }
    System.out.println("Done!");
  }
  
  private static int[][] randomGraph(int n, float p) {
    Random rnd = new Random();
    int[][] graph = new int[n][n];
    float rn;
    
    for (int i = 0; i < graph.length; i++) {
      graph[i][i] = 0;
      for (int j = 0; j < i; j++) {
        rn = rnd.nextFloat();
        if (rn < p) {
          graph[i][j] = 1;
          graph[j][i] = 1;
        } else {
          graph[i][j] = 0;
          graph[j][i] = 0;
        }
      }
    }
    return graph;
  }
  
  private static Integer[] computeVertexCover(int[][] graph, String alg) {
    TwoApproximation twoApp = new TwoApproximation();
    MaxGrade maxGrade = new MaxGrade();
    BetterTwoApproximation betterTwoApp = new BetterTwoApproximation();
    
    switch (alg) {
      case "TwoApp":
        return twoApp.createTwoApproximation(graph);
      case "MaxGrade":
        return maxGrade.createMaxGradeNonApproximation(graph);
      case "BetterTwoApp":
        return betterTwoApp.betterTwoApproximation(graph);
      default:
        return null;
    }
  }
  
  private static void writeResults(String filename, int n, float p, long time,
      Integer[] vertexCover) {
    BufferedWriter bw = null;
    FileWriter fw = null;
    
    try {
      fw = new FileWriter("results_rnd" + File.separator + filename);
      bw = new BufferedWriter(fw);
      bw.write("N = " + n); bw.newLine();
      bw.write("P = " + p); bw.newLine();
      bw.write("Time = " + time); bw.newLine(); bw.newLine();
      bw.write("Minimum Vertex Cover:"); bw.newLine();
      bw.write(Arrays.toString(vertexCover));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bw != null)
            bw.close();
        if (fw != null)
            fw.close();
      } catch (IOException e2) {
          e2.printStackTrace();
      }
    }
  }
}
