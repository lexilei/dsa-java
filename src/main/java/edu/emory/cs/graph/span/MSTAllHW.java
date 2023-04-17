
package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Edge;
import java.util.List;
import java.util.PriorityQueue;
import java.util.*;

/** @author Jinho D. Choi */
/***first of all, prim finds one spanning tree but I need to be able to use is, ask it, somehow find different ones each time.
 * maybe stack would be a good idea to exhaust all possibilities
 *
 * and once we find 1 minimum spanning tree, we know the total value for the spanning tree
 * is this helpful at all?
 *
 * 查重：
 * 当我们获得一个最小spanning tree的时候，我们可以吧他们的bijection也放进一个hashset or 里，然后这样去check？如果另一个minimum spanning tree的所有edges都在这个set里就throw？
 *
 *
 */



public class MSTAllHW implements MSTAll {
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        List<SpanningTree> result=getTrees(graph);

        return result;
    }

    public List<SpanningTree> getTrees(Graph graph){
        PriorityQueue<Edge> queue=new PriorityQueue<>();
        SpanningTree tree=new SpanningTree();
        Set<Integer> visited =new HashSet<>();
        SpanningTree testTree=new MSTKruskal().getMinimumSpanningTree(graph);
        double minWeight= testTree.getTotalWeight();
        List<SpanningTree> list=new ArrayList<>();

        add(queue,visited, graph,0);
        addE(queue, visited, tree, graph, list);

        ArrayList<String> arSum =new ArrayList<>();
        List<SpanningTree> resList=new ArrayList<>();
        for (SpanningTree sptree :list) {
//1.判断总权重
            if (sptree.getTotalWeight() != minWeight)
                continue;
//3.判断是否所有顶点都在
            HashSet<Integer> hs = new HashSet();
            StringBuilder sum = new StringBuilder();
            for (Edge e : sptree.getEdges()) {
                hs.add(e.getTarget());
                hs.add(e.getSource());
                sum.append(e.getTarget());
                sum.append(e.getSource());
            }
            if (hs.size() != graph.size())
                continue;
//2.边被重复用到的==> 对所有点所对应的数求和，若和相同则说明已存
            String str = new String(sum);
            boolean tt = false;
            for (String ss : arSum) {
                if
                (ss.equals(str)) {
                    tt = true;
                    break;
                }
            }
            if (tt) continue;
            else {
                arSum.add(str);
            }
            resList.add(sptree);
        }
        return resList;
    }

    private void addE(PriorityQueue<Edge> queue, Set<Integer> visited, SpanningTree tree, Graph graph, List<SpanningTree> list){
        while (!queue.isEmpty()){
            Edge[] edges =new Edge[graph.size() -1];
            Edge p= queue.poll();
            int source=p.getSource();
            int target = p.getTarget();
            while (visited.contains(source) &&visited.contains(target)&&!queue.isEmpty()){
                p=queue.poll();
                source=p.getSource();
                target = p.getTarget();
            }
            edges[0]=p;
            int i =1;
            PriorityQueue<Edge> newQueue =new PriorityQueue<>(queue);
            PriorityQueue<Edge> linshiQ =new PriorityQueue<>(queue);
            while(!linshiQ.isEmpty()){
                Edge poll=linshiQ.poll();
                double w=poll.getWeight();
                if (w==edges[0].getWeight()) edges[i++]=poll;

            }

            int j=0;
            for (j=0; j<i;j++){
                SpanningTree newTree= new SpanningTree(tree);
                Set<Integer> newVisited=new HashSet<>(visited);
                if (!visited.contains(edges[j].getSource())){
                        newTree. addEdge (edges [j]);
// if a spanning tree is found, break.
                        if (newTree.size()+1 == graph.size()){
                            list.add(newTree);
                            continue;
                        }
                        // add all connecting vertices from current vertex to the
                        add (newQueue, newVisited, graph, edges[j].getSource());
                        addE (newQueue, newVisited, newTree, graph, list);
//System. out.println("为啥tree变成newTree了...”）；

                }
            }

        }
    }

    private void add(PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph, int target) {
        visited.add(target);
        for (Edge edge : graph.getIncomingEdges(target)) {
            if (!visited.contains(edge.getSource()))
                queue.add(edge);
        }
    }

    static class Subset {
        int parent, rank;
    }
    public List<SpanningTree> getTrees2(Graph graph){
        List<SpanningTree> allMSTs = new ArrayList<>();

        //first of all kruskal it
        SpanningTree firstTree=new MSTKruskal().getMinimumSpanningTree(graph);
        double totalWeight=firstTree.getTotalWeight();
        List<Edge> firstTreeEdges= firstTree.getEdges();
        allMSTs.add(firstTree);
        for (Edge e : firstTreeEdges){

        }

        for
        PriorityQueue<Edge> queue=new PriorityQueue<>();
        List<Edge> alledges= graph.getAllEdges();

        SpanningTree tree=new SpanningTree();
        Set<Integer> visited =new HashSet<>();
        SpanningTree testTree=new MSTKruskal().getMinimumSpanningTree(graph);
        double minWeight= testTree.getTotalWeight();
        List<SpanningTree> list=new ArrayList<>();

        return null;
    }
}