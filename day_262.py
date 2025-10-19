# 1625. Lexicographically Smallest String After Applying Operations

# You are given a string s of even length consisting of digits from 0 to 9, and two integers a and b.

# You can apply either of the following two operations any number of times and in any order on s:

# Add a to all odd indices of s (0-indexed). Digits post 9 are cycled back to 0. For example, if s = "3456" and a = 5, s becomes "3951".
# Rotate s to the right by b positions. For example, if s = "3456" and b = 1, s becomes "6345".
# Return the lexicographically smallest string you can obtain by applying the above operations any number of times on s.

# A string a is lexicographically smaller than a string b (of the same length) if in the first position where a and b differ, string a has a letter that appears earlier in the alphabet than the corresponding letter in b. For example, "0158" is lexicographically smaller than "0190" because the first position they differ is at the third letter, and '5' comes before '9'.

 

# Example 1:

# Input: s = "5525", a = 9, b = 2
# Output: "2050"
# Explanation: We can apply the following operations:
# Start:  "5525"
# Rotate: "2555"
# Add:    "2454"
# Add:    "2353"
# Rotate: "5323"
# Add:    "5222"
# Add:    "5121"
# Rotate: "2151"
# Add:    "2050"​​​​​
# There is no way to obtain a string that is lexicographically smaller than "2050".
# Example 2:

# Input: s = "74", a = 5, b = 1
# Output: "24"
# Explanation: We can apply the following operations:
# Start:  "74"
# Rotate: "47"
# ​​​​​​​Add:    "42"
# ​​​​​​​Rotate: "24"​​​​​​​​​​​​
# There is no way to obtain a string that is lexicographically smaller than "24".
# Example 3:

# Input: s = "0011", a = 4, b = 2
# Output: "0011"
# Explanation: There are no sequence of operations that will give us a lexicographically smaller string than "0011".
 

# Constraints:

# 2 <= s.length <= 100
# s.length is even.
# s consists of digits from 0 to 9 only.
# 1 <= a <= 9
# 1 <= b <= s.length - 1



# Solution: 


class Solution(object):
    def findLexSmallestString(self, s, a, b):
        """
        :type s: str
        :type a: int
        :type b: int
        :rtype: str
        """

        def gcd(x, y):
            while y:
                x, y = y, x % y
            return x

        def add_odd(s_list, a):
            for i in range(1, len(s_list), 2):
                s_list[i] = str((int(s_list[i]) + a) % 10)

        def min_add_odd(s_list, a, A):
            d = int(s_list[1])
            if d < a:
                return
            steps = (10 - d // a) * a % 10
            add_odd(s_list, steps)

        def add_even(s_list, a):
            for i in range(0, len(s_list), 2):
                s_list[i] = str((int(s_list[i]) + a) % 10)

        def min_add_even(s_list, a, A):
            d = int(s_list[0])
            if d < a:
                return
            steps = (10 - d // a) * a % 10
            add_even(s_list, steps)

        def rotate(s_list, b):
            n = len(s_list)
            b = b % n
            return s_list[-b:] + s_list[:-b]

        n = len(s)
        s_list = list(s)
        a0 = gcd(a, 10)
        b0 = gcd(n, b)
        A = 10 // a0
        B = n // b0

        best = ['9'] * n

        if b0 % 2 == 0:
            for _ in range(B):
                s_list = rotate(s_list, b0)
                temp = s_list[:]
                min_add_odd(temp, a0, A)
                if temp < best:
                    best = temp[:]
        else:
            for _ in range(B):
                s_list = rotate(s_list, b0)
                temp = s_list[:]
                min_add_odd(temp, a0, A)
                min_add_even(temp, a0, A)
                if temp < best:
                    best = temp[:]

        return ''.join(best)
