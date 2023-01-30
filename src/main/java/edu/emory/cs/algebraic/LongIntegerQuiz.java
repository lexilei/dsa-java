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
package edu.emory.cs.algebraic;
import java.util.Arrays;

/** @author Jinho D. Choi */
public class LongIntegerQuiz extends LongInteger {
    public LongIntegerQuiz(LongInteger n) { super(n); }

    public LongIntegerQuiz(String n) { super(n); }

    @Override
    protected void addDifferentSign(LongInteger n) {
        int big = compareAbs(n);
        //if they cancel out we directly return it
        if (big == 0){
            digits = new byte[1];
            return;
        }
        //determine sign of the result
        if (big > 0 && isPositive() || big < 0 && n.isPositive()){
            sign = Sign.POSITIVE;
        }else{
            sign = Sign.NEGATIVE;
        }
        //to carry out calculation, we first create result array
        int m = Math.max(digits.length, n.digits.length);
        byte[] result = new byte[m+1];
        //then if big>0, copy "this" and subtract n
        if (big > 0) {
            System.arraycopy(digits, 0, result, 0, digits.length);
            for (int i = 0; i < m; i++) {
                if (i < n.digits.length)
                    result[i] -= n.digits[i];
                if (result[i] < 0) {
                    result[i] += 10;
                    result[i + 1] -= 1;
                }
            }
            //else copy n subtract "this"
        }else{
            System.arraycopy(n.digits, 0, result, 0, n.digits.length);
            for (int i = 0; i < m; i++) {
                if (i < digits.length)
                    result[i] -= digits[i];
                if (result[i] < 0) {
                    result[i] += 10;
                    result[i + 1] -= 1;
                }
            }
        }

        //now get rid off zeros at the front
        while (result[m] == 0 && m > 0){
            m -= 1;
        }
        //then copy result into digits
        digits = Arrays.copyOf(result, m+1);
    }
}