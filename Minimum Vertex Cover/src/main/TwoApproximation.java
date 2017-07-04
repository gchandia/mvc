package main;

import java.util.ArrayList;

public class TwoApproximation {
  
  class Edge {
    private int firstNode;
    private int secondNode;
    
    public Edge(int adjacentX, int adjacentY) {
      firstNode = adjacentX;
      secondNode = adjacentY;
    }
    
    public int getFirst() {return firstNode;}
    public int getSecond() {return secondNode;}
  }
  
  public Integer[] createTwoApproximation(int[][] initialGraph) {
    ArrayList<Edge> edges = new ArrayList<Edge>();
    ArrayList<Integer> vertexCover = new ArrayList<Integer>();
    
    for (int i = 0; i < initialGraph.length; i++) {
      for (int j = 0; j < initialGraph[0].length; j++) {
        if (initialGraph[i][j] == 1) {
          Edge newEdge = new Edge(i + 1,j + 1);
          edges.add(newEdge);
        }
      }
    }
    
    while (!edges.isEmpty()) {
      Edge edge = edges.remove(0);
      vertexCover.add(edge.getFirst());
      vertexCover.add(edge.getSecond());
      
      for (int remover = 0; remover < edges.size(); remover++) {
        Edge removalEdge = edges.get(remover);
        if (edge.getFirst() == removalEdge.getFirst() || edge.getFirst() == removalEdge.getSecond() || edge.getSecond() == removalEdge.getFirst() || edge.getSecond() == removalEdge.getSecond()) {
          edges.remove(remover);
          remover--;
        }
      }
    }
    
    return vertexCover.toArray(new Integer[vertexCover.size()]);
  }
  
  public static void main(String[] args) {
    TwoApproximation myApprox = new TwoApproximation();
    
    int[][] testGraph = new int[][] {
      {0, 1, 0, 0, 1, 0},
      {1, 0, 1, 0, 1, 0},
      {0, 1, 0, 1, 0, 0},
      {0, 0, 1, 0, 1, 1},
      {1, 1, 0, 1, 0, 0},
      {0, 0, 0, 1, 0, 0}
    };
    
    Integer[] vertexCover = myApprox.createTwoApproximation(testGraph);
    
    for (int i = 0; i < testGraph.length; i++) {
      for (int j = 0; j < testGraph[0].length; j++) {
        System.out.print(testGraph[i][j] + " ");
      }
      System.out.println("");
    }
    
    System.out.println("Vertex cover is:");
    
    for (int i = 0; i < vertexCover.length; i++) {
      System.out.print(vertexCover[i] + " ");
    }
  }

}