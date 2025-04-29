// 2444. Count Subarrays With Fixed Bounds

// You are given an integer array nums and two integers minK and maxK.

// A fixed-bound subarray of nums is a subarray that satisfies the following conditions:

// The minimum value in the subarray is equal to minK.
// The maximum value in the subarray is equal to maxK.
// Return the number of fixed-bound subarrays.

// A subarray is a contiguous part of an array.

 

// Example 1:

// Input: nums = [1,3,5,2,7,5], minK = 1, maxK = 5
// Output: 2
// Explanation: The fixed-bound subarrays are [1,3,5] and [1,3,5,2].
// Example 2:

// Input: nums = [1,1,1,1], minK = 1, maxK = 1
// Output: 10
// Explanation: Every subarray of nums is a fixed-bound subarray. There are 10 possible subarrays.
 

// Constraints:

// 2 <= nums.length <= 10^5
// 1 <= nums[i], minK, maxK <= 10^6


// Solution:  


class Solution {
    public:
        long long countSubarrays(vector<int>& nums, int minK, int maxK) {
            long long ans = 0;
            int n = nums.size();
            int minPos = -1, maxPos = -1, leftBound = -1;
            
            for (int i = 0; i < n; ++i) {
                if (nums[i] < minK || nums[i] > maxK) {
                    leftBound = i; // Reset if out of [minK, maxK]
                    minPos = -1;
                    maxPos = -1;
                }
                if (nums[i] == minK) minPos = i;
                if (nums[i] == maxK) maxPos = i;
                
                int validPos = min(minPos, maxPos);
                if (validPos > leftBound) {
                    ans += validPos - leftBound;
                }
            }
            
            return ans;
        }
    };
    
