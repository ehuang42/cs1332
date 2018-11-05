import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Le-En Huang
 * @userid ehuang42
 * @GTID 903303142
 * @version 2.0
 */
public class GraphAlgorithms {

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("At least one input is empty.");
        }

        if (!(graph.getVertices().contains(start))) {
            throw new IllegalArgumentException("The starting "
                    + "vertex not in the graph.");
        }
        List<Vertex<T>> visited = new ArrayList<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();


        dfs(start, visited, visitedSet, graph);
        return visited;

    }

    /**
     * Helper method of depth first search.
     *
     * @param <T> the generic typing of the data
     * @param curr the current vertex being put on call stack
     * @param graph the graph to search through
     * @param visited the list of visited vertices
     * @param visitedSet the set of visited vertices
     */
    private static <T> void dfs(Vertex<T> curr, List<Vertex<T>> visited,
                        Set<Vertex<T>> visitedSet, Graph<T> graph) {
        if (visitedSet.contains(curr)) {
            return;
        }

        visited.add(curr);
        visitedSet.add(curr);

        for (int i = 0; i < graph.getAdjList().get(curr).size(); i++) {
            Vertex<T> ver = graph.getAdjList().get(curr).get(i).getVertex();
            dfs(ver, visited, visitedSet, graph);
        }
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as it's efficient.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start index representing which vertex to start at (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every other node
     *         in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                      Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("At least one input is empty.");
        }

        if (!(graph.getVertices().contains(start))) {
            throw  new IllegalArgumentException("The starting "
                    + "vertex isn't in the graph.");
        }

        Map<Vertex<T>, Integer> paths = new HashMap<>();
        Queue<VertexDistance<T>> pq = new PriorityQueue<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();


        for (Vertex<T> el: graph.getVertices()) {
            paths.put(el, Integer.MAX_VALUE);
        }
        paths.put(start, 0);
        pq.add(new VertexDistance<>(start, 0));

        while (!pq.isEmpty()
                && visitedSet.size() < graph.getVertices().size()) {
            VertexDistance<T> curr = pq.remove();
            if (!(visitedSet.contains(curr.getVertex()))) {
                visitedSet.add(curr.getVertex());
                paths.put(curr.getVertex(), curr.getDistance());
                for (int i = 0;
                     i < graph.getAdjList().get(curr.getVertex()).size(); i++) {
                    VertexDistance<T> ver =
                            graph.getAdjList().get(curr.getVertex()).get(i);
                    if (paths.get(ver.getVertex()) == Integer.MAX_VALUE) {
                        pq.add(new VertexDistance<>(ver.getVertex(),
                                curr.getDistance() + ver.getDistance()));
                    }
                }
            }
        }
        return paths;

    }


    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the {@code DisjointSet} and {@code DisjointSetNode} classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops into the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("At least one input is empty.");
        }

        Queue<Edge<T>> pq = new PriorityQueue<>();
        DisjointSet<Vertex<T>> dj = new DisjointSet<>(graph.getVertices());
        Set<Edge<T>> mst = new HashSet<>();

        for (Edge<T> ed: graph.getEdges()) {
            pq.add(ed);
        }

        while (!pq.isEmpty()
                && mst.size() != 2 * (graph.getVertices().size() - 1)) {
            Edge<T> edge = pq.remove();
            if (dj.find(edge.getU()) != dj.find(edge.getV())) {

                mst.add(edge);
                //add backwards
                Edge<T> backwards =
                        new Edge<>(edge.getV(), edge.getU(), edge.getWeight());
                mst.add(backwards);
                //union (u,v)
                dj.union(edge.getU(), edge.getV());
            }
        }

        if (mst.size() == 2 * (graph.getVertices().size() - 1)) {
            return mst;
        } else {
            return null;
        }
    }
}