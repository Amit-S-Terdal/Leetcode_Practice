# 1081. Smallest Subsequence of Distinct Characters

# Given a string s, return the lexicographically smallest subsequence of s that contains all the distinct characters of s exactly once.

 

# Example 1:

# Input: s = "bcabc"
# Output: "abc"
# Example 2:

# Input: s = "cbacdcbc"
# Output: "acdb"
 

# Constraints:

# 1 <= s.length <= 1000
# s consists of lowercase English letters.

# Solution:


class Solution(object):
    def smallestSubsequence(self, s):
        """
        :type s: str
        :rtype: str
        """
        freq = [0] * 26
        for c in s:
            freq[ord(c) - ord('a')] += 1

        stack = []
        seen = [False] * 26

        for c in s:
            idx = ord(c) - ord('a')
            freq[idx] -= 1

            if seen[idx]:
                continue

            while stack and stack[-1] > c and freq[ord(stack[-1]) - ord('a')] > 0:
                seen[ord(stack.pop()) - ord('a')] = False

            stack.append(c)
            seen[idx] = True

        return "".join(stack)