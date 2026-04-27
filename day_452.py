# 1391. Check if There is a Valid Path in a Grid

# You are given an m x n grid. Each cell of grid represents a street. The street of grid[i][j] can be:

# 1 which means a street connecting the left cell and the right cell.
# 2 which means a street connecting the upper cell and the lower cell.
# 3 which means a street connecting the left cell and the lower cell.
# 4 which means a street connecting the right cell and the lower cell.
# 5 which means a street connecting the left cell and the upper cell.
# 6 which means a street connecting the right cell and the upper cell.

# You will initially start at the street of the upper-left cell (0, 0). A valid path in the grid is a path that starts from the upper left cell (0, 0) and ends at the bottom-right cell (m - 1, n - 1). The path should only follow the streets.

# Notice that you are not allowed to change any street.

# Return true if there is a valid path in the grid or false otherwise.

 

# Example 1:


# Input: grid = [[2,4,3],[6,5,2]]
# Output: true
# Explanation: As shown you can start at cell (0, 0) and visit all the cells of the grid to reach (m - 1, n - 1).
# Example 2:


# Input: grid = [[1,2,1],[1,2,1]]
# Output: false
# Explanation: As shown you the street at cell (0, 0) is not connected with any street of any other cell and you will get stuck at cell (0, 0)
# Example 3:

# Input: grid = [[1,1,2]]
# Output: false
# Explanation: You will get stuck at cell (0, 1) and you cannot reach cell (0, 2).
 

# Constraints:

# m == grid.length
# n == grid[i].length
# 1 <= m, n <= 300
# 1 <= grid[i][j] <= 6



# Solution:


class Solution(object):
    def hasValidPath(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: bool
        """
        r, c = len(grid), len(grid[0])
        N = r * c

        root = list(range(N))
        rank = [0] * N

        def find(x):
            if root[x] != x:
                root[x] = find(root[x])  # path compression
            return root[x]

        def union(x, y):
            rx, ry = find(x), find(y)
            if rx == ry:
                return False
            if rank[rx] > rank[ry]:
                rx, ry = ry, rx
            root[rx] = ry
            if rank[rx] == rank[ry]:
                rank[ry] += 1
            return True

        def connected(x, y):
            return find(x) == find(y)

        def idx(i, j):
            return i * c + j

        # main grid traversal
        for i in range(r - 1):
            for j in range(c - 1):
                C = grid[i][j]
                R = grid[i][j + 1]
                D = grid[i + 1][j]

                cc = idx(i, j)
                rr = cc + 1
                dd = cc + c

                CR = (C == 1) or (C == 4) or (C == 6)
                RC = (R & 1) == 1

                CD = (C == 2) or (C == 4) or (C == 3)
                DC = (D == 2) or (D == 5) or (D == 6)

                if CR and RC:
                    union(cc, rr)

                if CD and DC:
                    union(cc, dd)

            # last column (down)
            C = grid[i][c - 1]
            D = grid[i + 1][c - 1]

            cc = idx(i, c - 1)
            dd = cc + c

            CD = (C == 2) or (C == 4) or (C == 3)
            DC = (D == 2) or (D == 5) or (D == 6)

            if CD and DC:
                union(cc, dd)

        # last row (right)
        for j in range(c - 1):
            C = grid[r - 1][j]
            R = grid[r - 1][j + 1]

            cc = idx(r - 1, j)
            rr = cc + 1

            CR = (C == 1) or (C == 4) or (C == 6)
            RC = (R & 1) == 1

            if CR and RC:
                union(cc, rr)

        return connected(0, N - 1)
