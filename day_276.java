// 2257. Count Unguarded Cells in the Grid

// You are given two integers m and n representing a 0-indexed m x n grid. You are also given two 2D integer arrays guards and walls where guards[i] = [rowi, coli] and walls[j] = [rowj, colj] represent the positions of the ith guard and jth wall respectively.

// A guard can see every cell in the four cardinal directions (north, east, south, or west) starting from their position unless obstructed by a wall or another guard. A cell is guarded if there is at least one guard that can see it.

// Return the number of unoccupied cells that are not guarded.

 

// Example 1:


// Input: m = 4, n = 6, guards = [[0,0],[1,1],[2,3]], walls = [[0,1],[2,2],[1,4]]
// Output: 7
// Explanation: The guarded and unguarded cells are shown in red and green respectively in the above diagram.
// There are a total of 7 unguarded cells, so we return 7.
// Example 2:


// Input: m = 3, n = 3, guards = [[1,1]], walls = [[0,1],[1,0],[2,1],[1,2]]
// Output: 4
// Explanation: The unguarded cells are shown in green in the above diagram.
// There are a total of 4 unguarded cells, so we return 4.
 

// Constraints:

// 1 <= m, n <= 10^5
// 2 <= m * n <= 10^5
// 1 <= guards.length, walls.length <= 5 * 10^4
// 2 <= guards.length + walls.length <= m * n
// guards[i].length == walls[j].length == 2
// 0 <= rowi, rowj < m
// 0 <= coli, colj < n
// All the positions in guards and walls are unique.



// Solution: 



class UnionFind {
    private int[] root, size;
    public int components;

    // Constructor
    public UnionFind(int n) {
        components = n + 1;
        root = new int[n + 1];
        size = new int[n + 1];
        
        for (int i = 0; i <= n; i++) {
            root[i] = i;
            size[i] = 1;
        }
    }

    // Find with path compression
    public int find(int x) {
        if (x == root[x]) {
            return x;
        }
        return root[x] = find(root[x]);
    }

    // Union by size
    public boolean union(int x, int y) {
        x = find(x);
        y = find(y);

        if (x == y) {
            return false;
        }

        if (size[x] > size[y]) {
            size[x] += size[y];
            root[y] = x;
        } else {
            size[y] += size[x];
            root[x] = y;
        }

        components--;
        return true;
    }
}

class Solution {
    private boolean[] grid;
    private int m, n, N;

    // Convert (r, c) to a 1D index
    private int idx(int r, int c) {
        return r * n + c;
    }

    // Function to connect all unguarded cells in all 4 directions
    private void cross(int r, int c, UnionFind G) {
        // downwards
        for (int i = r + 1; i < m; i++) {
            if (grid[idx(i, c)]) break;
            G.union(idx(i, c), N); // connect to N
        }
        // upwards
        for (int i = r - 1; i >= 0; i--) {
            if (grid[idx(i, c)]) break;
            G.union(idx(i, c), N); // connect to N
        }
        // rightwards
        for (int j = c + 1; j < n; j++) {
            if (grid[idx(r, j)]) break;
            G.union(idx(r, j), N); // connect to N
        }
        // leftwards
        for (int j = c - 1; j >= 0; j--) {
            if (grid[idx(r, j)]) break;
            G.union(idx(r, j), N); // connect to N
        }
    }

    // Main function to count unguarded cells
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        this.m = m;
        this.n = n;
        N = n * m;
        UnionFind G = new UnionFind(N);

        // Initialize grid as a 1D boolean array (false for empty, true for walls/guards)
        grid = new boolean[N];
        
        // Mark walls and guards
        for (int[] ij : walls) {
            int r = ij[0], c = ij[1];
            grid[idx(r, c)] = true; // mark as a wall
            G.components--;
        }

        for (int[] ij : guards) {
            int r = ij[0], c = ij[1];
            grid[idx(r, c)] = true; // mark as a guard
            G.components--;
        }

        // Connect all guarded cells to the virtual node N
        for (int[] ij : guards) {
            int r = ij[0], c = ij[1];
            cross(r, c, G);
        }

        return G.components - 1; // Return the number of unguarded components
    }
}
