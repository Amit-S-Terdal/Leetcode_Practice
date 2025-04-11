// 416. Partition Equal Subset Sum

// Given an integer array nums, return true if you can partition the array into two subsets such that the sum of the elements in both subsets is equal or false otherwise.

 

// Example 1:

// Input: nums = [1,5,11,5]
// Output: true
// Explanation: The array can be partitioned as [1, 5, 5] and [11].
// Example 2:

// Input: nums = [1,2,3,5]
// Output: false
// Explanation: The array cannot be partitioned into equal sum subsets.
 

// Constraints:

// 1 <= nums.length <= 200
// 1 <= nums[i] <= 100


// Solution: 

class Solution {
    public:
        bool canPartition(vector<int>& nums) {
            int totalSum = 0;
            for (int num : nums) totalSum += num;
    
            if (totalSum % 2 != 0) return false;
    
            int targetSum = totalSum / 2;
            vector<bool> dp(targetSum + 1, false);
            dp[0] = true;
            for (int num : nums) {
                for (int currSum = targetSum; currSum >= num; --currSum) {
                    dp[currSum] = dp[currSum] || dp[currSum - num];
                    if (dp[targetSum]) return true;
                }
            }
            return dp[targetSum];
        }
    };