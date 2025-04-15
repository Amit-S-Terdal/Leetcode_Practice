# 3272. Find the Count of Good Integers

# You are given two positive integers n and k.

# An integer x is called k-palindromic if:

# x is a palindrome.
# x is divisible by k.
# An integer is called good if its digits can be rearranged to form a k-palindromic integer. For example, for k = 2, 2020 can be rearranged to form the k-palindromic integer 2002, whereas 1010 cannot be rearranged to form a k-palindromic integer.

# Return the count of good integers containing n digits.

# Note that any integer must not have leading zeros, neither before nor after rearrangement. For example, 1010 cannot be rearranged to form 101.

 

# Example 1:

# Input: n = 3, k = 5

# Output: 27

# Explanation:

# Some of the good integers are:

# 551 because it can be rearranged to form 515.
# 525 because it is already k-palindromic.
# Example 2:

# Input: n = 1, k = 4

# Output: 2

# Explanation:

# The two good integers are 4 and 8.

# Example 3:

# Input: n = 5, k = 6

# Output: 2468

 

# Constraints:

# 1 <= n <= 10
# 1 <= k <= 9


# Solution: 



from math import factorial
from collections import Counter

class Solution(object):
    def countGoodIntegers(self, n, k):
        """
        :type n: int
        :type k: int
        :rtype: int
        """
        dict_set = set()
        base = 10 ** ((n - 1) // 2)
        skip = n % 2

        # Step 1: Generate all palindromic integers of length n
        for i in range(base, base * 10):
            left = str(i)
            if skip == 1:
                right = left[:-1][::-1]
            else:
                right = left[::-1]
            pal = left + right
            pal_num = int(pal)
            if pal_num % k == 0:
                # Step 2: Normalize digit string
                sorted_digits = ''.join(sorted(pal))
                dict_set.add(sorted_digits)

        # Step 3: Count permutations without leading zero
        def permutations_count(s):
            cnt = Counter(s)
            total = (n - cnt['0']) * factorial(n - 1)
            for val in cnt.values():
                total //= factorial(val)
            return total

        ans = 0
        for s in dict_set:
            ans += permutations_count(s)

        return ans
