// 3346. Maximum Frequency of an Element After Performing Operations I

// You are given an integer array nums and two integers k and numOperations.

// You must perform an operation numOperations times on nums, where in each operation you:

// Select an index i that was not selected in any previous operations.
// Add an integer in the range [-k, k] to nums[i].
// Return the maximum possible frequency of any element in nums after performing the operations.

 

// Example 1:

// Input: nums = [1,4,5], k = 1, numOperations = 2

// Output: 2

// Explanation:

// We can achieve a maximum frequency of two by:

// Adding 0 to nums[1]. nums becomes [1, 4, 5].
// Adding -1 to nums[2]. nums becomes [1, 4, 4].
// Example 2:

// Input: nums = [5,11,20,20], k = 5, numOperations = 1

// Output: 2

// Explanation:

// We can achieve a maximum frequency of two by:

// Adding 0 to nums[1].
 

// Constraints:

// 1 <= nums.length <= 10^5
// 1 <= nums[i] <= 10^5
// 0 <= k <= 10^5
// 0 <= numOperations <= nums.length



// Solution: 


import java.util.*;

class Solution {
    public int maxFrequency(int[] nums, int k, int numOperations) {
        final int N = 100001;
        int[] freq = new int[N];
        int maxNum = 0;

        // Count frequency and find max value in nums
        for (int x : nums) {
            freq[x]++;
            maxNum = Math.max(maxNum, x);
        }

        // Compute prefix sum
        int[] prefix = new int[maxNum + 2]; // +2 to avoid index out of bounds
        for (int i = 1; i <= maxNum; i++) {
            prefix[i] = prefix[i - 1] + freq[i];
        }

        int ans = 0;

        // Try to maximize frequency for each number x
        for (int x = 1; x <= maxNum; x++) {
            int l = Math.max(1, x - k);
            int r = Math.min(maxNum, x + k);
            int countInRange = prefix[r] - prefix[l - 1];
            int availableOps = numOperations;
            int canAdd = Math.min(countInRange - freq[x], availableOps);
            ans = Math.max(ans, freq[x] + canAdd);
        }

        return ans;
    }
}
