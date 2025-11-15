# 3234. Count the Number of Substrings With Dominant Ones

# You are given a binary string s.

# Return the number of substrings with dominant ones.

# A string has dominant ones if the number of ones in the string is greater than or equal to the square of the number of zeros in the string.

 

# Example 1:

# Input: s = "00011"

# Output: 5

# Explanation:

# The substrings with dominant ones are shown in the table below.

# i	j	s[i..j]	Number of Zeros	Number of Ones
# 3	3	1	0	1
# 4	4	1	0	1
# 2	3	01	1	1
# 3	4	11	0	2
# 2	4	011	1	2
# Example 2:

# Input: s = "101101"

# Output: 16

# Explanation:

# The substrings with non-dominant ones are shown in the table below.

# Since there are 21 substrings total and 5 of them have non-dominant ones, it follows that there are 16 substrings with dominant ones.

# i	j	s[i..j]	Number of Zeros	Number of Ones
# 1	1	0	1	0
# 4	4	0	1	0
# 1	4	0110	2	2
# 0	4	10110	2	3
# 1	5	01101	2	3
 

# Constraints:

# 1 <= s.length <= 4 * 10^4
# s consists only of characters '0' and '1'.


# Solution: 


class Solution(object):
    def numberOfSubstrings(self, s):
        """
        :type s: str
        :rtype: int
        """
        n = len(s)
        Zmax = int(( (1 + 4*n)**0.5 - 1 ) // 2)

        # p0 will store all positions of '0', plus a sentinel n
        p0 = []
        for i, ch in enumerate(s):
            if ch == '0':
                p0.append(i)
        p0.append(n)

        ans = 0
        front = 0
        back = len(p0)

        for l in range(n):
            # advance front past left boundary
            while front < back and p0[front] < l:
                front += 1

            prev = l

            # iterate all possible zero-count levels
            for p in range(front, back):
                cnt0 = p - front
                if cnt0 > Zmax:
                    break

                r = p0[p]
                minLen = max(prev - l + 1, cnt0 * (cnt0 + 1))

                ans += max(0, (r - l) - minLen + 1)

                prev = r

        return ans
