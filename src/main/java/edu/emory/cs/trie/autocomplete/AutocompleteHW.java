package edu.emory.cs.trie.autocomplete;
import edu.emory.cs.trie.TrieNode;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteHW extends Autocomplete<List<String>> {
    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
        generateList(this.getRoot());
    }

    @Override
    public List<String> getCandidates(String prefix) {
        // Start at the root of the Trie
        TrieNode<List<String>> root = this.getRoot();
        // Find the node corresponding to the last letter of the prefix
        root = findLastLetter(root, prefix);

        // Get the list of words associated with the node
        List<String> words = root.getValue();
        // Create an empty list to hold the results
        List<String> result = new ArrayList<>();

        // For each word in the list, append it to the prefix (excluding the last character)
        for (String word : words) {
            result.add(prefix.substring(0, prefix.length() - 1) + word);
        }

        // Return the first getMax() results
        return result.subList(0, getMax());
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
        Collections.sort(wordList, new StringComparator());
        // Associate the sorted word list with the current node
        root.setValue(wordList);
    }

    public static class StringComparator implements Comparator<String> {
        public int compare(String o1, String o2) {
            // Sort by length first, then lexicographically
            if (o1.length() != o2.length()) {
                return o1.length() - o2.length();
            } else {
                return o1.compareTo(o2);
            }
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


}