# 3691. Maximum Total Subarray Value II

# You are given an integer array nums of length n and an integer k.

# You must select exactly k distinct non-empty subarrays nums[l..r] of nums. Subarrays may overlap, but the exact same subarray (same l and r) cannot be chosen more than once.

# The value of a subarray nums[l..r] is defined as: max(nums[l..r]) - min(nums[l..r]).

# The total value is the sum of the values of all chosen subarrays.

# Return the maximum possible total value you can achieve.

 

# Example 1:

# Input: nums = [1,3,2], k = 2

# Output: 4

# Explanation:

# One optimal approach is:

# Choose nums[0..1] = [1, 3]. The maximum is 3 and the minimum is 1, giving a value of 3 - 1 = 2.
# Choose nums[0..2] = [1, 3, 2]. The maximum is still 3 and the minimum is still 1, so the value is also 3 - 1 = 2.
# Adding these gives 2 + 2 = 4.

# Example 2:

# Input: nums = [4,2,5,1], k = 3

# Output: 12

# Explanation:

# One optimal approach is:

# Choose nums[0..3] = [4, 2, 5, 1]. The maximum is 5 and the minimum is 1, giving a value of 5 - 1 = 4.
# Choose nums[1..3] = [2, 5, 1]. The maximum is 5 and the minimum is 1, so the value is also 4.
# Choose nums[2..3] = [5, 1]. The maximum is 5 and the minimum is 1, so the value is again 4.
# Adding these gives 4 + 4 + 4 = 12.

 

# Constraints:

# 1 <= n == nums.length <= 5 * 10^4
# 0 <= nums[i] <= 10^9
# 1 <= k <= min(10^5, n * (n + 1) / 2)


# Solution: 



import heapq

class SparseTable:
    def __init__(self, nums):
        n = len(nums)
        bit_width = n.bit_length()

        self.Min = [[0] * n for _ in range(bit_width)]
        self.Max = [[0] * n for _ in range(bit_width)]

        for i in range(n):
            self.Min[0][i] = nums[i]
            self.Max[0][i] = nums[i]

        for k in range(1, bit_width):
            length = 1 << k
            half = length >> 1
            for j in range(n - length + 1):
                self.Min[k][j] = min(self.Min[k - 1][j],
                                     self.Min[k - 1][j + half])
                self.Max[k][j] = max(self.Max[k - 1][j],
                                     self.Max[k - 1][j + half])

    def query(self, left, right):
        k = (right - left).bit_length() - 1
        mx = max(self.Max[k][left],
                 self.Max[k][right - (1 << k)])
        mn = min(self.Min[k][left],
                 self.Min[k][right - (1 << k)])
        return mx - mn


class Solution(object):
    def maxTotalValue(self, nums, k):
        """
        :type nums: List[int]
        :type k: int
        :rtype: int
        """
        n = len(nums)
        lut = SparseTable(nums)

        # max-heap via negative values
        pq = []
        for i in range(n):
            val = lut.query(i, n)
            heapq.heappush(pq, (-val, i, n))

        res = 0

        while pq and -pq[0][0] > 0:
            neg_val, left, right = heapq.heappop(pq)
            val = -neg_val

            res += val

            right -= 1
            new_val = lut.query(left, right) if right > left else 0

            heapq.heappush(pq, (-new_val, left, right))

            k -= 1
            if k <= 0:
                break

        return res