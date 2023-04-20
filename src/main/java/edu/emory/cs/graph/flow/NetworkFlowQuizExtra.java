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
public class NetworkFlowQuizExtra {
    /**
     * Using breadth-first traverse.
     * @param graph  a directed graph.
     * @param source the ource vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> result= new HashSet<Subgraph>();
        Subgraph sub;
        helper(graph, new Subgraph(), source, target, result);

//        for (Subgraph a: result) {
//            System.out.println(a);
//        }
        System.out.println(result.size());
        return result;
    }

    private void helper(Graph graph,  Subgraph sub, int source, int target,Set<Subgraph> result) {
        //if (source == target) return sub;
        Subgraph tmp;

        for (Edge edge : graph.getIncomingEdges(target)) {
            if (sub.contains(edge.getSource())) continue;    // cycle
            tmp = new Subgraph(sub);
            tmp.addEdge(edge);
            tmp = helper2(graph,  tmp, source, edge.getSource(), result);
            if (tmp != null) result.add(tmp);
        }

    }
    private Subgraph helper2(Graph graph,  Subgraph sub, int source, int target,Set<Subgraph> result) {
        if (source == target) return sub;
        Subgraph tmp;

        for (Edge edge : graph.getIncomingEdges(target)) {
            if (sub.contains(edge.getSource())) continue;    // cycle
            tmp = new Subgraph(sub);
            tmp.addEdge(edge);
            tmp = helper2(graph,  tmp, source, edge.getSource(), result);
            return tmp;
        }

        return null;
    }
}