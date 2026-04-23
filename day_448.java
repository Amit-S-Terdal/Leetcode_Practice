// 2615. Sum of Distances

// You are given a 0-indexed integer array nums. There exists an array arr of length nums.length, where arr[i] is the sum of |i - j| over all j such that nums[j] == nums[i] and j != i. If there is no such j, set arr[i] to be 0.

// Return the array arr.

 

// Example 1:

// Input: nums = [1,3,1,1,2]
// Output: [5,0,3,4,0]
// Explanation: 
// When i = 0, nums[0] == nums[2] and nums[0] == nums[3]. Therefore, arr[0] = |0 - 2| + |0 - 3| = 5. 
// When i = 1, arr[1] = 0 because there is no other index with value 3.
// When i = 2, nums[2] == nums[0] and nums[2] == nums[3]. Therefore, arr[2] = |2 - 0| + |2 - 3| = 3. 
// When i = 3, nums[3] == nums[0] and nums[3] == nums[2]. Therefore, arr[3] = |3 - 0| + |3 - 2| = 4. 
// When i = 4, arr[4] = 0 because there is no other index with value 2. 

// Example 2:

// Input: nums = [0,5,3]
// Output: [0,0,0]
// Explanation: Since each element in nums is distinct, arr[i] = 0 for all i.
 

// Constraints:

// 1 <= nums.length <= 10^5
// 0 <= nums[i] <= 10^9




// Solution: 


import java.util.*;

class Solution {
    static final int N = 100001;
    static long[] nxt = new long[N];

    public long[] distance(int[] nums) {
        Arrays.fill(nxt, -1);

        Map<Integer, Integer> idx = new HashMap<>();
        int n = nums.length;

        // Build linked structure via nxt[]
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (!idx.containsKey(x)) {
                idx.put(x, i);
                nxt[i] = -1;
            } else {
                nxt[i] = idx.get(x);
                idx.put(x, i);
            }
        }

        long[] ans = new long[n];

        // Traverse each group
        for (Map.Entry<Integer, Integer> entry : idx.entrySet()) {
            int h = entry.getValue();

            if (nxt[h] == -1) continue;

            long total = 0, prefix = 0;
            int vz = 0;

            // Count total sum and size
            for (int j = h; j != -1; j = (int)nxt[j]) {
                total += j;
                vz++;
            }

            // Compute answer
            int i = vz - 1;
            for (int j = h; j != -1; j = (int)nxt[j], i--) {
                ans[j] = (2L * i - vz + 2) * j + 2L * prefix - total;
                prefix += j;
            }
        }

        return ans;
    }
}