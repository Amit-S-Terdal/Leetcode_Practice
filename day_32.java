// 1749. Maximum Absolute Sum of Any Subarray

// You are given an integer array nums. The absolute sum of a subarray [numsl, numsl+1, ..., numsr-1, numsr] is abs(numsl + numsl+1 + ... + numsr-1 + numsr).

// Return the maximum absolute sum of any (possibly empty) subarray of nums.

// Note that abs(x) is defined as follows:

// If x is a negative integer, then abs(x) = -x.
// If x is a non-negative integer, then abs(x) = x.
 

// Example 1:

// Input: nums = [1,-3,2,3,-4]
// Output: 5
// Explanation: The subarray [2,3] has absolute sum = abs(2+3) = abs(5) = 5.
// Example 2:

// Input: nums = [2,-5,1,-4,3,-2]
// Output: 8
// Explanation: The subarray [-5,1,-4] has absolute sum = abs(-5+1-4) = abs(-8) = 8.
 

// Constraints:

// 1 <= nums.length <= 10^5
// -10^4 <= nums[i] <= 10^4


// Solution:


class Solution {
    public int maxAbsoluteSum(int[] nums) {
        int maxSum = nums[0]; // Tracks the maximum subarray sum
        int minSum = nums[0]; // Tracks the minimum subarray sum
        int currentMax = nums[0]; // Tracks the maximum subarray sum ending at the current position
        int currentMin = nums[0]; // Tracks the minimum subarray sum ending at the current position
        int result = Math.abs(nums[0]); // Tracks the maximum absolute sum found so far

        for (int i = 1; i < nums.length; i++) {
            // Update currentMax and currentMin
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            currentMin = Math.min(nums[i], currentMin + nums[i]);

            // Update maxSum and minSum
            maxSum = Math.max(maxSum, currentMax);
            minSum = Math.min(minSum, currentMin);

            // Update result with the maximum absolute value
            result = Math.max(result, Math.max(Math.abs(maxSum), Math.abs(minSum)));
        }

        return result;
    }
}