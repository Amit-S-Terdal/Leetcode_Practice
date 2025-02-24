# 889. Construct Binary Tree from Preorder and Postorder Traversal

# Given two integer arrays, preorder and postorder where preorder is the preorder traversal of a binary tree of distinct values and postorder is the postorder traversal of the same tree, reconstruct and return the binary tree.

# If there exist multiple answers, you can return any of them.

 

# Example 1:


# Input: preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
# Output: [1,2,3,4,5,6,7]
# Example 2:

# Input: preorder = [1], postorder = [1]
# Output: [1]
 

# Constraints:

# 1 <= preorder.length <= 30
# 1 <= preorder[i] <= preorder.length
# All the values of preorder are unique.
# postorder.length == preorder.length
# 1 <= postorder[i] <= postorder.length
# All the values of postorder are unique.
# It is guaranteed that preorder and postorder are the preorder traversal and postorder traversal of the same binary tree.

# Solution:

# Definition for a binary tree node.
class TreeNode(object):
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

class Solution(object):
    def constructFromPrePost(self, preorder, postorder):
        """
        :type preorder: List[int]
        :type postorder: List[int]
        :rtype: TreeNode
        """
        # Create a map to store the indices of postorder elements for quick lookup
        post_map = {val: idx for idx, val in enumerate(postorder)}
        
        # Start the recursive construction process
        return self.buildTree(preorder, 0, len(preorder) - 1, postorder, 0, len(postorder) - 1, post_map)
    
    def buildTree(self, preorder, pre_start, pre_end, postorder, post_start, post_end, post_map):
        # Base case: if there are no elements to process, return None
        if pre_start > pre_end:
            return None
        
        # The first element in the current preorder segment is the root
        root = TreeNode(preorder[pre_start])
        if pre_start == pre_end:
            return root  # If there's only one element, return the root
        
        # The next element in preorder is the root of the left subtree
        left_root_val = preorder[pre_start + 1]
        # Find the index of the left subtree root in the postorder array
        left_root_idx_in_post = post_map[left_root_val]
        # Calculate the size of the left subtree
        left_subtree_size = left_root_idx_in_post - post_start + 1
        
        # Recursively construct the left subtree
        root.left = self.buildTree(
            preorder, pre_start + 1, pre_start + left_subtree_size,
            postorder, post_start, left_root_idx_in_post, post_map
        )
        # Recursively construct the right subtree
        root.right = self.buildTree(
            preorder, pre_start + left_subtree_size + 1, pre_end,
            postorder, left_root_idx_in_post + 1, post_end - 1, post_map
        )
        
        return root