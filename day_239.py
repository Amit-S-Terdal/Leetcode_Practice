# 611. Valid Triangle Number

# Given an integer array nums, return the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.

 

# Example 1:

# Input: nums = [2,2,3,4]
# Output: 3
# Explanation: Valid combinations are: 
# 2,3,4 (using the first 2)
# 2,3,4 (using the second 2)
# 2,2,3
# Example 2:

# Input: nums = [4,2,3,4]
# Output: 4
 

# Constraints:

# 1 <= nums.length <= 1000
# 0 <= nums[i] <= 1000


# Solution: 

class Solution(object):
    def triangleNumber(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        freq = [0] * 1001
        xMin, xMax = 1001, 0

        # Count frequency and find min/max
        for x in nums:
            freq[x] += 1
            xMin = min(xMin, x)
            xMax = max(xMax, x)

        # Reconstruct nums in sorted order using frequency array
        i = 0
        for x in range(xMin, xMax + 1):
            f = freq[x]
            if f == 0:
                continue
            for _ in range(f):
                nums[i] = x
                i += 1

        n = len(nums)
        ans = 0

        # Count valid triangles using 2-pointer approach
        for i in range(2, n):
            l, r = 0, i - 1
            while l < r:
                if nums[l] + nums[r] > nums[i]:
                    ans += r - l
                    r -= 1
                else:
                    l += 1

        return ans
