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

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class RadixSortQuiz extends RadixSort {
    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex);
        for (int bit = maxBit-1; bit >=0 ; bit--) {
            int div = (int)Math.pow(10, bit);
            sort(array, beginIndex, endIndex, key -> (key / div) % 10);
        }
    }
    public static void main(String[] args){
        Integer[] test=new Integer[]{2,345,1,5,2,7,4,86,234,356,12};
        RadixSortQuiz testee=new RadixSortQuiz();
        testee.sort(test);
        for (int i=0;i<test.length;i++){
            System.out.print(test[i]+",");
        }
    }
}