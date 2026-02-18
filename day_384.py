# 693. Binary Number with Alternating Bits

# Given a positive integer, check whether it has alternating bits: namely, if two adjacent bits will always have different values.

 

# Example 1:

# Input: n = 5
# Output: true
# Explanation: The binary representation of 5 is: 101
# Example 2:

# Input: n = 7
# Output: false
# Explanation: The binary representation of 7 is: 111.
# Example 3:

# Input: n = 11
# Output: false
# Explanation: The binary representation of 11 is: 1011.
 

# Constraints:

# 1 <= n <= 2^31 - 1




# Solution: 


class Solution(object):
    def hasAlternatingBits(self, n):
        """
        :type n: int
        :rtype: bool
        """
        mB = n.bit_length() - 1
        prevB = True

        for i in range(mB - 1, -1, -1):
            if ((n >> i) & 1) == (1 if prevB else 0):
                return False
            prevB = not prevB

        return True
