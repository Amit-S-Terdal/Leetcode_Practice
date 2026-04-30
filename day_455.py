# 3742. Maximum Path Score in a Grid

# You are given an m x n grid where each cell contains one of the values 0, 1, or 2. You are also given an integer k.

# You start from the top-left corner (0, 0) and want to reach the bottom-right corner (m - 1, n - 1) by moving only right or down.

# Each cell contributes a specific score and incurs an associated cost, according to their cell values:

# 0: adds 0 to your score and costs 0.
# 1: adds 1 to your score and costs 1.
# 2: adds 2 to your score and costs 1. ​​​​​​​
# Return the maximum score achievable without exceeding a total cost of k, or -1 if no valid path exists.

# Note: If you reach the last cell but the total cost exceeds k, the path is invalid.

 

# Example 1:

# Input: grid = [[0, 1],[2, 0]], k = 1

# Output: 2

# Explanation:​​​​​​​

# The optimal path is:

# Cell	grid[i][j]	Score	Total
# Score	Cost	Total
# Cost
# (0, 0)	0	0	0	0	0
# (1, 0)	2	2	2	1	1
# (1, 1)	0	0	2	0	1
# Thus, the maximum possible score is 2.

# Example 2:

# Input: grid = [[0, 1],[1, 2]], k = 1

# Output: -1

# Explanation:

# There is no path that reaches cell (1, 1)​​​​​​​ without exceeding cost k. Thus, the answer is -1.

 

# Constraints:

# 1 <= m, n <= 200
# 0 <= k <= 103​​​​​​​
# ​​​​​​​grid[0][0] == 0
# 0 <= grid[i][j] <= 2



# Solution:




class Solution(object):
    def maxPathScore(self, grid, k):
        """
        :type grid: List[List[int]]
        :type k: int
        :rtype: int
        """
        rows, cols = len(grid), len(grid[0])
        NO = float('-inf')

        # dp[i][j][c] = max score reaching (i,j) with c non-zero cells used so far
        dp = [[[-1] * (k + 1) for _ in range(cols)] for _ in range(rows)]

        def f(i, j, c):
            if i < 0 or j < 0 or c > k:
                return NO

            if dp[i][j][c] != -1:
                return dp[i][j][c]

            x = grid[i][j]
            cost = c + (1 if x != 0 else 0)

            if cost > k:
                dp[i][j][c] = NO
                return NO

            if i == 0 and j == 0:
                dp[i][j][c] = grid[0][0]
                return dp[i][j][c]

            up = f(i - 1, j, cost)
            left = f(i, j - 1, cost)

            best = max(up, left)

            if best == NO:
                dp[i][j][c] = NO
            else:
                dp[i][j][c] = x + best

            return dp[i][j][c]

        ans = f(rows - 1, cols - 1, 0)
        return -1 if ans < 0 else ans