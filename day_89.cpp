// 2799. Count Complete Subarrays in an Array

// You are given an array nums consisting of positive integers.

// We call a subarray of an array complete if the following condition is satisfied:

// The number of distinct elements in the subarray is equal to the number of distinct elements in the whole array.
// Return the number of complete subarrays.

// A subarray is a contiguous non-empty part of an array.

 

// Example 1:

// Input: nums = [1,3,1,2,2]
// Output: 4
// Explanation: The complete subarrays are the following: [1,3,1,2], [1,3,1,2,2], [3,1,2] and [3,1,2,2].
// Example 2:

// Input: nums = [5,5,5,5]
// Output: 10
// Explanation: The array consists only of the integer 5, so any subarray is complete. The number of subarrays that we can choose is 10.
 

// Constraints:

// 1 <= nums.length <= 1000
// 1 <= nums[i] <= 2000


// Solution:


class Solution {
    public:
        int countCompleteSubarrays(vector<int>& nums) {
            int res = 0;
            unordered_map<int, int> cnt;
            int n = nums.size();
            int right = 0;
            
            // Get total number of distinct elements in the array
            unordered_set<int> distinct(nums.begin(), nums.end());
            int distinct_count = distinct.size();
    
            // Sliding window from left to right
            for (int left = 0; left < n; left++) {
                // Shrink the window by removing the element at left - 1
                if (left > 0) {
                    int remove = nums[left - 1];
                    cnt[remove]--;
                    if (cnt[remove] == 0) {
                        cnt.erase(remove);
                    }
                }
    
                // Expand the window until we have enough distinct elements
                while (right < n && cnt.size() < distinct_count) {
                    int add = nums[right];
                    cnt[add]++;
                    right++;
                }
    
                // If the window is valid, count how many complete subarrays start at `left`
                if (cnt.size() == distinct_count) {
                    res += (n - right + 1);
                }
            }
    
            return res;
        }
    };
    