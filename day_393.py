# 3666. Minimum Operations to Equalize Binary String

# You are given a binary string s, and an integer k.

# In one operation, you must choose exactly k different indices and flip each '0' to '1' and each '1' to '0'.

# Return the minimum number of operations required to make all characters in the string equal to '1'. If it is not possible, return -1.

 

# Example 1:

# Input: s = "110", k = 1

# Output: 1

# Explanation:

# There is one '0' in s.
# Since k = 1, we can flip it directly in one operation.
# Example 2:

# Input: s = "0101", k = 3

# Output: 2

# Explanation:

# One optimal set of operations choosing k = 3 indices in each operation is:

# Operation 1: Flip indices [0, 1, 3]. s changes from "0101" to "1000".
# Operation 2: Flip indices [1, 2, 3]. s changes from "1000" to "1111".
# Thus, the minimum number of operations is 2.

# Example 3:

# Input: s = "101", k = 2

# Output: -1

# Explanation:

# Since k = 2 and s has only one '0', it is impossible to flip exactly k indices to make all '1'. Hence, the answer is -1.

 

# Constraints:

# 1 <= s.length <= 10^5
# s[i] is either '0' or '1'.
# 1 <= k <= s.length



# Solution: 


class Solution(object):
    def minOperations(self, s, k):
        """
        :type s: str
        :type k: int
        :rtype: int
        """
        slen = len(s)

        # Count zeros efficiently
        zero = 0
        for c in s:
            if c == '0':
                zero += 1

        if zero == 0:
            return 0

        if slen == k:
            return 1 if zero == slen else -1

        base = slen - k

        # Integer ceil division
        def ceil_div(a, b):
            return (a + b - 1) // b

        # Compute candidates
        odd = max(ceil_div(zero, k), ceil_div(slen - zero, base))
        if odd % 2 == 0:
            odd += 1

        even = max(ceil_div(zero, k), ceil_div(zero, base))
        if even % 2 == 1:
            even += 1

        res = float('inf')

        if (k % 2) == (zero % 2):
            res = min(res, odd)

        if zero % 2 == 0:
            res = min(res, even)

        return -1 if res == float('inf') else res