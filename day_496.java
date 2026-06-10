// 3691. Maximum Total Subarray Value II

// You are given an integer array nums of length n and an integer k.

// You must select exactly k distinct non-empty subarrays nums[l..r] of nums. Subarrays may overlap, but the exact same subarray (same l and r) cannot be chosen more than once.

// The value of a subarray nums[l..r] is defined as: max(nums[l..r]) - min(nums[l..r]).

// The total value is the sum of the values of all chosen subarrays.

// Return the maximum possible total value you can achieve.

 

// Example 1:

// Input: nums = [1,3,2], k = 2

// Output: 4

// Explanation:

// One optimal approach is:

// Choose nums[0..1] = [1, 3]. The maximum is 3 and the minimum is 1, giving a value of 3 - 1 = 2.
// Choose nums[0..2] = [1, 3, 2]. The maximum is still 3 and the minimum is still 1, so the value is also 3 - 1 = 2.
// Adding these gives 2 + 2 = 4.

// Example 2:

// Input: nums = [4,2,5,1], k = 3

// Output: 12

// Explanation:

// One optimal approach is:

// Choose nums[0..3] = [4, 2, 5, 1]. The maximum is 5 and the minimum is 1, giving a value of 5 - 1 = 4.
// Choose nums[1..3] = [2, 5, 1]. The maximum is 5 and the minimum is 1, so the value is also 4.
// Choose nums[2..3] = [5, 1]. The maximum is 5 and the minimum is 1, so the value is again 4.
// Adding these gives 4 + 4 + 4 = 12.

 

// Constraints:

// 1 <= n == nums.length <= 5 * 10^4
// 0 <= nums[i] <= 10^9
// 1 <= k <= min(10^5, n * (n + 1) / 2)


// Solution: 


class Solution {
    public long maxTotalValue(int[] nums, int k) {
        int n = nums.length;
        SparseTable LUT = new SparseTable(nums);

        PriorityQueue<int[]> pq = new PriorityQueue<>((num, b) -> b[0] - num[0]);
        for (int i = 0; i < n; i++)
            pq.add(new int[] { LUT.query(i, n), i, n });

        long res = 0;
        
        while (pq.peek()[0] > 0) {
            int[] top = pq.poll();
            res += top[0];
            top[2]--;
            top[0] = LUT.query(top[1], top[2]);
            pq.add(top);
            if (--k <= 0) break;
        }

        return res;
    }
}

class SparseTable {
    private final int[][] Min, Max;

    public SparseTable(int[] num) {
        int n = num.length;
        int bitWidth = 32 - Integer.numberOfLeadingZeros(n);
        Min = new int[bitWidth][n];
        Max = new int[bitWidth][n];

        for (int i = 0; i < n; i++)
            Min[0][i] = Max[0][i] = num[i];

        for (int i = 1; i < bitWidth; i++)
            for (int j = 0; j + (1 << i) <= n; j++) {
                Min[i][j] = Math.min(Min[i - 1][j], Min[i - 1][j + (1 << (i - 1))]);
                Max[i][j] = Math.max(Max[i - 1][j], Max[i - 1][j + (1 << (i - 1))]);
            }
    }

    public int query(int left, int right) {
        int k = 31 - Integer.numberOfLeadingZeros(right - left);
        return Math.max(Max[k][left], Max[k][right - (1 << k)]) -
               Math.min(Min[k][left], Min[k][right - (1 << k)]);
    }
}