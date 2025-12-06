// 3578. Count Partitions With Max-Min Difference at Most K

// You are given an integer array nums and an integer k. Your task is to partition nums into one or more non-empty contiguous segments such that in each segment, the difference between its maximum and minimum elements is at most k.

// Return the total number of ways to partition nums under this condition.

// Since the answer may be too large, return it modulo 109 + 7.

 

// Example 1:

// Input: nums = [9,4,1,3,7], k = 4

// Output: 6

// Explanation:

// There are 6 valid partitions where the difference between the maximum and minimum elements in each segment is at most k = 4:

// [[9], [4], [1], [3], [7]]
// [[9], [4], [1], [3, 7]]
// [[9], [4], [1, 3], [7]]
// [[9], [4, 1], [3], [7]]
// [[9], [4, 1], [3, 7]]
// [[9], [4, 1, 3], [7]]
// Example 2:

// Input: nums = [3,3,4], k = 0

// Output: 2

// Explanation:

// There are 2 valid partitions that satisfy the given conditions:

// [[3], [3], [4]]
// [[3, 3], [4]]
 

// Constraints:

// 2 <= nums.length <= 5 * 10^4
// 1 <= nums[i] <= 10^9
// 0 <= k <= 10^9



// Solution: 



class Solution {
    public int countPartitions(int[] nums, int k) {
        final int N = 50000;
        final int MOD = 1000000007;
        
        int[] qMax = new int[N];
        int[] qMin = new int[N];
        int frontX = 0, backX = -1, frontN = 0, backN = -1;
        
        int n = nums.length;
        long cnt = 0;
        
        // Create a sum array to store the prefix sums of the valid partitions
        int[] sum = new int[n + 2];
        sum[1] = 1;  // Base case for sum
        
        for (int l = 0, r = 0; r < n; r++) {
            int x = nums[r];

            // max queue
            while (backX >= frontX && qMax[backX] < x) {
                backX--;
            }
            qMax[++backX] = x;

            // min queue
            while (backN >= frontN && qMin[backN] > x) {
                backN--;
            }
            qMin[++backN] = x;

            // shrink window
            while (qMax[frontX] - qMin[frontN] > k) {
                int y = nums[l];
                if (qMax[frontX] == y) frontX++;
                if (qMin[frontN] == y) frontN++;
                l++;
            }

            cnt = (MOD + sum[r + 1] - sum[l]) % MOD;
            sum[r + 2] = (sum[r + 1] + (int) cnt) % MOD;
        }
        
        return (int) cnt;
    }
}
