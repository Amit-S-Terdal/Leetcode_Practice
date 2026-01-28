# 3651. Minimum Cost Path with Teleportations

# You are given a m x n 2D integer array grid and an integer k. You start at the top-left cell (0, 0) and your goal is to reach the bottom‐right cell (m - 1, n - 1).

# There are two types of moves available:

# Normal move: You can move right or down from your current cell (i, j), i.e. you can move to (i, j + 1) (right) or (i + 1, j) (down). The cost is the value of the destination cell.

# Teleportation: You can teleport from any cell (i, j), to any cell (x, y) such that grid[x][y] <= grid[i][j]; the cost of this move is 0. You may teleport at most k times.

# Return the minimum total cost to reach cell (m - 1, n - 1) from (0, 0).

 

# Example 1:

# Input: grid = [[1,3,3],[2,5,4],[4,3,5]], k = 2

# Output: 7

# Explanation:

# Initially we are at (0, 0) and cost is 0.

# Current Position	Move	New Position	Total Cost
# (0, 0)	Move Down	(1, 0)	0 + 2 = 2
# (1, 0)	Move Right	(1, 1)	2 + 5 = 7
# (1, 1)	Teleport to (2, 2)	(2, 2)	7 + 0 = 7
# The minimum cost to reach bottom-right cell is 7.

# Example 2:

# Input: grid = [[1,2],[2,3],[3,4]], k = 1

# Output: 9

# Explanation:

# Initially we are at (0, 0) and cost is 0.

# Current Position	Move	New Position	Total Cost
# (0, 0)	Move Down	(1, 0)	0 + 2 = 2
# (1, 0)	Move Right	(1, 1)	2 + 3 = 5
# (1, 1)	Move Down	(2, 1)	5 + 4 = 9
# The minimum cost to reach bottom-right cell is 9.

 

# Constraints:

# 2 <= m, n <= 80
# m == grid.length
# n == grid[i].length
# 0 <= grid[i][j] <= 10^4
# 0 <= k <= 10




# Solution: 


class Solution(object):
    def minCost(self, grid, k):
        """
        :type grid: List[List[int]]
        :type k: int
        :rtype: int
        """
        INF = 10**9 + 7

        r, c = len(grid), len(grid[0])
        N = r * c

        # 1D index
        def idx(i, j):
            return i * c + j

        # Group positions by value
        x_pos = {}
        xMax = 0
        for i in range(r):
            for j in range(c):
                x = grid[i][j]
                xMax = max(xMax, x)
                x_pos.setdefault(x, []).append(idx(i, j))

        # dp[t][pos]: min cost with <= t teleports
        dp = [[INF] * N for _ in range(k + 1)]

        # Base case: 0 teleports
        dp[0][0] = 0
        for i in range(r):
            for j in range(c):
                pos = idx(i, j)
                x = grid[i][j]
                if i > 0:
                    dp[0][pos] = min(dp[0][pos], dp[0][idx(i - 1, j)] + x)
                if j > 0:
                    dp[0][pos] = min(dp[0][pos], dp[0][idx(i, j - 1)] + x)

        # DP with teleports
        suffixMin = [INF] * (xMax + 2)

        for t in range(1, k + 1):
            currMin = INF

            # Compute suffix minimums
            for x in range(xMax, -1, -1):
                if x in x_pos:
                    for pos in x_pos[x]:
                        currMin = min(currMin, dp[t - 1][pos])
                suffixMin[x] = currMin

            # Update dp[t]
            for i in range(r):
                for j in range(c):
                    pos = idx(i, j)
                    x = grid[i][j]

                    # No teleport or teleport
                    dp[t][pos] = min(dp[t - 1][pos], suffixMin[x])

                    # Move normally
                    if i > 0:
                        dp[t][pos] = min(dp[t][pos], dp[t][idx(i - 1, j)] + x)
                    if j > 0:
                        dp[t][pos] = min(dp[t][pos], dp[t][idx(i, j - 1)] + x)

        return dp[k][N - 1]
