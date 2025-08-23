// 1277. Count Square Submatrices with All Ones

// Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.

 

// Example 1:

// Input: matrix =
// [
//   [0,1,1,1],
//   [1,1,1,1],
//   [0,1,1,1]
// ]
// Output: 15
// Explanation: 
// There are 10 squares of side 1.
// There are 4 squares of side 2.
// There is  1 square of side 3.
// Total number of squares = 10 + 4 + 1 = 15.
// Example 2:

// Input: matrix = 
// [
//   [1,0,1],
//   [1,1,0],
//   [1,1,0]
// ]
// Output: 7
// Explanation: 
// There are 6 squares of side 1.  
// There is 1 square of side 2. 
// Total number of squares = 6 + 1 = 7.
 

// Constraints:

// 1 <= arr.length <= 300
// 1 <= arr[0].length <= 300
// 0 <= arr[i][j] <= 1


// Solution: 






class Solution {
    public int countSquares(int[][] matrix) {
        int r = matrix.length;
        if (r == 0) return 0;
        int c = matrix[0].length;
        if (c == 0) return 0;

        int cnt = 0;

        // Count first row
        for (int j = 0; j < c; j++) {
            if (matrix[0][j] == 1) cnt++;
        }
        // Count first column (excluding [0][0] which is already counted if 1)
        for (int i = 1; i < r; i++) {
            if (matrix[i][0] == 1) cnt++;
        }

        // DP: each cell holds size of largest all-1s square ending at (i, j)
        for (int i = 1; i < r; i++) {
            for (int j = 1; j < c; j++) {
                if (matrix[i][j] == 0) continue;
                matrix[i][j] = 1 + Math.min(
                    matrix[i - 1][j],
                    Math.min(matrix[i - 1][j - 1], matrix[i][j - 1])
                );
                cnt += matrix[i][j];
            }
        }
        return cnt;
    }
}



