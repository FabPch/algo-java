package org.algo.graph;

public class Edge implements Comparable<Edge> {
    public int src, dst, weigth;

    public Edge() {
    }

    public Edge(int src, int dst, int weigth) {
        this.src = src;
        this.dst = dst;
        this.weigth = weigth;
    }

    @Override
    public int compareTo(Edge o) {
        return this.weigth - o.weigth;
    }
}
