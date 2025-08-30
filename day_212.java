// 3459. Length of Longest V-Shaped Diagonal Segment

// You are given a 2D integer matrix grid of size n x m, where each element is either 0, 1, or 2.

// A V-shaped diagonal segment is defined as:

// The segment starts with 1.
// The subsequent elements follow this infinite sequence: 2, 0, 2, 0, ....
// The segment:
// Starts along a diagonal direction (top-left to bottom-right, bottom-right to top-left, top-right to bottom-left, or bottom-left to top-right).
// Continues the sequence in the same diagonal direction.
// Makes at most one clockwise 90-degree turn to another diagonal direction while maintaining the sequence.


// Return the length of the longest V-shaped diagonal segment. If no valid segment exists, return 0.

 

// Example 1:

// Input: grid = [[2,2,1,2,2],[2,0,2,2,0],[2,0,1,1,0],[1,0,2,2,2],[2,0,0,2,2]]

// Output: 5

// Explanation:



// The longest V-shaped diagonal segment has a length of 5 and follows these coordinates: (0,2) → (1,3) → (2,4), takes a 90-degree clockwise turn at (2,4), and continues as (3,3) → (4,2).

// Example 2:

// Input: grid = [[2,2,2,2,2],[2,0,2,2,0],[2,0,1,1,0],[1,0,2,2,2],[2,0,0,2,2]]

// Output: 4

// Explanation:



// The longest V-shaped diagonal segment has a length of 4 and follows these coordinates: (2,3) → (3,2), takes a 90-degree clockwise turn at (3,2), and continues as (2,1) → (1,0).

// Example 3:

// Input: grid = [[1,2,2,2,2],[2,2,2,2,0],[2,0,0,0,0],[0,0,2,2,2],[2,0,0,2,0]]

// Output: 5

// Explanation:



// The longest V-shaped diagonal segment has a length of 5 and follows these coordinates: (0,0) → (1,1) → (2,2) → (3,3) → (4,4).

// Example 4:

// Input: grid = [[1]]

// Output: 1

// Explanation:

// The longest V-shaped diagonal segment has a length of 1 and follows these coordinates: (0,0).

 

// Constraints:

// n == grid.length
// m == grid[i].length
// 1 <= n, m <= 500
// grid[i][j] is either 0, 1 or 2.


// Solution: 

class Solution {
    // Directions: (1,1), (1,-1), (-1,-1), (-1,1)
    private static final int[] d = {1, 1, -1, -1, 1};
    private int n, m;
    private int[][][][] dp;

    private boolean isOutside(int i, int j) {
        return i < 0 || i >= n || j < 0 || j >= m;
    }

    private int dfs(int i, int j, int dir, int turn, int nxt, int[][] grid) {
        if (dp[i][j][dir][turn] != -1) return dp[i][j][dir][turn];

        int ans = 1;

        // Move in the current direction
        int s = i + d[dir], t = j + d[dir + 1];
        if (!isOutside(s, t)) {
            int y = grid[s][t];
            if (nxt == y) { // continue moving in the same diagonal
                ans = Math.max(ans, 1 + dfs(s, t, dir, turn, nxt ^ 2, grid));
            }
        }

        // Try turning to the next diagonal direction
        if (turn == 0) {
            int newDir = (dir + 1) & 3; // same as % 4
            int u = i + d[newDir], v = j + d[newDir + 1];
            if (!isOutside(u, v)) {
                int z = grid[u][v];
                if (nxt == z) { // turn to the next diagonal
                    ans = Math.max(ans, 1 + dfs(u, v, newDir, 1, nxt ^ 2, grid));
                }
            }
        }

        dp[i][j][dir][turn] = ans;
        return ans;
    }

    public int lenOfVDiagonal(int[][] grid) {
        n = grid.length;
        m = grid[0].length;

        dp = new int[n][m][4][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int dir = 0; dir < 4; dir++) {
                    dp[i][j][dir][0] = -1;
                    dp[i][j][dir][1] = -1;
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) { // Start from cells that are part of the path
                    for (int dir = 0; dir < 4; dir++) {
                        ans = Math.max(ans, dfs(i, j, dir, 0, 2, grid));
                    }
                }
            }
        }

        return ans;
    }
}
