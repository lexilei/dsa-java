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
import edu.emory.cs.set.DisjointSet;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** @author Jinho D. Choi */
public class NetworkFlowQuizExtra {
    /**
     * Using breadth-first traverse.
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> augmentingPaths = new HashSet<>();
        Queue<List<Edge>> incomplete_path = new LinkedList<>();
        List<Deque<Edge>> outgoingEdgesAll = graph.getOutgoingEdges();

        for (Edge edge : outgoingEdgesAll.get(source)) {
            incomplete_path.add(Collections.singletonList(edge));
        }

        while (!incomplete_path.isEmpty()) {
            List<Edge> currentPath = incomplete_path.poll();
            int last_vertex = currentPath.get(currentPath.size() - 1).getTarget();

            if (last_vertex == target) {
                Subgraph path = new Subgraph();
                for (Edge edge : currentPath) {
                    path.addEdge(edge);
                }
                augmentingPaths.add(path);
            } else {
                boolean[] visited = new boolean[graph.size()];
                for (Edge edge : currentPath) {
                    visited[edge.getSource()] = true;
                    visited[edge.getTarget()] = true;
                }

                Deque<Edge> outgoingEdges = outgoingEdgesAll.get(last_vertex);
                for (Edge edge : outgoingEdges) {
                    if (!visited[edge.getTarget()]) {
                        List<Edge> new_path = new ArrayList<>(currentPath);
                        new_path.add(edge);
                        incomplete_path.add(new_path);
                    }
                }
            }
        }
        return augmentingPaths;
    }

}