///*
// * Copyright 2020 Emory University
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package edu.emory.cs.trie.autocomplete;
//
//import java.util.List;
//import java.util.Collections;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Set;
//
//
///**
// * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
// */
//public class AutocompleteHW extends Autocomplete<List<String>> {
//    private String prefix;
//    private List<String> candidates;
//
//    public AutocompleteHW(String dict_file, int max) {
//        super(dict_file, max);
//        prefix = "";
//        candidates = new ArrayList<>();
//
//    }
//
//    @Override
//    public List<String> getCandidates(String prefix) {
//        if (prefix.equals(this.prefix)) {
//            return candidates;
//        }
//        this.prefix = prefix;
//        candidates.clear();
//        Set<String> words = super.getWordsStartingWithPrefix(prefix);
//        if (words.isEmpty()) {
//            return candidates;
//        }
//        List<String> sortedWords = new ArrayList<>(words);
//        Collections.sort(sortedWords);
//        Collections.sort(sortedWords, (w1, w2) -> w2.length() - w1.length());
//        Set<String> uniqueWords = new LinkedHashSet<>();
//        for (String word : sortedWords) {
//            if (uniqueWords.contains(word)) {
//                continue;
//            }
//            uniqueWords.add(word);
//            candidates.add(word);
//            if (candidates.size() == getMax()) {
//                break;
//            }
//        }
//        return candidates;
//    }
//
//    @Override
//    public void pickCandidate(String prefix, String candidate) {
//        // TODO: to be filled
//    }
//}