// 3212. Count Submatrices With Equal Frequency of X and Y

// Given a 2D character matrix grid, where grid[i][j] is either 'X', 'Y', or '.', return the number of submatrices that contain:

// grid[0][0]
// an equal frequency of 'X' and 'Y'.
// at least one 'X'.
 

// Example 1:

// Input: grid = [["X","Y","."],["Y",".","."]]

// Output: 3

// Explanation:



// Example 2:

// Input: grid = [["X","X"],["X","Y"]]

// Output: 0

// Explanation:

// No submatrix has an equal frequency of 'X' and 'Y'.

// Example 3:

// Input: grid = [[".","."],[".","."]]

// Output: 0

// Explanation:

// No submatrix has at least one 'X'.

 

// Constraints:

// 1 <= grid.length, grid[i].length <= 1000
// grid[i][j] is either 'X', 'Y', or '.'.


// Solution: 



class Solution {
    static long[][] XY = new long[2][1001];

    public int numberOfSubmatrices(char[][] grid) {
        int r = grid.length, c = grid[0].length;

        // reset prefix rows
        for (int j = 0; j <= c; j++) {
            XY[0][j] = 0;
            XY[1][j] = 0;
        }

        int cnt = 0;

        for (int i = 0; i < r; i++) {
            int iEven = (i & 1);
            int prv = iEven ^ 1;

            for (int j = 0; j < c; j++) {
                char ch = grid[i][j];
                long isX = (ch == 'X') ? 1L : 0L;
                long isY = (ch == 'Y') ? 1L : 0L;

                // pack (isX, isY) into one long: high 32 bits = X, low 32 bits = Y
                long dp = (isX << 32) + isY
                        + XY[iEven][j]
                        + XY[prv][j + 1]
                        - XY[prv][j];

                XY[iEven][j + 1] = dp;

                int cntX = (int) (dp >>> 32);
                int cntY = (int) (dp & 0xFFFFFFFFL);

                if (cntX > 0 && cntX == cntY) {
                    cnt++;
                }
            }
        }

        return cnt;
    }
}

