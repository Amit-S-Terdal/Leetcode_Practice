// 1886. Determine Whether Matrix Can Be Obtained By Rotation

// Given two n x n binary matrices mat and target, return true if it is possible to make mat equal to target by rotating mat in 90-degree increments, or false otherwise.

 

// Example 1:


// Input: mat = [[0,1],[1,0]], target = [[1,0],[0,1]]
// Output: true
// Explanation: We can rotate mat 90 degrees clockwise to make mat equal target.
// Example 2:


// Input: mat = [[0,1],[1,1]], target = [[1,0],[0,1]]
// Output: false
// Explanation: It is impossible to make mat equal to target by rotating mat.
// Example 3:


// Input: mat = [[0,0,0],[0,1,0],[1,1,1]], target = [[1,1,1],[0,1,0],[0,0,0]]
// Output: true
// Explanation: We can rotate mat 90 degrees clockwise two times to make mat equal target.
 

// Constraints:

// n == mat.length == target.length
// n == mat[i].length == target[i].length
// 1 <= n <= 10
// mat[i][j] and target[i][j] are either 0 or 1.




// Solution:




class Solution {
public:
    static bool findRotation(vector<vector<int>>& mat, vector<vector<int>>& target) {
        const int n=mat.size();
        uint8_t rot90=15;
        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++){
                uint8_t cur=0;
                cur|=(mat[i][j]==target[i][j]);
                cur|=((mat[j][n-1-i]==target[i][j])<<1);
                cur|=((mat[n-1-i][n-1-j]==target[i][j])<<2);
                cur|=((mat[n-1-j][i]==target[i][j])<<3);
                rot90&=cur;
            }
        return rot90!=0;
    }
};