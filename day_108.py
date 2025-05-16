# 3335. Total Characters in String After Transformations I

# You are given a string s and an integer t, representing the number of transformations to perform. In one transformation, every character in s is replaced according to the following rules:

# If the character is 'z', replace it with the string "ab".
# Otherwise, replace it with the next character in the alphabet. For example, 'a' is replaced with 'b', 'b' is replaced with 'c', and so on.
# Return the length of the resulting string after exactly t transformations.

# Since the answer may be very large, return it modulo 109 + 7.

 

# Example 1:

# Input: s = "abcyy", t = 2

# Output: 7

# Explanation:

# First Transformation (t = 1):
# 'a' becomes 'b'
# 'b' becomes 'c'
# 'c' becomes 'd'
# 'y' becomes 'z'
# 'y' becomes 'z'
# String after the first transformation: "bcdzz"
# Second Transformation (t = 2):
# 'b' becomes 'c'
# 'c' becomes 'd'
# 'd' becomes 'e'
# 'z' becomes "ab"
# 'z' becomes "ab"
# String after the second transformation: "cdeabab"
# Final Length of the string: The string is "cdeabab", which has 7 characters.
# Example 2:

# Input: s = "azbk", t = 1

# Output: 5

# Explanation:

# First Transformation (t = 1):
# 'a' becomes 'b'
# 'z' becomes "ab"
# 'b' becomes 'c'
# 'k' becomes 'l'
# String after the first transformation: "babcl"
# Final Length of the string: The string is "babcl", which has 5 characters.
 

# Constraints:

# 1 <= s.length <= 10^5
# s consists only of lowercase English letters.
# 1 <= t <= 10^5

# Solution:


class Solution(object):
    def lengthAfterTransformations(self, s, t):
        """
        :type s: str
        :type t: int
        :rtype: int
        """
        MOD = 10**9 + 7
        cnt = [0] * 26

        # Count initial frequency of each character
        for ch in s:
            cnt[ord(ch) - ord('a')] += 1

        # Perform t transformation rounds
        for _ in range(t):
            nxt = [0] * 26

            # 'z' -> "ab"
            nxt[0] = cnt[25]  # 'a'
            nxt[1] = cnt[25]  # 'b'

            # Other characters shift to next
            for i in range(25):
                nxt[i + 1] = (nxt[i + 1] + cnt[i]) % MOD

            cnt = nxt

        # Sum of all counts is the final string length
        return sum(cnt) % MOD
