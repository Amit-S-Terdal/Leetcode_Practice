// 3020. Find the Maximum Number of Elements in Subset

// You are given an array of positive integers nums.

// You need to select a subset of nums which satisfies the following condition:

// You can place the selected elements in a 0-indexed array such that it follows the pattern: [x, x2, x4, ..., xk/2, xk, xk/2, ..., x4, x2, x] (Note that k can be be any non-negative power of 2). For example, [2, 4, 16, 4, 2] and [3, 9, 3] follow the pattern while [2, 4, 8, 4, 2] does not.
// Return the maximum number of elements in a subset that satisfies these conditions.

 

// Example 1:

// Input: nums = [5,4,1,2,2]
// Output: 3
// Explanation: We can select the subset {4,2,2}, which can be placed in the array as [2,4,2] which follows the pattern and 22 == 4. Hence the answer is 3.
// Example 2:

// Input: nums = [1,3,2,4]
// Output: 1
// Explanation: We can select the subset {1}, which can be placed in the array as [1] which follows the pattern. Hence the answer is 1. Note that we could have also selected the subsets {2}, {3}, or {4}, there may be multiple subsets which provide the same answer. 
 

// Constraints:

// 2 <= nums.length <= 10^5
// 1 <= nums[i] <= 10^9


// Solution: 

import java.util.*;

class Solution {
    static int[] K = new int[4];
    static int[] freq = new int[31623];
    static boolean[] seenSq = new boolean[31623];

    static void findK() {
        if (K[0] != 0) return;
        K[0] = (int) Math.sqrt(1e9);
        for (int i = 1; i < 4; i++) {
            K[i] = (int) Math.sqrt(K[i - 1]);
        }
    }

    static void reset(int[] nums) {
        for (int x : nums) {
            if (x <= K[0]) {
                freq[x] = 0;
            }
        }
        Arrays.fill(seenSq, false);
    }

    public int maximumLength(int[] nums) {
        findK();

        int xMax = 0;
        int n = nums.length;

        for (int x : nums) {
            if (x > K[0]) {
                int rx = (int) Math.sqrt(x);
                if (rx * rx == x) {
                    seenSq[rx] = true;
                }
            } else {
                freq[x]++;
                xMax = Math.max(xMax, x);
            }
        }

        if (xMax == 0) {
            reset(nums);
            return 1;
        }

        int ans = 1;

        if (freq[1] > 0) {
            int f1 = freq[1];
            ans = Math.max(ans, f1 - ((f1 & 1) == 0 ? 1 : 0));
        }

        if (ans >= 9) {
            reset(nums);
            return ans;
        }

        int k = 3;

        for (int x = 2; x <= xMax; x++) {
            if (freq[x] == 0) continue;

            while (x > K[k]) {
                k--;
            }

            int cnt = 0;
            long y = x;
            boolean flag = false;

            while (y <= K[0] && freq[(int) y] >= 2) {
                cnt += 2;
                long y2 = y * y;

                if (y2 > K[0]) {
                    flag = true;
                    cnt += (seenSq[(int) y] ? 2 : 0) - 1;
                    break;
                }

                y = y2;
            }

            if (!flag) {
                boolean isIn = (y <= K[0]) && (freq[(int) y] >= 1);
                cnt += (isIn ? 2 : 0) - 1;
            }

            ans = Math.max(ans, cnt);

            if (ans == 2 * (k + 1) + 1) {
                reset(nums);
                return ans;
            }
        }

        reset(nums);
        return ans;
    }
}