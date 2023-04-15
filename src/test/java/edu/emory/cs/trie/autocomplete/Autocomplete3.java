package edu.emory.cs.trie.autocomplete;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
public class Autocomplete3 {
        static class Eval {
            int correct = 0;
            int total = 0;
        }

        @Test
        public void test() {
            final String dict_file = "src/main/resources/swy.txt";
            final int max = 6;

            Autocomplete<?> ac = new AutocompleteHW(dict_file, max);
            Eval eval = new Eval();
            testAutocomplete(ac, eval);
        }

        private void testAutocomplete(Autocomplete<?> ac, Eval eval) {
            String prefix;
            List<String> expected;

            prefix = "a";
            expected = List.of("a", "aa", "ab", "abc");
            testGetCandidates(ac, eval, prefix, expected);

            prefix = "a";
            ac.pickCandidate(prefix, "ab");
            expected = List.of("ab", "a", "aa", "abc");
            testGetCandidates(ac, eval, prefix, expected);

            prefix = "a";
            expected = List.of("ab", "a", "aa", "abc");
            testGetCandidates(ac, eval, prefix, expected);

            prefix = "a"; //3
            ac.pickCandidate(prefix, "af");
            expected = List.of("af", "ab", "a", "aa", "abc");
            testGetCandidates(ac, eval, prefix, expected);

            prefix = "a"; //4
            expected = List.of("af", "ab", "a", "aa", "abc");
            testGetCandidates(ac, eval, prefix, expected);

            prefix = "af"; //5
            ac.pickCandidate(prefix, "af");
            expected = List.of("af");
            testGetCandidates(ac, eval, prefix, expected);

            prefix = ""; //6
            expected = List.of("a","b");
            testGetCandidates(ac, eval, prefix, expected);

            prefix = " ";
            expected = List.of("a","b");
            testGetCandidates(ac, eval, prefix, expected);

            prefix = " ab "; //8
            expected = List.of("ab","abc");
            testGetCandidates(ac, eval, prefix, expected);

            prefix = "ab"; //9
            expected = List.of("ab","abc");
            testGetCandidates(ac, eval, prefix, expected);

            prefix = "!";
            expected = Collections.emptyList();;//
            testGetCandidates(ac, eval, prefix, expected);

            prefix = "abd";
            expected = Collections.emptyList();
            testGetCandidates(ac, eval, prefix, expected);

            prefix = "！ab";
            expected = Collections.emptyList();
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

}
