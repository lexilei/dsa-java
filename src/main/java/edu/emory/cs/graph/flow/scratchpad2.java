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

import java.util.*;

/** @author Jinho D. Choi */
public class scratchpad2 {
    /**
     * Using breadth-first traverse.
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> augmentingPaths = new HashSet<>();
        Queue<List<Edge>> incomplete_path = new LinkedList<>();   //store intermediate paths during the BFS traversal
        List<Deque<Edge>> outgoingEdgesAll = graph.getOutgoingEdges();  //outgoing edges for all vertices

        for (Edge edge : outgoingEdgesAll.get(source)) {    //edges from source vertex, get on the move
            incomplete_path.add(Collections.singletonList(edge));
        }

        while (!incomplete_path.isEmpty()) {    //starts BFS traversal. stop when queue is empty.
            List<Edge> currentPath = incomplete_path.poll();    //dequeue next possible path
            int current = currentPath.get(currentPath.size() - 1).getTarget();  // Get target of the last edge in the current path

            //If the target vertex of the last edge in the current path is the target vertex,
            // create a new Subgraph, add all edges from the current path to the new subgraph,
            // and add the new subgraph to the set of augmenting paths.
            if (current == target) {
                Subgraph path = new Subgraph();
                for (Edge edge : currentPath) {
                    path.addEdge(edge);
                }
                augmentingPaths.add(path);
            } else {
                // else create visited array to keep track of visit status.
                boolean[] visited = new boolean[graph.size()];
                for (Edge edge : currentPath) {
                    visited[edge.getSource()] = true;
                    visited[edge.getTarget()] = true;
                }

                Deque<Edge> outgoingEdges = outgoingEdgesAll.get(current);
                for (Edge edge : outgoingEdges) {
                    if (!visited[edge.getTarget()]) {
                        List<Edge> newPath = new ArrayList<>(currentPath);
                        newPath.add(edge);
                        incomplete_path.add(newPath);
                    }
                }
            }
        }
        return augmentingPaths;
    }
}