// 1931. Painting a Grid With Three Different Colors
// Hard
// Topics
// Companies
// Hint
// You are given two integers m and n. Consider an m x n grid where each cell is initially white. You can paint each cell red, green, or blue. All cells must be painted.

// Return the number of ways to color the grid with no two adjacent cells having the same color. Since the answer can be very large, return it modulo 109 + 7.

 

// Example 1:


// Input: m = 1, n = 1
// Output: 3
// Explanation: The three possible colorings are shown in the image above.
// Example 2:


// Input: m = 1, n = 2
// Output: 6
// Explanation: The six possible colorings are shown in the image above.
// Example 3:

// Input: m = 5, n = 5
// Output: 580986
 

// Constraints:

// 1 <= m <= 5
// 1 <= n <= 1000


// Solution:


class Solution {
    public:
        const int mod = 1e9 + 7;
        int dp[1005][250], rowValid[250][250];
        vector<int> good, pattern [250];
        int colorTheGrid(int m, int n) {
            int total = pow(3, m);
    
            for (int i = 0; i < total; i++) {
                int val = i, valid = 1;
                for (int j = 0; j < m; j++) 
                    pattern[i].push_back(val % 3), val /= 3;
                for (int j = 1; j < m; j++) 
                    if (pattern[i][j] == pattern[i][j - 1]) valid = 0;
                if (valid) good.push_back(i);
            }
            for (int i : good) dp[1][i] = 1;
    
            for (int i : good) {
                for (int j : good) {
                    rowValid[i][j] = 1;
                    for (int k = 0; k < m; k++) 
                        if (pattern[i][k] == pattern[j][k]) 
                            rowValid[i][j] = 0;
                }
            }
    
            for (int col = 2; col <= n; col++)
                for (int i : good)
                    for (int j : good)
                        if (rowValid[i][j]) 
                            dp[col][i] = (dp[col][i] + dp[col - 1][j]) % mod;
                            
            return accumulate(dp[n], dp[n] + total, 0L) % mod;
        }
    };