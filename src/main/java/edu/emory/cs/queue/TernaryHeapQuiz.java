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
package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** @author Jinho D. Choi */
public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }
//Everything is adapted from binary heap code, only with algebraic changes.
    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
        keys.add(null);
    }

    private int compare(int i1, int i2) {
        return priority.compare(keys.get(i1), keys.get(i2));
    }
    @Override
    public void add(T key) {
        keys.add(key);
        swim(size());
    }

    private void swim(int k) {
        for (; 1 < k && compare(( k+1 ) / 3, k) < 0; k = ( k+1 )/ 3)
            Collections.swap(keys, ( k+1 ) / 3, k);
    }

    @Override
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 1, size());
        T max = keys.remove(size());
        sink();
        return max;
    }

    private void sink() {
        for (int k = 1, i  = 2; i <= size(); k = i, i =i*3-1) {
            if (i < size()) {
                if (( i+2 ) > size()){
                    if (compare(i, i + 1) < 0) i++;
                }else if ( i+1 > size()){

                }else {
                    if (compare(i, i + 1) >= 0 && compare(i, i + 2) >= 0) ;
                    else if (compare(i + 1, i) >= 0 && compare(i + 1, i + 2) >= 0) i ++;
                    else if (compare(i + 2, i) >= 0 && compare(i + 2, i + 1) >= 0) i += 2;
                }
            }
            if (compare(k, i) >= 0) break;
            Collections.swap(keys, k, i);
        }
    }


    @Override
    public int size() {
        return keys.size() - 1;
    }


}
}