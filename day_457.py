# 788. Rotated Digits

# An integer x is a good if after rotating each digit individually by 180 degrees, we get a valid number that is different from x. Each digit must be rotated - we cannot choose to leave it alone.

# A number is valid if each digit remains a digit after rotation. For example:

# 0, 1, and 8 rotate to themselves,
# 2 and 5 rotate to each other (in this case they are rotated in a different direction, in other words, 2 or 5 gets mirrored),
# 6 and 9 rotate to each other, and
# the rest of the numbers do not rotate to any other number and become invalid.
# Given an integer n, return the number of good integers in the range [1, n].

 

# Example 1:

# Input: n = 10
# Output: 4
# Explanation: There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
# Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.
# Example 2:

# Input: n = 1
# Output: 0
# Example 3:

# Input: n = 2
# Output: 1
 

# Constraints:

# 1 <= n <= 10^4




# Solution: 




class Solution(object):
    def rotatedDigits(self, n):
        """
        :type n: int
        :rtype: int
        """
        dp = [[[-1]*10 for _ in range(2)] for _ in range(7)]
        
        mask = (1<<2) | (1<<5) | (1<<6) | (1<<9)
        invalid = (1<<3) | (1<<4) | (1<<7)
        
        def toS(num):
            s = ['0'] * 6
            i = 5
            while num > 0:
                s[i] = chr(ord('0') + (num % 10))
                num //= 10
                i -= 1
            return ''.join(s)
        
        def f(i, tight, prev, s):
            if i == 6:
                return 1 if prev > 0 else 0
            
            if dp[i][tight][prev] != -1:
                return dp[i][tight][prev]
            
            cnt = 0
            lim = int(s[i]) if tight else 9
            
            for d in range(lim + 1):
                nxt_tight = 1 if (tight and d == lim) else 0
                
                if (1 << d) & invalid:
                    continue
                
                if (1 << d) & mask:
                    cnt += f(i + 1, nxt_tight, d, s)
                else:
                    cnt += f(i + 1, nxt_tight, prev, s)
            
            dp[i][tight][prev] = cnt
            return cnt
        
        s = toS(n)
        return f(0, 1, 0, s)