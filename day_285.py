# 474. Ones and Zeroes

# You are given an array of binary strings strs and two integers m and n.

# Return the size of the largest subset of strs such that there are at most m 0's and n 1's in the subset.

# A set x is a subset of a set y if all elements of x are also elements of y.

 

# Example 1:

# Input: strs = ["10","0001","111001","1","0"], m = 5, n = 3
# Output: 4
# Explanation: The largest subset with at most 5 0's and 3 1's is {"10", "0001", "1", "0"}, so the answer is 4.
# Other valid but smaller subsets include {"0001", "1"} and {"10", "1", "0"}.
# {"111001"} is an invalid subset because it contains 4 1's, greater than the maximum of 3.
# Example 2:

# Input: strs = ["10","0","1"], m = 1, n = 1
# Output: 2
# Explanation: The largest subset is {"0", "1"}, so the answer is 2.
 

# Constraints:

# 1 <= strs.length <= 600
# 1 <= strs[i].length <= 100
# strs[i] consists only of digits '0' and '1'.
# 1 <= m, n <= 100


# Solution: 



class Solution(object):
    def findMaxForm(self, strs, m, n):
        """
        :type strs: List[str]
        :type m: int
        :type n: int
        :rtype: int
        """
        dp = [[0] * (n + 1) for _ in range(m + 1)]
        counts = []
        for s in strs:
            c0 = s.count('0')
            counts.append((c0, len(s) - c0))
        
        # local variable bindings for speed
        dp_ref = dp
        for c0, c1 in counts:
            for i in range(m, c0 - 1, -1):
                dpi = dp_ref[i]
                dpi_prev = dp_ref[i - c0]
                for j in range(n, c1 - 1, -1):
                    val = dpi_prev[j - c1] + 1
                    if val > dpi[j]:
                        dpi[j] = val
        
        return dp_ref[m][n]

