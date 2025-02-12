# 2342. Max Sum of a Pair With Equal Sum of Digits

# Hint
# You are given a 0-indexed array nums consisting of positive integers. You can choose two indices i and j, such that i != j, and the sum of digits of the number nums[i] is equal to that of nums[j].

# Return the maximum value of nums[i] + nums[j] that you can obtain over all possible indices i and j that satisfy the conditions.

 

# Example 1:

# Input: nums = [18,43,36,13,7]
# Output: 54
# Explanation: The pairs (i, j) that satisfy the conditions are:
# - (0, 2), both numbers have a sum of digits equal to 9, and their sum is 18 + 36 = 54.
# - (1, 4), both numbers have a sum of digits equal to 7, and their sum is 43 + 7 = 50.
# So the maximum sum that we can obtain is 54.
# Example 2:

# Input: nums = [10,12,19,14]
# Output: -1
# Explanation: There are no two numbers that satisfy the conditions, so we return -1.
 

# Constraints:

# 1 <= nums.length <= 105
# 1 <= nums[i] <= 109

# Solution:

class Solution(object):
    def maximumSum(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        digit_sum_map = {}
        max_sum = -1

        # Function to calculate sum of digits
        def digit_sum(num):
            return sum(int(d) for d in str(num))

        for num in nums:
            sum_digits = digit_sum(num)
            
            if sum_digits in digit_sum_map:
                # Calculate possible max sum
                max_sum = max(max_sum, digit_sum_map[sum_digits] + num)
                # Store the largest number for this sum of digits
                digit_sum_map[sum_digits] = max(digit_sum_map[sum_digits], num)
            else:
                digit_sum_map[sum_digits] = num

        return max_sum
