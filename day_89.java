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


import java.util.*;

class Solution {
    public int countCompleteSubarrays(int[] nums) {
        int res = 0;
        Map<Integer, Integer> countMap = new HashMap<>();
        int n = nums.length;
        int right = 0;

        // Step 1: Get the total number of distinct elements in the array
        Set<Integer> distinct = new HashSet<>();
        for (int num : nums) {
            distinct.add(num);
        }
        int distinctCount = distinct.size();

        // Step 2: Sliding window logic
        for (int left = 0; left < n; left++) {
            if (left > 0) {
                int remove = nums[left - 1];
                countMap.put(remove, countMap.get(remove) - 1);
                if (countMap.get(remove) == 0) {
                    countMap.remove(remove);
                }
            }

            while (right < n && countMap.size() < distinctCount) {
                int add = nums[right];
                countMap.put(add, countMap.getOrDefault(add, 0) + 1);
                right++;
            }

            if (countMap.size() == distinctCount) {
                res += (n - right + 1);
            }
        }

        return res;
    }
}
