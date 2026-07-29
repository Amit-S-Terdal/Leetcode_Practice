# 3518. Smallest Palindromic Rearrangement II

# You are given a palindromic string s and an integer k.

# Return the k-th lexicographically smallest palindromic permutation of s. If there are fewer than k distinct palindromic permutations, return an empty string.

# Note: Different rearrangements that yield the same palindromic string are considered identical and are counted once.

 

# Example 1:

# Input: s = "abba", k = 2

# Output: "baab"

# Explanation:

# The two distinct palindromic rearrangements of "abba" are "abba" and "baab".
# Lexicographically, "abba" comes before "baab". Since k = 2, the output is "baab".
# Example 2:

# Input: s = "aa", k = 2

# Output: ""

# Explanation:

# There is only one palindromic rearrangement: "aa".
# The output is an empty string since k = 2 exceeds the number of possible rearrangements.
# Example 3:

# Input: s = "bacab", k = 1

# Output: "abcba"

# Explanation:

# The two distinct palindromic rearrangements of "bacab" are "abcba" and "bacab".
# Lexicographically, "abcba" comes before "bacab". Since k = 1, the output is "abcba".
 

# Constraints:

# 1 <= s.length <= 10^4
# s consists of lowercase English letters.
# s is guaranteed to be palindromic.
# 1 <= k <= 10^6



# Solution: 


class Solution(object):
    INF = 10**6 + 1
    N = 24
    C = [[0] * N for _ in range(N)]

    @classmethod
    def pascal(cls):
        if cls.C[0][0] == 1:
            return

        cls.C[0][0] = 1
        for i in range(1, cls.N):
            cls.C[i][0] = cls.C[i][i] = 1
            for j in range(1, i // 2 + 1):
                cls.C[i][j] = cls.C[i][i - j] = cls.C[i - 1][j - 1] + cls.C[i - 1][j]

    @classmethod
    def comb(cls, n, k):
        if n < cls.N:
            return cls.C[n][k]

        if 2 * k > n:
            k = n - k

        ans = 1
        for i in range(1, k + 1):
            ans = ans * (n - i + 1) // i
            if ans >= cls.INF:
                return cls.INF
        return ans

    @classmethod
    def perm(cls, freq, seen, sz):
        ans = 1
        while seen:
            bit = (seen & -seen).bit_length() - 1
            f = freq[bit]
            ans *= cls.comb(sz, f)
            if ans >= cls.INF:
                return cls.INF
            sz -= f
            seen &= seen - 1
        return ans

    def smallestPalindrome(self, s, k):
        self.pascal()

        n = len(s)
        half = n // 2

        freq = [0] * 26
        seen = 0

        for i in range(half):
            idx = ord(s[i]) - ord('a')
            freq[idx] += 1
            seen |= 1 << idx

        total = self.perm(freq, seen, half)
        if k > total:
            return ""

        left = []
        sz = half

        for _ in range(half):
            for c in range(26):
                if freq[c] == 0:
                    continue

                freq[c] -= 1
                sz -= 1

                cnt = self.perm(freq, seen, sz)

                if cnt >= k:
                    left.append(chr(ord('a') + c))
                    if freq[c] == 0:
                        seen &= ~(1 << c)
                    break
                else:
                    k -= cnt
                    freq[c] += 1
                    sz += 1

        ans = "".join(left)
        if n & 1:
            ans += s[half]
        ans += ans[:half][::-1]

        return ans
