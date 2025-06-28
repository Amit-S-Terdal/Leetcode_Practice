// 2040. Kth Smallest Product of Two Sorted Arrays

// Given two sorted 0-indexed integer arrays nums1 and nums2 as well as an integer k, return the kth (1-based) smallest product of nums1[i] * nums2[j] where 0 <= i < nums1.length and 0 <= j < nums2.length.
 

// Example 1:

// Input: nums1 = [2,5], nums2 = [3,4], k = 2
// Output: 8
// Explanation: The 2 smallest products are:
// - nums1[0] * nums2[0] = 2 * 3 = 6
// - nums1[0] * nums2[1] = 2 * 4 = 8
// The 2nd smallest product is 8.
// Example 2:

// Input: nums1 = [-4,-2,0,3], nums2 = [2,4], k = 6
// Output: 0
// Explanation: The 6 smallest products are:
// - nums1[0] * nums2[1] = (-4) * 4 = -16
// - nums1[0] * nums2[0] = (-4) * 2 = -8
// - nums1[1] * nums2[1] = (-2) * 4 = -8
// - nums1[1] * nums2[0] = (-2) * 2 = -4
// - nums1[2] * nums2[0] = 0 * 2 = 0
// - nums1[2] * nums2[1] = 0 * 4 = 0
// The 6th smallest product is 0.
// Example 3:

// Input: nums1 = [-2,-1,0,1,2], nums2 = [-3,-1,2,4,5], k = 3
// Output: -6
// Explanation: The 3 smallest products are:
// - nums1[0] * nums2[4] = (-2) * 5 = -10
// - nums1[0] * nums2[3] = (-2) * 4 = -8
// - nums1[4] * nums2[0] = 2 * (-3) = -6
// The 3rd smallest product is -6.
 

// Constraints:

// 1 <= nums1.length, nums2.length <= 5 * 10^4
// -105 <= nums1[i], nums2[j] <= 10^5
// 1 <= k <= nums1.length * nums2.length
// nums1 and nums2 are sorted.


// Solution: 

import java.util.*;

class Solution {
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        int a0 = lowerBound(nums1, 0);
        int b0 = lowerBound(nums2, 0);

        int[] A0 = Arrays.copyOfRange(nums1, 0, a0); // < 0
        int[] A1 = Arrays.copyOfRange(nums1, a0, nums1.length); // >= 0
        int[] B0 = Arrays.copyOfRange(nums2, 0, b0); // < 0
        int[] B1 = Arrays.copyOfRange(nums2, b0, nums2.length); // >= 0

        int a1 = nums1.length - a0;
        int b1 = nums2.length - b0;
        long cntNeg = (long)a0 * b1 + (long)a1 * b0;

        if (k <= cntNeg) {
            reverse(A1);
            reverse(B1);
            long l = -1_000_000_000_0L, r = 0, ans = 0;
            while (l <= r) {
                long m = l + (r - l) / 2;
                long cnt = cntLessEq(A0, B1, m) + cntLessEq(A1, B0, m);
                if (cnt < k) {
                    l = m + 1;
                } else {
                    ans = m;
                    r = m - 1;
                }
            }
            return ans;
        } else {
            k -= cntNeg;
            reverse(A0);
            reverse(B0);
            long l = 0, r = 1_000_000_000_0L, ans = 0;
            while (l <= r) {
                long m = l + (r - l) / 2;
                long cnt = cntLessEq(A1, B1, m) + cntLessEq(A0, B0, m);
                if (cnt < k) {
                    l = m + 1;
                } else {
                    ans = m;
                    r = m - 1;
                }
            }
            return ans;
        }
    }

    private long cntLessEq(int[] A, int[] B, long x) {
        long cnt = 0;
        int j = B.length - 1;
        for (int i = 0; i < A.length; i++) {
            while (j >= 0 && (long) A[i] * B[j] > x) {
                j--;
            }
            cnt += j + 1;
        }
        return cnt;
    }

    private int lowerBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int m = l + (r - l) / 2;
            if (arr[m] < target) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    private void reverse(int[] arr) {
        int i = 0, j = arr.length - 1;
        while (i < j) {
            int tmp = arr[i];
            arr[i++] = arr[j];
            arr[j--] = tmp;
        }
    }
}
