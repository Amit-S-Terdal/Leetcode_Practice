# 827. Making A Large Island


# You are given an n x n binary matrix grid. You are allowed to change at most one 0 to be 1.

# Return the size of the largest island in grid after applying this operation.

# An island is a 4-directionally connected group of 1s.

 

# Example 1:

# Input: grid = [[1,0],[0,1]]
# Output: 3
# Explanation: Change one 0 to 1 and connect two 1s, then we get an island with area = 3.
# Example 2:

# Input: grid = [[1,1],[1,0]]
# Output: 4
# Explanation: Change the 0 to 1 and make the island bigger, only one island with area = 4.
# Example 3:

# Input: grid = [[1,1],[1,1]]
# Output: 4
# Explanation: Can't change any 0 to 1, only one island with area = 4.
 

# Constraints:

# n == grid.length
# n == grid[i].length
# 1 <= n <= 500
# grid[i][j] is either 0 or 1.

# Solution:





class Solution(object):
    def largestIsland(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: int
        """
        n = len(grid)
        island_id = 2  # Start assigning island IDs from 2
        island_sizes = {}  # Dictionary to store island sizes
        
        # Step 1: Assign IDs to islands and calculate their sizes
        for i in range(n):
            for j in range(n):
                if grid[i][j] == 1:
                    size = self.dfs(grid, i, j, island_id)
                    island_sizes[island_id] = size
                    island_id += 1
        
        # Step 2: Find the maximum island size after changing one 0 to 1
        # Handle the case where island_sizes is empty
        max_size = max(island_sizes.values()) if island_sizes else 0
        
        for i in range(n):
            for j in range(n):
                if grid[i][j] == 0:
                    neighbors = set()
                    for dx, dy in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
                        x, y = i + dx, j + dy
                        if 0 <= x < n and 0 <= y < n and grid[x][y] != 0:
                            neighbors.add(grid[x][y])
                    
                    total = 1  # The current cell being changed to 1
                    for neighbor_id in neighbors:
                        total += island_sizes.get(neighbor_id, 0)
                    
                    max_size = max(max_size, total)
        
        return max_size
    
    def dfs(self, grid, i, j, island_id):
        n = len(grid)
        if i < 0 or i >= n or j < 0 or j >= n or grid[i][j] != 1:
            return 0
        grid[i][j] = island_id
        size = 1
        for dx, dy in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
            size += self.dfs(grid, i + dx, j + dy, island_id)
        return size