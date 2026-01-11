// 85. Maximal Rectangle

// Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

 

// Example 1:


// Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
// Output: 6
// Explanation: The maximal rectangle is shown in the above picture.
// Example 2:

// Input: matrix = [["0"]]
// Output: 0
// Example 3:

// Input: matrix = [["1"]]
// Output: 1
 

// Constraints:

// rows == matrix.length
// cols == matrix[i].length
// 1 <= rows, cols <= 200
// matrix[i][j] is '0' or '1'.


// Solution: 



class Solution {
    public int maximalRectangle(char[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        if (row == 1 && col == 1) {
            return matrix[0][0] == '1' ? 1 : 0;
        }

        // heights array (histogram)
        int[] h = new int[col + 1];   // +1 for the sentinel column
        // stack to store indices
        int[] st = new int[col + 1];

        int maxArea = 0;

        for (int i = 0; i < row; i++) {
            int top = 0;
            st[0] = -1; // sentinel

            for (int j = 0; j <= col; j++) {
                // build histogram
                if (j == col || matrix[i][j] == '0') {
                    h[j] = 0;
                } else {
                    h[j]++;
                }

                // monotonic stack
                while (top > 0 && (j == col || h[j] < h[st[top]])) {
                    int m = st[top--];
                    int w = j - st[top] - 1;
                    int area = h[m] * w;
                    if (area > maxArea) {
                        maxArea = area;
                    }
                }

                st[++top] = j;
            }
        }

        return maxArea;
    }
}
