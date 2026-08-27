# 3720. Lexicographically Smallest Permutation Greater Than Target

# You are given two strings s and target, both having length n, consisting of lowercase English letters.

# Return the lexicographically smallest permutation of s that is strictly greater than target. If no permutation of s is lexicographically strictly greater than target, return an empty string.

# A string a is lexicographically strictly greater than a string b (of the same length) if in the first position where a and b differ, string a has a letter that appears later in the alphabet than the corresponding letter in b.

 

# Example 1:

# Input: s = "abc", target = "bba"

# Output: "bca"

# Explanation:

# The permutations of s (in lexicographical order) are "abc", "acb", "bac", "bca", "cab", and "cba".
# The lexicographically smallest permutation that is strictly greater than target is "bca".
# Example 2:

# Input: s = "leet", target = "code"

# Output: "eelt"

# Explanation:

# The permutations of s (in lexicographical order) are "eelt", "eetl", "elet", "elte", "etel", "etle", "leet", "lete", "ltee", "teel", "tele", and "tlee".
# The lexicographically smallest permutation that is strictly greater than target is "eelt".
# Example 3:

# Input: s = "baba", target = "bbaa"

# Output: ""

# Explanation:

# The permutations of s (in lexicographical order) are "aabb", "abab", "abba", "baab", "baba", and "bbaa".
# None of them is lexicographically strictly greater than target. Therefore, the answer is "".
 

# Constraints:

# 1 <= s.length == target.length <= 300
# s and target consist of only lowercase English letters.


# Solution: 



class Solution(object):

    def lexGreaterPermutation(self, s, target):
        """
        :type s: str
        :type target: str
        :rtype: str
        """
        n = len(s)

        freq = [0] * 26
        hasC = 0

        for c in s:
            idx = ord(c) - ord('a')
            freq[idx] += 1
            hasC |= (1 << idx)

        diffPos = -1

        freq0 = freq[:]
        hasC0 = hasC

        for i in range(n):
            largestC = hasC0.bit_length() - 1
            idx = ord(target[i]) - ord('a')

            if largestC < idx:
                break

            if largestC > idx:
                diffPos = i

            if freq0[idx] > 0:
                freq0[idx] -= 1

                if freq0[idx] == 0:
                    hasC0 &= ~(1 << idx)
            else:
                break

        if diffPos == -1:
            return ""

        # Rebuild s up to diffPos
        result = list(s)

        for j in range(diffPos):
            idx = ord(target[j]) - ord('a')
            result[j] = target[j]

            freq[idx] -= 1

            if freq[idx] == 0:
                hasC &= ~(1 << idx)

        # Increase character at diffPos
        shift = ord(target[diffPos]) - ord('a') + 1
        higher = hasC >> shift

        if higher == 0:
            return ""

        # Find smallest character greater than target[diffPos]
        idx = (higher & -higher).bit_length() - 1 + shift

        result[diffPos] = chr(ord('a') + idx)

        freq[idx] -= 1

        if freq[idx] == 0:
            hasC &= ~(1 << idx)

        # Fill remaining characters in sorted order
        for j in range(diffPos + 1, n):
            if hasC == 0:
                return ""

            idx = (hasC & -hasC).bit_length() - 1
            result[j] = chr(ord('a') + idx)

            freq[idx] -= 1

            if freq[idx] == 0:
                hasC &= ~(1 << idx)

        return ''.join(result)