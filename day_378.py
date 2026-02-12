# 3713. Longest Balanced Substring I

# You are given a string s consisting of lowercase English letters.

# A substring of s is called balanced if all distinct characters in the substring appear the same number of times.

# Return the length of the longest balanced substring of s.

 

# Example 1:

# Input: s = "abbac"

# Output: 4

# Explanation:

# The longest balanced substring is "abba" because both distinct characters 'a' and 'b' each appear exactly 2 times.

# Example 2:

# Input: s = "zzabccy"

# Output: 4

# Explanation:

# The longest balanced substring is "zabc" because the distinct characters 'z', 'a', 'b', and 'c' each appear exactly 1 time.​​​​​​​

# Example 3:

# Input: s = "aba"

# Output: 2

# Explanation:

# ​​​​​​​One of the longest balanced substrings is "ab" because both distinct characters 'a' and 'b' each appear exactly 1 time. Another longest balanced substring is "ba".

 

# Constraints:

# 1 <= s.length <= 1000
# s consists of lowercase English letters.


# Solution: 



class Solution(object):
    def longestBalanced(self, s):
        """
        :type s: str
        :rtype: int
        """
        n = len(s)
        cnt = 1

        for l in range(n):
            freq = [0] * 26
            uniq = 0
            maxF = 0
            cntMax = 0

            for r in range(l, n):
                idx = ord(s[r]) - ord('a')
                freq[idx] += 1
                f = freq[idx]

                if f == 1:
                    uniq += 1

                if f > maxF:
                    maxF = f
                    cntMax = 1
                elif f == maxF:
                    cntMax += 1

                if uniq == cntMax:
                    cnt = max(cnt, r - l + 1)

        return cnt
