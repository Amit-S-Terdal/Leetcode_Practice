
// 1727. Largest Submatrix With Rearrangements


// You are given a binary matrix matrix of size m x n, and you are allowed to rearrange the columns of the matrix in any order.

// Return the area of the largest submatrix within matrix where every element of the submatrix is 1 after reordering the columns optimally.

 

// Example 1:


// Input: matrix = [[0,0,1],[1,1,1],[1,0,1]]
// Output: 4
// Explanation: You can rearrange the columns as shown above.
// The largest submatrix of 1s, in bold, has an area of 4.
// Example 2:


// Input: matrix = [[1,0,1,0,1]]
// Output: 3
// Explanation: You can rearrange the columns as shown above.
// The largest submatrix of 1s, in bold, has an area of 3.
// Example 3:

// Input: matrix = [[1,1,0],[1,0,1]]
// Output: 2
// Explanation: Notice that you must rearrange entire columns, and there is no way to make a submatrix of 1s larger than an area of 2.
 

// Constraints:

// m == matrix.length
// n == matrix[i].length
// 1 <= m * n <= 105
// matrix[i][j] is either 0 or 1.




// Solution:



class Solution {

    static int[] freq = new int[100000];

    public int largestSubmatrix(int[][] matrix) {

        int m = matrix.length;
        int n = matrix[0].length;

        int area = 0;
        for (int x : matrix[0]) {
            if (x == 1) area++;
        }

        if (m == 1) return area;

        if (n == 1) {
            for (int i = 1; i < m; i++) {
                int x = matrix[i][0];
                x += (-x & matrix[i - 1][0]);
                matrix[i][0] = x;
                area = Math.max(area, x);
            }
            return area;
        }

        for (int i = 1; i < m; i++) {

            for (int j = 0; j < n; j++) {
                int x = matrix[i][j];
                x += (-x & matrix[i - 1][j]);
                matrix[i][j] = x;
            }

            int minH = i + 1;
            int maxH = 0;

            for (int x : matrix[i]) {
                minH = Math.min(minH, x);
                maxH = Math.max(maxH, x);
            }

            for (int k = 0; k <= maxH - minH; k++) {
                freq[k] = 0;
            }

            for (int x : matrix[i]) {
                freq[x - minH]++;
            }

            int acc = 0;

            for (int x = maxH - minH; acc < n; x--) {
                if (freq[x] > 0) {
                    acc += freq[x];
                    area = Math.max(area, acc * (x + minH));
                }
            }
        }

        return area;
    }
}