# 498. Diagonal Traverse

# Given an m x n matrix mat, return an array of all the elements of the array in a diagonal order.

 

# Example 1:


# Input: mat = [[1,2,3],[4,5,6],[7,8,9]]
# Output: [1,2,4,7,5,3,6,8,9]
# Example 2:

# Input: mat = [[1,2],[3,4]]
# Output: [1,2,3,4]
 

# Constraints:

# m == mat.length
# n == mat[i].length
# 1 <= m, n <= 10^4
# 1 <= m * n <= 10^4
# -10^5 <= mat[i][j] <= 10^5

# Solution: 


class Solution(object):
    def findDiagonalOrder(self, mat):
        """
        :type mat: List[List[int]]
        :rtype: List[int]
        """
        if not mat or not mat[0]:
            return []

        r, c = len(mat), len(mat[0])
        ans = [0] * (r * c)
        idx = 0
        flip = False

        for d in range(r + c - 1):
            if not flip:  # go NE
                i = min(d, r - 1)
                j = d - i
                while i >= 0 and j < c:
                    ans[idx] = mat[i][j]
                    idx += 1
                    i -= 1
                    j += 1
            else:  # go SW
                j = min(d, c - 1)
                i = d - j
                while j >= 0 and i < r:
                    ans[idx] = mat[i][j]
                    idx += 1
                    i += 1
                    j -= 1
            flip = not flip

        return ans
