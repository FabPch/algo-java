package org.algo.graph;

import org.algo.graph.articulation.ArticulationGraph;
import org.algo.graph.browse.Node;
import org.algo.graph.huffman.HuffmanNode;
import org.algo.graph.shortest.DijkstraGraph;
import org.algo.graph.shortest.DijkstraNode;
import org.algo.graph.spanning.SpanningGraph;

import java.util.*;

public class Graphs {
    public static void main(String[] args) {

        // Init before Huffman encoding
        int charNumber = 6;
        char[] chars = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] charfrequencies = {5, 9, 12, 13, 16, 45};

        Queue<HuffmanNode> queue = new PriorityQueue<>();
        for (int i=0; i<charNumber; i++) {
            HuffmanNode node = new HuffmanNode(charfrequencies[i], chars[i]);
            queue.add(node);
        }

        HuffmanNode root = null;
        while (queue.size() > 1) {
            HuffmanNode left = queue.remove();
            HuffmanNode right = queue.remove();

            HuffmanNode parent = new HuffmanNode(left, right);
            root = parent;
            queue.add(parent);
        }

        printHuffmanCode(root, "");
    }

    private static void printHuffmanCode(HuffmanNode root, String code) {
        if (root.isLeaf()) {
            System.out.println(root.letter + ": " + code);
            return;
        }
        printHuffmanCode(root.left, code + "0");
        printHuffmanCode(root.right, code + "1");
    }

    private static void hamiltonianProblem() {
        /* Let us create the following graph
           (0)--(1)--(2)
            |   / \   |
            |  /   \  |
            | /     \ |
           (3)-------(4)    */
        int graph1[][] = {{0, 1, 0, 1, 0},
                {1, 0, 1, 1, 1},
                {0, 1, 0, 0, 1},
                {1, 1, 0, 0, 1},
                {0, 1, 1, 1, 0},
        };

        int[] results = new int[graph1.length];
        int k = 1;
        hamiltonian(k, results, graph1);
    }

    // 2 things:
    //   - Find correct vertex for position k with nextVertex function
    //   - If k is not at last position, find the correct vertex for k+1 position using recursive call.
    private static void hamiltonian(int k, int results[], int graph[][]) {
        while (true) {
            nextVertex(k, results, graph);
            // We try every vertex for the node k in results tab, we end the loop and we'll try again from
            //   previous vertex. So k is 0 if no vertex is found.
            if (results[k] == 0) {
                return;
            }
            // If k is the last position, it means we can print the results.
            //   Else, we call the function recursively to find the next possible vertex.
            if (k == results.length-1) {
                for (int i : results) {
                    System.out.print(i + " ");
                }
                System.out.println("");
            } else {
                hamiltonian(k+1, results, graph);
            }
        }
    }

    // 3 things to check:
    //   - It should not take duplicate elements
    //   - Whenever you take any vertex, there should be an edge from the previous vertex
    //   - If you are on the last vertex, there should be an edge from the first vertex
    // Ends with vertex number at position k. If no one is found, k is 0 and the loop ends.
    private static void nextVertex(int k, int[] results, int[][] graph) {
        while (true) {
            // Get nextValue in results, increase of 1.
            results[k] = (results[k] + 1) % (results.length);
            // We try every vertex for the k position in results tab, we end the loop and we'll try again from
            //   previous vertex.
            if (results[k] == 0) {
                return;
            }

            // Is there any edge from the previous vertex ?
            if (graph[results[k-1]][results[k]] != 0) {
                // Check for duplicate elements and break
                int i;
                for (i=1; i<k; i++) {
                    if (results[i] == results[k]) {
                        break;
                    }
                }
                // Then, if j is equals to k, it means that there is no duplicate elements
                //   If no duplicate, return if k is not at the end, or
                //   if it is at the end and there is an edge to the first vertex in results.
                if (i == k && (k < results.length-1 || (k == results.length-1 && graph[results[k]][0] != 0))) {
                        return;
                }
            }
        }
    }

    private static void articulationOrBridge() {
        ArticulationGraph graph = new ArticulationGraph(6);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 0);
        graph.addEdge(2, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 2);
        graph.APOrBridges(true);

        ArticulationGraph graph1 = new ArticulationGraph(6);
        graph1.addEdge(0, 1);
        graph1.addEdge(1, 2);
//        graph1.addEdge(2, 3);
        graph1.addEdge(3, 0);
        graph1.addEdge(2, 4);
        graph1.addEdge(4, 5);
        graph1.addEdge(5, 2);
        graph1.APOrBridges(false);

        ArticulationGraph graph2 = new ArticulationGraph(7);
        graph2.addEdge(0, 1);
        graph2.addEdge(1, 2);
        graph2.addEdge(2, 0);
        graph2.addEdge(1, 3);
        graph2.addEdge(1, 4);
        graph2.addEdge(1, 6);
        graph2.addEdge(3, 5);
        graph2.addEdge(4, 5);
        graph2.APOrBridges(false);
    }


    private static void articulationPoint() {
        Map<Integer, Node<Integer>> nodes = new HashMap<>();
        for (int i=1; i<7; i++) {
            nodes.put(i, new Node<>(i));
        }
        nodes.get(1).root = true;
        nodes.get(1).addNeighbour(nodes.get(4));
        nodes.get(1).addNeighbour(nodes.get(2));

        nodes.get(4).addNeighbour(nodes.get(3));
        nodes.get(2).addNeighbour(nodes.get(3));

        nodes.get(3).addNeighbour(nodes.get(5));
        nodes.get(3).addNeighbour(nodes.get(6));

        nodes.get(5).addNeighbour(nodes.get(6));

        Set<Integer> visited = new HashSet<>();
        dfsArticulationPoint(nodes.get(1), visited);
        Set<Integer> articulationPoints = findArticulationPoints(nodes);

        for (int point : articulationPoints) {
            System.out.println("Articualtion: " + point);
        }
    }

    private static Set<Integer> findArticulationPoints(Map<Integer, Node<Integer>> nodes) {
        Set<Integer> articulationPoints = new HashSet<>();
        for (Node<Integer> node : nodes.values()) {
//            if (node.root && node.neighbours.size() > 1) {
//                articulationPoints.add(node.value);
//            }
            if (!node.root && node.discoveryWithBackEdge >= nodes.get(node.parent).discoveryTime) {
                articulationPoints.add(node.parent);
            }
        }
        return articulationPoints;
    }

    // TODO: To fix
    private static void dfsArticulationPoint(Node<Integer> root, Set<Integer> visited) {
        Stack<Node<Integer>> stack = new Stack<>();
        root.parent = root.value;
        stack.push(root);
        Node<Integer> current;

        int discovery = root.value;
        while (!stack.isEmpty()) {
            current = stack.pop();
            if (visited.contains(current.value)) {
                continue;
            }
            visited.add(current.value);
            current.discoveryTime = discovery;
            discovery++;
            for (Node<Integer> node : current.neighbours) {
                if (!visited.contains(node.value)) {
                    if (node.value != current.value) {
                        node.parent = current.value;
                    }
                    dfsArticulationPoint(node, visited);
                    current.discoveryWithBackEdge = Math.min(current.discoveryWithBackEdge, node.discoveryWithBackEdge);
                } else {
                    if (!node.value.equals(current.parent)) {
                        current.discoveryWithBackEdge = Math.min(current.discoveryWithBackEdge, node.discoveryTime);
                    }
                }
            }
        }
    }

    private static void floodFill() {
        int[][] maze = new int[4][4];
        dfsFlooFill(maze, 0, 0);
        for (int i=0; i<maze.length; i++) {
            for (int j=0; j<maze.length; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private static void dfsFlooFill(int[][] matrix, int x, int y) {
        if (x >= matrix.length || y >= matrix.length) {
            return;
        }
        if (x < 0 || y < 0) {
            return;
        }
        if (matrix[x][y] == -1) {
            return;
        }
        matrix[x][y] = -1;
        dfsFlooFill(matrix, x-1, y-1);
        dfsFlooFill(matrix, x-1, y);
        dfsFlooFill(matrix, x-1, y+1);
        dfsFlooFill(matrix, x, y-1);
        dfsFlooFill(matrix, x, y+1);
        dfsFlooFill(matrix, x+1, y-1);
        dfsFlooFill(matrix, x+1, y);
        dfsFlooFill(matrix, x+1, y+1);
    }

    private static void floydWarshall() {
        //Init Matrix
        int INF = 99999;
        int[][] matrix = {{0, 3, INF, 7}, {8, 0, 2, INF}, {5, INF, 0, 1}, {2, INF, INF, 0}};

        // Iterate matrix.length time on matrix and calculate optimum distance between 2 nodes (check if it is better
        //   to add an intermediate node or node)
        for (int k=0; k<matrix.length; k++) {
            for (int i=0; i<matrix.length; i++) {
                for (int j=0; j<matrix.length; j++) {
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                }
            }
        }

        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private static void dijkstra() {
        DijkstraGraph graph = buildDijkstraGraph();
        graph.dijkstra();

        for (DijkstraNode node : graph.nodes.values()) {
            System.out.println("Node " + node.value + ": Dist -> " + node.distanceFromRoot + " Parent -> " + node.parent);
        }
    }

    private static DijkstraGraph buildDijkstraGraph() {
        DijkstraNode node0 = new DijkstraNode(0, 0, 0);
        DijkstraNode node1 = new DijkstraNode(1);
        DijkstraNode node2 = new DijkstraNode(2);
        DijkstraNode node3 = new DijkstraNode(3);
        DijkstraNode node4 = new DijkstraNode(4);
        DijkstraNode node5 = new DijkstraNode(5);

        node0.addEdge(new Edge(0, 1, 10));
        node0.addEdge(new Edge(0, 2, 15));

        node1.addEdge(new Edge(1, 3, 12));
        node1.addEdge(new Edge(1, 5, 15));

        node2.addEdge(new Edge(2, 4, 10));

        node3.addEdge(new Edge(3, 5, 1));
        node3.addEdge(new Edge(3, 4, 2));

        node5.addEdge(new Edge(5, 4, 5));

        DijkstraGraph graph = new DijkstraGraph(node0);
        graph.nodes.put(node1.value, node1);
        graph.nodes.put(node2.value, node2);
        graph.nodes.put(node3.value, node3);
        graph.nodes.put(node4.value, node4);
        graph.nodes.put(node5.value, node5);

        return graph;
    }

    private static void bellmanFord() {
        SpanningGraph graph = buildShortestGraph();
        Map<Integer, Integer> results = graph.bellmanFord(0);
        for (Map.Entry<Integer, Integer> entry : results.entrySet()) {
            System.out.println("Node " + entry.getKey() + ": " + entry.getValue());
        }
    }

    private static SpanningGraph buildShortestGraph() {
        SpanningGraph graph = new SpanningGraph(7, 10);

        graph.edges[0].weigth = 6;
        graph.edges[0].src = 0;
        graph.edges[0].dst = 1;

        graph.edges[1].weigth = 5;
        graph.edges[1].src = 0;
        graph.edges[1].dst = 2;

        graph.edges[2].weigth = 5;
        graph.edges[2].src = 0;
        graph.edges[2].dst = 3;

        graph.edges[3].weigth = -1;
        graph.edges[3].src = 1;
        graph.edges[3].dst = 4;

        graph.edges[4].weigth = -2;
        graph.edges[4].src = 2;
        graph.edges[4].dst = 1;

        graph.edges[5].weigth = -2;
        graph.edges[5].src = 3;
        graph.edges[5].dst = 2;

        graph.edges[6].weigth = 1;
        graph.edges[6].src = 2;
        graph.edges[6].dst = 4;

        graph.edges[7].weigth = 3;
        graph.edges[7].src = 5;
        graph.edges[7].dst = 6;

        graph.edges[8].weigth = 3;
        graph.edges[8].src = 4;
        graph.edges[8].dst = 6;

        graph.edges[9].weigth = -1;
        graph.edges[9].src = 3;
        graph.edges[9].dst = 5;

        return graph;
    }

    private static void spanningTest() {
        SpanningGraph graph1 = buildSpanningGraph();
        SpanningGraph graph2 = buildBiggerSpanningGraph();


        Edge[] results1 = graph1.kruskalMST();
        Edge[] results2 = graph2.kruskalMST();

        Edge[] results3 = graph1.primMethod();
        Edge[] results4 = graph2.primMethod();

        int cost1 = getCostFromResults(results1);
        int cost2 = getCostFromResults(results2);
        int cost3 = getCostFromResults(results3);
        int cost4 = getCostFromResults(results4);

        System.out.println("Cost1 is: " + cost1);
        System.out.println("Cost2 is: " + cost2);
        System.out.println("Cost3 is: " + cost3);
        System.out.println("Cost4 is: " + cost4);
    }

    private static int getCostFromResults(Edge[] results) {
        int cost = 0;
        for (Edge edge : results) {
            cost += edge.weigth;
        }
        return cost;
    }

    private static SpanningGraph buildBiggerSpanningGraph() {
        SpanningGraph graph = new SpanningGraph(7, 9);

        graph.edges[0].weigth = 10;
        graph.edges[0].src = 0;
        graph.edges[0].dst = 5;

        graph.edges[1].weigth = 25;
        graph.edges[1].src = 5;
        graph.edges[1].dst = 6;

        graph.edges[2].weigth = 22;
        graph.edges[2].src = 4;
        graph.edges[2].dst = 3;

        graph.edges[3].weigth = 24;
        graph.edges[3].src = 4;
        graph.edges[3].dst = 6;

        graph.edges[4].weigth = 18;
        graph.edges[4].src = 6;
        graph.edges[4].dst = 3;

        graph.edges[5].weigth = 12;
        graph.edges[5].src = 3;
        graph.edges[5].dst = 2;

        graph.edges[6].weigth = 14;
        graph.edges[6].src = 6;
        graph.edges[6].dst = 1;

        graph.edges[7].weigth = 16;
        graph.edges[7].src = 2;
        graph.edges[7].dst = 1;

        graph.edges[8].weigth = 28;
        graph.edges[8].src = 1;
        graph.edges[8].dst = 0;

        return graph;
    }

    private static SpanningGraph buildSpanningGraph() {
        /* Let us create following weighted graph
                 10
            0--------1
            |  \     |
           6|   5\   |15
            |      \ |
            2--------3
                4       */

        int verticeNumber = 4;
        int edgeNumber = 5;
        SpanningGraph graph = new SpanningGraph(4, 5);

        graph.edges[0].weigth = 10;
        graph.edges[0].src = 0;
        graph.edges[0].dst = 1;

        graph.edges[1].weigth = 15;
        graph.edges[1].src = 1;
        graph.edges[1].dst = 3;

        graph.edges[2].weigth = 4;
        graph.edges[2].src = 3;
        graph.edges[2].dst = 2;

        graph.edges[3].weigth = 6;
        graph.edges[3].src = 2;
        graph.edges[3].dst = 0;

        graph.edges[4].weigth = 5;
        graph.edges[4].src = 0;
        graph.edges[4].dst = 3;

        return graph;
    }


    private static void TreeSearch() {
        Node<Integer> start = new Node<>(10);
        Node<Integer> firstNeighbor = new Node<>(2);
        start.addNeighbour(firstNeighbor);

        Node<Integer> firstNeighborNeighbor = new Node<>(3);
        firstNeighbor.addNeighbour(firstNeighborNeighbor);
        firstNeighborNeighbor.addNeighbour(start);

        Node<Integer> secondNeighbor = new Node<>(4);
        start.addNeighbour(secondNeighbor);

        Optional<Node<Integer>> node = bfsSearch(3, start);
        Optional<Node<Integer>> node2 = dfsSearch(3, start);

        System.out.println("Found bfs: " + node.get().value);
        System.out.println("Found dfs: " + node2.get().value);

    }

    private static <T> Optional<Node<T>> dfsSearch(T value, Node<T> start) {
        Stack<Node<T>> stack = new Stack<>();
        Set<Node<T>> alreadyVisited = new HashSet<>();
        stack.push(start);
        Node<T> currentNode;

        while (!stack.isEmpty()) {
            currentNode = stack.pop();
            if (currentNode.value.equals(value)) {
                return Optional.of(currentNode);
            }
            alreadyVisited.add(start);
            for (Node<T> node : currentNode.neighbours) {
                if (!alreadyVisited.contains(node)) {
                    stack.push(node);
                }
            }
        }
        return Optional.empty();
    }


    // To find level, possibility to use a HashSet (key is node value, value is level)
    //   and add entry with level (+ 1 from parent level) in else condition
    private static <T> Optional<Node<T>> bfsSearch(T value, Node<T> start) {
        ArrayDeque<Node<T>> queue = new ArrayDeque<>();
        Set<Node<T>> alreadyVisited = new HashSet<>();
        queue.add(start);
        Node<T> currentNode;

        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            if (currentNode.value.equals(value)) {
                return Optional.of(currentNode);
            } else {
                alreadyVisited.add(currentNode);
                queue.addAll(currentNode.neighbours);
                queue.removeAll(alreadyVisited);
            }
        }
        return Optional.empty();
    }

}
