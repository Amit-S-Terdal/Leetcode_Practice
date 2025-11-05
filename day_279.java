// 3321. Find X-Sum of All K-Long Subarrays II

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

// nums.length == n
// 1 <= n <= 10^5
// 1 <= nums[i] <= 10^9
// 1 <= x <= k <= nums.length



// Solution:


import java.util.*;

class Helper {

    private int x;
    private long result;
    private TreeSet<Pair> top;   // corresponds to "top" in C++
    private TreeSet<Pair> rest;  // corresponds to "rest" in C++
    private Map<Integer, Integer> freq;

    // Pair: (frequency, value)
    private static class Pair {
        int freq;
        int val;

        Pair(int freq, int val) {
            this.freq = freq;
            this.val = val;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair p = (Pair) o;
            return freq == p.freq && val == p.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(freq, val);
        }
    }

    public Helper(int x) {
        this.x = x;
        this.result = 0L;
        this.freq = new HashMap<>();

        // "top" uses descending order by (freq, val)
        this.top = new TreeSet<>((a, b) -> {
            if (a.freq != b.freq) return Integer.compare(b.freq, a.freq);
            return Integer.compare(b.val, a.val);
        });

        // "rest" uses ascending order by (freq, val)
        this.rest = new TreeSet<>((a, b) -> {
            if (a.freq != b.freq) return Integer.compare(a.freq, b.freq);
            return Integer.compare(a.val, b.val);
        });
    }

    public void insert(int v) {
        int f = freq.getOrDefault(v, 0);

        if (f > 0) {
            Pair old = new Pair(f, v);
            if (top.contains(old)) {
                top.remove(old);
                result -= 1L * f * v;
            } else {
                rest.remove(old);
            }
        }

        f++;
        freq.put(v, f);

        Pair np = new Pair(f, v);
        top.add(np);
        result += 1L * f * v;

        // If too many in top, move smallest one to rest
        if (top.size() > x) {
            Pair smallest = top.last();
            result -= 1L * smallest.freq * smallest.val;
            rest.add(smallest);
            top.remove(smallest);
        }
    }

    public void remove(int v) {
        Integer fObj = freq.get(v);
        if (fObj == null || fObj == 0) return;
        int f = fObj;

        Pair old = new Pair(f, v);
        if (top.contains(old)) {
            top.remove(old);
            result -= 1L * f * v;
        } else {
            rest.remove(old);
        }

        f--;
        if (f == 0) {
            freq.remove(v);
        } else {
            Pair np = new Pair(f, v);
            rest.add(np);
            freq.put(v, f);
        }

        // Maintain x elements in top
        if (top.size() < x && !rest.isEmpty()) {
            Pair best = rest.last(); // largest from rest
            rest.remove(best);
            top.add(best);
            result += 1L * best.freq * best.val;
        }
    }

    public long get() {
        return result;
    }
}

class Solution {

    public long[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        long[] ans = new long[n - k + 1];
        Helper helper = new Helper(x);

        // First window
        for (int i = 0; i < k; i++) helper.insert(nums[i]);
        ans[0] = helper.get();

        // Slide window
        for (int i = k; i < n; i++) {
            helper.remove(nums[i - k]);
            helper.insert(nums[i]);
            ans[i - k + 1] = helper.get();
        }

        return ans;
    }
}
