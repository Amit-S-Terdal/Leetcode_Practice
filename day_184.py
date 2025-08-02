# 2419. Longest Subarray With Maximum Bitwise AND

# You are given an integer array nums of size n.

# Consider a non-empty subarray from nums that has the maximum possible bitwise AND.

# In other words, let k be the maximum value of the bitwise AND of any subarray of nums. Then, only subarrays with a bitwise AND equal to k should be considered.
# Return the length of the longest such subarray.

# The bitwise AND of an array is the bitwise AND of all the numbers in it.

# A subarray is a contiguous sequence of elements within an array.

 

# Example 1:

# Input: nums = [1,2,3,3,2,2]
# Output: 2
# Explanation:
# The maximum possible bitwise AND of a subarray is 3.
# The longest subarray with that value is [3,3], so we return 2.
# Example 2:

# Input: nums = [1,2,3,4]
# Output: 1
# Explanation:
# The maximum possible bitwise AND of a subarray is 4.
# The longest subarray with that value is [4], so we return 1.
 

# Constraints:

# 1 <= nums.length <= 10^5
# 1 <= nums[i] <= 10^6


# Solution:


class Solution(object):
    def longestSubarray(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        n = len(nums)
        max_len = 0
        x_max = 0
        l = 0
        r = 0

        while r < n:
            while r < n and nums[r] == x_max:
                r += 1
            if r == n or nums[r] < x_max:
                max_len = max(max_len, r - l)
                l = r + 1
                r = l  # reset r to match new l
            else:
                x_max = nums[r]
                max_len = 1
                l = r
                r = r + 1

        return max_len
