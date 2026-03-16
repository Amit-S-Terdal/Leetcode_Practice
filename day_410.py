# 1878. Get Biggest Three Rhombus Sums in a Grid

# You are given an m x n integer matrix grid​​​.

# A rhombus sum is the sum of the elements that form the border of a regular rhombus shape in grid​​​. The rhombus must have the shape of a square rotated 45 degrees with each of the corners centered in a grid cell. Below is an image of four valid rhombus shapes with the corresponding colored cells that should be included in each rhombus sum:


# Note that the rhombus can have an area of 0, which is depicted by the purple rhombus in the bottom right corner.

# Return the biggest three distinct rhombus sums in the grid in descending order. If there are less than three distinct values, return all of them.

 

# Example 1:


# Input: grid = [[3,4,5,1,3],[3,3,4,2,3],[20,30,200,40,10],[1,5,5,4,1],[4,3,2,2,5]]
# Output: [228,216,211]
# Explanation: The rhombus shapes for the three biggest distinct rhombus sums are depicted above.
# - Blue: 20 + 3 + 200 + 5 = 228
# - Red: 200 + 2 + 10 + 4 = 216
# - Green: 5 + 200 + 4 + 2 = 211
# Example 2:


# Input: grid = [[1,2,3],[4,5,6],[7,8,9]]
# Output: [20,9,8]
# Explanation: The rhombus shapes for the three biggest distinct rhombus sums are depicted above.
# - Blue: 4 + 2 + 6 + 8 = 20
# - Red: 9 (area 0 rhombus in the bottom right corner)
# - Green: 8 (area 0 rhombus in the bottom middle)
# Example 3:

# Input: grid = [[7,7,7]]
# Output: [7]
# Explanation: All three possible rhombus sums are the same, so return [7].
 

# Constraints:

# m == grid.length
# n == grid[i].length
# 1 <= m, n <= 50
# 1 <= grid[i][j] <= 10^5


# Solution: 


class Solution(object):
    def getBiggestThree(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: List[int]
        """
        OFFSET = 50
        diag = [[0]*51 for _ in range(100)]
        antid = [[0]*51 for _ in range(100)]

        m, n = len(grid), len(grid[0])

        def rhombusSum(i, j, d):
            if d == 0:
                return grid[i][j]

            l, r = j-d, j+d
            u, b = i-d, i+d

            # diagonals (\)
            i0 = u - j + OFFSET
            i1 = i - l + OFFSET
            s = diag[i0][r+1] - diag[i0][j]
            s += diag[i1][j+1] - diag[i1][l]

            # anti-diagonals (/)
            j0 = u + j
            j1 = b + j
            s += antid[j0][i] - antid[j0][u+1]
            s += antid[j1][b] - antid[j1][i+1]

            return s

        # build prefix sums
        for i in range(m):
            for j in range(n):
                i0 = i - j + OFFSET
                j0 = i + j
                x = grid[i][j]

                diag[i0][j+1] = diag[i0][j] + x
                antid[j0][i+1] = antid[j0][i] + x

        dM = min(m, n) // 2
        top = [-1, -1, -1]

        for d in range(dM + 1):
            for i in range(d, m-d):
                for j in range(d, n-d):

                    y = rhombusSum(i, j, d)

                    if y in top:
                        continue

                    if y > top[0]:
                        top[2] = top[1]
                        top[1] = top[0]
                        top[0] = y
                    elif y > top[1]:
                        top[2] = top[1]
                        top[1] = y
                    elif y > top[2]:
                        top[2] = y

        return [x for x in top if x != -1]
