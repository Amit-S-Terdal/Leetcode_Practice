// 3539. Find Sum of Array Product of Magical Sequences

// You are given two integers, m and k, and an integer array nums.

// A sequence of integers seq is called magical if:
// seq has a size of m.
// 0 <= seq[i] < nums.length
// The binary representation of 2seq[0] + 2seq[1] + ... + 2seq[m - 1] has k set bits.
// The array product of this sequence is defined as prod(seq) = (nums[seq[0]] * nums[seq[1]] * ... * nums[seq[m - 1]]).

// Return the sum of the array products for all valid magical sequences.

// Since the answer may be large, return it modulo 109 + 7.

// A set bit refers to a bit in the binary representation of a number that has a value of 1.

 

// Example 1:

// Input: m = 5, k = 5, nums = [1,10,100,10000,1000000]

// Output: 991600007

// Explanation:

// All permutations of [0, 1, 2, 3, 4] are magical sequences, each with an array product of 1013.

// Example 2:

// Input: m = 2, k = 2, nums = [5,4,3,2,1]

// Output: 170

// Explanation:

// The magical sequences are [0, 1], [0, 2], [0, 3], [0, 4], [1, 0], [1, 2], [1, 3], [1, 4], [2, 0], [2, 1], [2, 3], [2, 4], [3, 0], [3, 1], [3, 2], [3, 4], [4, 0], [4, 1], [4, 2], and [4, 3].

// Example 3:

// Input: m = 1, k = 1, nums = [28]

// Output: 28

// Explanation:

// The only magical sequence is [0].

 

// Constraints:

// 1 <= k <= m <= 30
// 1 <= nums.length <= 50
// 1 <= nums[i] <= 10^8



// Solution: 



class Solution {
    static final int MOD = 1000000007;
    static int[][] C = new int[31][31]; 
    static int[][][][] dp = new int[31][31][50][31];
    
    public int magicalSum(int m, int k, int[] nums) {
        Pascal(); // Initialize Pascal's triangle
        int n = nums.length;

        // Initialize dp array with -1
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= m; j++) {
                for (int s = 0; s < n; s++) {
                    for (int t = 0; t <= m; t++) {
                        dp[i][j][s][t] = -1;
                    }
                }
            }
        }

        return dfs(m, k, 0, 0, nums);
    }

    private void Pascal() {
        if (C[0][0] == 1) return; // Compute only once
        for (int i = 1; i <= 30; i++) {
            C[i][0] = C[i][i] = 1;
            for (int j = 1; j <= i / 2; j++) {
                int Cij = C[i - 1][j - 1] + C[i - 1][j];
                if (Cij >= MOD) Cij -= MOD;
                C[i][j] = C[i][i - j] = Cij;
            }
        }
    }

    private int dfs(int m, int k, int i, int flag, int[] nums) {
        int bz = Integer.bitCount(flag); // Popcount equivalent in Java
        if (m < 0 || k < 0 || m + bz < k) return 0;
        if (m == 0) return (k == bz) ? 1 : 0;
        if (i >= nums.length) return 0;

        if (dp[m][k][i][flag] != -1) return dp[m][k][i][flag];

        long ans = 0;
        long powX = 1;
        int x = nums[i];
        for (int f = 0; f <= m; f++) {
            long perm = (C[m][f] * powX) % MOD;

            int newFlag = flag + f;
            int nextFlag = newFlag >> 1;
            boolean bitSet = (newFlag & 1) != 0;

            ans = (ans + perm * dfs(m - f, k - (bitSet ? 1 : 0), i + 1, nextFlag, nums)) % MOD;
            powX = (powX * x) % MOD;
        }

        return dp[m][k][i][flag] = (int) ans;
    }
}
