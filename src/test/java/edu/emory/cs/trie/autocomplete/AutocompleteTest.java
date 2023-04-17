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

//for the dict test this is good enough
//still wanna know if I search for empty string, what should I return

package edu.emory.cs.trie.autocomplete;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteTest {
    static class Eval {
        int correct = 0;
        int total = 0;
    }

    @Test
    public void test() {
        final String dict_file = "src/main/resources/dict.txt";
        final int max = 20;

        Autocomplete<?> ac = new AutocompleteHWExtra(dict_file, max);
        Eval eval = new Eval();
        testAutocomplete(ac, eval);
    }

    private void testAutocomplete(Autocomplete<?> ac, Eval eval) {
        String prefix;
        List<String> expected;
        String candidate;

        //type 1 regular search
        //passed
        prefix = "a";
        expected = List.of("zoism", "zoist", "zoisite", "zoistic", "zoisites", "zoisitization");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zois";
        expected = List.of("zoism", "zoist", "zoisite", "zoistic", "zoisites", "zoisitization");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "wu";
        expected = List.of("wu", "wud", "wun", "wup", "wur", "wus", "wut", "wudu", "wuff", "wugg", "wulk", "wull", "wush", "wusp", "wuss", "wust", "wuzu", "wudge", "wunna", "wurly");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = " wu      ";
        expected = List.of("wu", "wud", "wun", "wup", "wur", "wus", "wut", "wudu", "wuff", "wugg", "wulk", "wull", "wush", "wusp", "wuss", "wust", "wuzu", "wudge", "wunna", "wurly");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "lzh";
        candidate= "lzhhh";
        ac.pickCandidate(prefix, candidate);
        expected = List.of(candidate);
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "lzhhh";
        expected = List.of(candidate);
        testGetCandidates(ac, eval, prefix, expected);

        //type 2 with candidate which picks
        prefix = "jinho";
        candidate= "jinho";
        ac.pickCandidate(prefix, candidate);
        expected = List.of(candidate);
        testGetCandidates(ac, eval, prefix, expected);

        //when we don't pick
        prefix = "nonsubsid";
        expected = List.of("nonsubsidy", "nonsubsidies", "nonsubsiding", "nonsubsidiary", "nonsubsididies", "nonsubsidiaries");
        testGetCandidates(ac, eval, prefix, expected);

        //now we pick something that's not in the list
        //this is 6
        prefix = "nonsubsid";
        candidate= null;
        ac.pickCandidate(prefix, candidate);
        expected = List.of("nonsubsidy", "nonsubsidies", "nonsubsiding", "nonsubsidiary", "nonsubsididies", "nonsubsidiaries");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "nonsubsid";
        candidate= "jinho";
        ac.pickCandidate(prefix, candidate);
        expected = List.of(candidate,"nonsubsidy", "nonsubsidies", "nonsubsiding", "nonsubsidiary", "nonsubsididies", "nonsubsidiaries");
        testGetCandidates(ac, eval, prefix, expected);



        //now we pick something that's in the list
        //this is 7
        //this is weird, go figure it out
        prefix = "nonsubsid";
        candidate= "nonsubsidiary";
        ac.pickCandidate(prefix, candidate);
        expected = List.of("nonsubsidiary","jinho","nonsubsidy", "nonsubsidies", "nonsubsiding",  "nonsubsididies", "nonsubsidiaries");
        testGetCandidates(ac, eval, prefix, expected);

        //now this is search empty string
        prefix = "";
        expected = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "";
        candidate= "jinho";
        ac.pickCandidate(prefix, candidate);
        expected = List.of(candidate);
        testGetCandidates(ac, eval, prefix, expected);

        System.out.printf("Score: %d/%d\n", eval.correct, eval.total);
    }

    private void testGetCandidates(Autocomplete<?> ac, Eval eval, String prefix, List<String> expected) {
        String log = String.format("%2d: ", eval.total);
        eval.total++;

        try {
            List<String> candidates = ac.getCandidates(prefix);

            if (expected.equals(candidates)) {
                eval.correct++;
                log += "PASS";
            }
            else
                log += String.format("FAIL -> expected = %s, returned = %s", expected, candidates);
        }
        catch (Exception e) {
            log += "ERROR";
        }

        System.out.println(log);
    }
    private void testGetCandidates2(Autocomplete<?> ac, Eval eval, String prefix, List<String> expected) {
        String log = String.format("%2d: ", eval.total);
        eval.total++;

        try {
            List<String> candidates = ac.getCandidates(prefix);

            if (expected.equals(candidates)) {
                eval.correct++;
                log += "PASS";
            }
            else
                log += String.format("FAIL -> expected = %s, returned = %s", expected, candidates);
        }
        catch (Exception e) {
            log += "ERROR";
        }

        System.out.println(log);
    }
}