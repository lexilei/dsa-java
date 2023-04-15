package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;
import java.util.*;

public class AutocompleteHWExtra<T> extends Autocomplete<T> {
    public AutocompleteHWExtra(String dict_file, int max) {
        super(dict_file, max);
    }


    public TrieNode<T> getNode(String prefix) {
        return find(prefix);
    }

    @Override
    public List<String> getCandidates(String prefix) {
        if (prefix==""){
            return findfirst();
        }
        prefix = prefix.trim();
        TrieNode<T> node = getNode(prefix);

        Queue<String> queue = new PriorityQueue<>(Comparator.comparing(String::length).thenComparing(String::compareTo));
        if (node != null) {
            searchHelper(node, prefix, queue);
        }

        List<String> candidates = new ArrayList<>();
        TrieNode<T> temp = find(prefix);
        T memory = null;
        if (temp != null && (memory = temp.getValue()) != null) {
            candidates.addAll((List<String>)memory);
        }

        while (!queue.isEmpty() && candidates.size() < getMax()) {
            String candidate = queue.poll();
            if (!candidates.contains(candidate)) {
                candidates.add(candidate);
            }
        }
        return candidates;
    }

    private void searchHelper(TrieNode<T> node, String prefix, Queue<String> queue) {
        if (node.isEndState()) {
            queue.add(prefix);
        }

        for (Character key : node.getChildrenMap().keySet()) {
            TrieNode<T> child = node.getChild(key);
            searchHelper(child, prefix + key, queue);
        }
    }

    private List<String> findfirst() {
        List<String> candidates = new ArrayList<>();
        for (Character key : getRoot().getChildrenMap().keySet()) {
            candidates.add(key.toString());
            if (candidates.size()==getMax()) return candidates;
        }
        return candidates;
    }
    @Override
    public void pickCandidate(String prefix, String candidate) {
        if (candidate == null) return;

        prefix = prefix.trim().toLowerCase();
        TrieNode<T> node = find(prefix);

        if (node == null) {
            put(prefix, null);
            node = find(prefix);
            node.setEndState(false);
        }

        T memory = node.getValue();
        if (memory == null) {
            memory = (T) new ArrayList<String>();
            node.setValue(memory);
        }

        ((List<String>) memory).remove(candidate);
        ((List<String>) memory).add(0, candidate);
        node = find(candidate);
        T curValue = node == null ? null : node.getValue();
        put(candidate, curValue);
    }
}
