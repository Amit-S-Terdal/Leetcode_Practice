// 2561. Rearranging Fruits

// You have two fruit baskets containing n fruits each. You are given two 0-indexed integer arrays basket1 and basket2 representing the cost of fruit in each basket. You want to make both baskets equal. To do so, you can use the following operation as many times as you want:

// Chose two indices i and j, and swap the ith fruit of basket1 with the jth fruit of basket2.
// The cost of the swap is min(basket1[i],basket2[j]).
// Two baskets are considered equal if sorting them according to the fruit cost makes them exactly the same baskets.

// Return the minimum cost to make both the baskets equal or -1 if impossible.

 

// Example 1:

// Input: basket1 = [4,2,2,2], basket2 = [1,4,1,2]
// Output: 1
// Explanation: Swap index 1 of basket1 with index 0 of basket2, which has cost 1. Now basket1 = [4,1,2,2] and basket2 = [2,4,1,2]. Rearranging both the arrays makes them equal.
// Example 2:

// Input: basket1 = [2,3,4,1], basket2 = [3,2,5,1]
// Output: -1
// Explanation: It can be shown that it is impossible to make both the baskets equal.
 

// Constraints:

// basket1.length == basket2.length
// 1 <= basket1.length <= 10^5
// 1 <= basket1[i],basket2[i] <= 10^9



// Solution:


import java.util.*;

class Solution {
    public long minCost(int[] basket1, int[] basket2) {
        int n = basket1.length;
        Map<Integer, Integer> freq = new HashMap<>();
        int minX = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            freq.put(basket1[i], freq.getOrDefault(basket1[i], 0) + 1);
            freq.put(basket2[i], freq.getOrDefault(basket2[i], 0) - 1);
            minX = Math.min(minX, Math.min(basket1[i], basket2[i]));
        }

        List<int[]> B1 = new ArrayList<>();
        List<int[]> B2 = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            int x = entry.getKey(), f = entry.getValue();
            if (f % 2 != 0) return -1;
            if (f > 0) B1.add(new int[]{x, f / 2});
            else if (f < 0) B2.add(new int[]{x, -f / 2});
        }

        // Sort B1 in increasing order of element values
        B1.sort(Comparator.comparingInt(a -> a[0]));
        // Sort B2 in decreasing order of element values
        B2.sort((a, b) -> Integer.compare(b[0], a[0]));

        long cost = 0;
        int i = 0, j = 0;

        while (i < B1.size() && j < B2.size()) {
            int b1 = B1.get(i)[0], f1 = B1.get(i)[1];
            int b2 = B2.get(j)[0], f2 = B2.get(j)[1];
            int f0 = Math.min(f1, f2);

            long swapCost = Math.min(Math.min(b1, b2), 2L * minX);
            cost += swapCost * f0;

            B1.get(i)[1] -= f0;
            B2.get(j)[1] -= f0;

            if (B1.get(i)[1] == 0) i++;
            if (B2.get(j)[1] == 0) j++;
        }

        return cost;
    }
}
