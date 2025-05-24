// 73. Set Matrix Zeroes

// Given an m x n integer matrix matrix, if an element is 0, set its entire row and column to 0's.

// You must do it in place.

 

// Example 1:


// Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
// Output: [[1,0,1],[0,0,0],[1,0,1]]
// Example 2:


// Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
// Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
 

// Constraints:

// m == matrix.length
// n == matrix[0].length
// 1 <= m, n <= 200
// -231 <= matrix[i][j] <= 231 - 1
 

// Solution: 


class Solution {
    public:
        void setZeroes(vector<vector<int>>& matrix) {
            int m = matrix.size(), n = matrix[0].size();
            bool firstRow = false, firstCol = false;
    
            // Check if first row needs to be zero
            for (int j = 0; j < n; ++j) {
                if (matrix[0][j] == 0) {
                    firstRow = true;
                    break;
                }
            }
    
            // Check if first column needs to be zero
            for (int i = 0; i < m; ++i) {
                if (matrix[i][0] == 0) {
                    firstCol = true;
                    break;
                }
            }
    
            // Use first row/column to mark zeroes
            for (int i = 1; i < m; ++i) {
                for (int j = 1; j < n; ++j) {
                    if (matrix[i][j] == 0) {
                        matrix[i][0] = 0;
                        matrix[0][j] = 0;
                    }
                }
            }
    
            // Zero out cells based on markers
            for (int i = 1; i < m; ++i) {
                for (int j = 1; j < n; ++j) {
                    if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                        matrix[i][j] = 0;
                    }
                }
            }
    
            // Zero out first row if needed
            if (firstRow) {
                for (int j = 0; j < n; ++j) {
                    matrix[0][j] = 0;
                }
            }
    
            // Zero out first column if needed
            if (firstCol) {
                for (int i = 0; i < m; ++i) {
                    matrix[i][0] = 0;
                }
            }
        }
    };
    