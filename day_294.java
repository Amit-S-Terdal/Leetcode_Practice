// 757. Set Intersection Size At Least Two

// You are given a 2D integer array intervals where intervals[i] = [starti, endi] represents all the integers from starti to endi inclusively.

// A containing set is an array nums where each interval from intervals has at least two integers in nums.

// For example, if intervals = [[1,3], [3,7], [8,9]], then [1,2,4,7,8,9] and [2,3,4,8,9] are containing sets.
// Return the minimum possible size of a containing set.

 

// Example 1:

// Input: intervals = [[1,3],[3,7],[8,9]]
// Output: 5
// Explanation: let nums = [2, 3, 4, 8, 9].
// It can be shown that there cannot be any containing array of size 4.
// Example 2:

// Input: intervals = [[1,3],[1,4],[2,5],[3,5]]
// Output: 3
// Explanation: let nums = [2, 3, 4].
// It can be shown that there cannot be any containing array of size 2.
// Example 3:

// Input: intervals = [[1,2],[2,3],[2,4],[4,5]]
// Output: 5
// Explanation: let nums = [1, 2, 3, 4, 5].
// It can be shown that there cannot be any containing array of size 4.
 

// Constraints:

// 1 <= intervals.length <= 3000
// intervals[i].length == 2
// 0 <= starti < endi <= 10^8



// Solution:



import java.util.*;

class Solution {
    public int intersectionSizeTwo(int[][] intervals) {
        // Sort by increasing end; if ends equal, by decreasing start
        Arrays.sort(intervals, (X, Y) -> {
            if (X[1] == Y[1]) return Y[0] - X[0];
            return X[1] - Y[1];
        });

        int n = intervals.length;
        int cnt = 2;

        int b = intervals[0][1];
        int a = b - 1;

        for (int i = 1; i < n; i++) {
            int L = intervals[i][0];
            int R = intervals[i][1];

            if (a >= L) {
                // Already have 2 points within interval
                continue;
            }

            boolean NO = (L > b);  // Means need 2 new points
            cnt += 1 + (NO ? 1 : 0);

            if (NO) {
                // Need to add {R-1, R}
                a = R - 1;
            } else {
                // Add just one point: b (old), and R (new)
                a = b;
            }

            b = R;
        }

        return cnt;
    }
}
