# 2787. Ways to Express an Integer as Sum of Powers

# Given two positive integers n and x.

# Return the number of ways n can be expressed as the sum of the xth power of unique positive integers, in other words, the number of sets of unique integers [n1, n2, ..., nk] where n = n1x + n2x + ... + nkx.

# Since the result can be very large, return it modulo 109 + 7.

# For example, if n = 160 and x = 3, one way to express n is n = 23 + 33 + 53.

 

# Example 1:

# Input: n = 10, x = 2
# Output: 1
# Explanation: We can express n as the following: n = 32 + 12 = 10.
# It can be shown that it is the only way to express 10 as the sum of the 2nd power of unique integers.
# Example 2:

# Input: n = 4, x = 1
# Output: 2
# Explanation: We can express n in the following ways:
# - n = 41 = 4.
# - n = 31 + 11 = 4.
 

# Constraints:

# 1 <= n <= 300
# 1 <= x <= 5


# Solution:

class Solution(object):
    def numberOfWays(self, n, x):
        """
        :type n: int
        :type x: int
        :rtype: int
        """
        MOD = 10**9 + 7

        # Generate all powers i^x <= n
        powers = []
        base = 1
        while True:
            p = pow(base, x)
            if p > n:
                break
            powers.append(p)
            base += 1

        # 1D DP for subset-sum counting (each power used at most once)
        dp = [0] * (n + 1)
        dp[0] = 1
        for p in powers:
            for s in range(n, p - 1, -1):
                dp[s] = (dp[s] + dp[s - p]) % MOD

        return dp[n]
