// 1559. Detect Cycles in 2D Grid

// Given a 2D array of characters grid of size m x n, you need to find if there exists any cycle consisting of the same value in grid.

// A cycle is a path of length 4 or more in the grid that starts and ends at the same cell. From a given cell, you can move to one of the cells adjacent to it - in one of the four directions (up, down, left, or right), if it has the same value of the current cell.

// Also, you cannot move to the cell that you visited in your last move. For example, the cycle (1, 1) -> (1, 2) -> (1, 1) is invalid because from (1, 2) we visited (1, 1) which was the last visited cell.

// Return true if any cycle of the same value exists in grid, otherwise, return false.

 

// Example 1:



// Input: grid = [["a","a","a","a"],["a","b","b","a"],["a","b","b","a"],["a","a","a","a"]]
// Output: true
// Explanation: There are two valid cycles shown in different colors in the image below:

// Example 2:



// Input: grid = [["c","c","c","a"],["c","d","c","c"],["c","c","e","c"],["f","c","c","c"]]
// Output: true
// Explanation: There is only one valid cycle highlighted in the image below:

// Example 3:



// Input: grid = [["a","b","b"],["b","z","b"],["b","b","a"]]
// Output: false
 

// Constraints:

// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 500
// grid consists only of lowercase English letters.


// Solution: 



class Solution {

    static class UnionFind {
        int[] root;
        int[] rank;

        UnionFind(int n) {
            root = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                root[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (x != root[x]) {
                root[x] = find(root[x]); // path compression
            }
            return root[x];
        }

        boolean union(int x, int y) {
            int rx = find(x);
            int ry = find(y);

            if (rx == ry) return false; // cycle detected

            if (rank[rx] > rank[ry]) {
                root[ry] = rx;
            } else if (rank[rx] < rank[ry]) {
                root[rx] = ry;
            } else {
                root[rx] = ry;
                rank[ry]++;
            }
            return true;
        }
    }

    public boolean containsCycle(char[][] grid) {
        int r = grid.length;
        int c = grid[0].length;
        int size = r * c;

        UnionFind uf = new UnionFind(size);

        for (int i = 0; i < r - 1; i++) {
            int rowIdx = i * c;
            for (int j = 0; j < c - 1; j++) {
                int cur = rowIdx + j;
                char alpha = grid[i][j];

                // down
                if (grid[i + 1][j] == alpha) {
                    if (!uf.union(cur, cur + c)) return true;
                }

                // right
                if (grid[i][j + 1] == alpha) {
                    if (!uf.union(cur, cur + 1)) return true;
                }
            }

            // last column (only down)
            int cur = i * c + (c - 1);
            if (grid[i][c - 1] == grid[i + 1][c - 1]) {
                if (!uf.union(cur, cur + c)) return true;
            }
        }

        // last row (only right)
        int rowIdx = (r - 1) * c;
        for (int j = 0; j < c - 1; j++) {
            int cur = rowIdx + j;
            if (grid[r - 1][j] == grid[r - 1][j + 1]) {
                if (!uf.union(cur, cur + 1)) return true;
            }
        }

        return false;
    }
}