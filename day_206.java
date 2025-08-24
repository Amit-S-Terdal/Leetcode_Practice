// 1504. Count Submatrices With All Ones

// Given an m x n binary matrix mat, return the number of submatrices that have all ones.

 

// Example 1:


// Input: mat = [[1,0,1],[1,1,0],[1,1,0]]
// Output: 13
// Explanation: 
// There are 6 rectangles of side 1x1.
// There are 2 rectangles of side 1x2.
// There are 3 rectangles of side 2x1.
// There is 1 rectangle of side 2x2. 
// There is 1 rectangle of side 3x1.
// Total number of rectangles = 6 + 2 + 3 + 1 + 1 = 13.
// Example 2:


// Input: mat = [[0,1,1,0],[0,1,1,1],[1,1,1,0]]
// Output: 24
// Explanation: 
// There are 8 rectangles of side 1x1.
// There are 5 rectangles of side 1x2.
// There are 2 rectangles of side 1x3. 
// There are 4 rectangles of side 2x1.
// There are 2 rectangles of side 2x2. 
// There are 2 rectangles of side 3x1. 
// There is 1 rectangle of side 3x2. 
// Total number of rectangles = 8 + 5 + 2 + 4 + 2 + 2 + 1 = 24.
 

// Constraints:

// 1 <= m, n <= 150
// mat[i][j] is either 0 or 1.


// Solution:


import java.util.Arrays;

class Solution {
    public int numSubmat(int[][] mat) {
        int r = mat.length, c = mat[0].length;
        int ans = 0;

        int[] st = new int[c];    // mono stack storing column indices
        int[] cnt = new int[c];   // dp: number of all-1 submatrices ending at (i, j) with bottom at row i

        for (int i = 0; i < r; i++) {
            int[] h = mat[i];     // treat current row as heights
            int top = -1;
            Arrays.fill(cnt, 0);

            for (int j = 0; j < c; j++) {
                if (h[j] == 0) {
                    // reset on zero; push sentinel index j
                    top = -1;
                    st[++top] = j;
                    continue;
                }

                if (i > 0) h[j] += mat[i - 1][j]; // accumulate heights

                while (top > -1 && h[st[top]] >= h[j]) top--;
                int left = (top == -1) ? -1 : st[top];

                int base = (top > -1) ? cnt[left] : 0;
                cnt[j] = base + h[j] * (j - left);
                ans += cnt[j];

                st[++top] = j; // push current index
            }
        }
        return ans;
    }
}
