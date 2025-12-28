// 1351. Count Negative Numbers in a Sorted Matrix

// Given a m x n matrix grid which is sorted in non-increasing order both row-wise and column-wise, return the number of negative numbers in grid.

 

// Example 1:

// Input: grid = [[4,3,2,-1],[3,2,1,-1],[1,1,-1,-2],[-1,-1,-2,-3]]
// Output: 8
// Explanation: There are 8 negatives number in the matrix.
// Example 2:

// Input: grid = [[3,2],[1,0]]
// Output: 0
 

// Constraints:

// m == grid.length
// n == grid[i].length
// 1 <= m, n <= 100
// -100 <= grid[i][j] <= 100



// Solution: 



class Solution {
    public int countNegatives(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int sum = 0;

        // Find iMax: first row where grid[row][0] < 0
        int iMax = firstNegativeInColumn(grid);

        // Count negatives in rows that have positives
        for (int i = 0; i < iMax; i++) {
            int firstNeg = firstNegativeInRow(grid[i]);
            sum += m - firstNeg;
        }

        // Remaining rows are fully negative
        sum += (n - iMax) * m;

        return sum;
    }

    // Binary search for first row with grid[row][0] < 0
    private int firstNegativeInColumn(int[][] grid) {
        int low = 0, high = grid.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (grid[mid][0] >= 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    // Binary search for first element < 0 in a row
    private int firstNegativeInRow(int[] row) {
        int low = 0, high = row.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (row[mid] >= 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
