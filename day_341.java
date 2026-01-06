// 1161. Maximum Level Sum of a Binary Tree

// Given the root of a binary tree, the level of its root is 1, the level of its children is 2, and so on.

// Return the smallest level x such that the sum of all the values of nodes at level x is maximal.

 

// Example 1:


// Input: root = [1,7,0,7,-8,null,null]
// Output: 2
// Explanation: 
// Level 1 sum = 1.
// Level 2 sum = 7 + 0 = 7.
// Level 3 sum = 7 + -8 = -1.
// So we return the level with the maximum sum which is level 2.
// Example 2:

// Input: root = [989,null,10250,98693,-89388,null,null,null,-32127]
// Output: 2
 

// Constraints:

// The number of nodes in the tree is in the range [1, 10^4].
// -10^5 <= Node.val <= 10^5



// Solution: 


import java.util.*;

class Solution {
    List<Integer> sum = new ArrayList<>();

    private void dfs(TreeNode node, int level) {
        if (node == null) return;

        if (sum.size() == level) {
            sum.add(node.val);
        } else {
            sum.set(level, sum.get(level) + node.val);
        }

        dfs(node.left, level + 1);
        dfs(node.right, level + 1);
    }

    public int maxLevelSum(TreeNode root) {
        // index 0 is unused, similar to INT_MIN in C++
        sum.add(Integer.MIN_VALUE);

        dfs(root, 1);

        int maxLevel = 0;
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < sum.size(); i++) {
            if (sum.get(i) > maxSum) {
                maxSum = sum.get(i);
                maxLevel = i;
            }
        }

        return maxLevel;
    }
}
