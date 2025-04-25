// 2338. Count the Number of Ideal Arrays

// You are given two integers n and maxValue, which are used to describe an ideal array.

// A 0-indexed integer array arr of length n is considered ideal if the following conditions hold:

// Every arr[i] is a value from 1 to maxValue, for 0 <= i < n.
// Every arr[i] is divisible by arr[i - 1], for 0 < i < n.
// Return the number of distinct ideal arrays of length n. Since the answer may be very large, return it modulo 109 + 7.

 

// Example 1:

// Input: n = 2, maxValue = 5
// Output: 10
// Explanation: The following are the possible ideal arrays:
// - Arrays starting with the value 1 (5 arrays): [1,1], [1,2], [1,3], [1,4], [1,5]
// - Arrays starting with the value 2 (2 arrays): [2,2], [2,4]
// - Arrays starting with the value 3 (1 array): [3,3]
// - Arrays starting with the value 4 (1 array): [4,4]
// - Arrays starting with the value 5 (1 array): [5,5]
// There are a total of 5 + 2 + 1 + 1 + 1 = 10 distinct ideal arrays.
// Example 2:

// Input: n = 5, maxValue = 3
// Output: 11
// Explanation: The following are the possible ideal arrays:
// - Arrays starting with the value 1 (9 arrays): 
//    - With no other distinct values (1 array): [1,1,1,1,1] 
//    - With 2nd distinct value 2 (4 arrays): [1,1,1,1,2], [1,1,1,2,2], [1,1,2,2,2], [1,2,2,2,2]
//    - With 2nd distinct value 3 (4 arrays): [1,1,1,1,3], [1,1,1,3,3], [1,1,3,3,3], [1,3,3,3,3]
// - Arrays starting with the value 2 (1 array): [2,2,2,2,2]
// - Arrays starting with the value 3 (1 array): [3,3,3,3,3]
// There are a total of 9 + 1 + 1 = 11 distinct ideal arrays.
 

// Constraints:

// 2 <= n <= 10^4
// 1 <= maxValue <= 10^4


// Solution: 

class Solution {
    final int MOD = 1_000_000_007;
    final int MAXN = 10010;
    long[] fact = new long[MAXN];
    long[] invFact = new long[MAXN];
    int[][] dp = new int[10010][15];

    // Fast power (modular exponentiation)
    long power(long a, long b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) res = res * a % MOD;
            a = a * a % MOD;
            b >>= 1;
        }
        return res;
    }

    // Precompute factorials and inverse factorials
    void precomputeFactorials() {
        fact[0] = invFact[0] = 1;
        for (int i = 1; i < MAXN; i++) {
            fact[i] = fact[i - 1] * i % MOD;
            invFact[i] = power(fact[i], MOD - 2);
        }
    }

    // Compute binomial coefficient C(n, k)
    long binomial(int n, int k) {
        if (k < 0 || k > n) return 0;
        return fact[n] * invFact[k] % MOD * invFact[n - k] % MOD;
    }

    public int idealArrays(int n, int maxValue) {
        precomputeFactorials();

        // Fill DP: dp[i][j] = count of arrays ending at i with length j
        for (int i = 1; i <= maxValue; i++) {
            dp[i][1] = 1;
        }

        for (int len = 1; len < 14; len++) {
            for (int val = 1; val <= maxValue; val++) {
                if (dp[val][len] == 0) continue;
                for (int mul = 2; val * mul <= maxValue; mul++) {
                    dp[val * mul][len + 1] = (dp[val * mul][len + 1] + dp[val][len]) % MOD;
                }
            }
        }

        long res = 0;
        for (int val = 1; val <= maxValue; val++) {
            for (int len = 1; len <= Math.min(14, n); len++) {
                long ways = binomial(n - 1, len - 1);
                res = (res + dp[val][len] * ways % MOD) % MOD;
            }
        }

        return (int) res;
    }
}
