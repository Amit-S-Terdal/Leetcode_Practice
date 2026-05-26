# 3120. Count the Number of Special Characters I

# You are given a string word. A letter is called special if it appears both in lowercase and uppercase in word.

# Return the number of special letters in word.

 

# Example 1:

# Input: word = "aaAbcBC"

# Output: 3

# Explanation:

# The special characters in word are 'a', 'b', and 'c'.

# Example 2:

# Input: word = "abc"

# Output: 0

# Explanation:

# No character in word appears in uppercase.

# Example 3:

# Input: word = "abBCab"

# Output: 1

# Explanation:

# The only special character in word is 'b'.

 

# Constraints:

# 1 <= word.length <= 50
# word consists of only lowercase and uppercase English letters.


# Solution:



class Solution(object):
    def numberOfSpecialChars(self, word):
        """
        :type word: str
        :rtype: int
        """
        lower = [False] * 26
        upper = [False] * 26

        for c in word:
            if 'a' <= c <= 'z':
                lower[ord(c) - ord('a')] = True
            elif 'A' <= c <= 'Z':
                upper[ord(c) - ord('A')] = True

        count = 0

        for i in range(26):
            if lower[i] and upper[i]:
                count += 1

        return count