# 2040. Kth Smallest Product of Two Sorted Arrays

# Given two sorted 0-indexed integer arrays nums1 and nums2 as well as an integer k, return the kth (1-based) smallest product of nums1[i] * nums2[j] where 0 <= i < nums1.length and 0 <= j < nums2.length.
 

# Example 1:

# Input: nums1 = [2,5], nums2 = [3,4], k = 2
# Output: 8
# Explanation: The 2 smallest products are:
# - nums1[0] * nums2[0] = 2 * 3 = 6
# - nums1[0] * nums2[1] = 2 * 4 = 8
# The 2nd smallest product is 8.
# Example 2:

# Input: nums1 = [-4,-2,0,3], nums2 = [2,4], k = 6
# Output: 0
# Explanation: The 6 smallest products are:
# - nums1[0] * nums2[1] = (-4) * 4 = -16
# - nums1[0] * nums2[0] = (-4) * 2 = -8
# - nums1[1] * nums2[1] = (-2) * 4 = -8
# - nums1[1] * nums2[0] = (-2) * 2 = -4
# - nums1[2] * nums2[0] = 0 * 2 = 0
# - nums1[2] * nums2[1] = 0 * 4 = 0
# The 6th smallest product is 0.
# Example 3:

# Input: nums1 = [-2,-1,0,1,2], nums2 = [-3,-1,2,4,5], k = 3
# Output: -6
# Explanation: The 3 smallest products are:
# - nums1[0] * nums2[4] = (-2) * 5 = -10
# - nums1[0] * nums2[3] = (-2) * 4 = -8
# - nums1[4] * nums2[0] = 2 * (-3) = -6
# The 3rd smallest product is -6.
 

# Constraints:

# 1 <= nums1.length, nums2.length <= 5 * 10^4
# -105 <= nums1[i], nums2[j] <= 10^5
# 1 <= k <= nums1.length * nums2.length
# nums1 and nums2 are sorted.


# Solution: 

class Solution(object):
    def kthSmallestProduct(self, nums1, nums2, k):
        """
        :type nums1: List[int]
        :type nums2: List[int]
        :type k: int
        :rtype: int
        """
        from bisect import bisect_left

        def cntLessEq(A, B, x):
            cnt = 0
            j = len(B) - 1
            for i in range(len(A)):
                while j >= 0 and A[i] * B[j] > x:
                    j -= 1
                cnt += j + 1
            return cnt

        a0 = bisect_left(nums1, 0)
        b0 = bisect_left(nums2, 0)

        A0 = nums1[:a0]     # negative
        A1 = nums1[a0:]     # non-negative
        B0 = nums2[:b0]     # negative
        B1 = nums2[b0:]     # non-negative

        a1 = len(A1)
        b1 = len(B1)
        cntNeg = len(A0) * b1 + a1 * len(B0)

        if k <= cntNeg:
            A1 = A1[::-1]
            B1 = B1[::-1]
            l, r = -10**10, 0
            ans = 0
            while l <= r:
                m = (l + r) // 2
                cnt = cntLessEq(A0, B1, m) + cntLessEq(A1, B0, m)
                if cnt < k:
                    l = m + 1
                else:
                    ans = m
                    r = m - 1
            return ans
        else:
            k -= cntNeg
            A0 = A0[::-1]
            B0 = B0[::-1]
            l, r = 0, 10**10
            ans = 0
            while l <= r:
                m = (l + r) // 2
                cnt = cntLessEq(A1, B1, m) + cntLessEq(A0, B0, m)
                if cnt < k:
                    l = m + 1
                else:
                    ans = m
                    r = m - 1
            return ans
