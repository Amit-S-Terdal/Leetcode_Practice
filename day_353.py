# 1895. Largest Magic Square

# A k x k magic square is a k x k grid filled with integers such that every row sum, every column sum, and both diagonal sums are all equal. The integers in the magic square do not have to be distinct. Every 1 x 1 grid is trivially a magic square.

# Given an m x n integer grid, return the size (i.e., the side length k) of the largest magic square that can be found within this grid.

 

# Example 1:


# Input: grid = [[7,1,4,5,6],[2,5,1,6,4],[1,5,4,3,2],[1,2,7,3,4]]
# Output: 3
# Explanation: The largest magic square has a size of 3.
# Every row sum, column sum, and diagonal sum of this magic square is equal to 12.
# - Row sums: 5+1+6 = 5+4+3 = 2+7+3 = 12
# - Column sums: 5+5+2 = 1+4+7 = 6+3+3 = 12
# - Diagonal sums: 5+4+3 = 6+4+2 = 12
# Example 2:


# Input: grid = [[5,1,3,1],[9,3,3,1],[1,3,3,8]]
# Output: 2
 

# Constraints:

# m == grid.length
# n == grid[i].length
# 1 <= m, n <= 50
# 1 <= grid[i][j] <= 10^6



# Solution: 




class Solution(object):
    def largestMagicSquare(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: int
        """
        r, c = len(grid), len(grid[0])

        # row prefix sums
        rowSum = [[0] * (c + 1) for _ in range(r)]
        for i in range(r):
            for j in range(c):
                rowSum[i][j + 1] = rowSum[i][j] + grid[i][j]

        # column prefix sums (using transpose idea)
        colSum = [[0] * (r + 1) for _ in range(c)]
        for j in range(c):
            for i in range(r):
                colSum[j][i + 1] = colSum[j][i] + grid[i][j]

        # diagonal and anti-diagonal prefix sums
        diag = [[0] * (c + 1) for _ in range(r + 1)]
        antidiag = [[0] * (c + 1) for _ in range(r + 1)]

        for i in range(r):
            for j in range(c):
                x = grid[i][j]
                diag[i + 1][j + 1] = diag[i][j] + x
                antidiag[i + 1][j] = antidiag[i][j + 1] + x

        def isMagic(k):
            for i in range(r - k + 1):
                for j in range(c - k + 1):
                    total = diag[i + k][j + k] - diag[i][j]
                    anti = antidiag[i + k][j] - antidiag[i][j + k]

                    if total != anti:
                        continue

                    match = True
                    for m in range(k):
                        if (rowSum[i + m][j + k] - rowSum[i + m][j] != total or
                            colSum[j + m][i + k] - colSum[j + m][i] != total):
                            match = False
                            break

                    if match:
                        return True
            return False

        for k in range(min(r, c), 1, -1):
            if isMagic(k):
                return k

        return 1
