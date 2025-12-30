# 840. Magic Squares In Grid

# A 3 x 3 magic square is a 3 x 3 grid filled with distinct numbers from 1 to 9 such that each row, column, and both diagonals all have the same sum.

# Given a row x col grid of integers, how many 3 x 3 magic square subgrids are there?

# Note: while a magic square can only contain numbers from 1 to 9, grid may contain numbers up to 15.

 

# Example 1:


# Input: grid = [[4,3,8,4],[9,5,1,9],[2,7,6,2]]
# Output: 1
# Explanation: 
# The following subgrid is a 3 x 3 magic square:

# while this one is not:

# In total, there is only one magic square inside the given grid.
# Example 2:

# Input: grid = [[8]]
# Output: 0
 

# Constraints:

# row == grid.length
# col == grid[i].length
# 1 <= row, col <= 10
# 0 <= grid[i][j] <= 15


# Solution: 


class Solution(object):
    def numMagicSquaresInside(self, grid):
        r, c = len(grid), len(grid[0])
        if r < 3 or c < 3:
            return 0

        ans = 0
        for i in range(r - 2):
            for j in range(c - 2):
                used = [False] * 10
                ok = True

                for x in range(3):
                    for y in range(3):
                        v = grid[i + x][j + y]
                        if v < 1 or v > 9 or used[v]:
                            ok = False
                            break
                        used[v] = True
                    if not ok:
                        break
                if not ok:
                    continue

                s = grid[i][j] + grid[i][j+1] + grid[i][j+2]
                for x in range(3):
                    if grid[i+x][j] + grid[i+x][j+1] + grid[i+x][j+2] != s:
                        ok = False
                for y in range(3):
                    if grid[i][j+y] + grid[i+1][j+y] + grid[i+2][j+y] != s:
                        ok = False
                if grid[i][j] + grid[i+1][j+1] + grid[i+2][j+2] != s:
                    ok = False
                if grid[i][j+2] + grid[i+1][j+1] + grid[i+2][j] != s:
                    ok = False

                if ok:
                    ans += 1

        return ans