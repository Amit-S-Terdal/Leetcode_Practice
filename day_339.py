# 1390. Four Divisors

# Given an integer array nums, return the sum of divisors of the integers in that array that have exactly four divisors. If there is no such integer in the array, return 0.

 

# Example 1:

# Input: nums = [21,4,7]
# Output: 32
# Explanation: 
# 21 has 4 divisors: 1, 3, 7, 21
# 4 has 3 divisors: 1, 2, 4
# 7 has 2 divisors: 1, 7
# The answer is the sum of divisors of 21 only.
# Example 2:

# Input: nums = [21,21]
# Output: 64
# Example 3:

# Input: nums = [1,2,3,4,5]
# Output: 0
 

# Constraints:

# 1 <= nums.length <= 10^4
# 1 <= nums[i] <= 10^5



# Solution: 



import math

class Solution:
    def __init__(self):
        self.prime = [
            2, 3, 5, 7, 11, 13, 17, 17, 19, 23, 29, 31, 37, 41, 43,
            47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 
            109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 
            179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 
            241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 
            313
        ]
        self.Div4 = [-1] * 100001

    def sum4Div(self, x):
        if self.Div4[x] != -1:
            return self.Div4[x]

        y = x
        sum_div = 1 + x
        cntPF = 0
        xsqrt = int(math.sqrt(x))

        for p in self.prime:
            if p > xsqrt:
                break
            if y % p != 0:
                continue

            e = 0
            while y % p == 0:
                y //= p
                e += 1
            cntPF += 1

            if y == 1 and cntPF == 1:
                if e == 1:
                    return 0
                if e == 3:
                    self.Div4[x] = 1 + p + p * p + p * p * p
                    return self.Div4[x]

            if e > 1 or cntPF > 2:
                self.Div4[x] = 0
                return self.Div4[x]

            sum_div += p

        if y > 1:
            cntPF += 1
            sum_div += y

        self.Div4[x] = sum_div if cntPF == 2 else 0
        return self.Div4[x]

    def sumFourDivisors(self, nums):
        if self.Div4[0] == 0:
            self.Div4 = [-1] * 100001  # Resetting Div4 array
        ans = 0
        for x in nums:
            ans += self.sum4Div(x)
        return ans
