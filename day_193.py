# 808. Soup Servings
# https://leetcode.com/problems/soup-servings/

class Solution:
    def soupServings(self, n: int) -> float:
        # For large n, the probability approaches 1.0
        if n >= 5000:
            return 1.0
        
        # Scale down by 25 since all operations are multiples of 25
        m = (n + 24) // 25
        
        # Memoization table
        dp = [[0.0] * (m + 1) for _ in range(m + 1)]
        
        # Base cases
        for i in range(m + 1):
            dp[0][i] = 1.0  # A is empty, B has some left
            dp[i][0] = 0.0   # B is empty, A has some left
        dp[0][0] = 0.5  # Both empty
        
        # Fill the DP table
        for a in range(1, m + 1):
            for b in range(1, m + 1):
                # Four possible operations, each with probability 0.25
                prob = 0.0
                
                # Operation 1: (100, 0) -> (4, 0)
                a1 = max(0, a - 4)
                b1 = b
                prob += 0.25 * dp[a1][b1]
                
                # Operation 2: (75, 25) -> (3, 1)
                a2 = max(0, a - 3)
                b2 = max(0, b - 1)
                prob += 0.25 * dp[a2][b2]
                
                # Operation 3: (50, 50) -> (2, 2)
                a3 = max(0, a - 2)
                b3 = max(0, b - 2)
                prob += 0.25 * dp[a3][b3]
                
                # Operation 4: (25, 75) -> (1, 3)
                a4 = max(0, a - 1)
                b4 = max(0, b - 3)
                prob += 0.25 * dp[a4][b4]
                
                dp[a][b] = prob
        
        return dp[m][m]
