# 1317. Convert Integer to the Sum of Two No-Zero Integers

# No-Zero integer is a positive integer that does not contain any 0 in its decimal representation.

# Given an integer n, return a list of two integers [a, b] where:

# a and b are No-Zero integers.
# a + b = n
# The test cases are generated so that there is at least one valid solution. If there are many valid solutions, you can return any of them.

 

# Example 1:

# Input: n = 2
# Output: [1,1]
# Explanation: Let a = 1 and b = 1.
# Both a and b are no-zero integers, and a + b = 2 = n.
# Example 2:

# Input: n = 11
# Output: [2,9]
# Explanation: Let a = 2 and b = 9.
# Both a and b are no-zero integers, and a + b = 11 = n.
# Note that there are other valid answers as [8, 3] that can be accepted.
 

# Constraints:

# 2 <= n <= 10^4


# Solution:



import random

class Solution(object):
    def getNoZeroIntegers(self, n):
        """
        :type n: int
        :rtype: List[int]
        """
        a, b, tens = 0, 0, 1

        while n > 0:
            d = n % 10

            if d == 0:
                x = random.randint(1, 9)  # [1, 9]
                a += x * tens
                b += (10 - x) * tens
                n -= 10  # borrow
            elif d == 1 and n >= 10:
                x = random.randint(2, 9)  # [2, 9]
                a += x * tens
                b += (11 - x) * tens
                n -= 10  # borrow
            else:
                x = 1 if d == 1 else random.randint(1, d - 1)  # [1, d-1]
                a += x * tens
                b += (d - x) * tens

            n //= 10
            tens *= 10

        return [a, b]

