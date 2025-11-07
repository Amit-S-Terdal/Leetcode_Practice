// 2528. Maximize the Minimum Powered City

// You are given a 0-indexed integer array stations of length n, where stations[i] represents the number of power stations in the ith city.

// Each power station can provide power to every city in a fixed range. In other words, if the range is denoted by r, then a power station at city i can provide power to all cities j such that |i - j| <= r and 0 <= i, j <= n - 1.

// Note that |x| denotes absolute value. For example, |7 - 5| = 2 and |3 - 10| = 7.
// The power of a city is the total number of power stations it is being provided power from.

// The government has sanctioned building k more power stations, each of which can be built in any city, and have the same range as the pre-existing ones.

// Given the two integers r and k, return the maximum possible minimum power of a city, if the additional power stations are built optimally.

// Note that you can build the k power stations in multiple cities.

 

// Example 1:

// Input: stations = [1,2,4,5,0], r = 1, k = 2
// Output: 5
// Explanation: 
// One of the optimal ways is to install both the power stations at city 1. 
// So stations will become [1,4,4,5,0].
// - City 0 is provided by 1 + 4 = 5 power stations.
// - City 1 is provided by 1 + 4 + 4 = 9 power stations.
// - City 2 is provided by 4 + 4 + 5 = 13 power stations.
// - City 3 is provided by 5 + 4 = 9 power stations.
// - City 4 is provided by 5 + 0 = 5 power stations.
// So the minimum power of a city is 5.
// Since it is not possible to obtain a larger power, we return 5.
// Example 2:

// Input: stations = [4,4,4,4], r = 0, k = 3
// Output: 4
// Explanation: 
// It can be proved that we cannot make the minimum power of a city greater than 4.
 

// Constraints:

// n == stations.length
// 1 <= n <= 10^5
// 0 <= stations[i] <= 10^5
// 0 <= r <= n - 1
// 0 <= k <= 10^9


// Solution:



import java.util.*;

class Solution {

    public long maxPower(int[] stations, int r, int k) {
        int n = stations.length;
        long left = 0, right = 0;
        for (int s : stations) right += s;
        right += k;

        long ans = 0;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (check(mid, stations, r, k, n)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    private boolean check(long minPower, int[] st, int r, long k, int n) {
        long power = 0;
        for (int i = 0; i <= Math.min(r, n - 1); i++)
            power += st[i];

        // record original values to recover
        List<int[]> recover = new ArrayList<>();
        recover.add(new int[]{n - 1, st[n - 1]});

        for (int i = 0; i < n; i++) {
            if (power < minPower) {
                long need = minPower - power;
                if (need > k) {
                    for (int[] p : recover) st[p[0]] = p[1];
                    return false;
                }
                k -= need;
                int pos = Math.min(n - 1, i + r);
                if (pos != n - 1) {
                    recover.add(new int[]{pos, st[pos]});
                    st[pos] += need;
                }
                power += need;
            }

            if (i >= r)
                power -= st[i - r];
            if (i + r + 1 < n)
                power += st[i + r + 1];
        }

        for (int[] p : recover) st[p[0]] = p[1];
        return true;
    }
}
