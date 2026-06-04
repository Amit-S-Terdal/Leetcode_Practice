# 3751. Total Waviness of Numbers in Range I

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

# 1 <= num1 <= num2 <= 10^5



# Solution:




class Solution(object):
    D = [0] * 6  # least significant digit first

    def wav(self, x):
        d = 0

        while x > 0:
            self.D[d] = x % 10
            d += 1
            x //= 10

        cnt = 0
        prv = self.D[0]
        nxt = self.D[1]

        for i in range(1, d - 1):
            cur = nxt
            nxt = self.D[i + 1]

            if ((prv < cur and cur > nxt) or
                (prv > cur and cur < nxt)):
                cnt += 1

            prv = cur

        return cnt

    def totalWaviness(self, num1, num2):
        """
        :type num1: int
        :type num2: int
        :rtype: int
        """
        num1 = max(num1, 101)

        ans = 0
        for x in range(num1, num2 + 1):
            ans += self.wav(x)

        return ans
