// 840. Magic Squares In Grid

// A 3 x 3 magic square is a 3 x 3 grid filled with distinct numbers from 1 to 9 such that each row, column, and both diagonals all have the same sum.

// Given a row x col grid of integers, how many 3 x 3 magic square subgrids are there?

// Note: while a magic square can only contain numbers from 1 to 9, grid may contain numbers up to 15.

 

// Example 1:


// Input: grid = [[4,3,8,4],[9,5,1,9],[2,7,6,2]]
// Output: 1
// Explanation: 
// The following subgrid is a 3 x 3 magic square:

// while this one is not:

// In total, there is only one magic square inside the given grid.
// Example 2:

// Input: grid = [[8]]
// Output: 0
 

// Constraints:

// row == grid.length
// col == grid[i].length
// 1 <= row, col <= 10
// 0 <= grid[i][j] <= 15


// Solution: 


class Solution {
    public int numMagicSquaresInside(int[][] grid) {
        int r = grid.length;
        int c = grid[0].length;
        int ans = 0;

        if (r < 3 || c < 3) return 0;

        for (int i = 0; i + 2 < r; i++) {
            for (int j = 0; j + 2 < c; j++) {
                boolean[] used = new boolean[10];
                boolean ok = true;

                // Check numbers 1..9 and uniqueness
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        int v = grid[i + x][j + y];
                        if (v < 1 || v > 9 || used[v]) {
                            ok = false;
                            break;
                        }
                        used[v] = true;
                    }
                    if (!ok) break;
                }
                if (!ok) continue;

                int s = grid[i][j] + grid[i][j + 1] + grid[i][j + 2];

                // Check rows
                for (int x = 0; x < 3; x++) {
                    if (grid[i + x][j] + grid[i + x][j + 1] + grid[i + x][j + 2] != s) {
                        ok = false;
                        break;
                    }
                }

                // Check columns
                for (int y = 0; y < 3 && ok; y++) {
                    if (grid[i][j + y] + grid[i + 1][j + y] + grid[i + 2][j + y] != s) {
                        ok = false;
                        break;
                    }
                }

                // Check diagonals
                if (grid[i][j] + grid[i + 1][j + 1] + grid[i + 2][j + 2] != s) ok = false;
                if (grid[i][j + 2] + grid[i + 1][j + 1] + grid[i + 2][j] != s) ok = false;

                if (ok) ans++;
            }
        }
        return ans;
    }
}
