package edu.emory.cs.trie.autocomplete;

import org.junit.jupiter.api.Test;

import java.util.List;

public class AutocompleteTestExtra {
    static class Eval {
        int correct = 0;
        int total = 0;
    }

    @Test
    public void test() {
        final String dict_file2 = "src/main/resources/word_vectors.txt";
        final int max = 20;

        Autocomplete<?> ac = new AutocompleteHWExtra(dict_file2, max);
        AutocompleteTestExtra.Eval eval = new AutocompleteTestExtra.Eval();
        testAutocomplete(ac, eval);
    }

    private void testAutocomplete(Autocomplete<?> ac, AutocompleteTestExtra.Eval eval) {
        String prefix;
        List<String> expected;
        String candidate;

        //type 1 regular search
        //passed
        prefix = "1";
        expected = List.of("zoism", "zoist", "zoisite", "zoistic", "zoisites", "zoisitization");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "";
        candidate= "jinho";
        ac.pickCandidate(prefix, candidate);
        expected = List.of(candidate);
        testGetCandidates(ac, eval, prefix, expected);

        System.out.printf("Score: %d/%d\n", eval.correct, eval.total);
    }

    private void testGetCandidates(Autocomplete<?> ac, AutocompleteTestExtra.Eval eval, String prefix, List<String> expected) {
        String log = String.format("%2d: ", eval.total);
        eval.total++;

        try {
            List<String> candidates = ac.getCandidates(prefix);

            if (expected.equals(candidates)) {
                eval.correct++;
                log += "PASS";
            }
            else
                log += String.format("FAIL -> expected = %s, returned = %d", expected, candidates);
        }
        catch (Exception e) {
            log += "ERROR";
        }

        System.out.println(log);
    }
    private void testGetCandidates2(Autocomplete<?> ac, AutocompleteTest.Eval eval, String prefix, List<String> expected) {
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
