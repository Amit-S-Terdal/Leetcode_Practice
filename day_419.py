# 3546. Equal Sum Grid Partition I

# You are given an m x n matrix grid of positive integers. Your task is to determine if it is possible to make either one horizontal or one vertical cut on the grid such that:

# Each of the two resulting sections formed by the cut is non-empty.
# The sum of the elements in both sections is equal.
# Return true if such a partition exists; otherwise return false.

 

# Example 1:

# Input: grid = [[1,4],[2,3]]

# Output: true

# Explanation:



# A horizontal cut between row 0 and row 1 results in two non-empty sections, each with a sum of 5. Thus, the answer is true.

# Example 2:

# Input: grid = [[1,3],[2,4]]

# Output: false

# Explanation:

# No horizontal or vertical cut results in two non-empty sections with equal sums. Thus, the answer is false.

 

# Constraints:

# 1 <= m == grid.length <= 10^5
# 1 <= n == grid[i].length <= 10^5
# 2 <= m * n <= 10^5
# 1 <= grid[i][j] <= 10^5


# Solution:



class Solution(object):
    def canPartitionGrid(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: bool
        """
        r, c = len(grid), len(grid[0])

        rowSum = [0] * r
        colSum = [0] * c

        # Build prefix sums for rows and columns
        for i in range(r):
            if i > 0:
                rowSum[i] += rowSum[i - 1]
            for j in range(c):
                x = grid[i][j]
                rowSum[i] += x
                colSum[j] += x

        # Prefix sum for columns
        for j in range(1, c):
            colSum[j] += colSum[j - 1]

        totalSum = colSum[-1]

        # If total sum is odd, cannot partition
        if totalSum % 2 != 0:
            return False

        target = totalSum // 2

        # Check row partition
        import bisect
        i = bisect.bisect_left(rowSum, target)
        if i < r and rowSum[i] == target:
            return True

        # Check column partition
        j = bisect.bisect_left(colSum, target)
        return j < c and colSum[j] == target