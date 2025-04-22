# 2563. Count the Number of Fair Pairs

# Given a 0-indexed integer array nums of size n and two integers lower and upper, return the number of fair pairs.

# A pair (i, j) is fair if:

# 0 <= i < j < n, and
# lower <= nums[i] + nums[j] <= upper
 

# Example 1:

# Input: nums = [0,1,7,4,4,5], lower = 3, upper = 6
# Output: 6
# Explanation: There are 6 fair pairs: (0,3), (0,4), (0,5), (1,3), (1,4), and (1,5).
# Example 2:

# Input: nums = [1,7,9,2,5], lower = 11, upper = 11
# Output: 1
# Explanation: There is a single fair pair: (2,3).
 

# Constraints:

# 1 <= nums.length <= 10^5
# nums.length == n
# -10^9 <= nums[i] <= 10^9
# -10^9 <= lower <= upper <= 10^9


# Solution:


import bisect

class Solution(object):
    def countFairPairs(self, nums, lower, upper):
        """
        :type nums: List[int]
        :type lower: int
        :type upper: int
        :rtype: int
        """
        nums.sort()
        count = 0
        n = len(nums)

        for i in range(n):
            # We're looking for nums[j] such that:
            # lower - nums[i] <= nums[j] <= upper - nums[i]
            left = bisect.bisect_left(nums, lower - nums[i], i + 1)
            right = bisect.bisect_right(nums, upper - nums[i], i + 1)
            count += (right - left)

        return count
