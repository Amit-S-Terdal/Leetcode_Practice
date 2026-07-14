// 3336. Find the Number of Subsequences With Equal GCD

// You are given an integer array nums.

// Your task is to find the number of pairs of non-empty subsequences (seq1, seq2) of nums that satisfy the following conditions:

// The subsequences seq1 and seq2 are disjoint, meaning no index of nums is common between them.
// The GCD of the elements of seq1 is equal to the GCD of the elements of seq2.
// Return the total number of such pairs.

// Since the answer may be very large, return it modulo 109 + 7.

 

// Example 1:

// Input: nums = [1,2,3,4]

// Output: 10

// Explanation:

// The subsequence pairs which have the GCD of their elements equal to 1 are:

// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// Example 2:

// Input: nums = [10,20,30]

// Output: 2

// Explanation:

// The subsequence pairs which have the GCD of their elements equal to 10 are:

// ([10, 20, 30], [10, 20, 30])
// ([10, 20, 30], [10, 20, 30])
// Example 3:

// Input: nums = [1,1,1,1]

// Output: 50

 

// Constraints:

// 1 <= nums.length <= 200
// 1 <= nums[i] <= 200





// Solution:



class Solution {
    static final int N = 201;
    static final int MOD = 1_000_000_007;

    static int[][] GCD = new int[N][N];
    static int[][] dp = new int[2][N * N];
    static boolean gcdComputed = false;

    static void computeGCD() {
        if (gcdComputed) return;
        gcdComputed = true;

        for (int x = 0; x < N; x++) {
            GCD[x][x] = x;
            GCD[x][0] = x;
            GCD[0][x] = x;
        }

        for (int x = 1; x < N; x++) {
            GCD[x][1] = 1;
            GCD[1][x] = 1;
            for (int y = 2; y < x; y++) {
                GCD[x][y] = GCD[y][x] = GCD[y][x - y];
            }
        }
    }

    public int subsequencePairCount(int[] nums) {
        computeGCD();

        int M = 0;
        int qM = 0;

        long[] bit = new long[4];
        bit[0] |= 1L; // set bit 0

        for (int x : nums) {
            M = Math.max(M, x);
            int q = x >> 6;
            int r = x & 63;
            qM = Math.max(qM, q);
            bit[q] |= (1L << r);
        }

        int width = M + 1;
        int M2 = width * width;

        // reachable gcds
        for (int x = 0; x <= M; x++) {
            int q = x >> 6;
            int r = x & 63;

            if (((bit[q] >>> r) & 1L) != 0) continue;

            for (int y = 2 * x; y <= M; y += x) {
                int q2 = y >> 6;
                int r2 = y & 63;

                if (((bit[q2] >>> r2) & 1L) != 0) {
                    bit[q] |= (1L << r);
                    qM = Math.max(qM, q);
                    break;
                }
            }
        }

        java.util.Arrays.fill(dp[0], 0, M2, 0);
        dp[0][0] = 1;

        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int x = nums[i];

            int cur = i & 1;
            int nxt = cur ^ 1;

            java.util.Arrays.fill(dp[nxt], 0, M2, 0);

            for (int iq = 0; iq <= qM; iq++) {
                long ir = bit[iq];

                while (ir != 0) {
                    int r1 = Long.numberOfTrailingZeros(ir);
                    int g1 = (iq << 6) + r1;
                    ir &= ir - 1;

                    for (int jq = 0; jq <= qM; jq++) {
                        long jr = bit[jq];

                        while (jr != 0) {
                            int r2 = Long.numberOfTrailingZeros(jr);
                            int g2 = (jq << 6) + r2;
                            jr &= jr - 1;

                            int idx = g1 * width + g2;
                            int curDp = dp[cur][idx];
                            if (curDp == 0) continue;

                            int ng1 = GCD[g1][x];
                            int idx1 = ng1 * width + g2;
                            dp[nxt][idx1] += curDp;
                            if (dp[nxt][idx1] >= MOD)
                                dp[nxt][idx1] -= MOD;

                            int ng2 = GCD[g2][x];
                            int idx2 = g1 * width + ng2;
                            dp[nxt][idx2] += curDp;
                            if (dp[nxt][idx2] >= MOD)
                                dp[nxt][idx2] -= MOD;

                            dp[nxt][idx] += curDp;
                            if (dp[nxt][idx] >= MOD)
                                dp[nxt][idx] -= MOD;
                        }
                    }
                }
            }
        }

        int last = n & 1;
        long ans = 0;

        bit[0] ^= 1L; // remove bit 0

        for (int iq = 0; iq <= qM; iq++) {
            long ir = bit[iq];

            while (ir != 0) {
                int r1 = Long.numberOfTrailingZeros(ir);
                int g = (iq << 6) + r1;
                ir &= ir - 1;

                ans += dp[last][g * (width + 1)];
                ans %= MOD;
            }
        }

        return (int) ans;
    }
}