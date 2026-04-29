# 3225. Maximum Score From Grid Operations

# You are given a 2D matrix grid of size n x n. Initially, all cells of the grid are colored white. In one operation, you can select any cell of indices (i, j), and color black all the cells of the jth column starting from the top row down to the ith row.

# The grid score is the sum of all grid[i][j] such that cell (i, j) is white and it has a horizontally adjacent black cell.

# Return the maximum score that can be achieved after some number of operations.

 

# Example 1:

# Input: grid = [[0,0,0,0,0],[0,0,3,0,0],[0,1,0,0,0],[5,0,0,3,0],[0,0,0,0,2]]

# Output: 11

# Explanation:


# In the first operation, we color all cells in column 1 down to row 3, and in the second operation, we color all cells in column 4 down to the last row. The score of the resulting grid is grid[3][0] + grid[1][2] + grid[3][3] which is equal to 11.

# Example 2:

# Input: grid = [[10,9,0,0,15],[7,1,0,8,0],[5,20,0,11,0],[0,0,0,1,2],[8,12,1,10,3]]

# Output: 94

# Explanation:


# We perform operations on 1, 2, and 3 down to rows 1, 4, and 0, respectively. The score of the resulting grid is grid[0][0] + grid[1][0] + grid[2][1] + grid[4][1] + grid[1][3] + grid[2][3] + grid[3][3] + grid[4][3] + grid[0][4] which is equal to 94.

 

# Constraints:

# 1 <= n == grid.length <= 100
# n == grid[i].length
# 0 <= grid[i][j] <= 10^9



# Solution: 




class Solution(object):
    def maximumScore(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: int
        """
        n = len(grid)
        if n == 1:
            return 0

        # column prefix sums (1-indexed)
        colSum = [[0] * (n + 1) for _ in range(n)]
        for j in range(n):
            for i in range(n):
                colSum[j][i + 1] = colSum[j][i] + grid[i][j]

        # dp[j][b][state]
        dp = [[[0] * 2 for _ in range(n + 1)] for _ in range(n)]

        for j in range(1, n):
            for b0 in range(n + 1):
                p0 = dp[j - 1][b0][0]
                p1 = dp[j - 1][b0][1]

                for b1 in range(n + 1):
                    isBigger = b1 > b0

                    prvX = (colSum[j - 1][b1] - colSum[j - 1][b0]) if isBigger else 0
                    curX = (colSum[j][b0] - colSum[j][b1]) if not isBigger else 0

                    # State 0: current column exclusive
                    dp[j][b1][0] = max(dp[j][b1][0], max(prvX + p0, p1))

                    # State 1: current column inclusive
                    dp[j][b1][1] = max(dp[j][b1][1], curX + max(p1, prvX + p0))

        ans = 0
        for b in range(n + 1):
            ans = max(ans, dp[n - 1][b][1])

        return ans