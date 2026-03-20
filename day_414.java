// 3567. Minimum Absolute Difference in Sliding Submatrix

// You are given an m x n integer matrix grid and an integer k.

// For every contiguous k x k submatrix of grid, compute the minimum absolute difference between any two distinct values within that submatrix.

// Return a 2D array ans of size (m - k + 1) x (n - k + 1), where ans[i][j] is the minimum absolute difference in the submatrix whose top-left corner is (i, j) in grid.

// Note: If all elements in the submatrix have the same value, the answer will be 0.

// A submatrix (x1, y1, x2, y2) is a matrix that is formed by choosing all cells matrix[x][y] where x1 <= x <= x2 and y1 <= y <= y2.
 

// Example 1:

// Input: grid = [[1,8],[3,-2]], k = 2

// Output: [[2]]

// Explanation:

// There is only one possible k x k submatrix: [[1, 8], [3, -2]].
// Distinct values in the submatrix are [1, 8, 3, -2].
// The minimum absolute difference in the submatrix is |1 - 3| = 2. Thus, the answer is [[2]].
// Example 2:

// Input: grid = [[3,-1]], k = 1

// Output: [[0,0]]

// Explanation:

// Both k x k submatrix has only one distinct element.
// Thus, the answer is [[0, 0]].
// Example 3:

// Input: grid = [[1,-2,3],[2,3,5]], k = 2

// Output: [[1,2]]

// Explanation:

// There are two possible k × k submatrix:
// Starting at (0, 0): [[1, -2], [2, 3]].
// Distinct values in the submatrix are [1, -2, 2, 3].
// The minimum absolute difference in the submatrix is |1 - 2| = 1.
// Starting at (0, 1): [[-2, 3], [3, 5]].
// Distinct values in the submatrix are [-2, 3, 5].
// The minimum absolute difference in the submatrix is |3 - 5| = 2.
// Thus, the answer is [[1, 2]].
 

// Constraints:

// 1 <= m == grid.length <= 30
// 1 <= n == grid[i].length <= 30
// -10^5 <= grid[i][j] <= 10^5
// 1 <= k <= min(m, n)


// Solution: 


import java.util.*;

class Solution {
    static final int N = 512;
    static final int MASK = 511;
    static final int BSHIFT = 9;
    static final int BIAS = -100000;
    static final int INF = 200001;

    static int[] kksub = new int[900];
    static int[] freq = new int[N];

    static void radixSort(int[] nums, int n) {
        int[] buf = new int[n];
        int[] in = nums;
        int[] out = buf;

        // 1st round
        Arrays.fill(freq, 0);

        for (int i = 0; i < n; i++) {
            freq[in[i] & MASK]++;
        }

        for (int i = 1; i < N; i++) {
            freq[i] += freq[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int v = in[i];
            out[--freq[v & MASK]] = v;
        }

        // 2nd round
        Arrays.fill(freq, 0);

        for (int i = 0; i < n; i++) {
            freq[(out[i] >> BSHIFT) & MASK]++;
        }

        for (int i = 1; i < N; i++) {
            freq[i] += freq[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int v = out[i];
            in[--freq[(v >> BSHIFT) & MASK]] = v;
        }
    }

    public int[][] minAbsDiff(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int kk = k * k;

        int[][] ans = new int[m - k + 1][n - k + 1];

        int m0 = m - k;
        int n0 = n - k;

        for (int i = 0; i <= m0; i++) {
            for (int j = 0; j <= n0; j++) {
                int idx = 0;

                for (int x = i; x < i + k; x++) {
                    for (int y = j; y < j + k; y++) {
                        kksub[idx++] = grid[x][y] - BIAS;
                    }
                }

                radixSort(kksub, kk);

                int minD = INF;
                for (int a = 1; a < kk; a++) {
                    int diff = kksub[a] - kksub[a - 1];
                    if (diff > 0) {
                        minD = Math.min(minD, diff);
                    }
                }

                ans[i][j] = (minD == INF) ? 0 : minD;
            }
        }

        return ans;
    }
}

