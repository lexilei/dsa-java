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
package edu.emory.cs.set;

/** @author Jinho D. Choi */
public class DisjointSetQuiz {
    static public void main(String[] args) {
        DisjointSet ds = new DisjointSet(5);
        for (int i=0; i<ds.subsets.length;i++){
            System.out.println(ds.subsets[i]);
        }
        System.out.println(ds.union(0,1));
        System.out.println("after 0,1");
        for (int i=0; i<ds.subsets.length;i++){
            System.out.println(ds.subsets[i]);
        }
        System.out.println(ds.union(2,3));
        System.out.println("after 2,3");
        for (int i=0; i<ds.subsets.length;i++){
            System.out.println(ds.subsets[i]);
        }
        System.out.println(ds.union(3,4));
        System.out.println("after 3,4");
        for (int i=0; i<ds.subsets.length;i++){
            System.out.println(ds.subsets[i]);
        }
        System.out.println(ds.union(1,3));
        System.out.println("after 1,3");
        System.out.println(ds.subsets.toString());
        System.out.println(ds.subsets.length);
        for (int i=0; i<ds.subsets.length;i++){
            System.out.println(ds.subsets[i]);
        }
        ds.find(0);
        for (int i=0; i<ds.subsets.length;i++){
            System.out.println(ds.subsets[i]);
        }
    }

}