# 1028. Recover a Tree From Preorder Traversal

# Hint
# We run a preorder depth-first search (DFS) on the root of a binary tree.

# At each node in this traversal, we output D dashes (where D is the depth of this node), then we output the value of this node.  If the depth of a node is D, the depth of its immediate child is D + 1.  The depth of the root node is 0.

# If a node has only one child, that child is guaranteed to be the left child.

# Given the output traversal of this traversal, recover the tree and return its root.

 

# Example 1:


# Input: traversal = "1-2--3--4-5--6--7"
# Output: [1,2,5,3,4,6,7]
# Example 2:


# Input: traversal = "1-2--3---4-5--6---7"
# Output: [1,2,5,3,null,6,null,4,null,7]
# Example 3:


# Input: traversal = "1-401--349---90--88"
# Output: [1,401,null,349,88,90]
 

# Constraints:

# The number of nodes in the original tree is in the range [1, 1000].
# 1 <= Node.val <= 109

# Solution: 

class TreeNode(object):
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

class Solution(object):
    def recoverFromPreorder(self, traversal):
        """
        :type traversal: str
        :rtype: Optional[TreeNode]
        """
        stack = []
        i = 0
        n = len(traversal)
        
        while i < n:
            # Determine the depth of the current node
            depth = 0
            while i < n and traversal[i] == '-':
                depth += 1
                i += 1
            
            # Determine the value of the current node
            val = 0
            while i < n and traversal[i].isdigit():
                val = val * 10 + int(traversal[i])
                i += 1
            
            # Create the current node
            node = TreeNode(val)
            
            # If depth is 0, it's the root node
            if depth == 0:
                stack.append(node)
                continue
            
            # Pop nodes from the stack until we find the parent node
            while len(stack) > depth:
                stack.pop()
            
            # Attach the current node to the parent node
            if stack[-1].left is None:
                stack[-1].left = node
            else:
                stack[-1].right = node
            
            # Push the current node onto the stack
            stack.append(node)
        
        # The root is the first node in the stack
        return stack[0]