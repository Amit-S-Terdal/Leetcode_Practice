# 3753. Total Waviness of Numbers in Range II

# You are given two integers num1 and num2 representing an inclusive range [num1, num2].

# The waviness of a number is defined as the total count of its peaks and valleys:

# A digit is a peak if it is strictly greater than both of its immediate neighbors.
# A digit is a valley if it is strictly less than both of its immediate neighbors.
# The first and last digits of a number cannot be peaks or valleys.
# Any number with fewer than 3 digits has a waviness of 0.
# Return the total sum of waviness for all numbers in the range [num1, num2].
 

# Example 1:

# Input: num1 = 120, num2 = 130

# Output: 3

# Explanation:

# In the range [120, 130]:

# 120: middle digit 2 is a peak, waviness = 1.
# 121: middle digit 2 is a peak, waviness = 1.
# 130: middle digit 3 is a peak, waviness = 1.
# All other numbers in the range have a waviness of 0.
# Thus, total waviness is 1 + 1 + 1 = 3.

# Example 2:

# Input: num1 = 198, num2 = 202

# Output: 3

# Explanation:

# In the range [198, 202]:

# 198: middle digit 9 is a peak, waviness = 1.
# 201: middle digit 0 is a valley, waviness = 1.
# 202: middle digit 0 is a valley, waviness = 1.
# All other numbers in the range have a waviness of 0.
# Thus, total waviness is 1 + 1 + 1 = 3.

# Example 3:

# Input: num1 = 4848, num2 = 4848

# Output: 2

# Explanation:

# Number 4848: the second digit 8 is a peak, and the third digit 4 is a valley, giving a waviness of 2.

 

# Constraints:

# 1 <= num1 <= num2 <= 10^15​​​​​​​

# Solution:




class Solution(object):
    def totalWaviness(self, num1, num2):
        """
        :type num1: int
        :type num2: int
        :rtype: int
        """

        tens = [0] * 15
        tens[0] = 1
        for i in range(1, 15):
            tens[i] = tens[i - 1] * 10

        def solve(num):
            if num < 101:
                return 0

            s = [0] * 15
            x = num
            for i in range(14, -1, -1):
                s[i] = x % 10
                x //= 10

            dp = {}

            def f(i, tight, lead0, cmp_, prv):
                if i == 15:
                    return 0

                key = (i, tight, lead0, cmp_, prv)
                if key in dp:
                    return dp[key]

                lim = s[i] if tight else 9
                tight_ways = (num % tens[14 - i] + 1) if tight else 0

                cnt = 0

                for d in range(lim + 1):
                    nxt_tight = tight and (d == lim)
                    nxt_lead0 = lead0 and (d == 0)

                    if not lead0:
                        if prv < d:
                            nxt_cmp = 1
                        elif prv > d:
                            nxt_cmp = 2
                        else:
                            nxt_cmp = 0
                    else:
                        nxt_cmp = 0

                    is_wav = ((cmp_ | nxt_cmp) == 3)

                    ways = tight_ways if nxt_tight else tens[14 - i]
                    summand = ways if is_wav else 0

                    cnt += summand + f(
                        i + 1,
                        nxt_tight,
                        nxt_lead0,
                        nxt_cmp,
                        d
                    )

                dp[key] = cnt
                return cnt

            return f(0, True, True, 0, 0)

        num2 = min(num2, 10**15 - 1)

        if num1 > num2:
            return 0

        return solve(num2) - solve(num1 - 1)