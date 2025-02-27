# 1749. Maximum Absolute Sum of Any Subarray

# You are given an integer array nums. The absolute sum of a subarray [numsl, numsl+1, ..., numsr-1, numsr] is abs(numsl + numsl+1 + ... + numsr-1 + numsr).

# Return the maximum absolute sum of any (possibly empty) subarray of nums.

# Note that abs(x) is defined as follows:

# If x is a negative integer, then abs(x) = -x.
# If x is a non-negative integer, then abs(x) = x.
 

# Example 1:

# Input: nums = [1,-3,2,3,-4]
# Output: 5
# Explanation: The subarray [2,3] has absolute sum = abs(2+3) = abs(5) = 5.
# Example 2:

# Input: nums = [2,-5,1,-4,3,-2]
# Output: 8
# Explanation: The subarray [-5,1,-4] has absolute sum = abs(-5+1-4) = abs(-8) = 8.
 

# Constraints:

# 1 <= nums.length <= 10^5
# -10^4 <= nums[i] <= 10^4


# Solution:

class Solution(object):
    def maxAbsoluteSum(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        max_sum = min_sum = current_max = current_min = nums[0]
        result = abs(nums[0])
        
        for num in nums[1:]:
            current_max = max(num, current_max + num)
            current_min = min(num, current_min + num)
            
            max_sum = max(max_sum, current_max)
            min_sum = min(min_sum, current_min)
            
            result = max(result, abs(max_sum), abs(min_sum))
        
        return result
