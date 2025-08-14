// 2438. Range Product Queries of Powers

// Given a positive integer n, there exists a 0-indexed array called powers, composed of the minimum number of powers of 2 that sum to n. The array is sorted in non-decreasing order, and there is only one way to form the array.

// You are also given a 0-indexed 2D integer array queries, where queries[i] = [lefti, righti]. Each queries[i] represents a query where you have to find the product of all powers[j] with lefti <= j <= righti.

// Return an array answers, equal in length to queries, where answers[i] is the answer to the ith query. Since the answer to the ith query may be too large, each answers[i] should be returned modulo 109 + 7.

 

// Example 1:

// Input: n = 15, queries = [[0,1],[2,2],[0,3]]
// Output: [2,4,64]
// Explanation:
// For n = 15, powers = [1,2,4,8]. It can be shown that powers cannot be a smaller size.
// Answer to 1st query: powers[0] * powers[1] = 1 * 2 = 2.
// Answer to 2nd query: powers[2] = 4.
// Answer to 3rd query: powers[0] * powers[1] * powers[2] * powers[3] = 1 * 2 * 4 * 8 = 64.
// Each answer modulo 109 + 7 yields the same answer, so [2,4,64] is returned.
// Example 2:

// Input: n = 2, queries = [[0,0]]
// Output: [2]
// Explanation:
// For n = 2, powers = [2].
// The answer to the only query is powers[0] = 2. The answer modulo 109 + 7 is the same, so [2] is returned.
 

// Constraints:

// 1 <= n <= 10^9
// 1 <= queries.length <= 10^5
// 0 <= starti <= endi < powers.length


// Solution:


class Solution {
    private static final int MOD = 1_000_000_007;
    private static final int B30 = (int) ((1L << 30) % MOD);

    public int[] productQueries(int n, int[][] queries) {
        // Collect exponents of set bits in n
        java.util.ArrayList<Integer> exps = new java.util.ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if ((n & (1 << i)) != 0) exps.add(i);
        }

        // Prefix sums of exponents (in-place)
        for (int i = 1; i < exps.size(); i++) {
            exps.set(i, exps.get(i) + exps.get(i - 1));
        }

        int m = queries.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            int s = queries[i][0], e = queries[i][1];
            int exp = exps.get(e) - (s == 0 ? 0 : exps.get(s - 1));
            ans[i] = pow2mod(exp);
        }
        return ans;
    }

    private static int pow2mod(int exp) {
        if (exp < 30) return 1 << exp;  // 2^exp < MOD when exp < 30
        int q = exp / 30, r = exp % 30;
        int B = modPow(B30, q);
        long res = (long) B * (1L << r) % MOD;
        return (int) res;
    }

    private static int modPow(long x, int exp) {
        long base = x % MOD;
        long res = 1;
        int e = exp;
        while (e > 0) {
            if ((e & 1) == 1) res = (res * base) % MOD;
            base = (base * base) % MOD;
            e >>= 1;
        }
        return (int) res;
    }
}
