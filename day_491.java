// 3753. Total Waviness of Numbers in Range II

// You are given two integers num1 and num2 representing an inclusive range [num1, num2].

// The waviness of a number is defined as the total count of its peaks and valleys:

// A digit is a peak if it is strictly greater than both of its immediate neighbors.
// A digit is a valley if it is strictly less than both of its immediate neighbors.
// The first and last digits of a number cannot be peaks or valleys.
// Any number with fewer than 3 digits has a waviness of 0.
// Return the total sum of waviness for all numbers in the range [num1, num2].
 

// Example 1:

// Input: num1 = 120, num2 = 130

// Output: 3

// Explanation:

// In the range [120, 130]:

// 120: middle digit 2 is a peak, waviness = 1.
// 121: middle digit 2 is a peak, waviness = 1.
// 130: middle digit 3 is a peak, waviness = 1.
// All other numbers in the range have a waviness of 0.
// Thus, total waviness is 1 + 1 + 1 = 3.

// Example 2:

// Input: num1 = 198, num2 = 202

// Output: 3

// Explanation:

// In the range [198, 202]:

// 198: middle digit 9 is a peak, waviness = 1.
// 201: middle digit 0 is a valley, waviness = 1.
// 202: middle digit 0 is a valley, waviness = 1.
// All other numbers in the range have a waviness of 0.
// Thus, total waviness is 1 + 1 + 1 = 3.

// Example 3:

// Input: num1 = 4848, num2 = 4848

// Output: 2

// Explanation:

// Number 4848: the second digit 8 is a peak, and the third digit 4 is a valley, giving a waviness of 2.

 

// Constraints:

// 1 <= num1 <= num2 <= 10^15​​​​​​​

// Solution:




import java.util.Arrays;

class Solution {

    static long[][][][][] dp = new long[15][2][2][3][10];
    static long[] tens = new long[15];

    static void computeTens() {
        if (tens[0] == 1) return;

        long prod = 1;
        tens[0] = 1;

        for (int i = 1; i < 15; i++) {
            prod *= 10;
            tens[i] = prod;
        }
    }

    static long f(int i, int tight, int lead0, int cmp, int prv, long num, int[] s) {
        if (i == 15) return 0;

        if (dp[i][tight][lead0][cmp][prv] != -1)
            return dp[i][tight][lead0][cmp][prv];

        long cnt = 0;

        int lim = (tight == 1) ? s[i] : 9;

        // O(1) suffix calculation
        long tightWays = (tight == 1) ? (num % tens[14 - i] + 1) : 0;

        for (int d = 0; d <= lim; d++) {
            int nxtTight = (tight == 1 && d == lim) ? 1 : 0;
            int nxtLead0 = (lead0 == 1 && d == 0) ? 1 : 0;

            int nxtCmp;
            if (lead0 == 0) {
                nxtCmp = (prv < d) ? 1 : ((prv > d) ? 2 : 0);
            } else {
                nxtCmp = 0;
            }

            boolean isWav = ((cmp | nxtCmp) == 3);

            long ways = (nxtTight == 1) ? tightWays : tens[14 - i];
            long summand = isWav ? ways : 0;

            cnt += summand + f(i + 1, nxtTight, nxtLead0, nxtCmp, d, num, s);
        }

        return dp[i][tight][lead0][cmp][prv] = cnt;
    }

    static long solve(long num) {
        if (num < 101) return 0;

        int[] s = new int[15];

        long x = num;
        for (int i = 14; i >= 0; i--) {
            s[i] = (int) (x % 10);
            x /= 10;
        }

        for (long[][][][] a : dp)
            for (long[][][] b : a)
                for (long[][] c : b)
                    for (long[] d : c)
                        Arrays.fill(d, -1);

        return f(0, 1, 1, 0, 0, num, s);
    }

    public long totalWaviness(long num1, long num2) {
        computeTens();

        num2 = Math.min(num2, 1_000_000_000_000_000L - 1);

        if (num1 > num2) return 0;

        return solve(num2) - solve(num1 - 1);
    }
}