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
package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.BinaryNode;

//* @author Jinho D. Choi

public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    @Override
    protected void balance(BinaryNode<T> node) {
        if (node.hasParent() && node.getParent().hasParent() && node.getParent().getParent().hasBothChildren()) {
            BinaryNode<T> parent = node.getParent();
            BinaryNode<T> uncle = node.getUncle();
            BinaryNode<T> grandParent = node.getGrandParent();

            if ((!parent.hasBothChildren()) && grandParent.isRightChild(parent) && !uncle.hasBothChildren()) {
                // checks is the node is the only child
                // if yes we proceed
                if (((uncle.hasLeftChild()) && !uncle.hasRightChild()) || (!uncle.hasLeftChild() && uncle.hasRightChild())) {// Check if node's uncle has only one child
                    // if it's rotateable, we rotate it
                    if (parent.isLeftChild(node)) {
                        if (uncle.hasLeftChild()) {
                            rotateRight(parent);
                            rotateLeft(grandParent);
                            rotateRight(grandParent);
                        } else if (uncle.hasRightChild()) {
                            rotateRight(parent);
                            rotateLeft(uncle);
                            rotateLeft(grandParent);
                            rotateRight(grandParent);
                        }
                    } else {
                        if (uncle.hasLeftChild()) {
                            rotateLeft(grandParent);
                            rotateRight(grandParent);
                        } else if (uncle.hasRightChild()) {
                            rotateLeft(uncle);
                            rotateLeft(grandParent);
                            rotateRight(grandParent);
                        }
                    }
                }
            }
        }
    }

}