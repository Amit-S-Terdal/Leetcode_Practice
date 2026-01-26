// 1200. Minimum Absolute Difference

// Given an array of distinct integers arr, find all pairs of elements with the minimum absolute difference of any two elements.

// Return a list of pairs in ascending order(with respect to pairs), each pair [a, b] follows

// a, b are from arr
// a < b
// b - a equals to the minimum absolute difference of any two elements in arr
 

// Example 1:

// Input: arr = [4,2,1,3]
// Output: [[1,2],[2,3],[3,4]]
// Explanation: The minimum absolute difference is 1. List all pairs with difference equal to 1 in ascending order.
// Example 2:

// Input: arr = [1,3,6,10,15]
// Output: [[1,3]]
// Example 3:

// Input: arr = [3,8,-10,23,19,-4,-14,27]
// Output: [[-14,-10],[19,23],[23,27]]
 

// Constraints:

// 2 <= arr.length <= 10^5
// -10^6 <= arr[i] <= 10^6



// Solution: 



import java.util.*;

class Solution {

    static final int N = 2048;
    static final int MASK = 2047;
    static final int BSHIFT = 11;
    static final int[] freq = new int[N];

    static void radixSort(int[] nums) {
        int n = nums.length;
        int[] buf = new int[n];

        // find bias (min element)
        int bias = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] < bias) bias = nums[i];
        }

        // normalize
        for (int i = 0; i < n; i++) nums[i] -= bias;

        int[] in = nums;
        int[] out = buf;

        // -------- 1st round (lower 11 bits) --------
        Arrays.fill(freq, 0);

        for (int i = 0; i < n; i++) {
            freq[in[i] & MASK]++;
        }

        for (int i = 1; i < N; i++) {
            freq[i] += freq[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int v = in[i];
            out[--freq[v & MASK]] = v;
        }

        // -------- 2nd round (higher 11 bits) --------
        Arrays.fill(freq, 0);

        for (int i = 0; i < n; i++) {
            freq[(out[i] >> BSHIFT) & MASK]++;
        }

        for (int i = 1; i < N; i++) {
            freq[i] += freq[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int v = out[i];
            in[--freq[(v >> BSHIFT) & MASK]] = v;
        }

        // restore
        for (int i = 0; i < n; i++) nums[i] += bias;
    }

    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        int n = arr.length;

        radixSort(arr);

        int minD = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            minD = Math.min(minD, arr[i] - arr[i - 1]);
        }

        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (arr[i] - arr[i - 1] == minD) {
                ans.add(Arrays.asList(arr[i - 1], arr[i]));
            }
        }
        return ans;
    }
}
