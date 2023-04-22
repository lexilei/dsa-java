/*
 * Copyright 2020 Emory University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.HashSet;
import java.util.Set;

/** @author Jinho D. Choi */
public class NetworkFlowQuiz {
    //only s and t, with no edge and with one edge
    /**
     * Using depth-first traverse.
     * @param graph  a directed graph.
     * @param source the ource vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> result= new HashSet<Subgraph>();
        helper(graph, new Subgraph(), source, target, result);

        return result;
    }

    private void helper(Graph graph,  Subgraph sub, int source, int target,Set<Subgraph> result) {
        if (source == target) result.add(sub);
        Subgraph tmp;

        for (Edge edge : graph.getIncomingEdges(target)) {
            if (sub.contains(edge.getSource())) continue;    // cycle
            tmp = new Subgraph(sub);
            tmp.addEdge(edge);
            helper(graph,  tmp, source, edge.getSource(), result);;
        }

    }
    public static Graph getGraph0() {
        Graph graph = new Graph(6);
        int s = 0, t = 5;

        graph.setDirectedEdge(s, 1, 4);
        graph.setDirectedEdge(s, 2, 2);
        graph.setDirectedEdge(1, 3, 3);
        graph.setDirectedEdge(2, 3, 2);
        graph.setDirectedEdge(2, 4, 3);
        graph.setDirectedEdge(3, 2, 1);
        graph.setDirectedEdge(3, t, 2);
        graph.setDirectedEdge(4, t, 4);

        return graph;
    }

    public static Graph getGraph1() {
        Graph graph = new Graph(4);
        int s = 0, t = 3;

        graph.setDirectedEdge(2, 3, 1);
        graph.setDirectedEdge(1, 3, 1);
        graph.setDirectedEdge(1, 2, 1);
        graph.setDirectedEdge(s, 2, 1);
        graph.setDirectedEdge(s, 1, 1);

        return graph;
    }

    public static Graph getGraph2() {
        Graph graph = new Graph(2);
        int s = 0, t = 1;

        graph.setDirectedEdge(0, 1, 1);

        return graph;
    }

    public static Graph getGraph3() {
        Graph graph = new Graph(5);
        int s = 0, t = 1;

        graph.setDirectedEdge(0, 1, 1);
        graph.setDirectedEdge(1, 3, 1);
        graph.setDirectedEdge(3, 2, 1);
        graph.setDirectedEdge(2, 4, 1);


        return graph;
    }
    public static Graph getGraph4() {
        Graph graph = new Graph(6);
        int s = 0, t = 5;

        graph.setDirectedEdge(s, 1, 4);
        graph.setDirectedEdge(s, 2, 2);
        graph.setDirectedEdge(1, 3, 3);
        graph.setDirectedEdge(2, 3, 2);
        graph.setDirectedEdge(2, 4, 3);
        graph.setDirectedEdge(3, 2, 1);
        graph.setDirectedEdge(3, t, 2);
        graph.setDirectedEdge(4, t, 4);

        return graph;
    }

    public static void main(String[] args){
        NetworkFlowQuiz mfa = new NetworkFlowQuiz();
        Graph one= getGraph4();
        Set<Subgraph> a=mfa.getAugmentingPaths(one,0,one.size()-1);
        for(Subgraph e:a){
            System.out.println(e);
            System.out.println("next");
        }
        System.out.println("\n");
    }


}