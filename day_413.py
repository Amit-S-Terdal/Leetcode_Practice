# 3212. Count Submatrices With Equal Frequency of X and Y

# Given a 2D character matrix grid, where grid[i][j] is either 'X', 'Y', or '.', return the number of submatrices that contain:

# grid[0][0]
# an equal frequency of 'X' and 'Y'.
# at least one 'X'.
 

# Example 1:

# Input: grid = [["X","Y","."],["Y",".","."]]

# Output: 3

# Explanation:



# Example 2:

# Input: grid = [["X","X"],["X","Y"]]

# Output: 0

# Explanation:

# No submatrix has an equal frequency of 'X' and 'Y'.

# Example 3:

# Input: grid = [[".","."],[".","."]]

# Output: 0

# Explanation:

# No submatrix has at least one 'X'.

 

# Constraints:

# 1 <= grid.length, grid[i].length <= 1000
# grid[i][j] is either 'X', 'Y', or '.'.


# Solution: 




class Solution(object):
    def numberOfSubmatrices(self, grid):
        """
        :type grid: List[List[str]]
        :rtype: int
        """
        r, c = len(grid), len(grid[0])
        
        # Only 2 rows needed (rolling array)
        XY = [[0] * (c + 1) for _ in range(2)]
        
        cnt = 0
        
        for i in range(r):
            iEven = i & 1
            prv = iEven ^ 1
            
            for j in range(c):
                ch = grid[i][j]
                isX = 1 if ch == 'X' else 0
                isY = 1 if ch == 'Y' else 0
                
                # pack into one integer (like uint64)
                dp = (isX << 32) + isY \
                     + XY[iEven][j] \
                     + XY[prv][j + 1] \
                     - XY[prv][j]
                
                XY[iEven][j + 1] = dp
                
                cntX = dp >> 32
                cntY = dp & 0xFFFFFFFF
                
                if cntX > 0 and cntX == cntY:
                    cnt += 1
        
        return cnt