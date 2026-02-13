# 3714. Longest Balanced Substring II

# You are given a string s consisting only of the characters 'a', 'b', and 'c'.

# A substring of s is called balanced if all distinct characters in the substring appear the same number of times.

# Return the length of the longest balanced substring of s.

 

# Example 1:

# Input: s = "abbac"

# Output: 4

# Explanation:

# The longest balanced substring is "abba" because both distinct characters 'a' and 'b' each appear exactly 2 times.

# Example 2:

# Input: s = "aabcc"

# Output: 3

# Explanation:

# The longest balanced substring is "abc" because all distinct characters 'a', 'b' and 'c' each appear exactly 1 time.

# Example 3:

# Input: s = "aba"

# Output: 2

# Explanation:

# One of the longest balanced substrings is "ab" because both distinct characters 'a' and 'b' each appear exactly 1 time. Another longest balanced substring is "ba".

 

# Constraints:

# 1 <= s.length <= 10^5
# s contains only the characters 'a', 'b', and 'c'.




# Solution: 


class Solution(object):
    def longestBalanced(self, s):
        """
        :type s: str
        :rtype: int
        """
        n = len(s)
        if n == 0:
            return 0
        
        # Case 1: longest single repeated character
        ans = 1
        length = 1
        for i in range(1, n):
            if s[i] == s[i - 1]:
                length += 1
            else:
                ans = max(ans, length)
                length = 1
        ans = max(ans, length)

        # Prefix counters
        A = B = C = 0
        
        abc = {(0, 0): -1}  # (B-A, C-A)
        ab  = {(0, 0): -1}  # (A-B, C)
        bc  = {(0, 0): -1}  # (B-C, A)
        ca  = {(0, 0): -1}  # (C-A, B)

        for i, ch in enumerate(s):
            if ch == 'a':
                A += 1
            elif ch == 'b':
                B += 1
            else:
                C += 1

            # A = B = C
            key = (B - A, C - A)
            prev = abc.setdefault(key, i)
            if prev != i:
                ans = max(ans, i - prev)

            # A = B and no C
            key = (A - B, C)
            prev = ab.setdefault(key, i)
            if prev != i:
                ans = max(ans, i - prev)

            # B = C and no A
            key = (B - C, A)
            prev = bc.setdefault(key, i)
            if prev != i:
                ans = max(ans, i - prev)

            # C = A and no B
            key = (C - A, B)
            prev = ca.setdefault(key, i)
            if prev != i:
                ans = max(ans, i - prev)

        return ans
