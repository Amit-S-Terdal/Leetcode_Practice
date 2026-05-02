// 788. Rotated Digits

// An integer x is a good if after rotating each digit individually by 180 degrees, we get a valid number that is different from x. Each digit must be rotated - we cannot choose to leave it alone.

// A number is valid if each digit remains a digit after rotation. For example:

// 0, 1, and 8 rotate to themselves,
// 2 and 5 rotate to each other (in this case they are rotated in a different direction, in other words, 2 or 5 gets mirrored),
// 6 and 9 rotate to each other, and
// the rest of the numbers do not rotate to any other number and become invalid.
// Given an integer n, return the number of good integers in the range [1, n].

 

// Example 1:

// Input: n = 10
// Output: 4
// Explanation: There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
// Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.
// Example 2:

// Input: n = 1
// Output: 0
// Example 3:

// Input: n = 2
// Output: 1
 

// Constraints:

// 1 <= n <= 10^4




// Solution: 




class Solution {
    int[][][] dp = new int[7][2][10];
    int mask = (1<<2) | (1<<5) | (1<<6) | (1<<9);
    int invalid = (1<<3) | (1<<4) | (1<<7);

    public int f(int i, int tight, int prev, String s) {
        if (i == 6) return prev > 0 ? 1 : 0;

        if (dp[i][tight][prev] != -1) return dp[i][tight][prev];

        int cnt = 0;
        int lim = (tight == 1) ? (s.charAt(i) - '0') : 9;

        for (int d = 0; d <= lim; d++) {
            int nxtTight = (tight == 1 && d == lim) ? 1 : 0;

            if (((1 << d) & invalid) != 0) continue;

            if (((1 << d) & mask) != 0) {
                cnt += f(i + 1, nxtTight, d, s);
            } else {
                cnt += f(i + 1, nxtTight, prev, s);
            }
        }

        return dp[i][tight][prev] = cnt;
    }

    public String toS(int n) {
        char[] s = new char[6];
        for (int i = 0; i < 6; i++) s[i] = '0';

        int i = 5;
        while (n > 0) {
            s[i--] = (char)('0' + (n % 10));
            n /= 10;
        }

        return new String(s);
    }

    public int rotatedDigits(int n) {
        String s = toS(n);

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 10; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }

        return f(0, 1, 0, s);
    }
}