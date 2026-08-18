# 3471. Find the Largest Almost Missing Integer

# You are given an integer array nums and an integer k.

# An integer x is almost missing from nums if x appears in exactly one subarray of size k within nums.

# Return the largest almost missing integer from nums. If no such integer exists, return -1.

# A subarray is a contiguous sequence of elements within an array.
 

# Example 1:

# Input: nums = [3,9,2,1,7], k = 3

# Output: 7

# Explanation:

# 1 appears in 2 subarrays of size 3: [9, 2, 1] and [2, 1, 7].
# 2 appears in 3 subarrays of size 3: [3, 9, 2], [9, 2, 1], [2, 1, 7].
# 3 appears in 1 subarray of size 3: [3, 9, 2].
# 7 appears in 1 subarray of size 3: [2, 1, 7].
# 9 appears in 2 subarrays of size 3: [3, 9, 2], and [9, 2, 1].
# We return 7 since it is the largest integer that appears in exactly one subarray of size k.

# Example 2:

# Input: nums = [3,9,7,2,1,7], k = 4

# Output: 3

# Explanation:

# 1 appears in 2 subarrays of size 4: [9, 7, 2, 1], [7, 2, 1, 7].
# 2 appears in 3 subarrays of size 4: [3, 9, 7, 2], [9, 7, 2, 1], [7, 2, 1, 7].
# 3 appears in 1 subarray of size 4: [3, 9, 7, 2].
# 7 appears in 3 subarrays of size 4: [3, 9, 7, 2], [9, 7, 2, 1], [7, 2, 1, 7].
# 9 appears in 2 subarrays of size 4: [3, 9, 7, 2], [9, 7, 2, 1].
# We return 3 since it is the largest and only integer that appears in exactly one subarray of size k.

# Example 3:

# Input: nums = [0,0], k = 1

# Output: -1

# Explanation:

# There is no integer that appears in only one subarray of size 1.

 

# Constraints:

# 1 <= nums.length <= 50
# 0 <= nums[i] <= 50
# 1 <= k <= nums.length





# Solution: 



class Solution(object):

    def largestInteger(self, nums, k):
        n = len(nums)

        if k == n:
            return max(nums)

        freq = [0] * 51
        for x in nums:
            freq[x] += 1

        if k == 1:
            ans = -1
            for x in nums:
                if freq[x] == 1:
                    ans = max(ans, x)
            return ans

        x0 = nums[0]
        x1 = nums[-1]

        b0 = freq[x0] > 1
        b1 = freq[x1] > 1

        caseb = (2 if b0 else 0) + (1 if b1 else 0)

        if caseb == 0:
            return max(x0, x1)
        elif caseb == 1:
            return x0
        elif caseb == 2:
            return x1
        else:
            return -1