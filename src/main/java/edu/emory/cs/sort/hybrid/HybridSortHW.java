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
import edu.emory.cs.sort.comparison.ShellSortQuiz;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.utils.Utils;

import java.lang.reflect.Array;
import java.util.Comparator;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T>  {
    ShellSortQuiz shell=new ShellSortQuiz();
    @Override
    public T[] sort(T[][] input) {
        //check ascending, if yes, just keep the array
        //then check descending, if yes, reverse array
        //else use shell sort
        int l=0;
        for (int i=0;i<input.length;i++) {
            l+=input[i].length;
            if (checkAscending(input[i])==true) {
//                System.out.println(Arrays.toString(input[i]));
                continue;
            }
            else if (checkDescending(input[i])==true)
                reverseArray(input[i]);
            else
                shell.sort(input[i]);

//            System.out.println(Arrays.toString(input[i]));
        }
        //merge sorted arrays
        //try 1 merge with original order
        T[] result=mergenatural(input);
//        System.out.println(Arrays.toString(result));
        return result;
    }
    //is recursively faster?
    //try 1 一个个merge
    public T[] mergeAll(T[][] input,int len){
        T[] result=(T[])Array.newInstance(input[0][0].getClass(), len);
        int i=0;

        while (input.length>1){
            while (i<input.length/2) {
                T[][] temp=(T[][])Array.newInstance(input[0][0].getClass(), (input.length+1)/2,1);
                temp[i]=merge2Arrays(input[2 * i - 1], input[2 * i - 2]);
            }
        }
        return null;
    }

    public T[] mergenatural(T[][] input){
        T[] temp=input[0];
        for (int i=1;i<input.length;i++){
                temp=merge2Arrays(temp, input[i]);
            }

        return temp;
    }


    public boolean checkAscending(T[] input) {
        if (input.length<=1)
            return true;

        for (int i=1; i< input.length; i++){
            if ((input[i].compareTo(input[i - 1]))<0){
//                System.out.println("not as");
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
        // we already established descending,
        T temp;
        int n=input.length;
        for (int i=0; i<n/2;i++){
            //System.out.println(i);
            temp=input[i];
            input[i]=input[n-i-1];
            input[n-i-1]=temp;
        }
    }

    protected T[] merge2Arrays(T[] input1, T[] input2) {
        int fst = 0, snd = 0, a=input1.length, b=input2.length, n = input1.length+input2.length;
        T[] result=(T[])Array.newInstance(input1[0].getClass(), input1.length + input2.length);

        for (int k = 0; k < n; k++) {
            if (fst >= a)                    // no key left in the 1st half
                assign(result, k, input2[snd++]);
            else if (snd >= b)                  // no key left in the 2nd half
                assign(result, k, input1[fst++]);
            else if (input1[fst].compareTo(input2[snd]) < 0)    // 1st key < 2nd key
                assign(result, k, input1[fst++]);
            else
                assign(result, k, input2[snd++]);
        }
        return result;
    }

    protected void assign(T[] array, int index, T value) {
        array[index] = value;
    }




}