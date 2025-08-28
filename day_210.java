// 498. Diagonal Traverse

// Given an m x n matrix mat, return an array of all the elements of the array in a diagonal order.

 

// Example 1:


// Input: mat = [[1,2,3],[4,5,6],[7,8,9]]
// Output: [1,2,4,7,5,3,6,8,9]
// Example 2:

// Input: mat = [[1,2],[3,4]]
// Output: [1,2,3,4]
 

// Constraints:

// m == mat.length
// n == mat[i].length
// 1 <= m, n <= 10^4
// 1 <= m * n <= 10^4
// -10^5 <= mat[i][j] <= 10^5

// Solution: 


class Solution {
    public int[] findDiagonalOrder(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) return new int[0];

        int r = mat.length, c = mat[0].length;
        int[] ans = new int[r * c];

        boolean flip = false;
        int idx = 0;

        for (int d = 0; d < r + c - 1; d++, flip = !flip) {
            if (!flip) { // go NE
                int i = Math.min(d, r - 1);
                int j = d - i;
                while (i >= 0 && j < c) {
                    ans[idx++] = mat[i][j];
                    i--;
                    j++;
                }
            } else { // go SW
                int j = Math.min(d, c - 1);
                int i = d - j;
                while (j >= 0 && i < r) {
                    ans[idx++] = mat[i][j];
                    i++;
                    j--;
                }
            }
        }

        return ans;
    }
}
