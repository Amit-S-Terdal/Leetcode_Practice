# 2338. Count the Number of Ideal Arrays

# You are given two integers n and maxValue, which are used to describe an ideal array.

# A 0-indexed integer array arr of length n is considered ideal if the following conditions hold:

# Every arr[i] is a value from 1 to maxValue, for 0 <= i < n.
# Every arr[i] is divisible by arr[i - 1], for 0 < i < n.
# Return the number of distinct ideal arrays of length n. Since the answer may be very large, return it modulo 109 + 7.

 

# Example 1:

# Input: n = 2, maxValue = 5
# Output: 10
# Explanation: The following are the possible ideal arrays:
# - Arrays starting with the value 1 (5 arrays): [1,1], [1,2], [1,3], [1,4], [1,5]
# - Arrays starting with the value 2 (2 arrays): [2,2], [2,4]
# - Arrays starting with the value 3 (1 array): [3,3]
# - Arrays starting with the value 4 (1 array): [4,4]
# - Arrays starting with the value 5 (1 array): [5,5]
# There are a total of 5 + 2 + 1 + 1 + 1 = 10 distinct ideal arrays.
# Example 2:

# Input: n = 5, maxValue = 3
# Output: 11
# Explanation: The following are the possible ideal arrays:
# - Arrays starting with the value 1 (9 arrays): 
#    - With no other distinct values (1 array): [1,1,1,1,1] 
#    - With 2nd distinct value 2 (4 arrays): [1,1,1,1,2], [1,1,1,2,2], [1,1,2,2,2], [1,2,2,2,2]
#    - With 2nd distinct value 3 (4 arrays): [1,1,1,1,3], [1,1,1,3,3], [1,1,3,3,3], [1,3,3,3,3]
# - Arrays starting with the value 2 (1 array): [2,2,2,2,2]
# - Arrays starting with the value 3 (1 array): [3,3,3,3,3]
# There are a total of 9 + 1 + 1 = 11 distinct ideal arrays.
 

# Constraints:

# 2 <= n <= 10^4
# 1 <= maxValue <= 10^4


# Solution: 

MOD = 10**9 + 7

class Solution(object):
    def power(self, a, b):
        res = 1
        while b > 0:
            if b % 2 == 1:
                res = res * a % MOD
            a = a * a % MOD
            b //= 2
        return res

    def precompute_factorials(self, n):
        fact = [1] * (n + 1)
        invFact = [1] * (n + 1)
        for i in range(1, n + 1):
            fact[i] = fact[i - 1] * i % MOD
        invFact[n] = self.power(fact[n], MOD - 2)
        for i in range(n - 1, -1, -1):
            invFact[i] = invFact[i + 1] * (i + 1) % MOD
        return fact, invFact

    def comb(self, n, k, fact, invFact):
        if k < 0 or k > n:
            return 0
        return fact[n] * invFact[k] % MOD * invFact[n - k] % MOD

    def idealArrays(self, n, maxValue):
        maxLen = 14  # max number of prime factors possible up to 10^4
        dp = [[0] * (maxLen + 1) for _ in range(maxValue + 1)]

        for i in range(1, maxValue + 1):
            dp[i][1] = 1

        for length in range(1, maxLen):
            for val in range(1, maxValue + 1):
                if dp[val][length] == 0:
                    continue
                for mul in range(2, (maxValue // val) + 1):
                    dp[val * mul][length + 1] = (dp[val * mul][length + 1] + dp[val][length]) % MOD

        fact, invFact = self.precompute_factorials(n)

        result = 0
        for val in range(1, maxValue + 1):
            for length in range(1, min(maxLen + 1, n + 1)):
                ways = self.comb(n - 1, length - 1, fact, invFact)
                result = (result + dp[val][length] * ways) % MOD

        return result
