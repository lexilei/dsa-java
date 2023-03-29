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
        Set<Integer> visited=new HashSet<>();
        count+=countingCycles(notVisited.poll(), new ArrayDeque<>(notVisited), new HashSet<>(visited),0);
//        while (!notVisited.isEmpty()) {
//            int target=notVisited.peek();
//            int a=count;
//            count+=countingCycles(notVisited.poll(), new ArrayDeque<>(notVisited), new HashSet<>(visited),target);
//            //visited.add(target);
//            System.out.println(target+" "+(count-a));
//        }
        return count;
    }
    private int countingCycles(int target, Deque<Integer> notVisited, Set<Integer> visited,int parent) {
        notVisited.remove(target);
        visited.add(target);
        if (notVisited.isEmpty()) return 0;
        int count=0;

        for (Edge edge : getIncomingEdges(target)) {

            int source = edge.getSource();
            if (source == parent) count++;
            else if (visited.contains(source)) continue;
            else {
                int a= countingCycles(source, notVisited, new HashSet<>(visited),parent);
                count+=a;
            }

        }
        return count;
    }





    public static void main(String[] args){
        GraphQuiz g = new GraphQuiz(5);
        g.setDirectedEdge(0, 1, 0);
        g.setDirectedEdge(0, 2, 0);
        g.setDirectedEdge(2, 1, 0);
        g.setDirectedEdge(2, 3, 0);
        g.setDirectedEdge(3, 4, 0);
        g.setDirectedEdge(4, 2, 0);
        System.out.println(g.numberOfCycles());//should be 1

//        GraphQuiz g = new GraphQuiz(5);
//        g.setDirectedEdge(0, 1, 0);
//        g.setDirectedEdge(0, 2, 0);
//        g.setDirectedEdge(2, 1, 0);
//        g.setDirectedEdge(2, 3, 0);
//        g.setDirectedEdge(3, 4, 0);
//        g.setDirectedEdge(4, 2, 0);
//        System.out.print(g.numberOfCycles());

//        GraphQuiz g = new GraphQuiz(5);
//        g.setDirectedEdge(0, 1, 0);
//        g.setDirectedEdge(1, 2, 0);
//        g.setDirectedEdge(1, 3, 0);
//        g.setDirectedEdge(2, 0, 0);
//        g.setDirectedEdge(3, 4, 0);
//        g.setDirectedEdge(4, 2, 0);
//        System.out.print(g.numberOfCycles());
    }
}