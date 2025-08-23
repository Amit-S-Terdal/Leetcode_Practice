# 1277. Count Square Submatrices with All Ones

# Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.

 

# Example 1:

# Input: matrix =
# [
#   [0,1,1,1],
#   [1,1,1,1],
#   [0,1,1,1]
# ]
# Output: 15
# Explanation: 
# There are 10 squares of side 1.
# There are 4 squares of side 2.
# There is  1 square of side 3.
# Total number of squares = 10 + 4 + 1 = 15.
# Example 2:

# Input: matrix = 
# [
#   [1,0,1],
#   [1,1,0],
#   [1,1,0]
# ]
# Output: 7
# Explanation: 
# There are 6 squares of side 1.  
# There is 1 square of side 2. 
# Total number of squares = 6 + 1 = 7.
 

# Constraints:

# 1 <= arr.length <= 300
# 1 <= arr[0].length <= 300
# 0 <= arr[i][j] <= 1


# Solution: 


class Solution(object):
    def countSquares(self, matrix):
        """
        :type matrix: List[List[int]]
        :rtype: int
        """
        if not matrix or not matrix[0]:
            return 0

        r, c = len(matrix), len(matrix[0])
        cnt = 0

        # count first row
        for j in range(c):
            if matrix[0][j] == 1:
                cnt += 1

        # count first column (excluding [0][0], already counted if 1)
        for i in range(1, r):
            if matrix[i][0] == 1:
                cnt += 1

        # DP over the rest; matrix[i][j] becomes the size of the largest
        # all-1s square ending at (i, j)
        for i in range(1, r):
            for j in range(1, c):
                if matrix[i][j] == 0:
                    continue
                matrix[i][j] = 1 + min(matrix[i-1][j], matrix[i-1][j-1], matrix[i][j-1])
                cnt += matrix[i][j]

        return cnt
