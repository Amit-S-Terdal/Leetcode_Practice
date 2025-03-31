// 2818. Apply Operations to Maximize Score

// You are given an array nums of n positive integers and an integer k.

// Initially, you start with a score of 1. You have to maximize your score by applying the following operation at most k times:

// Choose any non-empty subarray nums[l, ..., r] that you haven't chosen previously.
// Choose an element x of nums[l, ..., r] with the highest prime score. If multiple such elements exist, choose the one with the smallest index.
// Multiply your score by x.
// Here, nums[l, ..., r] denotes the subarray of nums starting at index l and ending at the index r, both ends being inclusive.

// The prime score of an integer x is equal to the number of distinct prime factors of x. For example, the prime score of 300 is 3 since 300 = 2 * 2 * 3 * 5 * 5.

// Return the maximum possible score after applying at most k operations.

// Since the answer may be large, return it modulo 109 + 7.

 

// Example 1:

// Input: nums = [8,3,9,3,8], k = 2
// Output: 81
// Explanation: To get a score of 81, we can apply the following operations:
// - Choose subarray nums[2, ..., 2]. nums[2] is the only element in this subarray. Hence, we multiply the score by nums[2]. The score becomes 1 * 9 = 9.
// - Choose subarray nums[2, ..., 3]. Both nums[2] and nums[3] have a prime score of 1, but nums[2] has the smaller index. Hence, we multiply the score by nums[2]. The score becomes 9 * 9 = 81.
// It can be proven that 81 is the highest score one can obtain.
// Example 2:

// Input: nums = [19,12,14,6,10,18], k = 3
// Output: 4788
// Explanation: To get a score of 4788, we can apply the following operations: 
// - Choose subarray nums[0, ..., 0]. nums[0] is the only element in this subarray. Hence, we multiply the score by nums[0]. The score becomes 1 * 19 = 19.
// - Choose subarray nums[5, ..., 5]. nums[5] is the only element in this subarray. Hence, we multiply the score by nums[5]. The score becomes 19 * 18 = 342.
// - Choose subarray nums[2, ..., 3]. Both nums[2] and nums[3] have a prime score of 2, but nums[2] has the smaller index. Hence, we multipy the score by nums[2]. The score becomes 342 * 14 = 4788.
// It can be proven that 4788 is the highest score one can obtain.
 

// Constraints:

// 1 <= nums.length == n <= 10^5
// 1 <= nums[i] <= 10^5
// 1 <= k <= min(n * (n + 1) / 2, 10^9)

// Solution:


import java.util.*;

class Solution {
    static final int MOD = 1_000_000_007;

    public int maximumScore(List<Integer> nums, int k) {
        int n = nums.size();
        int[] primeScores = new int[n];

        // Step 1: Compute prime score for each element
        for (int i = 0; i < n; i++) {
            primeScores[i] = getPrimeScore(nums.get(i));
        }

        // Step 2: Monotonic stack to find previous and next dominant indexes
        int[] nextDominant = new int[n];
        int[] prevDominant = new int[n];
        Arrays.fill(nextDominant, n);
        Arrays.fill(prevDominant, -1);

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && primeScores[stack.peek()] < primeScores[i]) {
                nextDominant[stack.pop()] = i;
            }
            if (!stack.isEmpty()) {
                prevDominant[i] = stack.peek();
            }
            stack.push(i);
        }

        // Step 3: Count subarrays where each element is dominant
        long[] subarrays = new long[n];
        for (int i = 0; i < n; i++) {
            long left = i - prevDominant[i];
            long right = nextDominant[i] - i;
            subarrays[i] = left * right;
        }

        // Step 4: Max heap (priority queue) to process nums in descending order
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0
