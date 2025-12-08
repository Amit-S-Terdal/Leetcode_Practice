
# 1925. Count Square Sum Triples

# A square triple (a,b,c) is a triple where a, b, and c are integers and a2 + b2 = c2.

# Given an integer n, return the number of square triples such that 1 <= a, b, c <= n.

 

# Example 1:

# Input: n = 5
# Output: 2
# Explanation: The square triples are (3,4,5) and (4,3,5).
# Example 2:

# Input: n = 10
# Output: 4
# Explanation: The square triples are (3,4,5), (4,3,5), (6,8,10), and (8,6,10).
 

# Constraints:

# 1 <= n <= 250


# Solution:


class Solution(object):
    def GCD(self, x, y):
        bx = (x & -x).bit_length() - 1  # Integer.numberOfTrailingZeros(x) equivalent in Python
        by = (y & -y).bit_length() - 1  # Integer.numberOfTrailingZeros(y) equivalent in Python
        bb = min(bx, by)
        x >>= bx
        y >>= by
        while y > 0:
            r = x % y
            x = y
            y = r
        return x << bb

    def countTriples(self, n):
        cnt = 0
        nsqrt = int(n ** 0.5)  # Same as Math.sqrt in Java
        for s in range(2, nsqrt + 1):
            for t in range(1 + (s & 1), s, 2):  # (s & 1) determines if s is odd/even
                if self.GCD(s, t) != 1:
                    continue
                c = s * s + t * t
                if c > n:
                    break
                kmax = n // c  # Equivalent to n/c in Java
                cnt += 2 * kmax  # Count both (a, b, c) and (b, a, c)
        return cnt
