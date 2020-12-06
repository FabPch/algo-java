package org.algo.graph.connected;

import java.util.LinkedList;
import java.util.Stack;

public class ConnectedGraph {
        private int verticeNumber;
        private LinkedList<Integer>[] adjencyNodes;

    public ConnectedGraph(int verticeNumber) {
        this.verticeNumber = verticeNumber;
        for (int i=0; i<verticeNumber; i++) {
            adjencyNodes[i] = new LinkedList<>();
        }
    }

    public void addEdge(int src, int dst) {
        adjencyNodes[src].add(dst);
    }

    public void DFSUtil(int node, boolean[] visited) {
        visited[node] = true;
        System.out.println(node + " ");
        for (int adjencyNode : adjencyNodes[node]) {
            if (!visited[adjencyNode]) {
                DFSUtil(adjencyNode, visited);
            }
        }
    }

    public ConnectedGraph getReverseGraph() {
        ConnectedGraph reverseGraph = new ConnectedGraph(verticeNumber);
        for (int node=0; node<verticeNumber; node++) {
            for (int adjencyNode : adjencyNodes[node]) {
                reverseGraph.addEdge(adjencyNode, node);
            }
        }
        return reverseGraph;
    }

    public void fillOrder(int node, boolean[] visited, Stack stack) {
        visited[node] = true;
        for (int adjencyNode : adjencyNodes[node]) {
            if (!visited[adjencyNode]) {
                fillOrder(adjencyNode, visited, stack);
            }
        }
        stack.push(node);
    }

    public void printSCCs() {
        Stack stack = new Stack();
        boolean[] visited = new boolean[verticeNumber];

        for (int i=0; i<verticeNumber; i++) {
            visited[i] = false;
        }

        for (int i=0; i<verticeNumber; i++) {
            if (!visited[i]) {
                fillOrder(i, visited, stack);
            }
        }

        ConnectedGraph reverseGraph = getReverseGraph();

        for (int i=0; i<verticeNumber; i++) {
            visited[i] = false;
        }

        while (!stack.isEmpty()) {
            int currentNode = (int) stack.pop();
            if (!visited[currentNode]) {
                reverseGraph.DFSUtil(currentNode, visited);
                System.out.println();
            }
        }
    }
}
