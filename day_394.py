# 1680. Concatenation of Consecutive Binary Numbers

# Given an integer n, return the decimal value of the binary string formed by concatenating the binary representations of 1 to n in order, modulo 109 + 7.

 

# Example 1:

# Input: n = 1
# Output: 1
# Explanation: "1" in binary corresponds to the decimal value 1. 
# Example 2:

# Input: n = 3
# Output: 27
# Explanation: In binary, 1, 2, and 3 corresponds to "1", "10", and "11".
# After concatenating them, we have "11011", which corresponds to the decimal value 27.
# Example 3:

# Input: n = 12
# Output: 505379714
# Explanation: The concatenation results in "1101110010111011110001001101010111100".
# The decimal value of that is 118505380540.
# After modulo 109 + 7, the result is 505379714.
 

# Constraints:

# 1 <= n <= 10^5



# Solution:



class Solution(object):
    MOD = 10**9 + 7
    N = 100001
    dp = [0] * N
    End = 1

    def concatenatedBinary(self, n):
        """
        :type n: int
        :rtype: int
        """
        if n < Solution.End:
            return Solution.dp[n]

        ans = Solution.dp[Solution.End - 1]

        for x in range(Solution.End, n + 1):
            bw = x.bit_length()
            ans = ((ans << bw) + x) % Solution.MOD
            Solution.dp[Solution.End] = ans
            Solution.End += 1

        return ans