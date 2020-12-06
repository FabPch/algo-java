package org.algo.graph.spanning;

import org.algo.graph.Edge;

import java.util.*;

public class SpanningGraph {
    public int verticeNumber;
    public int edgeNumber;
    public Edge[] edges;

    public SpanningGraph(int verticeNumber, int edgeNunmber) {
        this.verticeNumber = verticeNumber;
        this.edgeNumber = edgeNunmber;
        this.edges = new Edge[edgeNunmber];
        for (int i=0; i<edgeNunmber; i++) {
            edges[i] = new Edge();
        }
    }

    private int find(Subset[] subsets, int vertice) {
        if (subsets[vertice].parent != vertice) {
            subsets[vertice].parent = find(subsets, subsets[vertice].parent);
        }
        return subsets[vertice].parent;
    }

    private void union(Subset[] subsets, int srcParent, int dstParent) {
        if (subsets[srcParent].rank > subsets[dstParent].rank) {
            subsets[dstParent].parent = srcParent;
        } else if (subsets[srcParent].rank < subsets[dstParent].rank){
            subsets[srcParent].parent = dstParent;
        } else {
            subsets[srcParent].parent = dstParent;
            subsets[dstParent].rank++;
        }
    }

    public Edge[] kruskalMST() {
        // On initialise le tableau de résultats et on trie les edges
        Edge[] results = new Edge[verticeNumber-1];
        Arrays.sort(edges);

        // On créé tous les sous-ensemble (rank initial à 0)
        Subset[] subsets = new Subset[verticeNumber];
        for (int i = 0; i < verticeNumber; i++) {
            subsets[i] = new Subset();
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }

        int i = 0;
        int resultNumber = 0;
        // On boucle jusqu'à remplir le tableau de résultats.
        while (resultNumber < verticeNumber-1) {
            Edge edge = edges[i];
            // S'il ne forme pas un cercle en l'ajoutant au résultat
            // On l'ajoute:
            //   D'abord, on trouve le parent de chaque vertice
            //   Ensuite, on vérifie que ce n'est pas le même, sinon on continue
            int srcParent = find(subsets, edge.src);
            int dstParent = find(subsets, edge.dst);

            // Si les parents sont différents, c'est que l'ajout de l'Edge ne fomre pas de cercle.
            // On peut donc l'ajouter aux résultats et on fait l'union des susbsets de chaque vertice.
            if (srcParent != dstParent) {
                results[resultNumber] = edge;
                union(subsets, srcParent, dstParent);
                resultNumber++;
            }
            i++;
        }
        return results;
    }

    public Edge[] primMethod() {
        // On initialise le tableau de résultats et on trie les edges
        Edge[] results = new Edge[verticeNumber-1];
        Set<Integer> vertices = new HashSet<>();
        List<Edge> tmpList = new ArrayList<>();
        PriorityQueue<Edge> queue = new PriorityQueue();
        queue.addAll(Arrays.asList(edges));

        int resultNumber = 0;
        while (resultNumber < verticeNumber-1) {
            Edge edge = queue.poll();
            // On ajoute si ça ne forme pas un cercle: s'il y a seulement un des deux vertices dans les résultats
            //   On remet les tmp dans la priority queue
            // Sinon on met le edge en tmp, et on passe au suivant.
            if ((vertices.contains(edge.src) && !vertices.contains(edge.dst)
                    || vertices.contains(edge.dst) && !vertices.contains(edge.src))
                    || resultNumber == 0) {
                results[resultNumber] = edge;
                vertices.add(edge.src);
                vertices.add(edge.dst);
                queue.addAll(tmpList);
                tmpList.clear();
                resultNumber++;
            } else {
                tmpList.add(edge);
            }
        }

        return results;
    }

    // Find shortest path (even with negative weights) if negative graph circles do not exist.
    public Map<Integer, Integer> bellmanFord(int src) {
        // Create a Map<nodeNumber, distanceFromSource> and initiate all value to Max Integer except source value (0)
        Map<Integer, Integer> results = new HashMap<>();
        results.put(src, 0);

        // Get max value for initialisation, set this value to every node
        int max = 0;
        for (Edge edge : edges) {
            max += edge.weigth;
        }

        for (int i=0; i<verticeNumber; i++) {
            if (i != src) {
                results.put(i, max);
            }
        }

        // Iterate on verticeNumber-1 and process to relaxation (calculate new distance for every node) until
        //   there is no more change.
        int i=0;
        boolean relaxed;
        do {
            relaxed = false;
            for (Edge edge : edges) {
                // If distance from source (node to evaluate) is bigger than distance from source (previous node) + weight
                if (results.get(edge.dst) > (results.get(edge.src) + edge.weigth)) {
                    results.put(edge.dst, results.get(edge.src) + edge.weigth);
                    relaxed = true;
                }
            }
            i++;
        } while (i<verticeNumber-1 && relaxed);

        return results;
    }

}
