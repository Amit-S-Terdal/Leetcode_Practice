# 2503. Maximum Number of Points From Grid Queries

# You are given an m x n integer matrix grid and an array queries of size k.

# Find an array answer of size k such that for each integer queries[i] you start in the top left cell of the matrix and repeat the following process:

# If queries[i] is strictly greater than the value of the current cell that you are in, then you get one point if it is your first time visiting this cell, and you can move to any adjacent cell in all 4 directions: up, down, left, and right.
# Otherwise, you do not get any points, and you end this process.
# After the process, answer[i] is the maximum number of points you can get. Note that for each query you are allowed to visit the same cell multiple times.

# Return the resulting array answer.

 

# Example 1:


# Input: grid = [[1,2,3],[2,5,7],[3,5,1]], queries = [5,6,2]
# Output: [5,8,1]
# Explanation: The diagrams above show which cells we visit to get points for each query.
# Example 2:


# Input: grid = [[5,2,1],[1,1,2]], queries = [3]
# Output: [0]
# Explanation: We can not get any points because the value of the top left cell is already greater than or equal to 3.
 

# Constraints:

# m == grid.length
# n == grid[i].length
# 2 <= m, n <= 1000
# 4 <= m * n <= 105
# k == queries.length
# 1 <= k <= 104
# 1 <= grid[i][j], queries[i] <= 10^6


# Solution: 


import heapq

class Solution(object):
    def maxPoints(self, grid, queries):
        m, n = len(grid), len(grid[0])
        visited = [[False]*n for _ in range(m)]
        heap = [(grid[0][0], 0, 0)]  # Min-heap of (value, row, col)
        visited[0][0] = True
        directions = [(0,1), (0,-1), (1,0), (-1,0)]

        # Sort queries and prepare answer placeholders
        sorted_queries = sorted([(val, idx) for idx, val in enumerate(queries)])
        result = [0]*len(queries)

        points = 0  # Number of unique points we can visit under current threshold
        idx = 0  # Index for processing queries

        # Heap-based BFS traversal
        while idx < len(sorted_queries):
            q_val, q_idx = sorted_queries[idx]

            # Expand heap while current grid value < query
            while heap and heap[0][0] < q_val:
                val, x, y = heapq.heappop(heap)
                points += 1
                for dx, dy in directions:
                    nx, ny = x+dx, y+dy
                    if 0 <= nx < m and 0 <= ny < n and not visited[nx][ny]:
                        heapq.heappush(heap, (grid[nx][ny], nx, ny))
                        visited[nx][ny] = True

            # All queries with this threshold get current points count
            while idx < len(sorted_queries) and sorted_queries[idx][0] == q_val:
                _, q_idx = sorted_queries[idx]
                result[q_idx] = points
                idx += 1

        return result
