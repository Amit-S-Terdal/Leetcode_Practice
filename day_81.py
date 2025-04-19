# 2537. Count the Number of Good Subarrays

# Given an integer array nums and an integer k, return the number of good subarrays of nums.

# A subarray arr is good if there are at least k pairs of indices (i, j) such that i < j and arr[i] == arr[j].

# A subarray is a contiguous non-empty sequence of elements within an array.

 

# Example 1:

# Input: nums = [1,1,1,1,1], k = 10
# Output: 1
# Explanation: The only good subarray is the array nums itself.
# Example 2:

# Input: nums = [3,1,4,3,2,2,4], k = 2
# Output: 4
# Explanation: There are 4 different good subarrays:
# - [3,1,4,3,2,2] that has 2 pairs.
# - [3,1,4,3,2,2,4] that has 3 pairs.
# - [1,4,3,2,2,4] that has 2 pairs.
# - [4,3,2,2,4] that has 2 pairs.
 

# Constraints:

# 1 <= nums.length <= 10^5
# 1 <= nums[i], k <= 10^9


# Solution: 

class Solution(object):
    def countGood(self, nums, k):
        """
        :type nums: List[int]
        :type k: int
        :rtype: int
        """
        from collections import defaultdict

        n = len(nums)
        count = defaultdict(int)
        left = 0
        pairs = 0
        ans = 0

        for right in range(n):
            val = nums[right]
            pairs += count[val]
            count[val] += 1

            while pairs >= k:
                ans += n - right
                count[nums[left]] -= 1
                pairs -= count[nums[left]]
                left += 1

        return ans
