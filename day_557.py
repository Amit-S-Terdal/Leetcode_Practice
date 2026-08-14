# 3090. Maximum Length Substring With Two Occurrences

# Given a string s, return the maximum length of a substring such that it contains at most two occurrences of each character.
 

# Example 1:

# Input: s = "bcbbbcba"

# Output: 4

# Explanation:

# The following substring has a length of 4 and contains at most two occurrences of each character: "bcbbbcba".
# Example 2:

# Input: s = "aaaa"

# Output: 2

# Explanation:

# The following substring has a length of 2 and contains at most two occurrences of each character: "aaaa".
 

# Constraints:

# 2 <= s.length <= 100
# s consists only of lowercase English letters.




# Solution: 



class Solution(object):
    def maximumLengthSubstring(self, s):
        freq = [0] * 26
        l = 0
        n = len(s)
        length = 0

        for r in range(n):
            x = ord(s[r]) - ord('a')
            freq[x] += 1

            while l < r and freq[x] > 2:
                freq[ord(s[l]) - ord('a')] -= 1
                l += 1

            length = max(length, r - l + 1)

        return length