# 3302. Find the Lexicographically Smallest Valid Sequence

# You are given two strings word1 and word2.

# A string x is called almost equal to y if you can change at most one character in x to make it identical to y.

# A sequence of indices seq is called valid if:

# The indices are sorted in ascending order.
# Concatenating the characters at these indices in word1 in the same order results in a string that is almost equal to word2.
# Return an array of size word2.length representing the lexicographically smallest valid sequence of indices. If no such sequence of indices exists, return an empty array.

# Note that the answer must represent the lexicographically smallest array, not the corresponding string formed by those indices.

 

# Example 1:

# Input: word1 = "vbcca", word2 = "abc"

# Output: [0,1,2]

# Explanation:

# The lexicographically smallest valid sequence of indices is [0, 1, 2]:

# Change word1[0] to 'a'.
# word1[1] is already 'b'.
# word1[2] is already 'c'.
# Example 2:

# Input: word1 = "bacdc", word2 = "abc"

# Output: [1,2,4]

# Explanation:

# The lexicographically smallest valid sequence of indices is [1, 2, 4]:

# word1[1] is already 'a'.
# Change word1[2] to 'b'.
# word1[4] is already 'c'.
# Example 3:

# Input: word1 = "aaaaaa", word2 = "aaabc"

# Output: []

# Explanation:

# There is no valid sequence of indices.

# Example 4:

# Input: word1 = "abc", word2 = "ab"

# Output: [0,1]

 

# Constraints:

# 1 <= word2.length < word1.length <= 3 * 10^5
# word1 and word2 consist only of lowercase English letters.


# Solution: 




class Solution(object):
    def validSequence(self, word1, word2):
        """
        :type word1: str
        :type word2: str
        :rtype: List[int]
        """
        n1 = len(word1)
        n2 = len(word2)

        last = [-1] * (n2 + 1)
        last[n2] = n1

        j = n1 - 1

        # Find the latest possible positions for word2 suffixes
        for i in range(n2 - 1, -1, -1):
            while j >= 0 and word1[j] != word2[i]:
                j -= 1

            if j < 0:
                break

            last[i] = j
            j -= 1

        ans = [0] * n2
        changed = False
        j = 0

        # Greedily construct the answer
        for i in range(n1):
            if j == n2:
                break

            same = word1[i] == word2[j]
            can_change = not changed and i < last[j + 1]

            if same or can_change:
                ans[j] = i
                j += 1

                if not same:
                    changed = True

        return ans if j == n2 else []