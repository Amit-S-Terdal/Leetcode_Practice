// 3700. Number of ZigZag Arrays II

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
// [5, 4, 5]
// Example 2:

// Input: n = 3, l = 1, r = 3

// Output: 10

// Explanation:

// ​​​​​​​There are 10 valid ZigZag arrays of length n = 3 using values in the range [1, 3]:

// [1, 2, 1], [1, 3, 1], [1, 3, 2]
// [2, 1, 2], [2, 1, 3], [2, 3, 1], [2, 3, 2]
// [3, 1, 2], [3, 1, 3], [3, 2, 3]
// All arrays meet the ZigZag conditions.

 

// Constraints:

// 3 <= n <= 10^9
// 1 <= l < r <= 75​​​​​​​




// Solution: 




import java.util.*;

class Solution {
    private static final int MOD = 1_000_000_007;
    private int m;

    private int[] multiply(int[] A, int[] B) {
        int[] C = new int[m * m];

        for (int i = 0; i < m; i++) {
            for (int k = 0; k < m; k++) {
                if (A[i * m + k] == 0) continue;

                long a = A[i * m + k];

                for (int j = 0; j < m; j++) {
                    C[i * m + j] = (int) ((C[i * m + j]
                            + a * B[k * m + j]) % MOD);
                }
            }
        }
        return C;
    }

    private int[] identity() {
        int[] ans = new int[m * m];
        for (int i = 0; i < m; i++) {
            ans[i * m + i] = 1;
        }
        return ans;
    }

    // MSBF modular Matrix Exponentiation
    private int[] power(int[] M, int exp) {
        if (exp == 0) {
            return identity();
        }

        int bMax = 31 - Integer.numberOfLeadingZeros(exp);
        int[] ans = M.clone();

        for (int i = bMax - 1; i >= 0; i--) {
            ans = multiply(ans, ans);

            if (((exp >> i) & 1) == 1) {
                ans = multiply(ans, M);
            }
        }

        return ans;
    }

    public int zigZagArrays(int n, int l, int r) {
        m = r - l + 1;

        int[] U = new int[m * m];
        int[] L = new int[m * m];

        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                U[i * m + j] = 1;
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < i; j++) {
                L[i * m + j] = 1;
            }
        }

        n--;

        int n0 = n >> 1;

        int[] UL = multiply(U, L);
        int[] P = power(UL, n0);

        if ((n & 1) == 1) {
            P = multiply(L, P);
        }

        long sum = 0;
        for (int x : P) {
            sum += x;
        }

        return (int) ((2L * (sum % MOD)) % MOD);
    }
}