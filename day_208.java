// 3197. Find the Minimum Area to Cover All Ones II

// You are given a 2D binary array grid. You need to find 3 non-overlapping rectangles having non-zero areas with horizontal and vertical sides such that all the 1's in grid lie inside these rectangles.

// Return the minimum possible sum of the area of these rectangles.

// Note that the rectangles are allowed to touch.

 

// Example 1:

// Input: grid = [[1,0,1],[1,1,1]]

// Output: 5

// Explanation:



// The 1's at (0, 0) and (1, 0) are covered by a rectangle of area 2.
// The 1's at (0, 2) and (1, 2) are covered by a rectangle of area 2.
// The 1 at (1, 1) is covered by a rectangle of area 1.
// Example 2:

// Input: grid = [[1,0,1,0],[0,1,0,1]]

// Output: 5

// Explanation:



// The 1's at (0, 0) and (0, 2) are covered by a rectangle of area 3.
// The 1 at (1, 1) is covered by a rectangle of area 1.
// The 1 at (1, 3) is covered by a rectangle of area 1.
 

// Constraints:

// 1 <= grid.length, grid[i].length <= 30
// grid[i][j] is either 0 or 1.
// The input is generated such that there are at least three 1's in grid.


// Solution: 


import java.util.Arrays;

class Solution {
    // Bitmasks for rows (A) and columns (T), up to 30x30 grid as in the original
    private static final int MAX = 30;
    private static final int[] A = new int[MAX];
    private static final int[] T = new int[MAX];

    private static void buildAT(int[][] grid, int n, int m) {
        Arrays.fill(A, 0);
        Arrays.fill(T, 0);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) continue;
                A[i] |= (1 << j); // row i
                T[j] |= (1 << i); // col j (transpose)
            }
        }
    }

    // area of the bounding box of 1s in submatrix [i0..iN][j0..jN]
    private static int minRect(int i0, int iN, int j0, int jN) {
        int iMin = 30, iMax = -1, jMin = 30, jMax = -1;

        // top row with any 1 in [j0..jN]
        for (int i = i0; i <= iN; i++) {
            int row = A[i];
            int mRow = (row >> j0) << j0;
            mRow &= ((1 << (jN + 1)) - 1);
            if (mRow != 0) { iMin = i; break; }
        }
        if (iMin == 30) return 100_000_000; // no 1s in this submatrix

        // bottom row
        for (int i = iN; i >= iMin; i--) {
            int row = A[i];
            int mRow = (row >> j0) << j0;
            mRow &= ((1 << (jN + 1)) - 1);
            if (mRow != 0) { iMax = i; break; }
        }
        // left col
        for (int j = j0; j <= jN; j++) {
            int col = T[j];
            int mCol = (col >> i0) << i0;
            mCol &= ((1 << (iN + 1)) - 1);
            if (mCol != 0) { jMin = j; break; }
        }
        // right col
        for (int j = jN; j >= jMin; j--) {
            int col = T[j];
            int mCol = (col >> i0) << i0;
            mCol &= ((1 << (iN + 1)) - 1);
            if (mCol != 0) { jMax = j; break; }
        }
        return (iMax - iMin + 1) * (jMax - jMin + 1);
    }

    public int minimumSum(int[][] grid) {
        final int n = grid.length, m = grid[0].length;
        buildAT(grid, n, m);

        int ans = Integer.MAX_VALUE;

        // 2 vertical cuts
        for (int c1 = 0; c1 < m - 2; c1++) {
            for (int c2 = c1 + 1; c2 < m - 1; c2++) {
                int a = minRect(0, n - 1, 0, c1);
                int b = minRect(0, n - 1, c1 + 1, c2);
                int c = minRect(0, n - 1, c2 + 1, m - 1);
                ans = Math.min(ans, a + b + c);
            }
        }

        // 2 horizontal cuts
        for (int r1 = 0; r1 < n - 2; r1++) {
            for (int r2 = r1 + 1; r2 < n - 1; r2++) {
                int a = minRect(0, r1, 0, m - 1);
                int b = minRect(r1 + 1, r2, 0, m - 1);
                int c = minRect(r2 + 1, n - 1, 0, m - 1);
                ans = Math.min(ans, a + b + c);
            }
        }

        // T-shapes (3-rectangle partitions)
        for (int r = 0; r < n - 1; r++) {
            for (int c = 0; c < m - 1; c++) {
                // top, bottom-left & bottom-right
                {
                    int top = minRect(0, r, 0, m - 1);
                    int bl = minRect(r + 1, n - 1, 0, c);
                    int br = minRect(r + 1, n - 1, c + 1, m - 1);
                    ans = Math.min(ans, top + bl + br);
                }
                // bottom, top-left & top-right
                {
                    int bottom = minRect(r + 1, n - 1, 0, m - 1);
                    int tl = minRect(0, r, 0, c);
                    int tr = minRect(0, r, c + 1, m - 1);
                    ans = Math.min(ans, bottom + tl + tr);
                }
                // left, top-right & bottom-right
                {
                    int left = minRect(0, n - 1, 0, c);
                    int tr = minRect(0, r, c + 1, m - 1);
                    int br = minRect(r + 1, n - 1, c + 1, m - 1);
                    ans = Math.min(ans, left + tr + br);
                }
                // right, top-left & bottom-left
                {
                    int right = minRect(0, n - 1, c + 1, m - 1);
                    int tl = minRect(0, r, 0, c);
                    int bl = minRect(r + 1, n - 1, 0, c);
                    ans = Math.min(ans, right + tl + bl);
                }
            }
        }

        return ans;
    }
}
