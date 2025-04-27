# 2799. Count Complete Subarrays in an Array

# You are given an array nums consisting of positive integers.

# We call a subarray of an array complete if the following condition is satisfied:

# The number of distinct elements in the subarray is equal to the number of distinct elements in the whole array.
# Return the number of complete subarrays.

# A subarray is a contiguous non-empty part of an array.

 

# Example 1:

# Input: nums = [1,3,1,2,2]
# Output: 4
# Explanation: The complete subarrays are the following: [1,3,1,2], [1,3,1,2,2], [3,1,2] and [3,1,2,2].
# Example 2:

# Input: nums = [5,5,5,5]
# Output: 10
# Explanation: The array consists only of the integer 5, so any subarray is complete. The number of subarrays that we can choose is 10.
 

# Constraints:

# 1 <= nums.length <= 1000
# 1 <= nums[i] <= 2000


# Solution:


class Solution(object):
    def countCompleteSubarrays(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        from collections import defaultdict

        res = 0
        n = len(nums)
        right = 0
        count_map = defaultdict(int)
        
        # Step 1: Count total distinct elements in the array
        distinct_count = len(set(nums))

        # Step 2: Sliding window from left to right
        for left in range(n):
            if left > 0:
                remove = nums[left - 1]
                count_map[remove] -= 1
                if count_map[remove] == 0:
                    del count_map[remove]

            while right < n and len(count_map) < distinct_count:
                add = nums[right]
                count_map[add] += 1
                right += 1

            if len(count_map) == distinct_count:
                res += (n - right + 1)

        return res
