// 1028. Recover a Tree From Preorder Traversal

// Hint
// We run a preorder depth-first search (DFS) on the root of a binary tree.

// At each node in this traversal, we output D dashes (where D is the depth of this node), then we output the value of this node.  If the depth of a node is D, the depth of its immediate child is D + 1.  The depth of the root node is 0.

// If a node has only one child, that child is guaranteed to be the left child.

// Given the output traversal of this traversal, recover the tree and return its root.

 

// Example 1:


// Input: traversal = "1-2--3--4-5--6--7"
// Output: [1,2,5,3,4,6,7]
// Example 2:


// Input: traversal = "1-2--3---4-5--6---7"
// Output: [1,2,5,3,null,6,null,4,null,7]
// Example 3:


// Input: traversal = "1-401--349---90--88"
// Output: [1,401,null,349,88,90]
 

// Constraints:

// The number of nodes in the original tree is in the range [1, 1000].
// 1 <= Node.val <= 109

// Solution: 

#include <string>
#include <stack>

using namespace std;

// Definition for a binary tree node.
struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

class Solution {
public:
    TreeNode* recoverFromPreorder(string traversal) {
        stack<TreeNode*> stack;
        int i = 0;
        int n = traversal.length();
        
        while (i < n) {
            // Determine the depth of the current node
            int depth = 0;
            while (i < n && traversal[i] == '-') {
                depth++;
                i++;
            }
            
            // Determine the value of the current node
            int val = 0;
            while (i < n && isdigit(traversal[i])) {
                val = val * 10 + (traversal[i] - '0');
                i++;
            }
            
            // Create the current node
            TreeNode* node = new TreeNode(val);
            
            // If depth is 0, it's the root node
            if (depth == 0) {
                stack.push(node);
                continue;
            }
            
            // Pop nodes from the stack until we find the parent node
            while (stack.size() > depth) {
                stack.pop();
            }
            
            // Attach the current node to the parent node
            if (stack.top()->left == nullptr) {
                stack.top()->left = node;
            } else {
                stack.top()->right = node;
            }
            
            // Push the current node onto the stack
            stack.push(node);
        }
        
        // The root is the first node in the stack
        while (stack.size() > 1) {
            stack.pop();
        }
        return stack.top();
    }
};