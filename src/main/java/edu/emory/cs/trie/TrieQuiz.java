package edu.emory.cs.trie;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

public class TrieQuiz extends Trie<Integer> {

    /**
     * PRE: this trie contains all country names as keys and their unique IDs as values
     * (e.g., this.get("United States") -> 0, this.get("South Korea") -> 1).
     *
     * @param input the input string in plain text
     *              (e.g., "I was born in South Korea and raised in the United States").
     * @return the list of entities (e.g., [Entity(14, 25, 1), Entity(44, 57, 0)]).
     */
    List<Entity> getEntities(String input) {
        Map<String, Integer> countryMap = new HashMap<>();
        this.makeCountryMap("", this.getRoot(), countryMap);

        List<Entity> answer = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : countryMap.entrySet()) {
            String country = entry.getKey();
            int id = entry.getValue();
            if (input.contains(country)) {
                int beginIndex = input.indexOf(country);
                int endIndex = beginIndex + country.length();
                answer.add(new Entity(beginIndex, endIndex, id));
            }
        }

        return answer;
    }


    public void makeCountryMap(String country, TrieNode<Integer> root, Map<String, Integer> ans) {
        if (root.getValue() != null) {
            country = country + root.getKey();
            ans.put(country, root.getValue());
            return;
        }
        Map<Character, TrieNode<Integer>> children = root.getChildrenMap();
        if (root.getKey() != 0) {
            country = country + root.getKey();
        }
        Set<Character> keySet = children.keySet();
        for (Character one : keySet) {
            TrieNode<Integer> child = children.get(one);
            makeCountryMap(country, child, ans);
        }
    }
}