// 3446. Sort Matrix by Diagonals

// You are given an n x n square matrix of integers grid. Return the matrix such that:

// The diagonals in the bottom-left triangle (including the middle diagonal) are sorted in non-increasing order.
// The diagonals in the top-right triangle are sorted in non-decreasing order.
 

// Example 1:

// Input: grid = [[1,7,3],[9,8,2],[4,5,6]]

// Output: [[8,2,3],[9,6,7],[4,5,1]]

// Explanation:



// The diagonals with a black arrow (bottom-left triangle) should be sorted in non-increasing order:

// [1, 8, 6] becomes [8, 6, 1].
// [9, 5] and [4] remain unchanged.
// The diagonals with a blue arrow (top-right triangle) should be sorted in non-decreasing order:

// [7, 2] becomes [2, 7].
// [3] remains unchanged.
// Example 2:

// Input: grid = [[0,1],[1,2]]

// Output: [[2,1],[1,0]]

// Explanation:



// The diagonals with a black arrow must be non-increasing, so [0, 2] is changed to [2, 0]. The other diagonals are already in the correct order.

// Example 3:

// Input: grid = [[1]]

// Output: [[1]]

// Explanation:

// Diagonals with exactly one element are already in order, so no changes are needed.

 

// Constraints:

// grid.length == grid[i].length == n
// 1 <= n <= 10
// -10^5 <= grid[i][j] <= 10^5


// Solution:


import java.util.Arrays;

class Solution {
    public int[][] sortMatrix(int[][] grid) {
        int n = grid.length;
        int[] diag = new int[n];

        // Sort upper-right diagonals (above main) in ascending order
        for (int d = n - 2; d > 0; d--) {
            int len = n - d;
            for (int i = 0; i < len; i++) {
                diag[i] = grid[i][i + d];
            }
            Arrays.sort(diag, 0, len);
            for (int i = 0; i < len; i++) {
                grid[i][i + d] = diag[i];
            }
        }

        // Sort main and lower-left diagonals in descending order
        for (int d = 0; d < n - 1; d++) {
            int len = n - d;
            for (int j = 0; j < len; j++) {
                diag[j] = grid[j + d][j];
            }
            Arrays.sort(diag, 0, len); // ascending
            // write back in reverse for descending
            for (int j = 0; j < len; j++) {
                grid[j + d][j] = diag[len - 1 - j];
            }
        }

        return grid;
    }
}
