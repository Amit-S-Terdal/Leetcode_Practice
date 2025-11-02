# 2257. Count Unguarded Cells in the Grid

# You are given two integers m and n representing a 0-indexed m x n grid. You are also given two 2D integer arrays guards and walls where guards[i] = [rowi, coli] and walls[j] = [rowj, colj] represent the positions of the ith guard and jth wall respectively.

# A guard can see every cell in the four cardinal directions (north, east, south, or west) starting from their position unless obstructed by a wall or another guard. A cell is guarded if there is at least one guard that can see it.

# Return the number of unoccupied cells that are not guarded.

 

# Example 1:


# Input: m = 4, n = 6, guards = [[0,0],[1,1],[2,3]], walls = [[0,1],[2,2],[1,4]]
# Output: 7
# Explanation: The guarded and unguarded cells are shown in red and green respectively in the above diagram.
# There are a total of 7 unguarded cells, so we return 7.
# Example 2:


# Input: m = 3, n = 3, guards = [[1,1]], walls = [[0,1],[1,0],[2,1],[1,2]]
# Output: 4
# Explanation: The unguarded cells are shown in green in the above diagram.
# There are a total of 4 unguarded cells, so we return 4.
 

# Constraints:

# 1 <= m, n <= 10^5
# 2 <= m * n <= 10^5
# 1 <= guards.length, walls.length <= 5 * 10^4
# 2 <= guards.length + walls.length <= m * n
# guards[i].length == walls[j].length == 2
# 0 <= rowi, rowj < m
# 0 <= coli, colj < n
# All the positions in guards and walls are unique.



# Solution: 



class Solution(object):
    def countUnguarded(self, m, n, guards, walls):
        """
        :type m: int
        :type n: int
        :type guards: List[List[int]]
        :type walls: List[List[int]]
        :rtype: int
        """
        
        def idx(r, c):
            return r * n + c
        
        d = (0, 1, 0, -1, 0)  # Directions: right, down, left, up
        
        def cross(r, c):
            # Access the list that holds 'comp'
            for a in range(4):
                di, dj = d[a], d[a+1]
                i, j = r + di, c + dj
                while True:
                    pos = idx(i, j)
                    if i < 0 or i >= m or j < 0 or j >= n or grid[pos] == 'X':
                        break
                    if grid[pos] == ' ':
                        comp[0] -= 1  # Decrease unguarded count for each unguarded cell
                    grid[pos] = 'V'
                    i += di
                    j += dj

        comp = [m * n]  # Wrap 'comp' in a list to allow modification
        grid = [' '] * (m * n)  # Create the grid initialized with empty spaces
        
        # Mark the walls
        for ij in walls:
            grid[idx(ij[0], ij[1])] = 'X'
            comp[0] -= 1  # Decrease unguarded count for each wall
        
        # Mark the guards
        for ij in guards:
            grid[idx(ij[0], ij[1])] = 'X'
            comp[0] -= 1  # Decrease unguarded count for each guard
        
        # Mark the cells that are seen by any guard
        for ij in guards:
            cross(ij[0], ij[1])
        
        return comp[0]  # Return the remaining unguarded cells
