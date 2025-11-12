
# 2654. Minimum Number of Operations to Make All Array Elements Equal to 1

# You are given a 0-indexed array nums consisiting of positive integers. You can do the following operation on the array any number of times:

# Select an index i such that 0 <= i < n - 1 and replace either of nums[i] or nums[i+1] with their gcd value.
# Return the minimum number of operations to make all elements of nums equal to 1. If it is impossible, return -1.

# The gcd of two integers is the greatest common divisor of the two integers.

 

# Example 1:

# Input: nums = [2,6,3,4]
# Output: 4
# Explanation: We can do the following operations:
# - Choose index i = 2 and replace nums[2] with gcd(3,4) = 1. Now we have nums = [2,6,1,4].
# - Choose index i = 1 and replace nums[1] with gcd(6,1) = 1. Now we have nums = [2,1,1,4].
# - Choose index i = 0 and replace nums[0] with gcd(2,1) = 1. Now we have nums = [1,1,1,4].
# - Choose index i = 2 and replace nums[3] with gcd(1,4) = 1. Now we have nums = [1,1,1,1].
# Example 2:

# Input: nums = [2,10,6,14]
# Output: -1
# Explanation: It can be shown that it is impossible to make all the elements equal to 1.
 

# Constraints:

# 2 <= nums.length <= 50
# 1 <= nums[i] <= 10^6



# Solution: 

class Solution(object):
    def minOperations(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        def gcd(a, b):
            while b:
                a, b = b, a % b
            return a

        n = len(nums)
        cnt1 = nums.count(1)
        
        if cnt1 > 0:
            return n - cnt1
        
        for d in range(2, n + 1):
            for l in range(0, n - d + 1):
                nums[l] = gcd(nums[l], nums[l + 1])
                if nums[l] == 1:
                    return d + n - 2
        return -1
