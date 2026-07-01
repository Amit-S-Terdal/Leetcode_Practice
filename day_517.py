# 2812. Find the Safest Path in a Grid

# You are given a 0-indexed 2D matrix grid of size n x n, where (r, c) represents:

# A cell containing a thief if grid[r][c] = 1
# An empty cell if grid[r][c] = 0
# You are initially positioned at cell (0, 0). In one move, you can move to any adjacent cell in the grid, including cells containing thieves.

# The safeness factor of a path on the grid is defined as the minimum manhattan distance from any cell in the path to any thief in the grid.

# Return the maximum safeness factor of all paths leading to cell (n - 1, n - 1).

# An adjacent cell of cell (r, c), is one of the cells (r, c + 1), (r, c - 1), (r + 1, c) and (r - 1, c) if it exists.

# The Manhattan distance between two cells (a, b) and (x, y) is equal to |a - x| + |b - y|, where |val| denotes the absolute value of val.

 

# Example 1:


# Input: grid = [[1,0,0],[0,0,0],[0,0,1]]
# Output: 0
# Explanation: All paths from (0, 0) to (n - 1, n - 1) go through the thieves in cells (0, 0) and (n - 1, n - 1).
# Example 2:


# Input: grid = [[0,0,1],[0,0,0],[0,0,0]]
# Output: 2
# Explanation: The path depicted in the picture above has a safeness factor of 2 since:
# - The closest cell of the path to the thief at cell (0, 2) is cell (0, 0). The distance between them is | 0 - 0 | + | 0 - 2 | = 2.
# It can be shown that there are no other paths with a higher safeness factor.
# Example 3:


# Input: grid = [[0,0,0,1],[0,0,0,0],[0,0,0,0],[1,0,0,0]]
# Output: 2
# Explanation: The path depicted in the picture above has a safeness factor of 2 since:
# - The closest cell of the path to the thief at cell (0, 3) is cell (1, 2). The distance between them is | 0 - 1 | + | 3 - 2 | = 2.
# - The closest cell of the path to the thief at cell (3, 0) is cell (3, 2). The distance between them is | 3 - 3 | + | 0 - 2 | = 2.
# It can be shown that there are no other paths with a higher safeness factor.
 

# Constraints:

# 1 <= grid.length == n <= 400
# grid[i].length == n
# grid[i][j] is either 0 or 1.
# There is at least one thief in the grid.



# Solution: 




class Solution(object):
    def maximumSafenessFactor(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: int
        """

        from collections import deque

        n = len(grid)

        if grid[0][0] == 1 or grid[n-1][n-1] == 1:
            return 0


        parent = list(range(n * n))
        rank = [0] * (n * n)


        def find(x):
            if parent[x] != x:
                parent[x] = find(parent[x])
            return parent[x]


        def union(a, b):
            ra = find(a)
            rb = find(b)

            if ra == rb:
                return False

            if rank[ra] > rank[rb]:
                ra, rb = rb, ra

            parent[ra] = rb

            if rank[ra] == rank[rb]:
                rank[rb] += 1

            return True


        def connected(a, b):
            return find(a) == find(b)


        def to1d(i, j):
            return i * n + j


        directions = [(0,1),(1,0),(0,-1),(-1,0)]


        # Multi-source BFS
        q = deque()

        for i in range(n):
            for j in range(n):
                if grid[i][j] == 1:
                    grid[i][j] = 0
                    q.append((i, j))
                else:
                    grid[i][j] = -1


        levels = [[] for _ in range(n * n)]


        dist = 0

        while q:

            for _ in range(len(q)):

                i, j = q.popleft()

                for di, dj in directions:

                    r = i + di
                    c = j + dj

                    if 0 <= r < n and 0 <= c < n and grid[r][c] == -1:

                        grid[r][c] = dist + 1

                        q.append((r, c))

                        levels[dist + 1].append(
                            to1d(r, c)
                        )

            dist += 1



        # Union cells from highest safety to lowest
        for level in range(dist - 1, -1, -1):

            for idx in levels[level]:

                i = idx // n
                j = idx % n


                for di, dj in directions:

                    r = i + di
                    c = j + dj


                    if 0 <= r < n and 0 <= c < n:

                        if grid[r][c] >= level:

                            union(
                                to1d(i, j),
                                to1d(r, c)
                            )


            if connected(0, n*n - 1):
                return level


        return 0