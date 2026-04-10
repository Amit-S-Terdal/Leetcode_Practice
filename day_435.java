// 3740. Minimum Distance Between Three Equal Elements I

// You are given an integer array nums.

// A tuple (i, j, k) of 3 distinct indices is good if nums[i] == nums[j] == nums[k].

// The distance of a good tuple is abs(i - j) + abs(j - k) + abs(k - i), where abs(x) denotes the absolute value of x.

// Return an integer denoting the minimum possible distance of a good tuple. If no good tuples exist, return -1.

 

// Example 1:

// Input: nums = [1,2,1,1,3]

// Output: 6

// Explanation:

// The minimum distance is achieved by the good tuple (0, 2, 3).

// (0, 2, 3) is a good tuple because nums[0] == nums[2] == nums[3] == 1. Its distance is abs(0 - 2) + abs(2 - 3) + abs(3 - 0) = 2 + 1 + 3 = 6.

// Example 2:

// Input: nums = [1,1,2,3,2,1,2]

// Output: 8

// Explanation:

// The minimum distance is achieved by the good tuple (2, 4, 6).

// (2, 4, 6) is a good tuple because nums[2] == nums[4] == nums[6] == 2. Its distance is abs(2 - 4) + abs(4 - 6) + abs(6 - 2) = 2 + 2 + 4 = 8.

// Example 3:

// Input: nums = [1]

// Output: -1

// Explanation:

// There are no good tuples. Therefore, the answer is -1.

 

// Constraints:

// 1 <= n == nums.length <= 100
// 1 <= nums[i] <= n



// Solution:



import java.util.Arrays;

class Solution {
    public int minimumDistance(int[] nums) {
        int n = nums.length;

        int M = 0;
        for (int x : nums) {
            M = Math.max(M, x);
        }

        int[][] pos = new int[M + 1][2];

        // initialize with {-1, -1}
        for (int i = 0; i <= M; i++) {
            pos[i][0] = -1;
            pos[i][1] = -1;
        }

        int ans = Integer.MAX_VALUE;

        for (int k = 0; k < n; k++) {
            int x = nums[k];

            if (pos[x][1] != -1) {
                ans = Math.min(ans, (k - pos[x][1]) * 2);
            }

            // equivalent of: pos[x][1] = exchange(pos[x][0], k);
            int temp = pos[x][0];
            pos[x][0] = k;
            pos[x][1] = temp;
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}