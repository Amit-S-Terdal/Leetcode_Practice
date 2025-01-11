# 1400. Construct K Palindrome Strings

# Given a string s and an integer k, return true if you can use all the characters in s to construct k palindrome strings or false otherwise.

# Example 1:

# Input: s = "annabelle", k = 2
# Output: true
# Explanation: You can construct two palindromes using all characters in s.
# Some possible constructions "anna" + "elble", "anbna" + "elle", "anellena" + "b"
# Example 2:

# Input: s = "leetcode", k = 3
# Output: false
# Explanation: It is impossible to construct 3 palindromes using all the characters of s.
# Example 3:

# Input: s = "true", k = 4
# Output: true
# Explanation: The only possible solution is to put each character in a separate string.
 

# Constraints:

# 1 <= s.length <= 105
# s consists of lowercase English letters.
# 1 <= k <= 105

# SOLUTION:

from collections import Counter

class Solution(object):
    def canConstruct(self, s, k):
        """
        :type s: str
        :type k: int
        :rtype: bool
        """
        # If k is greater than the length of the string, it's impossible to form k palindromes
        if k > len(s):
            return False

        # If k is equal to the length of the string, it's always possible
        if k == len(s):
            return True

        # Count the frequency of each character
        freq = Counter(s)

        # Count the number of characters with odd frequencies
        odd_count = sum(count % 2 for count in freq.values())

        # At most `odd_count` palindromes are needed for odd characters
        return odd_count <= k