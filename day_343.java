// 1458. Max Dot Product of Two Subsequences

// Given two arrays nums1 and nums2.

// Return the maximum dot product between non-empty subsequences of nums1 and nums2 with the same length.

// A subsequence of a array is a new array which is formed from the original array by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, [2,3,5] is a subsequence of [1,2,3,4,5] while [1,5,3] is not).

 

// Example 1:

// Input: nums1 = [2,1,-2,5], nums2 = [3,0,-6]
// Output: 18
// Explanation: Take subsequence [2,-2] from nums1 and subsequence [3,-6] from nums2.
// Their dot product is (2*3 + (-2)*(-6)) = 18.
// Example 2:

// Input: nums1 = [3,-2], nums2 = [2,-6,7]
// Output: 21
// Explanation: Take subsequence [3] from nums1 and subsequence [7] from nums2.
// Their dot product is (3*7) = 21.
// Example 3:

// Input: nums1 = [-1,-1], nums2 = [1,1]
// Output: -1
// Explanation: Take subsequence [-1] from nums1 and subsequence [1] from nums2.
// Their dot product is -1.
 

// Constraints:

// 1 <= nums1.length, nums2.length <= 500
// -1000 <= nums1[i], nums2[i] <= 1000




// Solution:


import java.util.Arrays;

class Solution {
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n1 = nums1.length, n2 = nums2.length;

        // Ensure nums1 is the longer array (space optimization)
        if (n1 < n2) {
            return maxDotProduct(nums2, nums1);
        }

        int[][] dp = new int[2][n2 + 1];
        for (int i = 0; i < 2; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }

        int res = Integer.MIN_VALUE;

        for (int i = n1 - 1; i >= 0; i--) {
            int cur = i & 1;
            int next = (i + 1) & 1;

            for (int j = n2 - 1; j >= 0; j--) {
                int x = nums1[i] * nums2[j];
                int tmp = dp[cur][j];

                tmp = Math.max(tmp, x);
                tmp = Math.max(tmp, x + ((i + 1 < n1 && j + 1 < n2) ? dp[next][j + 1] : 0));
                tmp = Math.max(tmp, dp[cur][j + 1]);

                dp[cur][j] = Math.max(tmp, dp[next][j]);
                res = Math.max(res, dp[cur][j]);
            }
        }

        return res;
    }
}


