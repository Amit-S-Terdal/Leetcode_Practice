// 3070. Count Submatrices with Top-Left Element and Sum Less Than k

// You are given a 0-indexed integer matrix grid and an integer k.

// Return the number of submatrices that contain the top-left element of the grid, and have a sum less than or equal to k.

 

// Example 1:


// Input: grid = [[7,6,3],[6,6,1]], k = 18
// Output: 4
// Explanation: There are only 4 submatrices, shown in the image above, that contain the top-left element of grid, and have a sum less than or equal to 18.
// Example 2:


// Input: grid = [[7,2,9],[1,5,0],[2,6,6]], k = 20
// Output: 6
// Explanation: There are only 6 submatrices, shown in the image above, that contain the top-left element of grid, and have a sum less than or equal to 20.
 

// Constraints:

// m == grid.length 
// n == grid[i].length
// 1 <= n, m <= 1000 
// 0 <= grid[i][j] <= 1000
// 1 <= k <= 10^9




// Solution:



class Solution {
    public int countSubmatrices(int[][] grid, int k) {
        int r = grid.length, c = grid[0].length;
        int cnt = 0, brCol = c;

        if (grid[0][0] > k) return 0; // early stop
        cnt++;

        // First row prefix sums
        for (int j = 1; j < c; j++) {
            grid[0][j] += grid[0][j - 1];
            if (grid[0][j] > k) {
                brCol = j;
                break;
            }
            cnt++;
        }

        // Remaining rows
        for (int i = 1; i < r; i++) {
            grid[i][0] += grid[i - 1][0];
            if (grid[i][0] > k) break;
            cnt++;

            for (int j = 1; j < brCol; j++) {
                grid[i][j] += grid[i - 1][j] + grid[i][j - 1] - grid[i - 1][j - 1];
                if (grid[i][j] > k) {
                    brCol = j;
                    break;
                }
                cnt++;
            }
        }

        return cnt;
    }
}