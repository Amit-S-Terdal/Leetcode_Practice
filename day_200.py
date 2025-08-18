# 342. Power of Four

# Given an integer n, return true if it is a power of four. Otherwise, return false.

# An integer n is a power of four, if there exists an integer x such that n == 4x.

 

# Example 1:

# Input: n = 16
# Output: true
# Example 2:

# Input: n = 5
# Output: false
# Example 3:

# Input: n = 1
# Output: true
 

# Constraints:

# -2^31 <= n <= 2^31 - 1



# Solution: 


class Solution(object):
    def isPowerOfFour(self, n):
        """
        :type n: int
        :rtype: bool
        """
        if n <= 0:
            return False
        
        # Find exponent exp = log2(n) using bit length
        exp = n.bit_length() - 1  # equivalent to 31 - countl_zero
        return (exp & 1) == 0 and n == 1 << exp