// 827. Making A Large Island

// You are given an n x n binary matrix grid. You are allowed to change at most one 0 to be 1.

// Return the size of the largest island in grid after applying this operation.

// An island is a 4-directionally connected group of 1s.

 

// Example 1:

// Input: grid = [[1,0],[0,1]]
// Output: 3
// Explanation: Change one 0 to 1 and connect two 1s, then we get an island with area = 3.
// Example 2:

// Input: grid = [[1,1],[1,0]]
// Output: 4
// Explanation: Change the 0 to 1 and make the island bigger, only one island with area = 4.
// Example 3:

// Input: grid = [[1,1],[1,1]]
// Output: 4
// Explanation: Can't change any 0 to 1, only one island with area = 4.
 

// Constraints:

// n == grid.length
// n == grid[i].length
// 1 <= n <= 500
// grid[i][j] is either 0 or 1.

//Solution:

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Solution {
    private int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Directions for exploring neighbors

    public int largestIsland(int[][] grid) {
        int n = grid.length;
        int islandId = 2; // Start assigning island IDs from 2
        Map<Integer, Integer> islandSizes = new HashMap<>(); // Map to store island sizes

        // Step 1: Assign IDs to islands and calculate their sizes
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    int size = dfs(grid, i, j, islandId);
                    islandSizes.put(islandId, size);
                    islandId++;
                }
            }
        }

        // Step 2: Find the maximum island size after changing one 0 to 1
        int maxSize = 0;
        if (!islandSizes.isEmpty()) {
            maxSize = islandSizes.values().stream().max(Integer::compare).get();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    Set<Integer> neighbors = new HashSet<>();
                    for (int[] dir : directions) {
                        int x = i + dir[0];
                        int y = j + dir[1];
                        if (x >= 0 && x < n && y >= 0 && y < n && grid[x][y] != 0) {
                            neighbors.add(grid[x][y]);
                        }
                    }

                    int total = 1; // The current cell being changed to 1
                    for (int neighborId : neighbors) {
                        total += islandSizes.getOrDefault(neighborId, 0);
                    }

                    maxSize = Math.max(maxSize, total);
                }
            }
        }

        return maxSize;
    }

    // DFS function to mark islands and calculate their sizes
    private int dfs(int[][] grid, int i, int j, int islandId) {
        int n = grid.length;
        if (i < 0 || i >= n || j < 0 || j >= n || grid[i][j] != 1) {
            return 0;
        }
        grid[i][j] = islandId;
        int size = 1;
        for (int[] dir : directions) {
            size += dfs(grid, i + dir[0], j + dir[1], islandId);
        }
        return size;
    }
}