# 1358. Number of Substrings Containing All Three Characters

# Given a string s consisting only of characters a, b and c.

# Return the number of substrings containing at least one occurrence of all these characters a, b and c.

 

# Example 1:

# Input: s = "abcabc"
# Output: 10
# Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again). 
# Example 2:

# Input: s = "aaacb"
# Output: 3
# Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb". 
# Example 3:

# Input: s = "abc"
# Output: 1
 

# Constraints:

# 3 <= s.length <= 5 x 10^4
# s only consists of a, b or c characters.



# Solution: 



class Solution(object):
    def numberOfSubstrings(self, s):
        """
        :type s: str
        :rtype: int
        """
        n = len(s)
        cnt = 0
        letter = 0
        freq = [0] * 3

        l = 0

        for r in range(n):
            c = ord(s[r]) - ord('a')

            freq[c] += 1
            if freq[c] == 1:
                letter += 1

            while letter == 3:
                freq[ord(s[l]) - ord('a')] -= 1
                if freq[ord(s[l]) - ord('a')] == 0:
                    letter -= 1
                l += 1

            cnt += l

        return cnt