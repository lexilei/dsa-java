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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** @author Jinho D. Choi */

public class GraphQuiz extends Graph {
    public GraphQuiz(int size) {
        super(size);
    }

    public GraphQuiz(Graph g) {
        super(g);
    }


    public int numberOfCycles(){
        // Create a deque of all nodes in the graph
        Deque<Integer> notVisited = IntStream.range(0, size()).boxed().collect(Collectors.toCollection(ArrayDeque::new));
        // Get the first node in the deque
        int i = notVisited.getFirst();
        // Create a list of all points in the graph
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        HashSet<ArrayList<Integer>> all_trees = new HashSet<>();
        ArrayList<Integer> points = IntStream.range(0, size()).boxed().collect(Collectors.toCollection(ArrayList::new));

        // For each point in the graph, generate all possible paths from that point
        for (int point: points){
            ArrayList<Integer> start = new ArrayList<>();
            start.add(point);
            result.add(start);
            while (result.size()!=0){
                // Update the list of paths
                result = updatePaths(result);
                ArrayList<ArrayList<Integer>> result_copy = new ArrayList<>();
                // For each path, check if it's a tree or a cycle
                for (ArrayList<Integer> path: result){
                    HashSet<Integer> hashSet = new HashSet<>(path);
                    // If the path has a repeated node, it's a cycle
                    if (hashSet.size() < path.size()){
                        int n = path.indexOf(path.get(path.size()-1));
                        List<Integer> temp = path.subList(n, path.size()-1);
                        int y = temp.indexOf(Collections.min(temp));
                        List<Integer> sorted = new ArrayList<>(temp.subList(y, temp.size()));
                        List<Integer> list = new ArrayList<>(temp.subList(0, y));
                        sorted.addAll(list);
                        // hashSet = new HashSet<>(sorted);
                        all_trees.add((ArrayList<Integer>) sorted);
                    }
                    else{
                        // Otherwise, it's still a tree
                        result_copy.add(path);
                    }
                }
                result = result_copy;
            }
        }
        return all_trees.size();
    }

    // Given a list of paths, generates all possible paths by adding new nodes to the end of each path
    public ArrayList<ArrayList<Integer>> updatePaths(ArrayList<ArrayList<Integer>> result){
        int result_length = result.size();
        ArrayList<ArrayList<Integer>> result_copy = new ArrayList<>();
        for (int j = 0; j <= result.size()-1; j++){
            int last = result.get(j).get(result.get(j).size()-1);
            ArrayList<Edge> edges = (ArrayList<Edge>) this.getIncomingEdges(last);
            if (edges.size() > 1) {
                ArrayList<Integer> copied_lists = null;
                for (int k = 0; k <= edges.size()-1; k++) {
                    copied_lists = (ArrayList<Integer>) result.get(j).clone();
                    copied_lists.add(edges.get(k).getSource());
                    result_copy.add(copied_lists);
                }
            }
            else{
                if (edges.size() == 1) {
                    ArrayList<Integer> copied_lists = null;
                    copied_lists = (ArrayList<Integer>) result.get(j).clone();
                    copied_lists.add(edges.get(0).getSource());
                    result_copy.add(copied_lists);
                }
            }
        }
        result = result_copy;
        return result;
    }
}
