# 3517. Smallest Palindromic Rearrangement I

# You are given a palindromic string s.

# Return the lexicographically smallest palindromic permutation of s.

 

# Example 1:

# Input: s = "z"

# Output: "z"

# Explanation:

# A string of only one character is already the lexicographically smallest palindrome.

# Example 2:

# Input: s = "babab"

# Output: "abbba"

# Explanation:

# Rearranging "babab" → "abbba" gives the smallest lexicographic palindrome.

# Example 3:

# Input: s = "daccad"

# Output: "acddca"

# Explanation:

# Rearranging "daccad" → "acddca" gives the smallest lexicographic palindrome.

 

# Constraints:

# 1 <= s.length <= 105
# s consists of lowercase English letters.
# s is guaranteed to be palindromic.



# Solution: 



class Solution(object):
    def smallestPalindrome(self, s):
        """
        :type s: str
        :rtype: str
        """
        n = len(s)
        n0 = n // 2

        freq = [0] * 26

        for i in range(n0):
            freq[ord(s[i]) - ord('a')] += 1

        ans = list(s)
        l = 0

        for x in range(26):
            f = freq[x]
            if f == 0:
                continue

            c = chr(ord('a') + x)

            for i in range(f):
                ans[l + i] = c
                ans[n - 1 - (l + i)] = c

            l += f

        return "".join(ans)