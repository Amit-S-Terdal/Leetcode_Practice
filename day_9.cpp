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

// Solution:

#include <vector>
#include <unordered_map>
#include <unordered_set>
#include <algorithm>

using namespace std;

class Solution {
public:
    int largestIsland(vector<vector<int>>& grid) {
        int n = grid.size();
        int island_id = 2; // Start assigning island IDs from 2
        unordered_map<int, int> island_sizes; // Map to store island sizes

        // Step 1: Assign IDs to islands and calculate their sizes
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1) {
                    int size = dfs(grid, i, j, island_id);
                    island_sizes[island_id] = size;
                    island_id++;
                }
            }
        }

        // Step 2: Find the maximum island size after changing one 0 to 1
        int max_size = 0;
        if (!island_sizes.empty()) {
            max_size = max_element(island_sizes.begin(), island_sizes.end(),
                                   [](const pair<int, int>& a, const pair<int, int>& b) {
                                       return a.second < b.second;
                                   })->second;
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 0) {
                    unordered_set<int> neighbors;
                    for (const auto& dir : directions) {
                        int x = i + dir.first;  // Access first element of the pair
                        int y = j + dir.second; // Access second element of the pair
                        if (x >= 0 && x < n && y >= 0 && y < n && grid[x][y] != 0) {
                            neighbors.insert(grid[x][y]);
                        }
                    }

                    int total = 1; // The current cell being changed to 1
                    for (int neighbor_id : neighbors) {
                        total += island_sizes[neighbor_id];
                    }

                    max_size = max(max_size, total);
                }
            }
        }

        return max_size;
    }

private:
    // Directions for exploring neighbors (up, down, left, right)
    vector<pair<int, int>> directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    // DFS function to mark islands and calculate their sizes
    int dfs(vector<vector<int>>& grid, int i, int j, int island_id) {
        int n = grid.size();
        if (i < 0 || i >= n || j < 0 || j >= n || grid[i][j] != 1) {
            return 0;
        }
        grid[i][j] = island_id;
        int size = 1;
        for (const auto& dir : directions) {
            size += dfs(grid, i + dir.first, j + dir.second, island_id);
        }
        return size;
    }
};