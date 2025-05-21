# 1931. Painting a Grid With Three Different Colors
# Hard
# Topics
# Companies
# Hint
# You are given two integers m and n. Consider an m x n grid where each cell is initially white. You can paint each cell red, green, or blue. All cells must be painted.

# Return the number of ways to color the grid with no two adjacent cells having the same color. Since the answer can be very large, return it modulo 109 + 7.

 

# Example 1:


# Input: m = 1, n = 1
# Output: 3
# Explanation: The three possible colorings are shown in the image above.
# Example 2:


# Input: m = 1, n = 2
# Output: 6
# Explanation: The six possible colorings are shown in the image above.
# Example 3:

# Input: m = 5, n = 5
# Output: 580986
 

# Constraints:

# 1 <= m <= 5
# 1 <= n <= 1000


# Solution:

class Solution(object):
    def colorTheGrid(self, m, n):
        mod = 10**9+7
        total = 1
        for _ in range(m):
            total *= 3
        dp = [[0]*total for _ in range(n+1)]
        rowValid = [[0]*total for _ in range(total)]
        good = []
        pattern = [[] for _ in range(total)]
        for i in range(total):
            val, valid = i, 1
            for _ in range(m):
                pattern[i].append(val % 3)
                val //= 3
            for j in range(1, m):
                if pattern[i][j] == pattern[i][j-1]:
                    valid = 0
            if valid:
                good.append(i)
        for i in good:
            dp[1][i] = 1
        for i in good:
            for j in good:
                rowValid[i][j] = 1
                for k in range(m):
                    if pattern[i][k] == pattern[j][k]:
                        rowValid[i][j] = 0
        for col in range(2, n+1):
            for i in good:
                total_ways = 0
                for j in good:
                    if rowValid[i][j]:
                        total_ways += dp[col-1][j]
                dp[col][i] = total_ways % mod
        return sum(dp[n]) % mod
