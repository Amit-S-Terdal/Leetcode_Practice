# 1559. Detect Cycles in 2D Grid

# Given a 2D array of characters grid of size m x n, you need to find if there exists any cycle consisting of the same value in grid.

# A cycle is a path of length 4 or more in the grid that starts and ends at the same cell. From a given cell, you can move to one of the cells adjacent to it - in one of the four directions (up, down, left, or right), if it has the same value of the current cell.

# Also, you cannot move to the cell that you visited in your last move. For example, the cycle (1, 1) -> (1, 2) -> (1, 1) is invalid because from (1, 2) we visited (1, 1) which was the last visited cell.

# Return true if any cycle of the same value exists in grid, otherwise, return false.

 

# Example 1:



# Input: grid = [["a","a","a","a"],["a","b","b","a"],["a","b","b","a"],["a","a","a","a"]]
# Output: true
# Explanation: There are two valid cycles shown in different colors in the image below:

# Example 2:



# Input: grid = [["c","c","c","a"],["c","d","c","c"],["c","c","e","c"],["f","c","c","c"]]
# Output: true
# Explanation: There is only one valid cycle highlighted in the image below:

# Example 3:



# Input: grid = [["a","b","b"],["b","z","b"],["b","b","a"]]
# Output: false
 

# Constraints:

# m == grid.length
# n == grid[i].length
# 1 <= m, n <= 500
# grid consists only of lowercase English letters.


# Solution: 



class Solution(object):
    def containsCycle(self, grid):
        """
        :type grid: List[List[str]]
        :rtype: bool
        """
        r, c = len(grid), len(grid[0])
        parent = list(range(r * c))
        rank = [0] * (r * c)

        def find(x):
            if parent[x] != x:
                parent[x] = find(parent[x])  # path compression
            return parent[x]

        def union(x, y):
            rx, ry = find(x), find(y)
            if rx == ry:
                return False  # cycle detected
            if rank[rx] > rank[ry]:
                parent[ry] = rx
            elif rank[rx] < rank[ry]:
                parent[rx] = ry
            else:
                parent[rx] = ry
                rank[ry] += 1
            return True

        for i in range(r - 1):
            row_idx = i * c
            for j in range(c - 1):
                cur = row_idx + j
                ch = grid[i][j]

                # down
                if grid[i + 1][j] == ch:
                    if not union(cur, cur + c):
                        return True

                # right
                if grid[i][j + 1] == ch:
                    if not union(cur, cur + 1):
                        return True

            # last column (down only)
            cur = i * c + (c - 1)
            if grid[i][c - 1] == grid[i + 1][c - 1]:
                if not union(cur, cur + c):
                    return True

        # last row (right only)
        row_idx = (r - 1) * c
        for j in range(c - 1):
            cur = row_idx + j
            if grid[r - 1][j] == grid[r - 1][j + 1]:
                if not union(cur, cur + 1):
                    return True

        return False