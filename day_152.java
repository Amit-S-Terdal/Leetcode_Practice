// 2099. Find Subsequence of Length K With the Largest Sum

// You are given an integer array nums and an integer k. You want to find a subsequence of nums of length k that has the largest sum.

// Return any such subsequence as an integer array of length k.

// A subsequence is an array that can be derived from another array by deleting some or no elements without changing the order of the remaining elements.

 

// Example 1:

// Input: nums = [2,1,3,3], k = 2
// Output: [3,3]
// Explanation:
// The subsequence has the largest sum of 3 + 3 = 6.
// Example 2:

// Input: nums = [-1,-2,3,4], k = 3
// Output: [-1,3,4]
// Explanation: 
// The subsequence has the largest sum of -1 + 3 + 4 = 6.
// Example 3:

// Input: nums = [3,4,3,3], k = 2
// Output: [3,4]
// Explanation:
// The subsequence has the largest sum of 3 + 4 = 7. 
// Another possible subsequence is [4, 3].
 

// Constraints:

// 1 <= nums.length <= 1000
// -105 <= nums[i] <= 10^5
// 1 <= k <= nums.length


// Solution:

import java.util.*;

class Solution {
    public int[] maxSubsequence(int[] nums, int k) {
        int n = nums.length;

        // Pair each element with its index
        int[][] nidx = new int[n][2];
        for (int i = 0; i < n; i++) {
            nidx[i][0] = nums[i];
            nidx[i][1] = i;
        }

        // Sort by value in descending order to get top k
        Arrays.sort(nidx, (a, b) -> Integer.compare(b[0], a[0]));

        // Take top k elements
        int[][] topK = Arrays.copyOf(nidx, k);

        // Sort top k elements by their original indices
        Arrays.sort(topK, Comparator.comparingInt(a -> a[1]));

        // Extract values to form the answer
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = topK[i][0];
        }

        return result;
    }
}
