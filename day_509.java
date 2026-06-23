// 3699. Number of ZigZag Arrays I

// You are given three integers n, l, and r.

// A ZigZag array of length n is defined as follows:

// Each element lies in the range [l, r].
// No two adjacent elements are equal.
// No three consecutive elements form a strictly increasing or strictly decreasing sequence.
// Return the total number of valid ZigZag arrays.

// Since the answer may be large, return it modulo 109 + 7.

// A sequence is said to be strictly increasing if each element is strictly greater than its previous one (if exists).

// A sequence is said to be strictly decreasing if each element is strictly smaller than its previous one (if exists).

 

// Example 1:

// Input: n = 3, l = 4, r = 5

// Output: 2

// Explanation:

// There are only 2 valid ZigZag arrays of length n = 3 using values in the range [4, 5]:

// [4, 5, 4]
// [5, 4, 5]​​​​​​​
// Example 2:

// Input: n = 3, l = 1, r = 3

// Output: 10

// Explanation:

// There are 10 valid ZigZag arrays of length n = 3 using values in the range [1, 3]:

// [1, 2, 1], [1, 3, 1], [1, 3, 2]
// [2, 1, 2], [2, 1, 3], [2, 3, 1], [2, 3, 2]
// [3, 1, 2], [3, 1, 3], [3, 2, 3]
// All arrays meet the ZigZag conditions.

 

// Constraints:

// 3 <= n <= 2000
// 1 <= l < r <= 2000



// Solution: 




class Solution {
    static final int P = 1_000_000_007;
    static final int N = 2000;

    static int[] dp = new int[N];

    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;

        for (int i = 0; i < m; i++) {
            dp[i] = 1;
        }

        int[][] ii = {
            {0, m, 1},
            {m - 1, -1, -1}
        };

        for (int i = 1; i < n; i++) {
            int dir = i & 1;
            long sum = 0;

            int start = ii[dir][0];
            int end = ii[dir][1];
            int step = ii[dir][2];

            for (int j = start; j != end; j += step) {
                long x = dp[j];
                dp[j] = (int) sum;
                sum = (sum + x) % P;
            }
        }

        long ans = 0;
        for (int i = 0; i < m; i++) {
            ans += dp[i];
        }

        return (int) ((2 * ans) % P);
    }
}