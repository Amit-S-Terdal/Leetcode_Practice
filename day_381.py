# 67. Add Binary

# Given two binary strings a and b, return their sum as a binary string.

 

# Example 1:

# Input: a = "11", b = "1"
# Output: "100"
# Example 2:

# Input: a = "1010", b = "1011"
# Output: "10101"
 

# Constraints:

# 1 <= a.length, b.length <= 10^4
# a and b consist only of '0' or '1' characters.
# Each string does not contain leading zeros except for the zero itself.



# Solution: 



class Solution(object):
    def addBinary(self, a, b):
        i = len(a) - 1
        j = len(b) - 1
        carry = 0
        result = []

        while i >= 0 or j >= 0 or carry:
            bitA = ord(a[i]) - ord('0') if i >= 0 else 0
            bitB = ord(b[j]) - ord('0') if j >= 0 else 0

            total = bitA + bitB + carry
            result.append(str(total & 1))
            carry = total >> 1

            i -= 1
            j -= 1

        return ''.join(reversed(result))
