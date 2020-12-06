package org.algo.graph.browse;

import java.util.LinkedList;

public class Node<T> {
    public T value;
    // For shortest path only
    public T parent;
    // For shortest path only
    public int discoveryWithBackEdge = 10000;
    public int discoveryTime;
    public boolean root = false;

    public LinkedList<Node<T>> neighbours;

    public Node(T value) {
        this.value = value;
        this.neighbours = new LinkedList<>();
    }

    public void addNeighbour(Node<T> node) {
        this.neighbours.add(node);
        node.neighbours.add(this);
    }

}
