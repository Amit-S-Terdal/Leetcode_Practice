# 3700. Number of ZigZag Arrays II

# You are given three integers n, l, and r.

# A ZigZag array of length n is defined as follows:

# Each element lies in the range [l, r].
# No two adjacent elements are equal.
# No three consecutive elements form a strictly increasing or strictly decreasing sequence.
# Return the total number of valid ZigZag arrays.

# Since the answer may be large, return it modulo 109 + 7.

# A sequence is said to be strictly increasing if each element is strictly greater than its previous one (if exists).

# A sequence is said to be strictly decreasing if each element is strictly smaller than its previous one (if exists).

 

# Example 1:

# Input: n = 3, l = 4, r = 5

# Output: 2

# Explanation:

# There are only 2 valid ZigZag arrays of length n = 3 using values in the range [4, 5]:

# [4, 5, 4]
# [5, 4, 5]
# Example 2:

# Input: n = 3, l = 1, r = 3

# Output: 10

# Explanation:

# ​​​​​​​There are 10 valid ZigZag arrays of length n = 3 using values in the range [1, 3]:

# [1, 2, 1], [1, 3, 1], [1, 3, 2]
# [2, 1, 2], [2, 1, 3], [2, 3, 1], [2, 3, 2]
# [3, 1, 2], [3, 1, 3], [3, 2, 3]
# All arrays meet the ZigZag conditions.

 

# Constraints:

# 3 <= n <= 10^9
# 1 <= l < r <= 75​​​​​​​




# Solution: 




class Solution(object):
    def zigZagArrays(self, n, l, r):
        """
        :type n: int
        :type l: int
        :type r: int
        :rtype: int
        """
        MOD = 1000000007
        m = r - l + 1

        def multiply(A, B):
            C = [0] * (m * m)

            for i in range(m):
                for k in range(m):
                    a = A[i * m + k]
                    if a == 0:
                        continue

                    for j in range(m):
                        C[i * m + j] = (C[i * m + j] +
                                        a * B[k * m + j]) % MOD
            return C

        def identity():
            I = [0] * (m * m)
            for i in range(m):
                I[i * m + i] = 1
            return I

        # MSBF modular matrix exponentiation
        def power(M, exp):
            if exp == 0:
                return identity()

            bmax = exp.bit_length() - 1
            ans = M[:]

            for i in range(bmax - 1, -1, -1):
                ans = multiply(ans, ans)

                if (exp >> i) & 1:
                    ans = multiply(ans, M)

            return ans

        U = [0] * (m * m)
        L = [0] * (m * m)

        for i in range(m):
            for j in range(i + 1, m):
                U[i * m + j] = 1

        for i in range(m):
            for j in range(i):
                L[i * m + j] = 1

        n -= 1
        n0 = n >> 1

        UL = multiply(U, L)
        P = power(UL, n0)

        if n & 1:
            P = multiply(L, P)

        return (2 * sum(P)) % MOD