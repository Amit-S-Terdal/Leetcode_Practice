// 1262. Greatest Sum Divisible by Three

// Given an integer array nums, return the maximum possible sum of elements of the array such that it is divisible by three.

 

// Example 1:

// Input: nums = [3,6,5,1,8]
// Output: 18
// Explanation: Pick numbers 3, 6, 1 and 8 their sum is 18 (maximum sum divisible by 3).
// Example 2:

// Input: nums = [4]
// Output: 0
// Explanation: Since 4 is not divisible by 3, do not pick any number.
// Example 3:

// Input: nums = [1,2,3,4,4]
// Output: 12
// Explanation: Pick numbers 1, 3, 4 and 4 their sum is 12 (maximum sum divisible by 3).
 

// Constraints:

// 1 <= nums.length <= 4 * 10^4
// 1 <= nums[i] <= 10^4


// Solution: 


class Solution {
    public int maxSumDivThree(int[] nums) {
        final int n = nums.length, minus = -1_000_000_000;
        int[][] dp = new int[2][3];
        
        // Initialize dp array
        dp[0][0] = 0;
        dp[0][1] = dp[0][2] = minus;
        dp[1][0] = 0;
        dp[1][1] = dp[1][2] = minus;
        
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int mod = 0; mod < 3; mod++) {
                // Calculate the previous modulo state
                int modPrev = (mod - x % 3 + 3) % 3;
                // Take current number x or skip it
                int take = x + dp[(i + 1) % 2][modPrev];
                int skip = dp[(i + 1) % 2][mod];
                // Update the dp array with the maximum of taking or skipping
                dp[i % 2][mod] = Math.max(take, skip);
            }
        }
        
        // The result is the maximum sum that is divisible by 3
        return Math.max(0, dp[(n - 1) % 2][0]);
    }
}
