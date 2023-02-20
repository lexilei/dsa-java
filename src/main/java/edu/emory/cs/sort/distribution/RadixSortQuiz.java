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
    private static void bitsort(Integer[] array, int beginIndex, int endIndex, int b) {
        if (beginIndex >= endIndex || b <= 0) {
            return;
        }

        int[] count = new int[10];
//
        for (int i = beginIndex; i < endIndex; i++) {
            int num = array[i] / (int) Math.pow(10, b-1);
            count[num%10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
            System.out.println(count[i]);
        }


        int[] sorted = new int[endIndex - beginIndex + 1];

        // sort elements based on the current digit
        for (int i = endIndex-1; i >= beginIndex; i--) {
            int num = (int)(array[i] / Math.pow(10, b-1))-1;
            sorted[count[num%10]] = array[i];
            count[num%10]++;
        }

        // copy the sorted elements back to the original array
        for (int i = beginIndex; i < endIndex; i++) {
            array[i] = sorted[i-beginIndex];
        }

        // recursively sort elements based on the next digit
        for (int i = 1; i <10; i++) {
            int nextLeft = beginIndex + count[i-1];
            int nextRight = beginIndex + count[i] ;
            System.out.println("next up:"+nextLeft+" "+nextRight);
            bitsort(array, nextLeft, nextRight, b - 1);
        }
    }


    public static void main(String[] args){
        Integer[] test=new Integer[]{14,26,99,90,96,20};
        RadixSortQuiz test2=new RadixSortQuiz();
        test2.sort(test,0,test.length);
        for (int i=0;i<test.length;i++){
            System.out.print(test[i]+",");
        }
    }
}