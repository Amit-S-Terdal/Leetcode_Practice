# 1871. Jump Game VII

# You are given a 0-indexed binary string s and two integers minJump and maxJump. In the beginning, you are standing at index 0, which is equal to '0'. You can move from index i to index j if the following conditions are fulfilled:

# i + minJump <= j <= min(i + maxJump, s.length - 1), and
# s[j] == '0'.
# Return true if you can reach index s.length - 1 in s, or false otherwise.

 

# Example 1:

# Input: s = "011010", minJump = 2, maxJump = 3
# Output: true
# Explanation:
# In the first step, move from index 0 to index 3. 
# In the second step, move from index 3 to index 5.
# Example 2:

# Input: s = "01101110", minJump = 2, maxJump = 3
# Output: false
 

# Constraints:

# 2 <= s.length <= 10^5
# s[i] is either '0' or '1'.
# s[0] == '0'
# 1 <= minJump <= maxJump < s.length

# Solution: 



class Solution(object):
    def canReach(self, s, minJump, maxJump):
        """
        :type s: str
        :type minJump: int
        :type maxJump: int
        :rtype: bool
        """

        n = len(s)

        if s[n - 1] == '1':
            return False

        q = [0] * 100000
        front = 0
        back = 0

        q[back] = 0
        back += 1

        i = 0
        far = 0

        while front < back:

            i = q[front]
            front += 1

            j0 = max(far + 1, i + minJump)
            jM = min(i + maxJump, n - 1)

            for j in range(j0, jM + 1):
                if s[j] == '0':

                    if j == n - 1:
                        return True

                    q[back] = j
                    back += 1

            far = max(far, i + maxJump)

        return False