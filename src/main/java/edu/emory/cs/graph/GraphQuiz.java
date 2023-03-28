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
package edu.emory.cs.graph;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** @author Jinho D. Choi */
public class GraphQuiz extends Graph {
    public GraphQuiz(int size) { super(size); }
    public GraphQuiz(Graph g) { super(g); }

    /** @return the total number of cycles in this graph. */
    public int numberOfCycles() {
        int count=0;
        Deque<Integer> notVisited = IntStream.range(0, size()).boxed().collect(Collectors.toCollection(ArrayDeque::new));

        while (!notVisited.isEmpty()) {
            if (containsCycleAux(notVisited.poll(), notVisited, new HashSet<>()))
                count++;
        }

        return count;
    }
    private boolean containsCycleAux(int target, Deque<Integer> notVisited, Set<Integer> visited) {
        notVisited.remove(target);
        visited.add(target);

        for (Edge edge : getIncomingEdges(target)) {
            if (visited.contains(edge.getSource()))
                return true;

            if (containsCycleAux(edge.getSource(), notVisited, new HashSet<>(visited)))
                return true;
        }

        return false;
    }

//    public static void main(String[] args){
//        GraphQuiz graph = new GraphQuiz(5);
//
//        graph.setDirectedEdge(0, 1, 1);
//        graph.setDirectedEdge(1, 0, 1);
//        graph.setDirectedEdge(2, 1, 1);
//        graph.setDirectedEdge(2, 3, 1);
//        graph.setDirectedEdge(3, 4, 1);
//        graph.setDirectedEdge(4, 2, 1);
//        System.out.print(graph.numberOfCycles());
//    }
}