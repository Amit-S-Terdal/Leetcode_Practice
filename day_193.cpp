// 808. Soup Servings
// https://leetcode.com/problems/soup-servings/

#include <vector>
#include <algorithm>

using namespace std;

class Solution {
public:
    double soupServings(int n) {
        // For large n, the probability approaches 1.0
        if (n >= 5000) {
            return 1.0;
        }
        
        // Scale down by 25 since all operations are multiples of 25
        int m = (n + 24) / 25;
        
        // Memoization table
        vector<vector<double>> dp(m + 1, vector<double>(m + 1, 0.0));
        
        // Base cases
        for (int i = 0; i <= m; i++) {
            dp[0][i] = 1.0;  // A is empty, B has some left
            dp[i][0] = 0.0;   // B is empty, A has some left
        }
        dp[0][0] = 0.5;  // Both empty
        
        // Fill the DP table
        for (int a = 1; a <= m; a++) {
            for (int b = 1; b <= m; b++) {
                // Four possible operations, each with probability 0.25
                double prob = 0.0;
                
                // Operation 1: (100, 0) -> (4, 0)
                int a1 = max(0, a - 4);
                int b1 = b;
                prob += 0.25 * dp[a1][b1];
                
                // Operation 2: (75, 25) -> (3, 1)
                int a2 = max(0, a - 3);
                int b2 = max(0, b - 1);
                prob += 0.25 * dp[a2][b2];
                
                // Operation 3: (50, 50) -> (2, 2)
                int a3 = max(0, a - 2);
                int b3 = max(0, b - 2);
                prob += 0.25 * dp[a3][b3];
                
                // Operation 4: (25, 75) -> (1, 3)
                int a4 = max(0, a - 1);
                int b4 = max(0, b - 3);
                prob += 0.25 * dp[a4][b4];
                
                dp[a][b] = prob;
            }
        }
        
        return dp[m][m];
    }
};

