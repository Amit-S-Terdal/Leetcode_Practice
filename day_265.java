// 3347. Maximum Frequency of an Element After Performing Operations II

// You are given an integer array nums and two integers k and numOperations.

// You must perform an operation numOperations times on nums, where in each operation you:

// Select an index i that was not selected in any previous operations.
// Add an integer in the range [-k, k] to nums[i].
// Return the maximum possible frequency of any element in nums after performing the operations.

 

// Example 1:

// Input: nums = [1,4,5], k = 1, numOperations = 2

// Output: 2

// Explanation:

// We can achieve a maximum frequency of two by:

// Adding 0 to nums[1], after which nums becomes [1, 4, 5].
// Adding -1 to nums[2], after which nums becomes [1, 4, 4].
// Example 2:

// Input: nums = [5,11,20,20], k = 5, numOperations = 1

// Output: 2

// Explanation:

// We can achieve a maximum frequency of two by:

// Adding 0 to nums[1].
 

// Constraints:

// 1 <= nums.length <= 10^5
// 1 <= nums[i] <= 10^9
// 0 <= k <= 10^9
// 0 <= numOperations <= nums.length


// Solution: 



import java.util.*;

class Solution {
    static final int N = 256, MASK = 255, BSHIFT = 8;
    
    private void radixSort(int[] nums) {
        int n = nums.length;
        int[] output = new int[n];
        int maxVal = 0;
        for (int x : nums) maxVal = Math.max(maxVal, x);
        
        int rounds = Math.max(1, (32 - Integer.numberOfLeadingZeros(maxVal) + BSHIFT - 1) / BSHIFT);
        
        int[] in = nums;
        int[] out = output;
        boolean swapped = false;
        
        for (int round = 0; round < rounds; round++) {
            int shift = round * BSHIFT;
            int[] freq = new int[N];
            
            for (int i = 0; i < n; i++)
                freq[(in[i] >> shift) & MASK]++;
            
            for (int i = 1; i < N; i++)
                freq[i] += freq[i - 1];
            
            for (int i = n - 1; i >= 0; i--) {
                int val = in[i];
                int key = (val >> shift) & MASK;
                out[--freq[key]] = val;
            }
            
            int[] temp = in;
            in = out;
            out = temp;
            swapped = !swapped;
        }
        
        if (swapped)
            System.arraycopy(in, 0, nums, 0, n);
    }

    public int maxFrequency(int[] nums, int k, int numOperations) {
        int n = nums.length;
        radixSort(nums);
        int M = nums[n - 1];
        
        // Compress nums to (value, frequency) pairs
        List<int[]> nWf = new ArrayList<>();
        int prev = -1;
        for (int x : nums) {
            if (x != prev) {
                nWf.add(new int[]{x, 1});
                prev = x;
            } else {
                int[] last = nWf.get(nWf.size() - 1);
                last[1]++;
            }
        }
        
        n = nWf.size();
        int ans = 0, cntP = 0, cntI = 0;
        int l = 0, r = 0, l2 = 0;

        for (int i = 0; i < n; i++) {
            int x = nWf.get(i)[0];
            int f = nWf.get(i)[1];
            int L = Math.max(1, x - k);
            int R = Math.min(M, x + k);
            int L2 = Math.max(1, x - 2 * k);
            
            // Extend right window for points
            while (r < n && nWf.get(r)[0] <= R) {
                cntP += nWf.get(r)[1];
                r++;
            }

            // Shrink left window for points
            while (l < n && nWf.get(l)[0] < L) {
                cntP -= nWf.get(l)[1];
                l++;
            }

            ans = Math.max(ans, f + Math.min(cntP - f, numOperations));

            // Maintain window for intervals
            cntI += f;
            while (l2 < r && nWf.get(l2)[0] < L2) {
                cntI -= nWf.get(l2)[1];
                l2++;
            }

            ans = Math.max(ans, Math.min(cntI, numOperations));
        }

        return ans;
    }
}
