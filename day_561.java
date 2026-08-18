// 3471. Find the Largest Almost Missing Integer

// You are given an integer array nums and an integer k.

// An integer x is almost missing from nums if x appears in exactly one subarray of size k within nums.

// Return the largest almost missing integer from nums. If no such integer exists, return -1.

// A subarray is a contiguous sequence of elements within an array.
 

// Example 1:

// Input: nums = [3,9,2,1,7], k = 3

// Output: 7

// Explanation:

// 1 appears in 2 subarrays of size 3: [9, 2, 1] and [2, 1, 7].
// 2 appears in 3 subarrays of size 3: [3, 9, 2], [9, 2, 1], [2, 1, 7].
// 3 appears in 1 subarray of size 3: [3, 9, 2].
// 7 appears in 1 subarray of size 3: [2, 1, 7].
// 9 appears in 2 subarrays of size 3: [3, 9, 2], and [9, 2, 1].
// We return 7 since it is the largest integer that appears in exactly one subarray of size k.

// Example 2:

// Input: nums = [3,9,7,2,1,7], k = 4

// Output: 3

// Explanation:

// 1 appears in 2 subarrays of size 4: [9, 7, 2, 1], [7, 2, 1, 7].
// 2 appears in 3 subarrays of size 4: [3, 9, 7, 2], [9, 7, 2, 1], [7, 2, 1, 7].
// 3 appears in 1 subarray of size 4: [3, 9, 7, 2].
// 7 appears in 3 subarrays of size 4: [3, 9, 7, 2], [9, 7, 2, 1], [7, 2, 1, 7].
// 9 appears in 2 subarrays of size 4: [3, 9, 7, 2], [9, 7, 2, 1].
// We return 3 since it is the largest and only integer that appears in exactly one subarray of size k.

// Example 3:

// Input: nums = [0,0], k = 1

// Output: -1

// Explanation:

// There is no integer that appears in only one subarray of size 1.

 

// Constraints:

// 1 <= nums.length <= 50
// 0 <= nums[i] <= 50
// 1 <= k <= nums.length





// Solution: 



class Solution {
    public int largestInteger(int[] nums, int k) {
        final int n = nums.length;

        if (k == n) {
            int max = nums[0];
            for (int x : nums) {
                max = Math.max(max, x);
            }
            return max;
        }

        int[] freq = new int[51];
        int distinct = 0;

        for (int x : nums) {
            freq[x]++;
        }

        if (k == 1) {
            for (int x : nums) {
                if (freq[x] == 1) {
                    distinct = Math.max(distinct, x);
                }
            }
            return distinct == 0 ? -1 : distinct;
        }

        int x0 = nums[0];
        int x1 = nums[n - 1];

        boolean b0 = freq[x0] > 1;
        boolean b1 = freq[x1] > 1;

        int caseb = (b0 ? 2 : 0) + (b1 ? 1 : 0);

        switch (caseb) {
            case 0:
                return Math.max(x0, x1);
            case 1:
                return x0;
            case 2:
                return x1;
            case 3:
                return -1;
            default:
                return -1;
        }
    }
}