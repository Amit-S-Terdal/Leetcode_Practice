# 3614. Process String with Special Operations II

# You are given a string s consisting of lowercase English letters and the special characters: '*', '#', and '%'.

# You are also given an integer k.

# Build a new string result by processing s according to the following rules from left to right:

# If the letter is a lowercase English letter append it to result.
# A '*' removes the last character from result, if it exists.
# A '#' duplicates the current result and appends it to itself.
# A '%' reverses the current result.
# Return the kth character of the final string result. If k is out of the bounds of result, return '.'.

 

# Example 1:

# Input: s = "a#b%*", k = 1

# Output: "a"

# Explanation:

# i	s[i]	Operation	Current result
# 0	'a'	Append 'a'	"a"
# 1	'#'	Duplicate result	"aa"
# 2	'b'	Append 'b'	"aab"
# 3	'%'	Reverse result	"baa"
# 4	'*'	Remove the last character	"ba"
# The final result is "ba". The character at index k = 1 is 'a'.

# Example 2:

# Input: s = "cd%#*#", k = 3

# Output: "d"

# Explanation:

# i	s[i]	Operation	Current result
# 0	'c'	Append 'c'	"c"
# 1	'd'	Append 'd'	"cd"
# 2	'%'	Reverse result	"dc"
# 3	'#'	Duplicate result	"dcdc"
# 4	'*'	Remove the last character	"dcd"
# 5	'#'	Duplicate result	"dcddcd"
# The final result is "dcddcd". The character at index k = 3 is 'd'.

# Example 3:

# Input: s = "z*#", k = 0

# Output: "."

# Explanation:

# i	s[i]	Operation	Current result
# 0	'z'	Append 'z'	"z"
# 1	'*'	Remove the last character	""
# 2	'#'	Duplicate the string	""
# The final result is "". Since index k = 0 is out of bounds, the output is '.'.

 

# Constraints:

# 1 <= s.length <= 10^5
# s consists of only lowercase English letters and special characters '*', '#', and '%'.
# 0 <= k <= 10^15
# The length of result after processing s will not exceed 10^15.



# Solution: 




class Solution(object):
    def processStr(self, s, k):
        """
        :type s: str
        :type k: int
        :rtype: str
        """
        n = len(s)
        length = [0] * n
        L = 0

        for i, c in enumerate(s):
            if c >= 'a':
                L += 1
            elif c == '*' and L:
                L -= 1

            if c == '#':
                L <<= 1

            length[i] = L

        if k >= L:
            return '.'

        for i in range(n - 1, -1, -1):
            c = s[i]
            prevL = length[i] >> 1 if c == '#' else length[i]

            if c == '%':
                k = prevL - 1 - k
            elif c == '#' and k >= prevL:
                k -= prevL

            if c >= 'a' and k == prevL - 1:
                return c

        return '.'