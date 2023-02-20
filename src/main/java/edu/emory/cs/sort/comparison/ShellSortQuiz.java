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
package edu.emory.cs.sort.comparison;

import edu.emory.cs.sort.distribution.RadixSortQuiz;

import java.util.Collections;
import java.util.Comparator;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class ShellSortQuiz<T extends Comparable<T>> extends ShellSort<T> {
    public ShellSortQuiz() {
        this(Comparator.naturalOrder());
    }

    public ShellSortQuiz(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    protected void populateSequence(int n) {
        n /= 2;

        for (int t = sequence.size() + 1; ; t++) {
            int h = (int) (Math.pow(2, t) - 1) ;
            if (h <= n) sequence.add(h);
            else break;
        }
    }

    @Override
    protected int getSequenceStartIndex(int n) {
        int index = Collections.binarySearch(sequence, n / 2);
        if (index < 0) index = -(index + 1);
        if (index == sequence.size()) index--;
        return index;
    }
    public static void main(String[] args){
        Integer[] t=new Integer[]{2,345,1,5,2,7,4,86,234,356,12};
        ShellSortQuiz testee=new ShellSortQuiz();
        testee.sort(t);
        for (int i=0;i<t.length;i++){
            System.out.print(t[i]+",");
        }
    }


}