// 120. Triangle

// Given a triangle array, return the minimum path sum from top to bottom.

// For each step, you may move to an adjacent number of the row below. More formally, if you are on index i on the current row, you may move to either index i or index i + 1 on the next row.

 

// Example 1:

// Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
// Output: 11
// Explanation: The triangle looks like:
//    2
//   3 4
//  6 5 7
// 4 1 8 3
// The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11 (underlined above).
// Example 2:

// Input: triangle = [[-10]]
// Output: -10
 

// Constraints:

// 1 <= triangle.length <= 200
// triangle[0].length == 1
// triangle[i].length == triangle[i - 1].length + 1
// -10^4 <= triangle[i][j] <= 10^4


// Solution: 


class Solution {
public:
    vector<vector<int>> dp;
    int n;
    int findMin(int i, int j, vector<vector<int>>& t) {
        if (dp[i][j] != INT_MAX) return dp[i][j];
        if (i == 0) return dp[0][0] = t[0][0];
        if (j == 0) return dp[i][j] = t[i][j] + findMin(i-1, j, t);
        if (i == j) return dp[i][j] = t[i][j] + findMin(i-1,j-1, t);
        return dp[i][j] = 
        t[i][j] + min(findMin(i - 1, j, t), findMin(i - 1, j - 1, t));
    }

    int minimumTotal(vector<vector<int>>& triangle) {
        n = triangle.size();
        dp.assign(n, vector<int>(n, INT_MAX));

        int ans = INT_MAX;  
        for (int j = 0; j < n; j++) {
            ans = min(ans, findMin(n - 1, j, triangle));
        }
        return ans;
    }      
};