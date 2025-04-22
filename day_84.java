// 2563. Count the Number of Fair Pairs

// Given a 0-indexed integer array nums of size n and two integers lower and upper, return the number of fair pairs.

// A pair (i, j) is fair if:

// 0 <= i < j < n, and
// lower <= nums[i] + nums[j] <= upper
 

// Example 1:

// Input: nums = [0,1,7,4,4,5], lower = 3, upper = 6
// Output: 6
// Explanation: There are 6 fair pairs: (0,3), (0,4), (0,5), (1,3), (1,4), and (1,5).
// Example 2:

// Input: nums = [1,7,9,2,5], lower = 11, upper = 11
// Output: 1
// Explanation: There is a single fair pair: (2,3).
 

// Constraints:

// 1 <= nums.length <= 10^5
// nums.length == n
// -10^9 <= nums[i] <= 10^9
// -10^9 <= lower <= upper <= 10^9


// Solution:


import java.util.Arrays;

class Solution {
    public long countFairPairs(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        long ans = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            // Binary search for lower bound (first j > i where nums[i] + nums[j] >= lower)
            int left = lowerBound(nums, i + 1, n - 1, lower - nums[i]);

            // Binary search for upper bound (first j > i where nums[i] + nums[j] > upper)
            int right = lowerBound(nums, i + 1, n - 1, upper - nums[i] + 1);

            ans += (right - left);
        }

        return ans;
    }

    // Custom implementation of lower_bound (same as C++ STL lower_bound)
    private int lowerBound(int[] nums, int start, int end, int target) {
        int low = start, high = end;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }
}
