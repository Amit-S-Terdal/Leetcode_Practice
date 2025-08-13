# 869. Reordered Power of 2

# You are given an integer n. We reorder the digits in any order (including the original order) such that the leading digit is not zero.

# Return true if and only if we can do this so that the resulting number is a power of two.

 

# Example 1:

# Input: n = 1
# Output: true
# Example 2:

# Input: n = 10
# Output: false
 

# Constraints:

# 1 <= n <= 109

# Solution: 

class Solution(object):
    def reorderedPowerOf2(self, n):
        """
        :type n: int
        :rtype: bool
        """
        # Edge case from the original code
        if n == 10**9:
            return False

        def sig(x):
            cnt = [0] * 10
            while x:
                cnt[x % 10] += 1
                x //= 10
            return tuple(cnt)

        target = sig(n)
        ln = len(str(n))

        # Check powers of two with the same digit length
        for i in range(30):  # 2^29 = 536870912 (still 9 digits)
            x = 1 << i
            if len(str(x)) == ln and sig(x) == target:
                return True
        return False
