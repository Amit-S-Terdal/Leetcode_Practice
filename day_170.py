# 3201. Find the Maximum Length of Valid Subsequence I

# You are given an integer array nums.
# A subsequence sub of nums with length x is called valid if it satisfies:

# (sub[0] + sub[1]) % 2 == (sub[1] + sub[2]) % 2 == ... == (sub[x - 2] + sub[x - 1]) % 2.
# Return the length of the longest valid subsequence of nums.

# A subsequence is an array that can be derived from another array by deleting some or no elements without changing the order of the remaining elements.

 

# Example 1:

# Input: nums = [1,2,3,4]

# Output: 4

# Explanation:

# The longest valid subsequence is [1, 2, 3, 4].

# Example 2:

# Input: nums = [1,2,1,1,2,1,2]

# Output: 6

# Explanation:

# The longest valid subsequence is [1, 2, 1, 2, 1, 2].

# Example 3:

# Input: nums = [1,3]

# Output: 2

# Explanation:

# The longest valid subsequence is [1, 3].

 

# Constraints:

# 2 <= nums.length <= 2 * 10^5
# 1 <= nums[i] <= 10^7


# Solution: 

class Solution(object):
    def maximumLength(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        n = len(nums)
        if n == 2:
            return 2

        z = nums[0] % 2  # 1 if odd, 0 if even
        length = [0, 0, 1]  # [even, odd, alternating]

        if z == 1:
            length[1] = 1
        else:
            length[0] = 1

        for i in range(1, n):
            x = nums[i] % 2
            if x == 1:
                length[1] += 1
            else:
                length[0] += 1

            if x != z:
                length[2] += 1
                z = x

        return max(length)
