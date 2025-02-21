# 1980. Find Unique Binary String

# Hint
# Given an array of strings nums containing n unique binary strings each of length n, return a binary string of length n that does not appear in nums. If there are multiple answers, you may return any of them.

 

# Example 1:

# Input: nums = ["01","10"]
# Output: "11"
# Explanation: "11" does not appear in nums. "00" would also be correct.
# Example 2:

# Input: nums = ["00","01"]
# Output: "11"
# Explanation: "11" does not appear in nums. "10" would also be correct.
# Example 3:

# Input: nums = ["111","011","001"]
# Output: "101"
# Explanation: "101" does not appear in nums. "000", "010", "100", and "110" would also be correct.
 

# Constraints:

# n == nums.length
# 1 <= n <= 16
# nums[i].length == n
# nums[i] is either '0' or '1'.
# All the strings of nums are unique.

# Solution:

class Solution(object):
    def findDifferentBinaryString(self, nums):
        """
        :type nums: List[str]
        :rtype: str
        """
        n = len(nums)
        # Generate all possible binary strings of length n
        for i in range(2**n):
            binary_str = bin(i)[2:].zfill(n)
            if binary_str not in nums:
                return binary_str
        return ""  # This line will never be reached as per the problem constraints

# Example usage:
solution = Solution()
print(solution.findDifferentBinaryString(["01", "10"]))  # Output: "11"
print(solution.findDifferentBinaryString(["00", "01"]))  # Output: "11"
print(solution.findDifferentBinaryString(["111", "011", "001"]))  # Output: "101"