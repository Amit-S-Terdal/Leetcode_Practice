// 889. Construct Binary Tree from Preorder and Postorder Traversal

// Given two integer arrays, preorder and postorder where preorder is the preorder traversal of a binary tree of distinct values and postorder is the postorder traversal of the same tree, reconstruct and return the binary tree.

// If there exist multiple answers, you can return any of them.

 

// Example 1:


// Input: preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
// Output: [1,2,3,4,5,6,7]
// Example 2:

// Input: preorder = [1], postorder = [1]
// Output: [1]
 

// Constraints:

// 1 <= preorder.length <= 30
// 1 <= preorder[i] <= preorder.length
// All the values of preorder are unique.
// postorder.length == preorder.length
// 1 <= postorder[i] <= postorder.length
// All the values of postorder are unique.
// It is guaranteed that preorder and postorder are the preorder traversal and postorder traversal of the same binary tree.

// Solution:

import java.util.HashMap;

class Solution {
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        // Create a map to store the indices of postorder elements for quick lookup
        HashMap<Integer, Integer> postMap = new HashMap<>();
        for (int i = 0; i < postorder.length; i++) {
            postMap.put(postorder[i], i);
        }
        // Start the recursive construction process
        return buildTree(preorder, 0, preorder.length - 1, postorder, 0, postorder.length - 1, postMap);
    }

    private TreeNode buildTree(int[] preorder, int preStart, int preEnd, int[] postorder, int postStart, int postEnd, HashMap<Integer, Integer> postMap) {
        // Base case: if there are no elements to process, return null
        if (preStart > preEnd) {
            return null;
        }

        // The first element in the current preorder segment is the root
        TreeNode root = new TreeNode(preorder[preStart]);
        if (preStart == preEnd) {
            return root; // If there's only one element, return the root
        }

        // The next element in preorder is the root of the left subtree
        int leftRootVal = preorder[preStart + 1];
        // Find the index of the left subtree root in the postorder array
        int leftRootIndexInPost = postMap.get(leftRootVal);
        // Calculate the size of the left subtree
        int leftSubtreeSize = leftRootIndexInPost - postStart + 1;

        // Recursively construct the left subtree
        root.left = buildTree(preorder, preStart + 1, preStart + leftSubtreeSize, postorder, postStart, leftRootIndexInPost, postMap);
        // Recursively construct the right subtree
        root.right = buildTree(preorder, preStart + leftSubtreeSize + 1, preEnd, postorder, leftRootIndexInPost + 1, postEnd - 1, postMap);

        return root;
    }
}