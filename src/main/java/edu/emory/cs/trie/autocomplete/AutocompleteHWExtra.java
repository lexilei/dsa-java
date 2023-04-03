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
import edu.emory.cs.trie.Trie;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteHWExtra extends Autocomplete<List<String>> {
    public AutocompleteHWExtra(String dict_file, int max) {
        super(dict_file, max);
        generateList(this.getRoot());
    }

    @Override
    public List<String> getCandidates(String prefix) {
        // Find the last letter of the prefix in the Trie
        TrieNode<List<String>> root = findLastLetter(getRoot(), prefix);

        // Get the list of words that match the prefix
        List<String> words = root.getValue();

        // Create a map to keep track of the frequency of each word
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            frequencyMap.put(word, 0);
        }

        // Traverse the Trie to update the frequency map for all words that start with the prefix
        traverseTrie(root, prefix, frequencyMap);

        // Sort the list of words based on frequency and recency
        words.sort((w1, w2) -> {
            int freqDiff = frequencyMap.get(w2) - frequencyMap.get(w1);
            if (freqDiff != 0) {
                return freqDiff;
            } else {
                return words.indexOf(w2) - words.indexOf(w1);
            }
        });

        // Fill in the rest of the candidates as in the original task
        List<String> candidates = new ArrayList<>();
        for (String word : words) {
            candidates.add(prefix.substring(0, prefix.length() - 1) + word);
        }
        return candidates.subList(0, getMax());
    }

    private void traverseTrie(TrieNode<List<String>> node, String prefix, Map<String, Integer> frequencyMap) {
        if (node.isEndState()) {
            for (String word : node.getValue()) {
                if (word.startsWith(prefix)) {
                    frequencyMap.put(word, frequencyMap.get(word) + 1);
                }
            }
        }
        for (TrieNode<List<String>> child : node.getChildrenMap().values()) {
            traverseTrie(child, prefix, frequencyMap);
        }
    }

    @Override
    public void pickCandidate(String prefix, String candidate) {
        // Start at the root of the Trie
        TrieNode<List<String>> root = this.getRoot();
        // Find the node corresponding to the last letter of the prefix
        root = findLastLetter(root, prefix);
        // Get the list of words associated with the node
        List<String> words = root.getValue();
        // Remove the candidate from the list of words
        String temp = candidate.substring(1);
        words.remove(temp);
        // Add the candidate to the beginning of the list of words
        words.add(0, temp);
    }

    public TrieNode<List<String>> findLastLetter(TrieNode<List<String>> root, String prefix) {
        Map<Character, TrieNode<List<String>>> childrenMap;
        // For each character in the prefix, traverse the Trie
        for (int i = 0; i < prefix.length(); i++) {
            // Get the children of the current node
            childrenMap = root.getChildrenMap();
            // Move to the child corresponding to the current character
            root = childrenMap.get(prefix.charAt(i));
        }
        // Return the node corresponding to the last character in the prefix
        return root;
    }

    public void generateList(TrieNode<List<String>> root) {
        // Get the children of the current node
        Map<Character, TrieNode<List<String>>> childrenMap = root.getChildrenMap();
        // Create an empty list to hold the words associated with the node
        List<String> wordList = new ArrayList<>();

        // Recursively generate the list of words for each child node
        for (Character key : childrenMap.keySet()) {
            TrieNode<List<String>> child = root.getChild(key);
            generateList(child);
            // For each word associated with the child node, add it to the word list
            for (String word : child.getValue()) {
                wordList.add(root.getKey() + word);
            }
        }

        // If the current node is a leaf node, add its key (i.e., the last character of a word) to the word list
        if (childrenMap.isEmpty() || root.isEndState()) {
            wordList.add(String.valueOf(root.getKey()));
        }

        // Sort the word list in lexicographic order
        Collections.sort(wordList, new AutocompleteHW.StringComparator());
        // Associate the sorted word list with the current node
        root.setValue(wordList);
    }

}