// 3548. Equal Sum Grid Partition II

// You are given an m x n matrix grid of positive integers. Your task is to determine if it is possible to make either one horizontal or one vertical cut on the grid such that:

// Each of the two resulting sections formed by the cut is non-empty.
// The sum of elements in both sections is equal, or can be made equal by discounting at most one single cell in total (from either section).
// If a cell is discounted, the rest of the section must remain connected.
// Return true if such a partition exists; otherwise, return false.

// Note: A section is connected if every cell in it can be reached from any other cell by moving up, down, left, or right through other cells in the section.

 

// Example 1:

// Input: grid = [[1,4],[2,3]]

// Output: true

// Explanation:



// A horizontal cut after the first row gives sums 1 + 4 = 5 and 2 + 3 = 5, which are equal. Thus, the answer is true.
// Example 2:

// Input: grid = [[1,2],[3,4]]

// Output: true

// Explanation:



// A vertical cut after the first column gives sums 1 + 3 = 4 and 2 + 4 = 6.
// By discounting 2 from the right section (6 - 2 = 4), both sections have equal sums and remain connected. Thus, the answer is true.
// Example 3:

// Input: grid = [[1,2,4],[2,3,5]]

// Output: false

// Explanation:



// A horizontal cut after the first row gives 1 + 2 + 4 = 7 and 2 + 3 + 5 = 10.
// By discounting 3 from the bottom section (10 - 3 = 7), both sections have equal sums, but they do not remain connected as it splits the bottom section into two parts ([2] and [5]). Thus, the answer is false.
// Example 4:

// Input: grid = [[4,1,8],[3,2,6]]

// Output: false

// Explanation:

// No valid cut exists, so the answer is false.

 

// Constraints:

// 1 <= m == grid.length <= 10^5
// 1 <= n == grid[i].length <= 10^5
// 2 <= m * n <= 10^5
// 1 <= grid[i][j] <= 10^5




// Solution: 


import java.util.*;

class Solution {
    static final int M = 100001;
    static int[] A = new int[M];
    static boolean[] seen = new boolean[M];

    public boolean canPartitionGrid(int[][] grid) {
        int r = grid.length, c = grid[0].length;
        int N = r * c;

        long Tsum = 0;

        Arrays.fill(seen, false);

        // Flatten grid
        int idx = 0;
        for (int[] row : grid) {
            for (int x : row) {
                A[idx++] = x;
            }
        }

        int xMax = 0;
        for (int i = 0; i < N; i++) {
            int x = A[i];
            Tsum += x;
            xMax = Math.max(xMax, x);
        }

        // Horizontal Cuts (Top → Bottom)
        long top = 0;
        for (int i = 0; i < r - 1; i++) {
            for (int j = 0; j < c; j++) {
                int x = A[i * c + j];
                top += x;
                seen[x] = true;
            }

            long bot = Tsum - top;
            if (top == bot) return true;

            long d = top - bot;
            if (d > 0 && d <= xMax) {
                if (i > 0 && c > 1) {
                    if (seen[(int)d]) return true;
                } else {
                    if (A[0] == d || A[(i + 1) * c - 1] == d) return true;
                }
            }
        }

        Arrays.fill(seen, false);

        // Horizontal Cuts (Bottom → Top)
        long bot = 0;
        for (int i = r - 1; i >= 1; i--) {
            for (int j = c - 1; j >= 0; j--) {
                int x = A[i * c + j];
                bot += x;
                seen[x] = true;
            }

            long topS = Tsum - bot;
            long d = bot - topS;

            if (d > 0 && d <= xMax) {
                if ((r - 1 - i) > 0 && c > 1) {
                    if (seen[(int)d]) return true;
                } else {
                    if (A[i * c] == d || A[N - 1] == d) return true;
                }
            }
        }

        // Vertical Cuts (Left → Right)
        Arrays.fill(seen, false);
        long left = 0;

        for (int j = 0; j < c - 1; j++) {
            for (int i = 0; i < r; i++) {
                int x = A[i * c + j];
                left += x;
                seen[x] = true;
            }

            long right = Tsum - left;
            if (left == right) return true;

            long d = left - right;
            if (d > 0 && d <= xMax) {
                if (r > 1 && j > 0) {
                    if (seen[(int)d]) return true;
                } else {
                    if (A[0] == d || A[(r - 1) * c + j] == d) return true;
                }
            }
        }

        // Vertical Cuts (Right → Left)
        Arrays.fill(seen, false);
        long right = 0;

        for (int j = c - 1; j >= 1; j--) {
            for (int i = 0; i < r; i++) {
                int x = A[i * c + j];
                right += x;
                seen[x] = true;
            }

            long leftS = Tsum - right;
            long d = right - leftS;

            if (d > 0 && d <= xMax) {
                if (r > 1 && (c - 1 - j) > 0) {
                    if (seen[(int)d]) return true;
                } else {
                    if (A[j] == d || A[N - 1] == d) return true;
                }
            }
        }

        return false;
    }
}