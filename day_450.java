// 3464. Maximize the Distance Between Points on a Square

// You are given an integer side, representing the edge length of a square with corners at (0, 0), (0, side), (side, 0), and (side, side) on a Cartesian plane.

// You are also given a positive integer k and a 2D integer array points, where points[i] = [xi, yi] represents the coordinate of a point lying on the boundary of the square.

// You need to select k elements among points such that the minimum Manhattan distance between any two points is maximized.

// Return the maximum possible minimum Manhattan distance between the selected k points.

// The Manhattan Distance between two cells (xi, yi) and (xj, yj) is |xi - xj| + |yi - yj|.

 

// Example 1:

// Input: side = 2, points = [[0,2],[2,0],[2,2],[0,0]], k = 4

// Output: 2

// Explanation:



// Select all four points.

// Example 2:

// Input: side = 2, points = [[0,0],[1,2],[2,0],[2,2],[2,1]], k = 4

// Output: 1

// Explanation:



// Select the points (0, 0), (2, 0), (2, 2), and (2, 1).

// Example 3:

// Input: side = 2, points = [[0,0],[0,1],[0,2],[1,2],[2,0],[2,2],[2,1]], k = 5

// Output: 1

// Explanation:



// Select the points (0, 0), (0, 1), (0, 2), (1, 2), and (2, 2).

 

// Constraints:

// 1 <= side <= 10^9
// 4 <= points.length <= min(4 * side, 15 * 10^3)
// points[i] == [xi, yi]
// The input is generated such that:
// points[i] lies on the boundary of the square.
// All points[i] are unique.
// 4 <= k <= min(25, points.length)


// Solution:


import java.util.*;

class Solution {
    public int maxDistance(int side, int[][] points, int k) {
        List<Long> res = new ArrayList<>();

        for (int[] p : points) {
            int x = p[0], y = p[1];
            if (x == 0) {
                res.add((long) y);
            } else if (y == side) {
                res.add((long) side + x);
            } else if (x == side) {
                res.add((long) side * 3 - y);
            } else {
                res.add((long) side * 4 - x);
            }
        }

        Collections.sort(res);

        int left = 1;
        int right = (int)((long) side * 4 / k) + 1;

        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (check(res, side, k, mid)) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return left;
    }

    private boolean check(List<Long> res, int side, int k, int n) {
        int m = res.size();
        int[] idx = new int[k];

        long curr = res.get(0);
        idx[0] = 0;

        for (int i = 1; i < k; i++) {
            int pos = lowerBound(res, curr + n);
            if (pos == m) return false;
            idx[i] = pos;
            curr = res.get(pos);
        }

        if (res.get(idx[k - 1]) - res.get(0) <= (long) side * 4 - n) {
            return true;
        }

        for (idx[0] = 1; idx[0] < idx[1]; idx[0]++) {
            for (int j = 1; j < k; j++) {
                while (idx[j] < m && res.get(idx[j]) < res.get(idx[j - 1]) + n) {
                    idx[j]++;
                }
                if (idx[j] == m) return false;
            }

            if (res.get(idx[k - 1]) - res.get(idx[0]) <= (long) side * 4 - n) {
                return true;
            }
        }

        return false;
    }

    private int lowerBound(List<Long> res, long target) {
        int left = 0, right = res.size();
        while (left < right) {
            int mid = (left + right) / 2;
            if (res.get(mid) >= target) right = mid;
            else left = mid + 1;
        }
        return left;
    }
}