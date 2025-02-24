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

#include <vector>
#include <unordered_map>

using namespace std;

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

class Solution {
public:
    TreeNode* constructFromPrePost(vector<int>& preorder, vector<int>& postorder) {
        unordered_map<int, int> postMap;
        for (int i = 0; i < postorder.size(); ++i) {
            postMap[postorder[i]] = i;
        }
        return buildTree(preorder, 0, preorder.size() - 1, postorder, 0, postorder.size() - 1, postMap);
    }
    
private:
    TreeNode* buildTree(vector<int>& preorder, int preStart, int preEnd, vector<int>& postorder, int postStart, int postEnd, unordered_map<int, int>& postMap) {
        if (preStart > preEnd) return nullptr;
        
        TreeNode* root = new TreeNode(preorder[preStart]);
        if (preStart == preEnd) return root;
        
        int leftRootVal = preorder[preStart + 1];
        int leftRootIndexInPost = postMap[leftRootVal];
        int leftSubtreeSize = leftRootIndexInPost - postStart + 1;
        
        root->left = buildTree(preorder, preStart + 1, preStart + leftSubtreeSize, postorder, postStart, leftRootIndexInPost, postMap);
        root->right = buildTree(preorder, preStart + leftSubtreeSize + 1, preEnd, postorder, leftRootIndexInPost + 1, postEnd - 1, postMap);
        
        return root;
    }
};