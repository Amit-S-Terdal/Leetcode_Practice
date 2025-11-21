# 1930. Unique Length-3 Palindromic Subsequences

# Given a string s, return the number of unique palindromes of length three that are a subsequence of s.

# Note that even if there are multiple ways to obtain the same subsequence, it is still only counted once.

# A palindrome is a string that reads the same forwards and backwards.

# A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.

# For example, "ace" is a subsequence of "abcde".
 

# Example 1:

# Input: s = "aabca"
# Output: 3
# Explanation: The 3 palindromic subsequences of length 3 are:
# - "aba" (subsequence of "aabca")
# - "aaa" (subsequence of "aabca")
# - "aca" (subsequence of "aabca")
# Example 2:

# Input: s = "adc"
# Output: 0
# Explanation: There are no palindromic subsequences of length 3 in "adc".
# Example 3:

# Input: s = "bbcbaba"
# Output: 4
# Explanation: The 4 palindromic subsequences of length 3 are:
# - "bbb" (subsequence of "bbcbaba")
# - "bcb" (subsequence of "bbcbaba")
# - "bab" (subsequence of "bbcbaba")
# - "aba" (subsequence of "bbcbaba")
 

# Constraints:

# 3 <= s.length <= 10^5
# s consists of only lowercase English letters.



# Solution:


class Solution(object):
    def countPalindromicSubsequence(self, s):
        """
        :type s: str
        :rtype: int
        """
        n = len(s)
        
        ll = [n] * 26      # first occurrence
        rr = [-1] * 26     # last occurrence
        
        # Compute first and last occurrence of each character
        for i, ch in enumerate(s):
            x = ord(ch) - ord('a')
            if ll[x] == n:
                ll[x] = i
            rr[x] = i
        
        count = 0
        
        # For each character as the outer endpoints
        for c in range(26):
            l, r = ll[c], rr[c]
            if l + 1 >= r:
                continue
            
            seen = set()
            # Count distinct characters between l and r
            for i in range(l + 1, r):
                seen.add(s[i])
            
            count += len(seen)
        
        return count
