# 2221. Find Triangular Sum of an Array

# You are given a 0-indexed integer array nums, where nums[i] is a digit between 0 and 9 (inclusive).

# The triangular sum of nums is the value of the only element present in nums after the following process terminates:

# Let nums comprise of n elements. If n == 1, end the process. Otherwise, create a new 0-indexed integer array newNums of length n - 1.
# For each index i, where 0 <= i < n - 1, assign the value of newNums[i] as (nums[i] + nums[i+1]) % 10, where % denotes modulo operator.
# Replace the array nums with newNums.
# Repeat the entire process starting from step 1.
# Return the triangular sum of nums.

 

# Example 1:


# Input: nums = [1,2,3,4,5]
# Output: 8
# Explanation:
# The above diagram depicts the process from which we obtain the triangular sum of the array.
# Example 2:

# Input: nums = [5]
# Output: 5
# Explanation:
# Since there is only one element in nums, the triangular sum is the value of that element itself.
 

# Constraints:

# 1 <= nums.length <= 1000
# 0 <= nums[i] <= 9



# Solution:



class Solution(object):
    def triangularSum(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        def inv_mod10():
            return {1: 1, 3: 7, 7: 3, 9: 9}
        
        def factor(x):
            exp2 = (x & -x).bit_length() - 1  # countr_zero
            x >>= exp2
            exp5 = 0
            while x % 5 == 0:
                x //= 5
                exp5 += 1
            return (exp2, exp5, x % 10)

        def mul(x, y):
            return (
                x[0] + y[0],
                x[1] + y[1],
                (x[2] * y[2]) % 10
            )

        def div(x, y):
            inv = inv_mod10()
            return (
                x[0] - y[0],
                x[1] - y[1],
                (x[2] * inv[y[2]]) % 10
            )

        def to_int(x):
            exp2, exp5, rem = x
            if exp2 > 0 and exp5 > 0:
                return 0
            if exp5 > 0:
                return (5 * rem) % 10
            return (rem << exp2) % 10

        def comb(n):
            a = [None] * (n + 1)
            A = [0] * (n + 1)
            a[0] = a[n] = (0, 0, 1)
            A[0] = A[n] = 1
            for k in range(1, n // 2 + 1):
                num = factor(n - k + 1)
                den = factor(k)
                a[k] = a[n - k] = div(mul(a[k - 1], num), den)
                A[k] = A[n - k] = to_int(a[k])
            return A

        n = len(nums) - 1
        coeff = comb(n)
        result = 0
        for i in range(n + 1):
            result = (result + coeff[i] * nums[i]) % 10
        return result
