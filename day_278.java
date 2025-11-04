// 3318. Find X-Sum of All K-Long Subarrays I

// You are given an array nums of n integers and two integers k and x.

// The x-sum of an array is calculated by the following procedure:

// Count the occurrences of all elements in the array.
// Keep only the occurrences of the top x most frequent elements. If two elements have the same number of occurrences, the element with the bigger value is considered more frequent.
// Calculate the sum of the resulting array.
// Note that if an array has less than x distinct elements, its x-sum is the sum of the array.

// Return an integer array answer of length n - k + 1 where answer[i] is the x-sum of the subarray nums[i..i + k - 1].

 

// Example 1:

// Input: nums = [1,1,2,2,3,4,2,3], k = 6, x = 2

// Output: [6,10,12]

// Explanation:

// For subarray [1, 1, 2, 2, 3, 4], only elements 1 and 2 will be kept in the resulting array. Hence, answer[0] = 1 + 1 + 2 + 2.
// For subarray [1, 2, 2, 3, 4, 2], only elements 2 and 4 will be kept in the resulting array. Hence, answer[1] = 2 + 2 + 2 + 4. Note that 4 is kept in the array since it is bigger than 3 and 1 which occur the same number of times.
// For subarray [2, 2, 3, 4, 2, 3], only elements 2 and 3 are kept in the resulting array. Hence, answer[2] = 2 + 2 + 2 + 3 + 3.
// Example 2:

// Input: nums = [3,8,7,8,7,5], k = 2, x = 2

// Output: [11,15,15,15,12]

// Explanation:

// Since k == x, answer[i] is equal to the sum of the subarray nums[i..i + k - 1].

 

// Constraints:

// 1 <= n == nums.length <= 50
// 1 <= nums[i] <= 50
// 1 <= x <= k <= nums.length


// Solution:



import java.util.*;

class Solution {
    static class Pair {
        int freq;
        int num;
        Pair(int f, int n) {
            freq = f;
            num = n;
        }
    }

    private static int x_sum(List<Pair> freq, int k, int x) {
        // Sort by frequency descending, then by num descending (like greater<pair<int,int>> in C++)
        List<Pair> freq2 = new ArrayList<>(freq);
        freq2.sort((a, b) -> {
            if (b.freq != a.freq) return Integer.compare(b.freq, a.freq);
            return Integer.compare(b.num, a.num);
        });

        int sum = 0;
        for (int i = 0; i < x && i < freq2.size(); i++) {
            Pair p = freq2.get(i);
            if (p.freq == 0) break;
            sum += p.num * p.freq;
        }
        return sum;
    }

    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int sz = n - k + 1;
        int[] ans = new int[sz];

        // Each index corresponds to number 0..50
        List<Pair> freq = new ArrayList<>();
        for (int i = 0; i <= 50; i++) {
            freq.add(new Pair(0, i));
        }

        // Initialize frequency for first window
        for (int r = 0; r < k; r++) {
            int z = nums[r];
            freq.get(z).freq++;
            freq.get(z).num = z;
        }

        ans[0] = x_sum(freq, k, x);

        // Sliding window
        for (int l = 1, r = k; l < sz; l++, r++) {
            int L = nums[l - 1];
            int R = nums[r];
            freq.get(L).freq--;
            freq.get(R).freq++;
            freq.get(R).num = R;

            ans[l] = x_sum(freq, k, x);
        }

        return ans;
    }
}
