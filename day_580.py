# 115. Distinct Subsequences

# Given two strings s and t, return the number of distinct subsequences of s which equals t.

# The test cases are generated so that the answer fits on a 32-bit signed integer.

 

# Example 1:

# Input: s = "rabbbit", t = "rabbit"
# Output: 3
# Explanation:
# As shown below, there are 3 ways you can generate "rabbit" from s.
# rabbbit
# rabbbit
# rabbbit
# Example 2:

# Input: s = "babgbag", t = "bag"
# Output: 5
# Explanation:
# As shown below, there are 5 ways you can generate "bag" from s.
# babgbag
# babgbag
# babgbag
# babgbag
# babgbag
 

# Constraints:

# 1 <= s.length, t.length <= 1000
# s and t consist of English letters.


# Solution: 


class Solution:
    def numDistinct(self, s, t):
        m = len(s)
        n = len(t)

        dp = [[-1] * (n + 1) for _ in range(m + 1)]

        def f(i, j):
            if j == 0:
                return 1

            if i == 0 or j > i:
                return 0

            if dp[i][j] != -1:
                return dp[i][j]

            if s[i - 1] == t[j - 1]:
                ans = f(i - 1, j - 1) + f(i - 1, j)
            else:
                ans = f(i - 1, j)

            dp[i][j] = ans
            return ans

        return f(m, n)

