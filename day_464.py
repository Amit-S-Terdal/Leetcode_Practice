# 1914. Cyclically Rotating a Grid

# You are given an m x n integer matrix grid​​​, where m and n are both even integers, and an integer k.

# The matrix is composed of several layers, which is shown in the below image, where each color is its own layer:



# A cyclic rotation of the matrix is done by cyclically rotating each layer in the matrix. To cyclically rotate a layer once, each element in the layer will take the place of the adjacent element in the counter-clockwise direction. An example rotation is shown below:


# Return the matrix after applying k cyclic rotations to it.

 

# Example 1:


# Input: grid = [[40,10],[30,20]], k = 1
# Output: [[10,20],[40,30]]
# Explanation: The figures above represent the grid at every state.
# Example 2:


# Input: grid = [[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]], k = 2
# Output: [[3,4,8,12],[2,11,10,16],[1,7,6,15],[5,9,13,14]]
# Explanation: The figures above represent the grid at every state.
 

# Constraints:

# m == grid.length
# n == grid[i].length
# 2 <= m, n <= 50
# Both m and n are even integers.
# 1 <= grid[i][j] <= 5000
# 1 <= k <= 10^9




# Solution: 



class Solution(object):
    layer = [0] * 196

    def rotateGrid(self, grid, k):
        """
        :type grid: List[List[int]]
        :type k: int
        :rtype: List[List[int]]
        """

        r, c = len(grid), len(grid[0])
        L = min(r, c) // 2

        for d in range(L):

            si, sj = d, d
            ei, ej = r - d - 1, c - d - 1

            h = r - 2 * d
            w = c - 2 * d

            p = (h + w - 2) * 2

            idx = 0

            # Extract layer
            for j in range(w - 1):
                self.layer[idx] = grid[si][sj + j]
                idx += 1

            for i in range(h - 1):
                self.layer[idx] = grid[si + i][ej]
                idx += 1

            for j in range(w - 1):
                self.layer[idx] = grid[ei][ej - j]
                idx += 1

            for i in range(h - 1):
                self.layer[idx] = grid[ei - i][sj]
                idx += 1

            # Rotate
            idx = k % p

            for j in range(w - 1):
                grid[si][sj + j] = self.layer[idx]
                idx = 0 if idx == p - 1 else idx + 1

            for i in range(h - 1):
                grid[si + i][ej] = self.layer[idx]
                idx = 0 if idx == p - 1 else idx + 1

            for j in range(w - 1):
                grid[ei][ej - j] = self.layer[idx]
                idx = 0 if idx == p - 1 else idx + 1

            for i in range(h - 1):
                grid[ei - i][sj] = self.layer[idx]
                idx = 0 if idx == p - 1 else idx + 1

        return grid