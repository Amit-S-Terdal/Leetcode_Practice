# 1888. Minimum Number of Flips to Make the Binary String Alternating

# You are given a binary string s. You are allowed to perform two types of operations on the string in any sequence:

# Type-1: Remove the character at the start of the string s and append it to the end of the string.
# Type-2: Pick any character in s and flip its value, i.e., if its value is '0' it becomes '1' and vice-versa.
# Return the minimum number of type-2 operations you need to perform such that s becomes alternating.

# The string is called alternating if no two adjacent characters are equal.

# For example, the strings "010" and "1010" are alternating, while the string "0100" is not.
 

# Example 1:

# Input: s = "111000"
# Output: 2
# Explanation: Use the first operation two times to make s = "100011".
# Then, use the second operation on the third and sixth elements to make s = "101010".
# Example 2:

# Input: s = "010"
# Output: 0
# Explanation: The string is already alternating.
# Example 3:

# Input: s = "1110"
# Output: 1
# Explanation: Use the second operation on the second element to make s = "1010".
 

# Constraints:

# 1 <= s.length <= 10^5
# s[i] is either '0' or '1'.



# Solution:



class Solution(object):
    def minFlips(self, s):
        """
        :type s: str
        :rtype: int
        """
        n = len(s)
        pre = [0] * (n + 1)

        cnt0 = 0
        cnt1 = 0
        flip = 0

        for i in range(n):
            if int(s[i]) == flip:
                cnt1 += 1
            cnt0 = (i + 1) - cnt1
            pre[i + 1] = (cnt0 << 32) + cnt1
            flip ^= 1

        ans = min(pre[n] & 0xffffffff, pre[n] >> 32)

        if n % 2 == 0:
            return ans

        for i in range(1, n):
            s0 = (pre[n] >> 32) - (pre[i] >> 32)
            s1 = (pre[n] & 0xffffffff) - (pre[i] & 0xffffffff)

            p0 = pre[i] >> 32
            p1 = pre[i] & 0xffffffff

            ans = min(ans, min(s0 + p1, s1 + p0))

        return ans