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


import bisect

class Solution(object):
    def numberOfSubstrings(self, s):
        """
        :type s: str
        :rtype: int
        """
        
        n=len(s)

        c1=[0]*(n+1)

        for i in range(1,n+1):
            c1[i]=c1[i-1] + (1 if s[i-1]=='1' else 0)

        total_ones=c1[-1]

        next_zero=[0]*n

        curr_zero_pos=n

        for i in range(n-1,-1,-1):
            next_zero[i]=curr_zero_pos
            if s[i]=='0':
                curr_zero_pos=i

        total=0

        for i in range(n):
            curr_zero_count= (1 if s[i]=='0' else 0)
            curr_zero_pos=i
            next_zero_pos=next_zero[i]

            for cz in range(201):

                if (total_ones-c1[i])<curr_zero_count**2:
                    break

                curr_valid_one_pos=bisect.bisect_left(
                    c1,
                    c1[i]+curr_zero_count**2,
                    curr_zero_pos+1,
                    next_zero_pos+1
                )

                total+=next_zero_pos+1-curr_valid_one_pos

                if next_zero_pos==n:
                    break
                curr_zero_pos=next_zero_pos
                next_zero_pos=next_zero[curr_zero_pos]
                curr_zero_count+=1


        return total