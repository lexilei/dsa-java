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
package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Collections;

public class MSTAllHW implements MSTAll {

    // Implements the getMinimumSpanningTrees() method from the MSTAll interface.
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        List<SpanningTree> result = new ArrayList<>();
        // Calls the helper() method to find all minimum spanning trees.
        helper(graph, new SpanningTree(), new HashSet<>(), new PriorityQueue<>(), 0, result);
        if (result.isEmpty()) return result;
        // After all minimum spanning trees are found, finds the minimum weight and stores all minimum spanning trees with the minimum weight.
        List<SpanningTree> newResult = new ArrayList<>();
        ArrayList<Double> weights = new ArrayList<>();
        for (SpanningTree tree : result){
            weights.add(tree.getTotalWeight());
        }
        double min_weight = Collections.min(weights);
        for (int i = 0; i < weights.size(); i++) {
            if (weights.get(i) == min_weight){
                newResult.add(result.get(i));
            }
        }
        // Returns the list of minimum spanning trees with the minimum weight.
        return newResult;
    }

    // Recursive helper method to find all minimum spanning trees in the graph.
    private void helper(Graph graph, SpanningTree tree, Set<Integer> visited,
                        PriorityQueue<Edge> queue, int start, List<SpanningTree> result) {
        // If the new tree has size n-1, it's a minimum spanning tree.
        if (tree.size() + 1 == graph.size()) {
            result.add(new SpanningTree(tree));
            return;
        }
        // Adds incoming edges to the priority queue.
        add(queue, visited, graph, start);

        // Extracts edges from the priority queue and recursively calls the helper() method with a new tree containing the extracted edge.
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();

            if (!visited.contains(edge.getSource())) {
                SpanningTree newTree = new SpanningTree(tree);
                newTree.addEdge(edge);
                Set<Integer> newVisited = new HashSet<>(visited);
                PriorityQueue<Edge> newQueue = new PriorityQueue<>(queue);
                helper(graph, newTree, newVisited, newQueue, edge.getSource(), result);
            }
        }
    }

    // Adds incoming edges to the priority queue.
    private void add(PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph, int target) {
        visited.add(target);
        for (Edge edge : graph.getIncomingEdges(target)) {
            if (!visited.contains(edge.getSource()))
                queue.add(edge);
        }
    }

}
