// 3397. Maximum Number of Distinct Elements After Operations

// You are given an integer array nums and an integer k.

// You are allowed to perform the following operation on each element of the array at most once:

// Add an integer in the range [-k, k] to the element.
// Return the maximum possible number of distinct elements in nums after performing the operations.

 

// Example 1:

// Input: nums = [1,2,2,3,3,4], k = 2

// Output: 6

// Explanation:

// nums changes to [-1, 0, 1, 2, 3, 4] after performing operations on the first four elements.

// Example 2:

// Input: nums = [4,4,4,4], k = 1

// Output: 3

// Explanation:

// By adding -1 to nums[0] and 1 to nums[1], nums changes to [3, 5, 4, 4].

 

// Constraints:

// 1 <= nums.length <= 10^5
// 1 <= nums[i] <= 10^9
// 0 <= k <= 10^9



// Solution: 

import java.util.Arrays;

class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        radixSort(nums);
        int cnt = 0;
        int prev = -k;

        for (int x : nums) {
            int xm = x - k;
            int xx = x + k;
            int choice = Math.min(Math.max(xm, prev + 1), xx);
            if (choice > prev) {
                cnt++;
                prev = choice;
            }
        }
        return cnt;
    }

    // Custom radix sort for non-negative integers
    private void radixSort(int[] nums) {
        final int N = 256, MASK = 255, BSHIFT = 8;
        int n = nums.length;
        int[] output = new int[n];
        int[] freq = new int[N];

        int M = 0;
        for (int x : nums) M = Math.max(M, x);
        int bits = 32 - Integer.numberOfLeadingZeros(M);
        int Mround = Math.max(1, (bits + BSHIFT - 1) / BSHIFT);

        int[] in = nums;
        int[] out = output;
        boolean swapped = false;

        for (int round = 0; round < Mround; round++) {
            int shift = round * BSHIFT;
            Arrays.fill(freq, 0);

            for (int x : in)
                freq[(x >> shift) & MASK]++;

            // Prefix sum (partial_sum)
            for (int i = 1; i < N; i++)
                freq[i] += freq[i - 1];

            for (int i = n - 1; i >= 0; i--) {
                int val = in[i];
                int x = (val >> shift) & MASK;
                out[--freq[x]] = val;
            }

            // Swap input/output arrays
            int[] temp = in;
            in = out;
            out = temp;
            swapped = !swapped;
        }

        if (swapped) {
            // Copy back to original array if needed
            System.arraycopy(in, 0, nums, 0, n);
        }
    }
}

