package org.algo.graph.shortest;

import org.algo.graph.Edge;

import java.util.ArrayList;
import java.util.List;

public class DijkstraNode implements Comparable<DijkstraNode>{
    public int value;
    public int parent = -1;
    public int distanceFromRoot;

    public List<Edge> edges;

    public DijkstraNode(int value) {
        this.value = value;
        this.edges = new ArrayList<>();
    }

    public DijkstraNode(int value, int parent, int distanceFromRoot) {
        this.value = value;
        this.parent = parent;
        this.distanceFromRoot = distanceFromRoot;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    // Use for shortest path only
    @Override
    public int compareTo(DijkstraNode o) {
        return this.distanceFromRoot - o.distanceFromRoot;
    }
}
