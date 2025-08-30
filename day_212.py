# 3459. Length of Longest V-Shaped Diagonal Segment

# You are given a 2D integer matrix grid of size n x m, where each element is either 0, 1, or 2.

# A V-shaped diagonal segment is defined as:

# The segment starts with 1.
# The subsequent elements follow this infinite sequence: 2, 0, 2, 0, ....
# The segment:
# Starts along a diagonal direction (top-left to bottom-right, bottom-right to top-left, top-right to bottom-left, or bottom-left to top-right).
# Continues the sequence in the same diagonal direction.
# Makes at most one clockwise 90-degree turn to another diagonal direction while maintaining the sequence.


# Return the length of the longest V-shaped diagonal segment. If no valid segment exists, return 0.

 

# Example 1:

# Input: grid = [[2,2,1,2,2],[2,0,2,2,0],[2,0,1,1,0],[1,0,2,2,2],[2,0,0,2,2]]

# Output: 5

# Explanation:



# The longest V-shaped diagonal segment has a length of 5 and follows these coordinates: (0,2) → (1,3) → (2,4), takes a 90-degree clockwise turn at (2,4), and continues as (3,3) → (4,2).

# Example 2:

# Input: grid = [[2,2,2,2,2],[2,0,2,2,0],[2,0,1,1,0],[1,0,2,2,2],[2,0,0,2,2]]

# Output: 4

# Explanation:



# The longest V-shaped diagonal segment has a length of 4 and follows these coordinates: (2,3) → (3,2), takes a 90-degree clockwise turn at (3,2), and continues as (2,1) → (1,0).

# Example 3:

# Input: grid = [[1,2,2,2,2],[2,2,2,2,0],[2,0,0,0,0],[0,0,2,2,2],[2,0,0,2,0]]

# Output: 5

# Explanation:



# The longest V-shaped diagonal segment has a length of 5 and follows these coordinates: (0,0) → (1,1) → (2,2) → (3,3) → (4,4).

# Example 4:

# Input: grid = [[1]]

# Output: 1

# Explanation:

# The longest V-shaped diagonal segment has a length of 1 and follows these coordinates: (0,0).

 

# Constraints:

# n == grid.length
# m == grid[i].length
# 1 <= n, m <= 500
# grid[i][j] is either 0, 1 or 2.


# Solution: 

class Solution(object):
    def lenOfVDiagonal(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: int
        """
        # Python 2/3 compatible xrange
        try:
            xrange  # Python 2
        except NameError:
            xrange = range  # Python 3

        d = (1, 1, -1, -1, 1)  # (1,1), (1,-1), (-1,-1), (-1,1)
        n, m = len(grid), len(grid[0])

        def isOutSide(i, j):
            return i < 0 or i >= n or j < 0 or j >= m

        zero = [0] * 4
        dp = [[zero[:] for _ in xrange(m)] for _ in xrange(n)]

        # Down-right (dir 0) and down-left (dir 1)
        for i in xrange(n - 2, -1, -1):
            for j in xrange(m - 2, -1, -1):
                if (grid[i + 1][j + 1] ^ 2) == grid[i][j]:
                    dp[i][j][0] = 1 + dp[i + 1][j + 1][0]
            for j in xrange(1, m):
                if (grid[i + 1][j - 1] ^ 2) == grid[i][j]:
                    dp[i][j][1] = 1 + dp[i + 1][j - 1][1]

        # Up-left (dir 2) and up-right (dir 3)
        for i in xrange(1, n):
            for j in xrange(1, m):
                if (grid[i - 1][j - 1] ^ 2) == grid[i][j]:
                    dp[i][j][2] = 1 + dp[i - 1][j - 1][2]
            for j in xrange(m - 2, -1, -1):
                if (grid[i - 1][j + 1] ^ 2) == grid[i][j]:
                    dp[i][j][3] = 1 + dp[i - 1][j + 1][3]

        ans = 0
        for i, row in enumerate(grid):
            for j, x in enumerate(row):
                if grid[i][j] == 1:
                    ans = max(ans, 1)
                    for dir in xrange(4):
                        s = i + d[dir]
                        t = j + d[dir + 1]
                        if isOutSide(s, t) or grid[s][t] != 2:
                            continue
                        newDir = (dir + 1) & 3
                        cnt = 1
                        # Expect alternating 2,0,2,0,... after starting at 1
                        while (not isOutSide(s, t)) and grid[s][t] == ((cnt & 1) << 1):
                            ans = max(ans, cnt + dp[s][t][newDir] + 1)
                            cnt += 1
                            s += d[dir]
                            t += d[dir + 1]
        return ans
