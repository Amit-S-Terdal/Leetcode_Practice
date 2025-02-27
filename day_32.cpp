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
    public:
        int maxAbsoluteSum(vector<int>& nums) {
            int maxSum = 0, minSum = 0; // Tracks the maximum and minimum subarray sums
            int currentMax = 0, currentMin = 0; // Tracks the current subarray sums
            int result = 0; // Tracks the maximum absolute sum
    
            for (int num : nums) {
                // Update currentMax and currentMin
                currentMax = max(num, currentMax + num);
                currentMin = min(num, currentMin + num);
    
                // Update maxSum and minSum
                maxSum = max(maxSum, currentMax);
                minSum = min(minSum, currentMin);
    
                // Update result with the maximum absolute value
                result = max(result, max(abs(maxSum), abs(minSum)));
            }
    
            return result;
        }
    };
