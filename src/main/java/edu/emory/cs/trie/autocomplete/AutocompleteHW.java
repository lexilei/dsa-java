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

package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;

import java.util.*;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteHW extends Autocomplete<List<String>> {
    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {
        prefix = prefix.trim().toLowerCase();
        TrieNode<List<String>> node = find(prefix);
        // Find all the candidates that start with the given prefix using DFS
        Queue<String> queue = new PriorityQueue<>(Comparator.comparing(String::length).thenComparing(String::compareTo));
        if (node != null) {
            searchHelper(node, prefix, queue);
        }
        // Add the candidates that exactly match the prefix from the Trie
        List<String> candidates = new ArrayList<>();
        TrieNode<List<String>> temp = find(prefix);
        List<String> memory = null;
        if (temp!=null && (memory = temp.getValue()) != null) {
            candidates.addAll(memory);
        }
        // Add the candidates that were found by DFS, up to the maximum number allowed
        while (!queue.isEmpty() && candidates.size() < getMax()) {
            String candidate = queue.poll();
            if (!candidates.contains(candidate)) {
                candidates.add(candidate);
            }
        }
        return candidates;
    }


    private void searchHelper(TrieNode<List<String>> node, String prefix, Queue<String> queue) {
        if (node.isEndState()) {
            queue.add(prefix);
        }
        for (Character key : node.getChildrenMap().keySet()) {
            TrieNode<List<String>> child = node.getChild(key);
            searchHelper(child, prefix + key, queue);
        }
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        if (candidate == null) return;
        prefix = prefix.trim().toLowerCase();
        TrieNode<List<String>> node = find(prefix);
        if (node == null) {
            put(prefix, new ArrayList<>());
            node = find(prefix);
            node.setEndState(false);
        }
        List<String> memory = node.getValue();
        if (memory == null) {
            memory = new ArrayList<>();
            node.setValue(memory);
        }
        memory.remove(candidate);
        memory.add(0, candidate);
        node = find(candidate);
        List<String> curValue = node == null ? null : node.getValue();
        put(candidate, curValue);
    }
    }