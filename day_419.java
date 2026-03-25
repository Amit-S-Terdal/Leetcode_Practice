// 3546. Equal Sum Grid Partition I

// You are given an m x n matrix grid of positive integers. Your task is to determine if it is possible to make either one horizontal or one vertical cut on the grid such that:

// Each of the two resulting sections formed by the cut is non-empty.
// The sum of the elements in both sections is equal.
// Return true if such a partition exists; otherwise return false.

 

// Example 1:

// Input: grid = [[1,4],[2,3]]

// Output: true

// Explanation:



// A horizontal cut between row 0 and row 1 results in two non-empty sections, each with a sum of 5. Thus, the answer is true.

// Example 2:

// Input: grid = [[1,3],[2,4]]

// Output: false

// Explanation:

// No horizontal or vertical cut results in two non-empty sections with equal sums. Thus, the answer is false.

 

// Constraints:

// 1 <= m == grid.length <= 10^5
// 1 <= n == grid[i].length <= 10^5
// 2 <= m * n <= 10^5
// 1 <= grid[i][j] <= 10^5


// Solution:



import java.util.*;

class Solution {
    public boolean canPartitionGrid(int[][] grid) {
        int r = grid.length, c = grid[0].length;

        long[] rowSum = new long[r];
        long[] colSum = new long[c];

        // Build prefix sums for rows and columns
        for (int i = 0; i < r; i++) {
            if (i > 0) {
                rowSum[i] += rowSum[i - 1];
            }
            for (int j = 0; j < c; j++) {
                int x = grid[i][j];
                rowSum[i] += x;
                colSum[j] += x;
            }
        }

        // Prefix sum for columns
        for (int j = 1; j < c; j++) {
            colSum[j] += colSum[j - 1];
        }

        long totalSum = colSum[c - 1];

        // If total is odd, cannot partition
        if ((totalSum & 1) == 1) return false;

        long target = totalSum / 2;

        // Check row partition
        int i = Arrays.binarySearch(rowSum, target);
        if (i >= 0) return true;

        // Check column partition
        int j = Arrays.binarySearch(colSum, target);
        return j >= 0;
    }
}