package main;

import java.util.ArrayList;


public class MaxGrade {
  
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
  
  class VertexGrade {
    private int vertex;
    private int degree;
    
    public VertexGrade(int vertex, int degree) {
      this.vertex = vertex;
      this.degree = degree;
    }
    
    public int getVertex() {return vertex;}
    public int getDegree() {return degree;}
    public void increaseDegree() {++degree;}
    public void setDegree() {degree = -1;}
  }
  
  public Integer[] createMaxGradeNonApproximation(int[][] initialGraph) {
    ArrayList<Edge> edges = new ArrayList<Edge>();
    ArrayList<Integer> vertexCover = new ArrayList<Integer>();          // C <- empty
    VertexGrade[] grades = new VertexGrade[initialGraph.length + 1];
    
    for (int i = 0; i < initialGraph.length; i++) {                     // E' <- E
      grades[i + 1] = new VertexGrade(i + 1, 0);
      for (int j = 0; j < initialGraph[0].length; j++) {
        if (initialGraph[i][j] == 1) {
          Edge newEdge = new Edge(i + 1,j + 1);
          edges.add(newEdge);
          grades[i + 1].increaseDegree();
        }
      }
    }
    
    while (!edges.isEmpty()) {                                          // while E' not empty
      int maxGradeVertex = getMaxGrade(grades);                         // take u in (V\C) with max degree
      vertexCover.add(maxGradeVertex);                                  // C <- C U {u}
      
      for (int remover = 0; remover < edges.size(); remover++) {        // remove from E' edges adjacent to u
        Edge removalEdge = edges.get(remover);
        if (maxGradeVertex == removalEdge.getFirst() || maxGradeVertex == removalEdge.getSecond()) {
          edges.remove(remover);
          remover--;
        }
      }
    }
    
    return vertexCover.toArray(new Integer[vertexCover.size()]);        // return C
  }
  
  private int getMaxGrade(VertexGrade[] gradeArray) {
    int max = 0;
    int index = 0;
    
    for (int i = 1; i < gradeArray.length; i++) {
      if (gradeArray[i].getDegree() > max) {
        max = gradeArray[i].getDegree();
        index = i;
      }
    }
    
    gradeArray[index].setDegree();
    return index;
  }
  
  public static void main(String[] args) {
    MaxGrade myMax = new MaxGrade();
    
    int[][] testGraph = new int[][] {
      {0, 1, 0, 0, 1, 0},
      {1, 0, 1, 0, 1, 0},
      {0, 1, 0, 1, 0, 0},
      {0, 0, 1, 0, 1, 1},
      {1, 1, 0, 1, 0, 0},
      {0, 0, 0, 1, 0, 0}
    };
    
    Integer[] vertexCover = myMax.createMaxGradeNonApproximation(testGraph);
    
    System.out.println("Vertex cover is:");
    
    for (int i = 0; i < vertexCover.length; i++) {
      System.out.print(vertexCover[i] + " ");
    }
  }
}