# 2048. Next Greater Numerically Balanced Number

# An integer x is numerically balanced if for every digit d in the number x, there are exactly d occurrences of that digit in x.

# Given an integer n, return the smallest numerically balanced number strictly greater than n.

 

# Example 1:

# Input: n = 1
# Output: 22
# Explanation: 
# 22 is numerically balanced since:
# - The digit 2 occurs 2 times. 
# It is also the smallest numerically balanced number strictly greater than 1.
# Example 2:

# Input: n = 1000
# Output: 1333
# Explanation: 
# 1333 is numerically balanced since:
# - The digit 1 occurs 1 time.
# - The digit 3 occurs 3 times. 
# It is also the smallest numerically balanced number strictly greater than 1000.
# Note that 1022 cannot be the answer because 0 appeared more than 0 times.
# Example 3:

# Input: n = 3000
# Output: 3133
# Explanation: 
# 3133 is numerically balanced since:
# - The digit 1 occurs 1 time.
# - The digit 3 occurs 3 times.
# It is also the smallest numerically balanced number strictly greater than 3000.
 

# Constraints:

# 0 <= n <= 10^6



# Solution:



class Solution(object):
    Bal = []

    def nextBeautifulNumber(self, n):
        if not Solution.Bal:
            self.genBalanced()
        for x in Solution.Bal:
            if x > n:
                return x
        return -1  # should not happen

    @staticmethod
    def permDigits(digits):
        from itertools import permutations

        dz = len(digits)
        if dz == 1:
            d = digits[0]
            x = int(str(d) * d)
            Solution.Bal.append(x)
            return

        s = ''.join(str(d) * d for d in digits)
        seen = set()
        for p in set(permutations(s)):
            x = int(''.join(p))
            if x <= 1224444 and x not in seen:
                Solution.Bal.append(x)
                seen.add(x)

    @staticmethod
    def genBalanced():
        for mask in range(1, 1 << 6):
            length = 0
            digits = []
            for d in range(6):
                if mask & (1 << d):
                    length += d + 1
                    if length > 7:
                        break
                    digits.append(d + 1)
            if length <= 7:
                Solution.permDigits(digits)
        Solution.Bal.sort()
