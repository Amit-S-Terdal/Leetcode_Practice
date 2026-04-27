// 1391. Check if There is a Valid Path in a Grid

// You are given an m x n grid. Each cell of grid represents a street. The street of grid[i][j] can be:

// 1 which means a street connecting the left cell and the right cell.
// 2 which means a street connecting the upper cell and the lower cell.
// 3 which means a street connecting the left cell and the lower cell.
// 4 which means a street connecting the right cell and the lower cell.
// 5 which means a street connecting the left cell and the upper cell.
// 6 which means a street connecting the right cell and the upper cell.

// You will initially start at the street of the upper-left cell (0, 0). A valid path in the grid is a path that starts from the upper left cell (0, 0) and ends at the bottom-right cell (m - 1, n - 1). The path should only follow the streets.

// Notice that you are not allowed to change any street.

// Return true if there is a valid path in the grid or false otherwise.

 

// Example 1:


// Input: grid = [[2,4,3],[6,5,2]]
// Output: true
// Explanation: As shown you can start at cell (0, 0) and visit all the cells of the grid to reach (m - 1, n - 1).
// Example 2:


// Input: grid = [[1,2,1],[1,2,1]]
// Output: false
// Explanation: As shown you the street at cell (0, 0) is not connected with any street of any other cell and you will get stuck at cell (0, 0)
// Example 3:

// Input: grid = [[1,1,2]]
// Output: false
// Explanation: You will get stuck at cell (0, 1) and you cannot reach cell (0, 2).
 

// Constraints:

// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 300
// 1 <= grid[i][j] <= 6



// Solution:



class Solution {

    static class UnionFind {
        int[] root, rank;

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
            x = find(x);
            y = find(y);
            if (x == y) return false;

            if (rank[x] > rank[y]) {
                int temp = x;
                x = y;
                y = temp;
            }

            root[x] = y;
            if (rank[x] == rank[y]) {
                rank[y]++;
            }
            return true;
        }

        boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }

    private int idx(int i, int j, int c) {
        return i * c + j;
    }

    public boolean hasValidPath(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        int N = r * c;

        UnionFind uf = new UnionFind(N);

        for (int i = 0; i < r - 1; i++) {
            for (int j = 0; j < c - 1; j++) {
                int C = grid[i][j];
                int R = grid[i][j + 1];
                int D = grid[i + 1][j];

                int cc = idx(i, j, c);
                int rr = cc + 1;
                int dd = cc + c;

                boolean CR = (C == 1) || (C == 4) || (C == 6);
                boolean RC = (R & 1) == 1;

                boolean CD = (C == 2) || (C == 4) || (C == 3);
                boolean DC = (D == 2) || (D == 5) || (D == 6);

                if (CR && RC) {
                    uf.union(cc, rr);
                }

                if (CD && DC) {
                    uf.union(cc, dd);
                }
            }

            // last column (down connection)
            int C = grid[i][c - 1];
            int D = grid[i + 1][c - 1];

            int cc = idx(i, c - 1, c);
            int dd = cc + c;

            boolean CD = (C == 2) || (C == 4) || (C == 3);
            boolean DC = (D == 2) || (D == 5) || (D == 6);

            if (CD && DC) {
                uf.union(cc, dd);
            }
        }

        // last row (right connection)
        for (int j = 0; j < c - 1; j++) {
            int C = grid[r - 1][j];
            int R = grid[r - 1][j + 1];

            int cc = idx(r - 1, j, c);
            int rr = cc + 1;

            boolean CR = (C == 1) || (C == 4) || (C == 6);
            boolean RC = (R & 1) == 1;

            if (CR && RC) {
                uf.union(cc, rr);
            }
        }

        return uf.connected(0, N - 1);
    }
}