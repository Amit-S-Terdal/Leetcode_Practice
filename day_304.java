// 1590. Make Sum Divisible by P


// Given an array of positive integers nums, remove the smallest subarray (possibly empty) such that the sum of the remaining elements is divisible by p. It is not allowed to remove the whole array.

// Return the length of the smallest subarray that you need to remove, or -1 if it's impossible.

// A subarray is defined as a contiguous block of elements in the array.

 

// Example 1:

// Input: nums = [3,1,4,2], p = 6
// Output: 1
// Explanation: The sum of the elements in nums is 10, which is not divisible by 6. We can remove the subarray [4], and the sum of the remaining elements is 6, which is divisible by 6.
// Example 2:

// Input: nums = [6,3,5,2], p = 9
// Output: 2
// Explanation: We cannot remove a single element to get a sum divisible by 9. The best way is to remove the subarray [5,2], leaving us with [6,3] with sum 9.
// Example 3:

// Input: nums = [1,2,3], p = 3
// Output: 0
// Explanation: Here the sum is 6. which is already divisible by 3. Thus we do not need to remove anything.
 

// Constraints:

// 1 <= nums.length <= 10^5
// 1 <= nums[i] <= 10^9
// 1 <= p <= 10^9



// Solution: 



class Solution {
    public int minSubarray(int[] nums, int p) {
        int n = nums.length;
        long modP = 0;
        for (int num : nums) {
            modP = (modP + num) % p;
        }

        // If modP is 0, the answer is 0 because no subarray is needed.
        if (modP == 0) return 0;

        int sz = Math.min(n, p);

        // If p <= n, use the array method
        if (p <= n) return arrayMethod(n, modP, nums, p);

        // Hashmap version for the general case
        Map<Integer, Integer> pos = new HashMap<>();
        pos.put(0, -1);

        int len = n, sumP = 0;
        for (int i = 0; i < n; i++) {
            sumP = (sumP + nums[i]) % p;
            int y = (sumP - (int)modP + p) % p;  // Fix: cast modP to int
            if (pos.containsKey(y)) {
                len = Math.min(len, i - pos.get(y));
            }
            pos.put(sumP, i);
        }
        return (len == n) ? -1 : len;
    }

    private int arrayMethod(int n, long modP, int[] nums, int p) {
        int[] pos = new int[p];
        Arrays.fill(pos, Integer.MAX_VALUE);
        pos[0] = -1;
        
        int len = n, sumP = 0;
        for (int i = 0; i < n; i++) {
            sumP = (sumP + nums[i]) % p;
            int y = (sumP - (int)modP + p) % p;  // Fix: cast modP to int
            if (pos[y] != Integer.MAX_VALUE) {
                len = Math.min(len, i - pos[y]);
            }
            pos[sumP] = i;
        }
        return (len == n) ? -1 : len;
    }
}
