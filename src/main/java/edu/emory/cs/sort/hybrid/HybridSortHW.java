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
package edu.emory.cs.sort.hybrid;
import java.util.Arrays;
import java.util.List;
import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.comparison.ShellSortQuiz;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.distribution.LSDRadixSort;
import edu.emory.cs.sort.divide_conquer.IntroSort;
import edu.emory.cs.utils.Utils;

import java.lang.reflect.Array;
import java.util.Comparator;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T>  {
    IntroSort intro= new IntroSort<T>(new ShellSortKnuth<T>());
    @Override
    public T[] sort(T[][] input) {
        for (int i = 0; i < input.length; i++) {
            if (!checkAscending(input[i])) {
                if (checkDescending(input[i])) {
                    reverseArray(input[i]);
                } else {
                    intro.sort(input[i]);
                }
            }
        }
        return mergeRecursive(input);
    }


    public boolean checkAscending(T[] input) {
        if (input.length<=1)
            return true;
        for (int i=1; i< input.length; i++){
            if ((input[i].compareTo(input[i - 1]))<0){
                return false;
            }
        }
        return true;
    }

    public boolean checkDescending(T[] input){//the opposite of check ascending
        if (input.length<=1)
            return false;
        for (int i=1; i< input.length; i++){
            if ((input[i].compareTo(input[i - 1]))>0){
                return false;
            }
        }
        return true;
    }

    public void reverseArray(T[] input){
        T temp;
        int n=input.length;
        for (int i=0; i<n/2;i++){
            temp=input[i];
            input[i]=input[n-i-1];
            input[n-i-1]=temp;
        }
    }
    public T[] mergeRecursive(T[][] input) {
        if (input.length == 1) {
            return input[0];
        }
        int mid = input.length / 2;
        T[][] left = Arrays.copyOfRange(input, 0, mid);
        T[][] right = Arrays.copyOfRange(input, mid, input.length);
        return merge2Arrays(mergeRecursive(left), mergeRecursive(right));
    }

    private T[] merge2Arrays(T[] a, T[] b) {
        int i = 0, j = 0, k = 0;
        T[] result = (T[])Array.newInstance(a[0].getClass(), a.length + b.length);
        while (i < a.length && j < b.length) {
            if (a[i].compareTo(b[j]) < 0) result[k++] = a[i++];
            else result[k++] = b[j++];
        }
        while (i < a.length) result[k++] = a[i++];
        while (j < b.length) result[k++] = b[j++];
        return result;
    }

}