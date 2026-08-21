// 3116. Kth Smallest Amount With Single Denomination Combination

// You are given an integer array coins representing coins of different denominations and an integer k.

// You have an infinite number of coins of each denomination. However, you are not allowed to combine coins of different denominations.

// Return the kth smallest amount that can be made using these coins.

 

// Example 1:

// Input: coins = [3,6,9], k = 3

// Output: 9

// Explanation: The given coins can make the following amounts:
// Coin 3 produces multiples of 3: 3, 6, 9, 12, 15, etc.
// Coin 6 produces multiples of 6: 6, 12, 18, 24, etc.
// Coin 9 produces multiples of 9: 9, 18, 27, 36, etc.
// All of the coins combined produce: 3, 6, 9, 12, 15, etc.

// Example 2:

// Input: coins = [5,2], k = 7

// Output: 12

// Explanation: The given coins can make the following amounts:
// Coin 5 produces multiples of 5: 5, 10, 15, 20, etc.
// Coin 2 produces multiples of 2: 2, 4, 6, 8, 10, 12, etc.
// All of the coins combined produce: 2, 4, 5, 6, 8, 10, 12, 14, 15, etc.

 

// Constraints:

// 1 <= coins.length <= 15
// 1 <= coins[i] <= 25
// 1 <= k <= 2 * 10^9
// coins contains pairwise distinct integers.




// Solution:



class Solution {
    static final int N = 1 << 15;
    static long[] dp = new long[N];

    static long f(long x, int bitMask) {
        long cnt = 0;

        for (int i = 1; i <= bitMask; i++) {
            cnt += (Integer.bitCount(i) & 1) == 1
                    ? x / dp[i]
                    : -x / dp[i];
        }

        return cnt;
    }

    public long findKthSmallest(int[] coins, int k) {
        Arrays.sort(coins);

        // If 1 is present, every positive integer is divisible by 1.
        if (coins[0] == 1) {
            return k;
        }

        // Remove redundant coins.
        // A coin is redundant if it is a multiple of a smaller coin.
        boolean[] valid = new boolean[26];

        for (int c : coins) {
            valid[c] = true;

            for (int r = 2 * c; r < 26; r += c) {
                valid[r] = false;
            }
        }

        int[] temp = new int[25];
        int sz = 0;

        for (int i = 1; i <= 25; i++) {
            if (valid[i]) {
                temp[sz++] = i;
            }
        }

        int[] reduced = Arrays.copyOf(temp, sz);

        if (sz == 1) {
            return (long) reduced[0] * k;
        }

        int bitMask = (1 << sz) - 1;

        Arrays.fill(dp, 0);

        // Calculate LCM for every subset.
        for (int mask = 1; mask <= bitMask; mask++) {
            long lcm = 1;

            for (int i = 0; i < sz; i++) {
                if ((mask & (1 << i)) != 0) {
                    lcm = lcm / gcd(lcm, reduced[i]) * reduced[i];
                }
            }

            dp[mask] = lcm;
        }

        long left = k + 1;
        long right = (long) reduced[0] * k;
        long ans = right;

        // Binary search for the smallest x such that
        // at least k positive integers <= x are divisible
        // by at least one coin.
        while (left <= right) {
            long mid = left + (right - left) / 2;

            if (f(mid, bitMask) >= k) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    static long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}