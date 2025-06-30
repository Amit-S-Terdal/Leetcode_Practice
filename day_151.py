# 2014. Longest Subsequence Repeated k Times

# You are given a string s of length n, and an integer k. You are tasked to find the longest subsequence repeated k times in string s.

# A subsequence is a string that can be derived from another string by deleting some or no characters without changing the order of the remaining characters.

# A subsequence seq is repeated k times in the string s if seq * k is a subsequence of s, where seq * k represents a string constructed by concatenating seq k times.

# For example, "bba" is repeated 2 times in the string "bababcba", because the string "bbabba", constructed by concatenating "bba" 2 times, is a subsequence of the string "bababcba".
# Return the longest subsequence repeated k times in string s. If multiple such subsequences are found, return the lexicographically largest one. If there is no such subsequence, return an empty string.

 

# Example 1:

# example 1
# Input: s = "letsleetcode", k = 2
# Output: "let"
# Explanation: There are two longest subsequences repeated 2 times: "let" and "ete".
# "let" is the lexicographically largest one.
# Example 2:

# Input: s = "bb", k = 2
# Output: "b"
# Explanation: The longest subsequence repeated 2 times is "b".
# Example 3:

# Input: s = "ab", k = 2
# Output: ""
# Explanation: There is no subsequence repeated 2 times. Empty string is returned.
 

# Constraints:

# n == s.length
# 2 <= n, k <= 2000
# 2 <= n < k * 8
# s consists of lowercase English letters.


# Solution:

class Solution(object):
    def longestSubsequenceRepeatedK(self, s, k):
        """
        :type s: str
        :type k: int
        :rtype: str
        """
        from collections import Counter

        self.n = len(s)
        self.maxLen = 0
        self.ans = ""

        # Frequency count
        freq = Counter(s)
        valid_chars = [c for c in freq if freq[c] >= k]

        if not valid_chars:
            return ""

        # Filter s to only include characters that appear at least k times
        filtered = [c for c in s if c in valid_chars]
        s = ''.join(filtered)
        self.n = len(s)
        self.maxLen = self.n // k

        # Create sorted list of chars in reverse lex order
        self.chars = sorted(valid_chars, reverse=True)

        def repeatK(s, t, m, k):
            i = j = count = 0
            while i < self.n and count < k:
                if s[i] == t[j]:
                    j += 1
                    if j == m:
                        count += 1
                        j = 0
                i += 1
            return count == k

        def dfs(t):
            m = len(t)
            if m > self.maxLen:
                return
            if m > 0 and not repeatK(s, t, m, k):
                return
            if len(t) > len(self.ans) or (len(t) == len(self.ans) and t > self.ans):
                self.ans = t
            for c in self.chars:
                if freq[c] >= k:
                    freq[c] -= k
                    dfs(t + c)
                    freq[c] += k

        dfs("")
        return self.ans
