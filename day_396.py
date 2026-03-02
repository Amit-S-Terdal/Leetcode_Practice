# 1536. Minimum Swaps to Arrange a Binary Grid

# Given an n x n binary grid, in one step you can choose two adjacent rows of the grid and swap them.

# A grid is said to be valid if all the cells above the main diagonal are zeros.

# Return the minimum number of steps needed to make the grid valid, or -1 if the grid cannot be valid.

# The main diagonal of a grid is the diagonal that starts at cell (1, 1) and ends at cell (n, n).

 

# Example 1:


# Input: grid = [[0,0,1],[1,1,0],[1,0,0]]
# Output: 3
# Example 2:


# Input: grid = [[0,1,1,0],[0,1,1,0],[0,1,1,0],[0,1,1,0]]
# Output: -1
# Explanation: All rows are similar, swaps have no effect on the grid.
# Example 3:


# Input: grid = [[1,0,0],[1,1,0],[1,1,1]]
# Output: 0
 

# Constraints:

# n == grid.length == grid[i].length
# 1 <= n <= 200
# grid[i][j] is either 0 or 1



# Solution: 



class Solution(object):
    def minSwaps(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: int
        """
        n = len(grid)
        
        # R1[i] = position of rightmost 1 in row i
        R1 = []
        for i in range(n):
            pos = -1
            for j in range(n - 1, -1, -1):
                if grid[i][j] == 1:
                    pos = j
                    break
            R1.append(pos)
        
        swaps = 0
        
        for i in range(n):
            if R1[i] <= i:
                continue
            
            j = i + 1
            
            # Find first row j such that R1[j] <= i
            while j < n and R1[j] > i:
                j += 1
            
            if j == n:
                return -1
            
            swaps += j - i
            
            # Bubble row j up to position i
            value = R1[j]
            while j > i:
                R1[j] = R1[j - 1]
                j -= 1
            R1[i] = value
        
        return swaps