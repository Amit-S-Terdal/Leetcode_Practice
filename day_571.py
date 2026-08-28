# 3734. Lexicographically Smallest Palindromic Permutation Greater Than Target

# You are given two strings s and target, each of length n, consisting of lowercase English letters.

# Return the lexicographically smallest string that is both a palindromic permutation of s and strictly greater than target. If no such permutation exists, return an empty string.

 

# Example 1:

# Input: s = "baba", target = "abba"

# Output: "baab"

# Explanation:

# The palindromic permutations of s (in lexicographical order) are "abba" and "baab".
# The lexicographically smallest permutation that is strictly greater than target is "baab".
# Example 2:

# Input: s = "baba", target = "bbaa"

# Output: ""

# Explanation:

# The palindromic permutations of s (in lexicographical order) are "abba" and "baab".
# None of them is lexicographically strictly greater than target. Therefore, the answer is "".
# Example 3:

# Input: s = "abc", target = "abb"

# Output: ""

# Explanation:

# s has no palindromic permutations. Therefore, the answer is "".

# Example 4:

# Input: s = "aac", target = "abb"

# Output: "aca"

# Explanation:

# The only palindromic permutation of s is "aca".
# "aca" is strictly greater than target. Therefore, the answer is "aca".
 

# Constraints:

# 1 <= n == s.length == target.length <= 300
# s and target consist of only lowercase English letters.



# Solution: 




class Solution(object):

    def lexPalindromicPermutation(self, s, target):
        """
        :type s: str
        :type target: str
        :rtype: str
        """
        n = len(s)
        half = n >> 1

        freq = [0] * 26
        parity = 0
        hasC = 0

        for c in s:
            idx = ord(c) - ord('a')
            freq[idx] += 1
            hasC |= 1 << idx
            parity ^= 1 << idx

        # More than one character has an odd frequency
        if bin(parity).count("1") > 1:
            return ""

        oddIdx = -1
        if n & 1:
            # Equivalent to countr_zero(parity)
            oddIdx = 0
            while ((parity >> oddIdx) & 1) == 0:
                oddIdx += 1

        # Convert frequencies to counts for the left half
        mask = hasC

        while mask:
            # Equivalent to countr_zero(mask)
            i = 0
            while ((mask >> i) & 1) == 0:
                i += 1

            freq[i] >>= 1

            if freq[i] == 0:
                hasC &= ~(1 << i)

            # Remove lowest set bit
            mask &= mask - 1

        dp = [-1] * half

        def revLeftIsGrR(t):
            for i in range(half):
                left = t[half - 1 - i]
                right = t[half + (n & 1) + i]

                if left > right:
                    return True
                if left < right:
                    return False

            return False

        def canPlace(i, target, hasC):
            if dp[i] != -1:
                return dp[i] == 1

            c = target[i]
            ci = ord(c) - ord('a')

            if freq[ci] == 0:
                dp[i] = 0
                return False

            # Use one occurrence of c
            freq[ci] -= 1

            hasC1 = hasC

            if freq[ci] == 0:
                hasC1 &= ~(1 << ci)

            ans = False

            if i == half - 1:

                if n & 1:
                    middle = chr(ord('a') + oddIdx)

                    if middle != target[half]:
                        ans = middle > target[half]
                    else:
                        ans = revLeftIsGrR(target)

                else:
                    ans = revLeftIsGrR(target)

            else:
                nxt = ord(target[i + 1]) - ord('a')

                # Some available character is greater than
                # target[i + 1]
                if (hasC1 >> (nxt + 1)) != 0:
                    ans = True

                # target[i + 1] isn't available
                elif ((hasC1 >> nxt) & 1) == 0:
                    ans = False

                else:
                    ans = canPlace(i + 1, target, hasC1)

            # Backtrack
            freq[ci] += 1

            dp[i] = 1 if ans else 0
            return ans

        def build_palindrome(ans):
            pal = ans

            if n & 1:
                pal += chr(ord('a') + oddIdx)

            pal += ans[::-1]

            return pal

        ans = ""

        for i in range(half):
            t_i = ord(target[i]) - ord('a')

            # Try to match target[i]
            if freq[t_i] > 0 and canPlace(i, target, hasC):
                ans += target[i]

                freq[t_i] -= 1

                if freq[t_i] == 0:
                    hasC &= ~(1 << t_i)

            else:
                # Find minimum available character
                # strictly greater than target[i]
                higher = hasC >> (t_i + 1)

                if higher == 0:
                    return ""

                # Equivalent to countr_zero(higher)
                low = 0
                while ((higher >> low) & 1) == 0:
                    low += 1

                choice = low + t_i + 1

                freq[choice] -= 1

                if freq[choice] == 0:
                    hasC &= ~(1 << choice)

                ans += chr(ord('a') + choice)

                # Fill remaining positions with the smallest
                # available characters
                for j in range(i + 1, half):
                    if hasC == 0:
                        return ""

                    low = 0
                    while ((hasC >> low) & 1) == 0:
                        low += 1

                    ans += chr(ord('a') + low)

                    freq[low] -= 1

                    if freq[low] == 0:
                        hasC &= ~(1 << low)

                return build_palindrome(ans)

        # Entire left half matches target
        pal = build_palindrome(ans)

        return pal if pal > target else ""