# 3286. Find a Safe Walk Through a Grid

# You are given an m x n binary matrix grid and an integer health.

# You start on the upper-left corner (0, 0) and would like to get to the lower-right corner (m - 1, n - 1).

# You can move up, down, left, or right from one cell to another adjacent cell as long as your health remains positive.

# Cells (i, j) with grid[i][j] = 1 are considered unsafe and reduce your health by 1.

# Return true if you can reach the final cell with a health value of 1 or more, and false otherwise.

 

# Example 1:

# Input: grid = [[0,1,0,0,0],[0,1,0,1,0],[0,0,0,1,0]], health = 1

# Output: true

# Explanation:

# The final cell can be reached safely by walking along the gray cells below.


# Example 2:

# Input: grid = [[0,1,1,0,0,0],[1,0,1,0,0,0],[0,1,1,1,0,1],[0,0,1,0,1,0]], health = 3

# Output: false

# Explanation:

# A minimum of 4 health points is needed to reach the final cell safely.


# Example 3:

# Input: grid = [[1,1,1],[1,0,1],[1,1,1]], health = 5

# Output: true

# Explanation:

# The final cell can be reached safely by walking along the gray cells below.



# Any path that does not go through the cell (1, 1) is unsafe since your health will drop to 0 when reaching the final cell.

 

# Constraints:

# m == grid.length
# n == grid[i].length
# 1 <= m, n <= 50
# 2 <= m * n
# 1 <= health <= m + n
# grid[i][j] is either 0 or 1.




# Solution: 



from collections import deque

class Solution(object):
    def findSafeWalk(self, grid, health):
        """
        :type grid: List[List[int]]
        :type health: int
        :rtype: bool
        """
        r, c = len(grid), len(grid[0])

        maxH = [-1] * (r * c)
        dq = deque()

        maxH[0] = health - grid[0][0]
        dq.append(0)

        d = (0, 1, 0, -1, 0)

        while dq:
            ij = dq.popleft()
            curH = maxH[ij]

            if ij == r * c - 1:
                return curH > 0

            i, j = divmod(ij, c)

            for k in range(4):
                ni = i + d[k]
                nj = j + d[k + 1]

                if ni < 0 or ni >= r or nj < 0 or nj >= c:
                    continue

                nxt = ni * c + nj
                h2 = curH - grid[ni][nj]

                if h2 > maxH[nxt]:
                    maxH[nxt] = h2
                    if grid[ni][nj] == 0:
                        dq.appendleft(nxt)
                    else:
                        dq.append(nxt)

        return False