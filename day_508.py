# 1189. Maximum Number of Balloons

# Given a string text, you want to use the characters of text to form as many instances of the word "balloon" as possible.

# You can use each character in text at most once. Return the maximum number of instances that can be formed.

 

# Example 1:



# Input: text = "nlaebolko"
# Output: 1
# Example 2:



# Input: text = "loonbalxballpoon"
# Output: 2
# Example 3:

# Input: text = "leetcode"
# Output: 0
 

# Constraints:

# 1 <= text.length <= 10^4
# text consists of lower case English letters only.





# Solution:




class Solution(object):
    def maxNumberOfBalloons(self, text):
        """
        :type text: str
        :rtype: int
        """
        freq = [0] * 26

        for c in text:
            freq[ord(c) - ord('a')] += 1

        return min(
            freq[ord('b') - ord('a')],
            freq[ord('a') - ord('a')],
            freq[ord('l') - ord('a')] // 2,
            freq[ord('o') - ord('a')] // 2,
            freq[ord('n') - ord('a')]
        )