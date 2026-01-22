// 3507. Minimum Pair Removal to Sort Array I

// Given an array nums, you can perform the following operation any number of times:

// Select the adjacent pair with the minimum sum in nums. If multiple such pairs exist, choose the leftmost one.
// Replace the pair with their sum.
// Return the minimum number of operations needed to make the array non-decreasing.

// An array is said to be non-decreasing if each element is greater than or equal to its previous element (if it exists).

 

// Example 1:

// Input: nums = [5,2,3,1]

// Output: 2

// Explanation:

// The pair (3,1) has the minimum sum of 4. After replacement, nums = [5,2,4].
// The pair (2,4) has the minimum sum of 6. After replacement, nums = [5,6].
// The array nums became non-decreasing in two operations.

// Example 2:

// Input: nums = [1,2,2]

// Output: 0

// Explanation:

// The array nums is already sorted.

 

// Constraints:

// 1 <= nums.length <= 50
// -1000 <= nums[i] <= 1000



// Solution:



class Solution {

    // check if nums is non-decreasing ignoring Integer.MAX_VALUE
    private static boolean isNonDec(int[] nums) {
        int prev = Integer.MIN_VALUE;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            while (i < n && nums[i] == Integer.MAX_VALUE) i++;
            if (i == n) break;

            if (nums[i] < prev) return false;
            prev = nums[i];
        }
        return true;
    }

    public int minimumPairRemoval(int[] nums) {
        int n = nums.length;
        int op = 0;

        while (!isNonDec(nums)) {
            int minPair = Integer.MAX_VALUE;
            int s = -1, t = -1;

            // Find pair with minimum sum where both values are not Integer.MAX_VALUE
            for (int i = 0; i < n - 1; i++) {
                if (nums[i] == Integer.MAX_VALUE) continue;

                // Find next valid j > i
                int j = i + 1;
                while (j < n && nums[j] == Integer.MAX_VALUE) j++;

                if (j < n && nums[j] != Integer.MAX_VALUE) {
                    int pairSum = nums[i] + nums[j];
                    if (pairSum < minPair) {
                        minPair = pairSum;
                        s = i;
                        t = j;
                    }
                }
            }

            // Merge t into s, mark t as removed
            if (s != -1 && t != -1) {
                nums[s] += nums[t];
                nums[t] = Integer.MAX_VALUE;
                op++;
            } else {
                break; // no valid pair found
            }
        }

        return op;
    }
}
