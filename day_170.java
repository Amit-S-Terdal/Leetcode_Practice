// 3201. Find the Maximum Length of Valid Subsequence I

// You are given an integer array nums.
// A subsequence sub of nums with length x is called valid if it satisfies:

// (sub[0] + sub[1]) % 2 == (sub[1] + sub[2]) % 2 == ... == (sub[x - 2] + sub[x - 1]) % 2.
// Return the length of the longest valid subsequence of nums.

// A subsequence is an array that can be derived from another array by deleting some or no elements without changing the order of the remaining elements.

 

// Example 1:

// Input: nums = [1,2,3,4]

// Output: 4

// Explanation:

// The longest valid subsequence is [1, 2, 3, 4].

// Example 2:

// Input: nums = [1,2,1,1,2,1,2]

// Output: 6

// Explanation:

// The longest valid subsequence is [1, 2, 1, 2, 1, 2].

// Example 3:

// Input: nums = [1,3]

// Output: 2

// Explanation:

// The longest valid subsequence is [1, 3].

 

// Constraints:

// 2 <= nums.length <= 2 * 10^5
// 1 <= nums[i] <= 10^7


// Solution: 

class Solution {
    public int maximumLength(int[] nums) {
        int n = nums.length;
        if (n == 2) return 2;

        boolean z = (nums[0] & 1) == 1;
        int[] len = new int[3];
        len[0] = z ? 0 : 1; // even
        len[1] = z ? 1 : 0; // odd
        len[2] = 1;         // alternating

        for (int i = 1; i < n; i++) {
            boolean x = (nums[i] & 1) == 1;
            if (x) len[1]++;
            else len[0]++;
            if (x != z) {
                len[2]++;
                z = !z;
            }
        }

        return Math.max(len[0], Math.max(len[1], len[2]));
    }
}
