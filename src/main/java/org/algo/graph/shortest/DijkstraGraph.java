package org.algo.graph.shortest;

import org.algo.graph.Edge;

import java.util.*;

public class DijkstraGraph {
    public DijkstraNode root;
    public Map<Integer, DijkstraNode> nodes;

    public DijkstraGraph(DijkstraNode root) {
        this.root = root;
        this.nodes = new HashMap<>();
    }

    public void dijkstra() {
        PriorityQueue<DijkstraNode> unsettledQueue = new PriorityQueue<>();
        List<DijkstraNode> settled = new ArrayList<>();

        // Initializattion to Max value for every node dist
        //   and add root to unsettled nodes queue
        for (DijkstraNode node : nodes.values()) {
            node.distanceFromRoot = Integer.MAX_VALUE;
        }
        unsettledQueue.add(root);

        while (!unsettledQueue.isEmpty()) {
            DijkstraNode nodeToSettled = unsettledQueue.poll();
            for (Edge edge : nodeToSettled.edges) {
                DijkstraNode nodeToEvaluate = nodes.get(edge.dst);
                if (nodeToEvaluate.distanceFromRoot > nodeToSettled.distanceFromRoot + edge.weigth) {
                    nodeToEvaluate.distanceFromRoot = nodeToSettled.distanceFromRoot + edge.weigth;
                    nodeToEvaluate.parent = nodeToSettled.value;
                    unsettledQueue.add(nodeToEvaluate);
                }
            }
        }
    }
}
