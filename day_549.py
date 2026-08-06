# 3345. Smallest Divisible Digit Product I

# You are given two integers n and t. Return the smallest number greater than or equal to n such that the product of its digits is divisible by t.

 

# Example 1:

# Input: n = 10, t = 2

# Output: 10

# Explanation:

# The digit product of 10 is 0, which is divisible by 2, making it the smallest number greater than or equal to 10 that satisfies the condition.

# Example 2:

# Input: n = 15, t = 3

# Output: 16

# Explanation:

# The digit product of 16 is 6, which is divisible by 3, making it the smallest number greater than or equal to 15 that satisfies the condition.

 

# Constraints:

# 1 <= n <= 100
# 1 <= t <= 10



# Solution:




class Solution(object):
    def product_digit(self, x):
        ans = 1
        while x:
            ans *= x % 10
            x //= 10
        return ans

    def smallestNumber(self, n, t):
        P = [
            self.product_digit(n // 10),
            self.product_digit(n // 10 + 1)
        ]

        z0 = (n // 10 + 1) * 10

        for z in range(n, n + 10):
            p = P[1 if z >= z0 else 0]
            d = z % 10

            if (p * d) % t == 0:
                return z

        return 0