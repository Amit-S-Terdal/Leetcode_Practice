// 869. Reordered Power of 2

// You are given an integer n. We reorder the digits in any order (including the original order) such that the leading digit is not zero.

// Return true if and only if we can do this so that the resulting number is a power of two.

 

// Example 1:

// Input: n = 1
// Output: true
// Example 2:

// Input: n = 10
// Output: false
 

// Constraints:

// 1 <= n <= 109

// Solution: 

class Solution {
    private static int[][][] dig = new int[10][4][10]; // digit count arrays
    private static boolean computed = false;
    private static int[] tens = {1, 10, 100, 1000, (int)1e4, (int)1e5, (int)1e6,
                                 (int)1e7, (int)1e8, (int)1e9};

    private static int[] freq(int x) {
        int[] f = new int[10];
        while (x > 0) {
            f[x % 10]++;
            x /= 10;
        }
        return f;
    }

    private static void computeDig() {
        if (computed) return; // run only once
        for (int i = 0, j = 0, d = 1; i < 30; i++) {
            int x = 1 << i;
            if (x > tens[d]) {
                d++;
                j = 0;
            }
            dig[d][j++] = freq(x);
        }
        computed = true;
    }

    public boolean reorderedPowerOf2(int n) {
        if (n == 1_000_000_000) return false; // edge case
        computeDig();
        int d = 0;
        while (d < 10 && tens[d] <= n) d++;
        int[] fn = freq(n);
        for (int i = 0; i < 4; i++) {
            if (java.util.Arrays.equals(fn, dig[d][i])) return true;
        }
        return false;
    }
}
