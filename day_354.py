# 1292. Maximum Side Length of a Square with Sum Less than or Equal to Threshold

# Given a m x n matrix mat and an integer threshold, return the maximum side-length of a square with a sum less than or equal to threshold or return 0 if there is no such square.

 

# Example 1:


# Input: mat = [[1,1,3,2,4,3,2],[1,1,3,2,4,3,2],[1,1,3,2,4,3,2]], threshold = 4
# Output: 2
# Explanation: The maximum side length of square with sum less than 4 is 2 as shown.
# Example 2:

# Input: mat = [[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2]], threshold = 1
# Output: 0
 

# Constraints:

# m == mat.length
# n == mat[i].length
# 1 <= m, n <= 300
# 0 <= mat[i][j] <= 10^4
# 0 <= threshold <= 10^5



# Solution:


class Solution(object):
    def maxSideLength(self, mat, threshold):
        """
        :type mat: List[List[int]]
        :type threshold: int
        :rtype: int
        """
        row, col = len(mat), len(mat[0])

        # build prefix sum in-place
        for j in range(1, col):
            mat[0][j] += mat[0][j - 1]

        for i in range(1, row):
            mat[i][0] += mat[i - 1][0]
            for j in range(1, col):
                mat[i][j] += mat[i - 1][j] + mat[i][j - 1] - mat[i - 1][j - 1]

        def sub_sum(r0, r1, c0, c1):
            return (mat[r1][c1]
                    - (mat[r0 - 1][c1] if r0 > 0 else 0)
                    - (mat[r1][c0 - 1] if c0 > 0 else 0)
                    + (mat[r0 - 1][c0 - 1] if r0 > 0 and c0 > 0 else 0))

        # sliding window along diagonal starting at (i0, j0)
        def slide_diag(i0, j0):
            MM = min(row - i0, col - j0)
            best = 0
            l = 0

            for r in range(MM):
                r0, r1 = i0 + l, i0 + r
                c0, c1 = j0 + l, j0 + r
                S = sub_sum(r0, r1, c0, c1)

                while l < r and S > threshold:
                    l += 1
                    r0 += 1
                    c0 += 1
                    S = sub_sum(r0, r1, c0, c1)

                if S <= threshold:
                    best = max(best, r - l + 1)
            return best

        ans = 0

        # start from first column
        for i in range(row):
            if row - i <= ans:
                break
            ans = max(ans, slide_diag(i, 0))

        # start from first row
        for j in range(1, col):
            if col - j <= ans:
                break
            ans = max(ans, slide_diag(0, j))

        return ans
