// 1594. Maximum Non Negative Product in a Matrix

// You are given a m x n matrix grid. Initially, you are located at the top-left corner (0, 0), and in each step, you can only move right or down in the matrix.

// Among all possible paths starting from the top-left corner (0, 0) and ending in the bottom-right corner (m - 1, n - 1), find the path with the maximum non-negative product. The product of a path is the product of all integers in the grid cells visited along the path.

// Return the maximum non-negative product modulo 109 + 7. If the maximum product is negative, return -1.

// Notice that the modulo is performed after getting the maximum product.

 

// Example 1:


// Input: grid = [[-1,-2,-3],[-2,-3,-3],[-3,-3,-2]]
// Output: -1
// Explanation: It is not possible to get non-negative product in the path from (0, 0) to (2, 2), so return -1.
// Example 2:


// Input: grid = [[1,-2,1],[1,-2,1],[3,-4,1]]
// Output: 8
// Explanation: Maximum non-negative product is shown (1 * 1 * -2 * -4 * 1 = 8).
// Example 3:


// Input: grid = [[1,3],[0,-4]]
// Output: 0
// Explanation: Maximum non-negative product is shown (1 * 0 * -4 = 0).
 

// Constraints:

// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 15
// -4 <= grid[i][j] <= 4



// Solution:



class Solution {
    public int maxProductPath(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        int MOD = 1_000_000_007;
        
        long[][][] dp = new long[r][c][2];
        
        long p = grid[0][0];
        dp[0][0][0] = p;
        dp[0][0][1] = p;

        // First row
        for (int j = 1; j < c; j++) {
            p *= grid[0][j];
            dp[0][j][0] = p;
            dp[0][j][1] = p;
        }

        // First column + rest
        p = grid[0][0];
        for (int i = 1; i < r; i++) {
            p *= grid[i][0];
            dp[i][0][0] = p;
            dp[i][0][1] = p;

            for (int j = 1; j < c; j++) {
                int x = grid[i][j];

                long a = x * dp[i][j - 1][0];
                long b = x * dp[i][j - 1][1];
                long c1 = x * dp[i - 1][j][0];
                long d = x * dp[i - 1][j][1];

                long minP = Math.min(Math.min(a, b), Math.min(c1, d));
                long maxP = Math.max(Math.max(a, b), Math.max(c1, d));

                dp[i][j][0] = minP;
                dp[i][j][1] = maxP;
            }
        }

        long ans = dp[r - 1][c - 1][1];
        return ans < 0 ? -1 : (int)(ans % MOD);
    }
}