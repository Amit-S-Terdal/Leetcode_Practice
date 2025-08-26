# 3197. Find the Minimum Area to Cover All Ones II

# You are given a 2D binary array grid. You need to find 3 non-overlapping rectangles having non-zero areas with horizontal and vertical sides such that all the 1's in grid lie inside these rectangles.

# Return the minimum possible sum of the area of these rectangles.

# Note that the rectangles are allowed to touch.

 

# Example 1:

# Input: grid = [[1,0,1],[1,1,1]]

# Output: 5

# Explanation:



# The 1's at (0, 0) and (1, 0) are covered by a rectangle of area 2.
# The 1's at (0, 2) and (1, 2) are covered by a rectangle of area 2.
# The 1 at (1, 1) is covered by a rectangle of area 1.
# Example 2:

# Input: grid = [[1,0,1,0],[0,1,0,1]]

# Output: 5

# Explanation:



# The 1's at (0, 0) and (0, 2) are covered by a rectangle of area 3.
# The 1 at (1, 1) is covered by a rectangle of area 1.
# The 1 at (1, 3) is covered by a rectangle of area 1.
 

# Constraints:

# 1 <= grid.length, grid[i].length <= 30
# grid[i][j] is either 0 or 1.
# The input is generated such that there are at least three 1's in grid.


# Solution: 


class Solution(object):
    def minimumSum(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: int
        """
        n, m = len(grid), len(grid[0])

        # Build bitmasks for rows (A) and columns (T)
        A = [0] * n
        T = [0] * m
        for i in range(n):
            rowmask = 0
            for j in range(m):
                if grid[i][j] == 1:
                    rowmask |= (1 << j)
                    T[j] |= (1 << i)
            A[i] = rowmask

        def minRect(i0, iN, j0, jN):
            """Area of bounding box of 1s in submatrix [i0..iN][j0..jN]."""
            iMin, iMax, jMin, jMax = 30, -1, 30, -1

            # top row with any 1 in [j0..jN]
            mask_j = ((1 << (jN + 1)) - 1)
            for i in range(i0, iN + 1):
                row = A[i]
                mRow = ((row >> j0) << j0) & mask_j
                if mRow:
                    iMin = i
                    break
            if iMin == 30:
                return 10**8  # no 1s in this submatrix

            # bottom row
            for i in range(iN, iMin - 1, -1):
                row = A[i]
                mRow = ((row >> j0) << j0) & mask_j
                if mRow:
                    iMax = i
                    break

            # left col
            mask_i = ((1 << (iN + 1)) - 1)
            for j in range(j0, jN + 1):
                col = T[j]
                mCol = ((col >> i0) << i0) & mask_i
                if mCol:
                    jMin = j
                    break

            # right col
            for j in range(jN, jMin - 1, -1):
                col = T[j]
                mCol = ((col >> i0) << i0) & mask_i
                if mCol:
                    jMax = j
                    break

            return (iMax - iMin + 1) * (jMax - jMin + 1)

        ans = float('inf')

        # 2 vertical cuts
        for c1 in range(0, m - 2):
            for c2 in range(c1 + 1, m - 1):
                a = minRect(0, n - 1, 0, c1)
                b = minRect(0, n - 1, c1 + 1, c2)
                c = minRect(0, n - 1, c2 + 1, m - 1)
                ans = min(ans, a + b + c)

        # 2 horizontal cuts
        for r1 in range(0, n - 2):
            for r2 in range(r1 + 1, n - 1):
                a = minRect(0, r1, 0, m - 1)
                b = minRect(r1 + 1, r2, 0, m - 1)
                c = minRect(r2 + 1, n - 1, 0, m - 1)
                ans = min(ans, a + b + c)

        # T-shapes (3-rectangle partitions)
        for r in range(0, n - 1):
            for c in range(0, m - 1):
                # top, bottom-left & bottom-right
                top = minRect(0, r, 0, m - 1)
                bl = minRect(r + 1, n - 1, 0, c)
                br = minRect(r + 1, n - 1, c + 1, m - 1)
                ans = min(ans, top + bl + br)

                # bottom, top-left & top-right
                bottom = minRect(r + 1, n - 1, 0, m - 1)
                tl = minRect(0, r, 0, c)
                tr = minRect(0, r, c + 1, m - 1)
                ans = min(ans, bottom + tl + tr)

                # left, top-right & bottom-right
                left = minRect(0, n - 1, 0, c)
                tr = minRect(0, r, c + 1, m - 1)
                br = minRect(r + 1, n - 1, c + 1, m - 1)
                ans = min(ans, left + tr + br)

                # right, top-left & bottom-left
                right = minRect(0, n - 1, c + 1, m - 1)
                tl = minRect(0, r, 0, c)
                bl = minRect(r + 1, n - 1, 0, c)
                ans = min(ans, right + tl + bl)

        return int(ans)
