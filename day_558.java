// 3702. Longest Subsequence With Non-Zero Bitwise XOR

// You are given an integer array nums.

// Return the length of the longest subsequence in nums whose bitwise XOR is non-zero. If no such subsequence exists, return 0.

 

// Example 1:

// Input: nums = [1,2,3]

// Output: 2

// Explanation:

// One longest subsequence is [2, 3]. The bitwise XOR is computed as 2 XOR 3 = 1, which is non-zero.

// Example 2:

// Input: nums = [2,3,4]

// Output: 3

// Explanation:

// The longest subsequence is [2, 3, 4]. The bitwise XOR is computed as 2 XOR 3 XOR 4 = 5, which is non-zero.

 

// Constraints:

// 1 <= nums.length <= 10^5
// 0 <= nums[i] <= 10^9


// Solution:




class Solution {
    public int longestSubsequence(int[] nums) {
        int sum = 0;
        int n = nums.length;
        boolean all0 = true;

        for (int x : nums) {
            sum ^= x;
            all0 &= (x == 0);
        }

        return all0 ? 0 : n - (sum == 0 ? 1 : 0);
    }
}