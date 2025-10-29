# 3370. Smallest Number With All Set Bits
# Easy

# You are given a positive number n.

# Return the smallest number x greater than or equal to n, such that the binary representation of x contains only set bits

 

# Example 1:

# Input: n = 5

# Output: 7

# Explanation:

# The binary representation of 7 is "111".

# Example 2:

# Input: n = 10

# Output: 15

# Explanation:

# The binary representation of 15 is "1111".

# Example 3:

# Input: n = 3

# Output: 3

# Explanation:

# The binary representation of 3 is "11".

 

# Constraints:

# 1 <= n <= 1000


# Solution:


class Solution(object):
    # Initialize the array A once
    A = [0] * 11

    @staticmethod
    def buildA():
        if Solution.A[0] == 1:
            return  # Only initialize once
        Solution.A[0] = 1
        for i in range(1, 11):
            Solution.A[i] = (1 << i) - 1  # 2^i - 1

    def smallestNumber(self, n):
        """
        :type n: int
        :rtype: int
        """
        Solution.buildA()  # Build the array A
        # Find the smallest number greater than or equal to n
        for num in Solution.A:
            if num >= n:
                return num
        return -1  # In case no value is found (although this is not expected)
