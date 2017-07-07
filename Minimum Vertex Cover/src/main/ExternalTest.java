package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ExternalTest {
  private static int N;
  
  public static void main(String[] args) {
    int iter = 3; // number of iterations

    String[] algorithms = {"TwoApp", "MaxGrade", "BetterTwoApp"};
    Integer[] vertexCover = null;
    
    int[][] extGraph;
    long time;
    long initTime;
    long endTime;
    String output;
    String[] files = {"planar_embedding1000000"};
    
    for (String file: files) {
      System.out.println( "Proccessing: " + file);
      extGraph = readGraph(file + ".pg");
      
      for (String alg: algorithms) {
        System.out.println("  " + alg);
        time = 0;
        for (int k = 0; k < iter; k++) {
          initTime = System.currentTimeMillis();
          vertexCover = computeVertexCover(extGraph, alg);
          endTime = System.currentTimeMillis();
          time += endTime - initTime;
        }
      time /= iter;
      output = String.format("%s-%s.txt", file, alg);
      writeResults(output, N, time, vertexCover);
      }
    }
    System.out.println("Done!");
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
  
  private static void writeResults(String filename, int n, long time,
      Integer[] vertexCover) {
    BufferedWriter bw = null;
    FileWriter fw = null;
    
    try {
      fw = new FileWriter("results_ext" + File.separator + filename);
      bw = new BufferedWriter(fw);
      bw.write("N = " + n); bw.newLine();
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
  
  private static int[][] readGraph(String filepath) {
    BufferedReader br = null;
    FileReader fr = null;
    int[][] graph = null;

    try {
        fr = new FileReader(filepath);
        br = new BufferedReader(fr);
        String sCurrentLine;
        String[] elems;
        int i, j;
        int counter = 0;
        
        while ((sCurrentLine = br.readLine()) != null) {
          switch (counter) {
            case 0:
              N = Integer.parseInt(sCurrentLine);
              System.out.println("Size: " + N);
              graph = new int[N][N];
              break;
            case 1:
              break;
            default:
              elems = sCurrentLine.split(" ");
              i = Integer.parseInt(elems[0]);
              j = Integer.parseInt(elems[1]);
              graph[i][j] = 1;
              graph[j][i] = 1;
              break;
          }
          counter++;
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (br != null)
                br.close();
            if (fr != null)
                fr.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    return graph;
  }
}
