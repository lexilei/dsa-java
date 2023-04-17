package edu.emory.cs.graph.span;

import java.util.*;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.set.DisjointSet;

public class MSTAllHW implements MSTAll {
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        if (graph.size()==0)
        {   List<SpanningTree> res = new ArrayList<>();
            SpanningTree empty = new SpanningTree();
            res.add(empty);
            System.out.println(res.size());
            return res;
        }

        List<Edge> allEdges = new ArrayList<>(graph.getAllEdges());
        if(!isCompleteGraph(graph))
            quicksort(allEdges);
        else
            return generateAllSpanningTrees(graph, graph.size());
        List<SpanningTree> forest = new ArrayList<>();

        SpanningTree dummy = getMinimumSpanningTree(graph);


        if (graph.size() == 1) {
            forest.add(dummy);
            return forest;
        }

        double mstWeight = dummy.getTotalWeight();
        findSpanningTrees(allEdges, graph.size(), new SpanningTree(), 0, new UnionFind(graph.size()), forest, mstWeight, 0);
        List<String> check = new ArrayList<>();
        for (int i = forest.size() - 1; i >= 0; i--) {
            String t = forest.get(i).getUndirectedSequence();
            if (!check.contains(t)) {
                check.add(t);
            } else {
                forest.remove(i);
            }
        }

        System.out.println(forest.size());

        return forest;
    }

    private void findSpanningTrees(List<Edge> edges, int vertex, SpanningTree currentTree, int edgeIndex, UnionFind uf,
                                   List<SpanningTree> forest, double mstWeight, double totalWeight) {

        double nextWeight;

        if (totalWeight > mstWeight) {
            return;
        }

        if (currentTree.size() == vertex - 1 && visitedAll(currentTree, vertex)) {
            forest.add(new SpanningTree(currentTree));
            return;
        }

        for (int i = edgeIndex; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            if (uf.union(edge.getSource(), edge.getTarget())) {
                nextWeight = totalWeight + edge.getWeight();
                if(nextWeight <= mstWeight) {
                    currentTree.addEdge(edge);
                    UnionFind ufNext = new UnionFind(uf);
                    totalWeight += edge.getWeight();
                    findSpanningTrees(edges, vertex, currentTree, i + 1, ufNext, forest, mstWeight, totalWeight);
                    totalWeight -= currentTree.getEdges().remove(currentTree.size() - 1).getWeight();
                    uf.undo();
                }
            }
        }
    }

    public List<SpanningTree> generateAllSpanningTrees(Graph graph, int n) {
        List<SpanningTree> trees = new ArrayList<>();
        if(n <= 2)
        {
            SpanningTree tree = getMinimumSpanningTree(graph);
            trees.add(tree);
            return trees;
        }
        int totalTrees = (int)Math.pow(n, n-2);
        Edge edge;
        for (int i = 0; i < totalTrees; i++) {
            long prufer = getPruferSequence(n, i);
            SpanningTree tree = constructSpanningTree(prufer, n);
            boolean flag = false;
            edge = new Edge(0, 0);
            for(Edge e: tree.getEdges())
            {
                if (e.getSource() == e.getTarget())
                {
                    edge = new Edge(e);
                    flag = true;
                    break;
                }
            }
            if(flag) {
                SpanningTree tree2 = new SpanningTree();
                List<Edge> edges = tree.getEdges();
                for(Edge e : edges)
                {
                    if(!(e.getTarget() == edge.getTarget() && e.getSource() == edge.getSource()))
                        tree2.addEdge(e);
                }
                trees.add(tree2);
            }
            else
                trees.add(tree);
        }
        return trees;
    }

    private long getPruferSequence(int n, int k) {
        long prufer = 0;
        for (int i = n-3; i >= 0; i--) {
            prufer |= (long) (k % n + 1) << (i * 5);
            k /= n;
        }
        return prufer;
    }

    private SpanningTree constructSpanningTree(long prufer, int n) {
        int[] degree = new int[n + 1];
        for (int i = 0; i < n-2; i++) {
            degree[(int)((prufer >> (i * 5)) & 0x1f)]++;
        }

        int[] ptr = new int[n + 1];
        for (int i = 1; i <= n; i++) ptr[i] = 1;

        SpanningTree tree = new SpanningTree();
        for (int i = 0; i < n-2; i++) {
            int v = (int)((prufer >> (i * 5)) & 0x1f);
            int u = 0;
            for (int j = ptr[v]; j <= n; j++) {
                if (degree[j] == 0) {
                    u = j;
                    ptr[v] = j+1;
                    break;
                }
            }
            degree[v]--;
            degree[u]--;
            tree.addEdge(new Edge(v-1, u-1, 1));
        }

        for (int i = 1; i <= n; i++) {
            if (degree[i] == 0) {
                int u = 0;
                for (int j = ptr[i]; j <= n; j++) {
                    if (degree[j] == 0) {
                        u = j;
                        ptr[i] = j+1;
                        break;
                    }
                }
                degree[i]--;
                degree[u]--;
                tree.addEdge(new Edge(i -1, u-1, 1));
            }
        }
        return tree;
    }

    public static void quicksort(List<Edge> edges) {
        quicksort(edges, 0, edges.size() - 1);
    }

    private static void quicksort(List<Edge> edges, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(edges, left, right);
            quicksort(edges, left, pivotIndex - 1);
            quicksort(edges, pivotIndex + 1, right);
        }
    }

    private static int partition(List<Edge> edges, int left, int right) {
        Edge pivot = edges.get(right);
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (edges.get(j).getWeight() <= pivot.getWeight()) {
                i++;
                swap(edges, i, j);
            }
        }
        swap(edges, i + 1, right);
        return i + 1;
    }

    private static void swap(List<Edge> edges, int i, int j) {
        Edge temp = edges.get(i);
        edges.set(i, edges.get(j));
        edges.set(j, temp);
    }



    public void remove(int i, SpanningTree currentTree) {
        currentTree.getEdges().remove(i);
    }

    static class UnionFind {
        int[] parent;
        int[] rank;
        List<int[]> history;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            history = new ArrayList<>();
            Arrays.fill(rank, 1);

            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public UnionFind(UnionFind uf) {
            this.parent = uf.parent.clone();
            this.rank = uf.rank.clone();
            this.history = new ArrayList<>(uf.history);
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) {
                return false;
            }

            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
                history.add(new int[] { rootY, rootX });
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
                history.add(new int[] { rootX, rootY });
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
                history.add(new int[] { rootY, rootX });
            }

            return true;
        }

        public void undo() {
            if (!history.isEmpty()) {
                int[] lastOperation = history.remove(history.size() - 1);
                int x = lastOperation[0];
                int y = lastOperation[1];

                parent[x] = x;

                if (rank[x] == rank[y]) {
                    rank[y]--;
                }
            }
        }
    }

    public boolean isCompleteGraph(Graph graph) {
        int n = graph.size();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (!hasEdge(graph, i, j)) {
                    return false;
                }
            }
        }

        double weight = 0;
        for(Edge e : graph.getAllEdges())
            weight += e.getWeight();
        return weight / (graph.size() - 1) == graph.size();
    }


    public boolean hasEdge(Graph graph, int u, int v) {
        for (Edge edge : graph.getAllEdges()) {
            if ((edge.getSource() == u && edge.getTarget() == v) || (edge.getSource() == v && edge.getTarget() == u)) {
                return true;
            }
        }
        return false;
    }


    public SpanningTree getMinimumSpanningTree(Graph graph) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(graph.getAllEdges());
        DisjointSet forest = new DisjointSet(graph.size());
        SpanningTree tree = new SpanningTree();

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();

            if (!forest.inSameSet(edge.getTarget(), edge.getSource())) {
                tree.addEdge(edge);

                // a spanning tree is found
                if (tree.size() + 1 == graph.size())
                    break;
                // merge forests
                forest.union(edge.getTarget(), edge.getSource());
            }
        }

        return tree;
    }

    private boolean visitedAll(SpanningTree currentTree, int vertices) {
        List<Integer> src = new ArrayList<>();
        List<Integer> dest = new ArrayList<>();

        for (Edge e : currentTree.getEdges()) {
            src.add(e.getSource());
            dest.add(e.getTarget());
        }

        List<Integer> check = new ArrayList<>(src);

        for (int a : dest) {
            if (!check.contains(a)) {
                check.add(a);
            }
        }

        return check.size() == vertices;

    }
}

