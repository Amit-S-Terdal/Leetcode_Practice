# 1015. Smallest Integer Divisible by K

# Given a positive integer k, you need to find the length of the smallest positive integer n such that n is divisible by k, and n only contains the digit 1.

# Return the length of n. If there is no such n, return -1.

# Note: n may not fit in a 64-bit signed integer.

 

# Example 1:

# Input: k = 1
# Output: 1
# Explanation: The smallest answer is n = 1, which has length 1.
# Example 2:

# Input: k = 2
# Output: -1
# Explanation: There is no such positive integer n divisible by 2.
# Example 3:

# Input: k = 3
# Output: 3
# Explanation: The smallest answer is n = 111, which has length 3.
 

# Constraints:

# 1 <= k <= 10^5



# Solution: 


from fractions import gcd
import bisect

class Solution(object):
    def smallestRepunitDivByK(self, k):
        """
        :type k: int
        :rtype: int
        """
        if gcd(10, k) > 1:
            return -1

        X = [1, 11, 111, 1111, 11111, 111111]
        l0 = bisect.bisect_left(X, k)
        r = X[l0] % k
        length = l0 + 1

        if r == 0:
            return length

        length += 1
        while True:
            r = (10 * r + 1) % k
            if r == 0:
                return length
            length += 1

        return -1
