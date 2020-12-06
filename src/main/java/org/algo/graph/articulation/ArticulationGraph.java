package org.algo.graph.articulation;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArticulationGraph {

    private int verticeNumber;
    private int time = 0;
    private LinkedList<Integer>[] adjacencyNodes;
    static final int NIL = -1;

    public ArticulationGraph(int verticeNumber) {
        this.verticeNumber = verticeNumber;
        this.adjacencyNodes = new LinkedList[verticeNumber];
        for (int i=0; i<verticeNumber; i++) {
            adjacencyNodes[i] = new LinkedList<>();
        }
    }

    public void addEdge(int src, int dst) {
        adjacencyNodes[src].add(dst);
        adjacencyNodes[dst].add(src);
    }

    public void APUtil(int currentNode, boolean[] visited, int[] discoveryTimes, int[] lowValues, int[] parents, boolean[] ap) {
        int children = 0;
        visited[currentNode] = true;

        // init discovery time and low value
        discoveryTimes[currentNode] = lowValues[currentNode] = time;
        time++;

        for (int adjacentNode : adjacencyNodes[currentNode]) {
            if (!visited[adjacentNode]) {
                children++;
                parents[adjacentNode] = currentNode;
                APUtil(adjacentNode, visited, discoveryTimes, lowValues, parents, ap);

                // Check for ancestor with lower value, so if child as connection to ancestor through a back edge
                //   In this case, update low value for currentNode
                lowValues[currentNode] = Math.min(lowValues[currentNode], lowValues[adjacentNode]);

                // If currentNode is root and has more than one child.
                if (parents[currentNode] == NIL && children > 1) {
                    ap[currentNode] = true;
                }

                // If currentNode is not root and its discovery time is equal or less than its child low value.
                // (so there is no back edge to reconnect the graph)
                if (parents[currentNode] != NIL && lowValues[adjacentNode] >= discoveryTimes[currentNode]) {
                    ap[currentNode] = true;
                }
            // If it has already been visited but is not the parent, it is probably an ancestor with a lower disccovery time.
            //   So we may to update the currentNode lowValue with the ancestor disccovery time, if it is less than the
            //   currentNode low value (means that there is a back edge to reconnect the graph).
            } else if (parents[currentNode] != adjacentNode) {
                lowValues[currentNode] = Math.min(lowValues[currentNode], discoveryTimes[adjacentNode]);
            }
        }
    }

    public void APOrBridges(boolean articulation) {
        boolean[] visited = new boolean[verticeNumber];
        boolean[] ap = new boolean[verticeNumber];
        int[] discoveryTimes = new int[verticeNumber];
        int[] parents = new int[verticeNumber];
        int[] lowValues = new int[verticeNumber];
        List<Pair<Integer, Integer>> bridges = new ArrayList<>();

        for (int vertice=0; vertice<verticeNumber; vertice++) {
            visited[vertice] = false;
            ap[vertice] = false;
            parents[vertice] = NIL;
        }

        for (int vertice=0; vertice<verticeNumber; vertice++) {
            if (!visited[vertice]) {
                if (articulation) {
                    System.out.println("Launch APUtil");
                    APUtil(vertice, visited, discoveryTimes, lowValues, parents, ap);
                } else {
                    System.out.println("Launch bridgeUil");
                    bridgeUtil(vertice, visited, discoveryTimes, lowValues, parents, bridges);
                }
            }
        }

        if (articulation) {
            for (int vertice=0; vertice<verticeNumber; vertice++) {
                if (ap[vertice]) {
                    System.out.println("Articulation point: " + vertice);
                }
            }
        } else {
            for (Pair<Integer, Integer> bridge : bridges) {
                System.out.println("Bridge: " + bridge.getKey() + " " + bridge.getValue());
            }
        }
        time=0;
    }

    public void bridgeUtil(int currentNode, boolean[] visited, int[] discoveryTimes, int[] lowValues, int[] parents, List<Pair<Integer, Integer>> bridges) {
        visited[currentNode] = true;

        discoveryTimes[currentNode] = lowValues[currentNode] = time;
        time++;
        for (int adjacentNode : adjacencyNodes[currentNode]) {
            if (!visited[adjacentNode]) {
                parents[adjacentNode] = currentNode;
                bridgeUtil(adjacentNode, visited, discoveryTimes, lowValues, parents, bridges);

                lowValues[currentNode] = Math.min(lowValues[currentNode], lowValues[adjacentNode]);

                if (lowValues[adjacentNode] > discoveryTimes[currentNode]) {
                    bridges.add(new Pair<>(currentNode, adjacentNode));
                }
            } else if (parents[currentNode] != adjacentNode){
                lowValues[currentNode] = Math.min(lowValues[currentNode], discoveryTimes[adjacentNode]);
            }
        }
    }
}
