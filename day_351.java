// 2975. Maximum Square Area by Removing Fences From a Field

// There is a large (m - 1) x (n - 1) rectangular field with corners at (1, 1) and (m, n) containing some horizontal and vertical fences given in arrays hFences and vFences respectively.

// Horizontal fences are from the coordinates (hFences[i], 1) to (hFences[i], n) and vertical fences are from the coordinates (1, vFences[i]) to (m, vFences[i]).

// Return the maximum area of a square field that can be formed by removing some fences (possibly none) or -1 if it is impossible to make a square field.

// Since the answer may be large, return it modulo 109 + 7.

// Note: The field is surrounded by two horizontal fences from the coordinates (1, 1) to (1, n) and (m, 1) to (m, n) and two vertical fences from the coordinates (1, 1) to (m, 1) and (1, n) to (m, n). These fences cannot be removed.

 

// Example 1:



// Input: m = 4, n = 3, hFences = [2,3], vFences = [2]
// Output: 4
// Explanation: Removing the horizontal fence at 2 and the vertical fence at 2 will give a square field of area 4.
// Example 2:



// Input: m = 6, n = 7, hFences = [2], vFences = [4]
// Output: -1
// Explanation: It can be proved that there is no way to create a square field by removing fences.
 

// Constraints:

// 3 <= m, n <= 10^9
// 1 <= hFences.length, vFences.length <= 600
// 1 < hFences[i] < m
// 1 < vFences[i] < n
// hFences and vFences are unique.


// Solution:


import java.util.*;

class Solution {
    static final int MOD = 1_000_000_007;

    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        // Always compute pairwise distances on the smaller array
        if (hFences.length > vFences.length) {
            return maximizeSquareArea(n, m, vFences, hFences);
        }

        int[] h = new int[hFences.length + 2];
        int[] v = new int[vFences.length + 2];

        System.arraycopy(hFences, 0, h, 0, hFences.length);
        System.arraycopy(vFences, 0, v, 0, vFences.length);

        h[hFences.length] = 1;
        h[hFences.length + 1] = m;
        v[vFences.length] = 1;
        v[vFences.length + 1] = n;

        Arrays.sort(h);
        Arrays.sort(v);

        // Store all horizontal distances
        HashSet<Integer> seen = new HashSet<>();
        for (int i = 0; i < h.length; i++) {
            for (int j = i + 1; j < h.length; j++) {
                seen.add(h[j] - h[i]);
            }
        }

        int maxSide = 0;

        // Check vertical distances
        for (int i = 0; i < v.length; i++) {
            for (int j = i + 1; j < v.length; j++) {
                int len = v[j] - v[i];
                if (seen.contains(len)) {
                    maxSide = Math.max(maxSide, len);
                }
            }
        }

        if (maxSide == 0) return -1;
        return (int) ((long) maxSide * maxSide % MOD);
    }
}
