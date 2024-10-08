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
package edu.emory.cs.sort.distribution;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class RadixSortQuiz extends RadixSort {
    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex);
        bitsort(array, beginIndex, endIndex, maxBit);
    }
    private void bitsort(Integer[] array, int beginIndex, int endIndex, int b) {
        if (beginIndex >= endIndex || b <= 0) {
            return;
        }
        int div = (int)Math.pow(10, b-1);
        int[] count = new int[10];

        for (int i = beginIndex; i < endIndex; i++) {
            int num = array[i] / div;
            count[num%10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        //now sort the array

        sort(array, beginIndex, endIndex, key -> (key / div) % 10);

        int nextLeft =beginIndex;
        int nextRight =beginIndex;
        // recursively sort elements based on the next digit
        for (int i = 0; i <10; i++) {
            if (i>0 && count[i] == count[i-1]) continue;
            nextLeft = nextRight;
            nextRight = beginIndex + count[i] ;
            bitsort(array, nextLeft, nextRight, b - 1);
        }
    }
}