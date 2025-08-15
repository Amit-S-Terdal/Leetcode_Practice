// 2787. Ways to Express an Integer as Sum of Powers

// Given two positive integers n and x.

// Return the number of ways n can be expressed as the sum of the xth power of unique positive integers, in other words, the number of sets of unique integers [n1, n2, ..., nk] where n = n1x + n2x + ... + nkx.

// Since the result can be very large, return it modulo 109 + 7.

// For example, if n = 160 and x = 3, one way to express n is n = 23 + 33 + 53.

 

// Example 1:

// Input: n = 10, x = 2
// Output: 1
// Explanation: We can express n as the following: n = 32 + 12 = 10.
// It can be shown that it is the only way to express 10 as the sum of the 2nd power of unique integers.
// Example 2:

// Input: n = 4, x = 1
// Output: 2
// Explanation: We can express n in the following ways:
// - n = 41 = 4.
// - n = 31 + 11 = 4.
 

// Constraints:

// 1 <= n <= 300
// 1 <= x <= 5


// Solution:

class Solution {
    private static final int MOD = 1_000_000_007;

    public int numberOfWays(int n, int x) {
        // Build the list of powers <= n (1^x, 2^x, 3^x, ...)
        java.util.ArrayList<Integer> powers = new java.util.ArrayList<>();
        for (int base = 1; ; base++) {
            int p = powLeqN(base, x, n);
            if (p > n) break;
            powers.add(p);
        }

        int m = powers.size();
        int[][] memo = new int[m][n + 1];
        for (int i = 0; i < m; i++) {
            java.util.Arrays.fill(memo[i], -1);
        }

        return dfs(0, n, powers, memo);
    }

    // Top-down DP: choose or skip each power exactly once (subset sum count)
    private int dfs(int idx, int remaining, java.util.List<Integer> powers, int[][] memo) {
        if (remaining == 0) return 1;
        if (idx == powers.size()) return 0;
        if (memo[idx][remaining] != -1) return memo[idx][remaining];

        long ans = dfs(idx + 1, remaining, powers, memo); // skip
        int val = powers.get(idx);
        if (remaining >= val) {
            ans += dfs(idx + 1, remaining - val, powers, memo); // take
        }
        if (ans >= MOD) ans -= MOD;
        return memo[idx][remaining] = (int) ans;
    }

    // Compute base^x but stop early if it exceeds n to avoid overflow
    private int powLeqN(int base, int x, int n) {
        long r = 1;
        for (int i = 0; i < x; i++) {
            r *= base;
            if (r > n) return n + 1; // sentinel meaning "too big"
        }
        return (int) r;
    }
}
