// 3362. Zero Array Transformation III

// You are given an integer array nums of length n and a 2D array queries where queries[i] = [li, ri].

// Each queries[i] represents the following action on nums:

// Decrement the value at each index in the range [li, ri] in nums by at most 1.
// The amount by which the value is decremented can be chosen independently for each index.
// A Zero Array is an array with all its elements equal to 0.

// Return the maximum number of elements that can be removed from queries, such that nums can still be converted to a zero array using the remaining queries. If it is not possible to convert nums to a zero array, return -1.

 

// Example 1:

// Input: nums = [2,0,2], queries = [[0,2],[0,2],[1,1]]

// Output: 1

// Explanation:

// After removing queries[2], nums can still be converted to a zero array.

// Using queries[0], decrement nums[0] and nums[2] by 1 and nums[1] by 0.
// Using queries[1], decrement nums[0] and nums[2] by 1 and nums[1] by 0.
// Example 2:

// Input: nums = [1,1,1,1], queries = [[1,3],[0,2],[1,3],[1,2]]

// Output: 2

// Explanation:

// We can remove queries[2] and queries[3].

// Example 3:

// Input: nums = [1,2,3,4], queries = [[0,3]]

// Output: -1

// Explanation:

// nums cannot be converted to a zero array even after using all the queries.

 

// Constraints:

// 1 <= nums.length <= 10^5
// 0 <= nums[i] <= 10^5
// 1 <= queries.length <= 10^5
// queries[i].length == 2
// 0 <= li <= ri < nums.length

// Solution:

import java.util.*;

class Solution {
    public int maxRemoval(int[] nums, int[][] queries) {
        // Sort queries by their starting index
        Arrays.sort(queries, Comparator.comparingInt(a -> a[0]));

        int n = nums.length;
        int[] deltaArray = new int[n + 1]; // Difference array for operation tracking
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder()); // Max-heap

        int operations = 0;
        int j = 0;

        for (int i = 0; i < n; i++) {
            operations += deltaArray[i]; // Apply operations expired at previous steps

            // Add all queries that start at position i to the heap
            while (j < queries.length && queries[j][0] == i) {
                heap.offer(queries[j][1]);
                j++;
            }

            // While we still need to decrement nums[i], use queries from heap
            while (operations < nums[i] && !heap.isEmpty() && heap.peek() >= i) {
                int end = heap.poll();
                operations++;
                if (end + 1 < deltaArray.length) {
                    deltaArray[end + 1] -= 1; // Schedule removal of operation effect
                }
            }

            // If we still can't cover nums[i] with enough operations, impossible
            if (operations < nums[i]) {
                return -1;
            }
        }

        // Remaining queries in the heap were unused → can be removed
        return heap.size();
    }
}
