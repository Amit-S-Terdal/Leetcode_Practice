# 3640. Trionic Array II

# You are given an integer array nums of length n.

# A trionic subarray is a contiguous subarray nums[l...r] (with 0 <= l < r < n) for which there exist indices l < p < q < r such that:

# nums[l...p] is strictly increasing,
# nums[p...q] is strictly decreasing,
# nums[q...r] is strictly increasing.
# Return the maximum sum of any trionic subarray in nums.

 

# Example 1:

# Input: nums = [0,-2,-1,-3,0,2,-1]

# Output: -4

# Explanation:

# Pick l = 1, p = 2, q = 3, r = 5:

# nums[l...p] = nums[1...2] = [-2, -1] is strictly increasing (-2 < -1).
# nums[p...q] = nums[2...3] = [-1, -3] is strictly decreasing (-1 > -3)
# nums[q...r] = nums[3...5] = [-3, 0, 2] is strictly increasing (-3 < 0 < 2).
# Sum = (-2) + (-1) + (-3) + 0 + 2 = -4.
# Example 2:

# Input: nums = [1,4,2,7]

# Output: 14

# Explanation:

# Pick l = 0, p = 1, q = 2, r = 3:

# nums[l...p] = nums[0...1] = [1, 4] is strictly increasing (1 < 4).
# nums[p...q] = nums[1...2] = [4, 2] is strictly decreasing (4 > 2).
# nums[q...r] = nums[2...3] = [2, 7] is strictly increasing (2 < 7).
# Sum = 1 + 4 + 2 + 7 = 14.
 

# Constraints:

# 4 <= n = nums.length <= 10^5
# -10^9 <= nums[i] <= 10^9
# It is guaranteed that at least one trionic subarray exists.




# Solution:




class Solution(object):
    def maxSumTrionic(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        A = nums
        n = len(A)
        neg = -(1 << 50)
        ans = neg

        i = l = p = q = r = 0

        while i < n - 1:
            # Find l, reset Sum
            while i < n - 1 and A[i] >= A[i + 1]:
                i += 1
            l = i
            Sum = 0

            # 1st uphill: find p
            while i < n - 1 and A[i] < A[i + 1]:
                x = A[i]
                Sum += x if x > 0 else 0
                i += 1
            p = i

            if p == l or (p + 1 < n and A[p] == A[p + 1]):
                continue

            if A[p - 1] < 0:
                Sum += A[p - 1]

            # 1st downhill: find q
            while i < n - 1 and A[i] > A[i + 1]:
                Sum += A[i]
                i += 1
            q = i

            if q == p or (q + 1 < n and A[q] == A[q + 1]):
                continue

            # 2nd uphill: find r
            Sum += A[q]
            inc = 0
            maxInc = neg

            while i < n - 1 and A[i] < A[i + 1]:
                inc += A[i + 1]
                maxInc = max(maxInc, inc)
                i += 1
            r = i

            if r > q:
                ans = max(ans, Sum + maxInc)
                i = q

            if l == i:
                i += 1

        return ans
