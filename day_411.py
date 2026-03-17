
# 1727. Largest Submatrix With Rearrangements


# You are given a binary matrix matrix of size m x n, and you are allowed to rearrange the columns of the matrix in any order.

# Return the area of the largest submatrix within matrix where every element of the submatrix is 1 after reordering the columns optimally.

 

# Example 1:


# Input: matrix = [[0,0,1],[1,1,1],[1,0,1]]
# Output: 4
# Explanation: You can rearrange the columns as shown above.
# The largest submatrix of 1s, in bold, has an area of 4.
# Example 2:


# Input: matrix = [[1,0,1,0,1]]
# Output: 3
# Explanation: You can rearrange the columns as shown above.
# The largest submatrix of 1s, in bold, has an area of 3.
# Example 3:

# Input: matrix = [[1,1,0],[1,0,1]]
# Output: 2
# Explanation: Notice that you must rearrange entire columns, and there is no way to make a submatrix of 1s larger than an area of 2.
 

# Constraints:

# m == matrix.length
# n == matrix[i].length
# 1 <= m * n <= 105
# matrix[i][j] is either 0 or 1.




# Solution:



class Solution(object):
    def largestSubmatrix(self, matrix):
        """
        :type matrix: List[List[int]]
        :rtype: int
        """
        m = len(matrix)
        n = len(matrix[0])

        area = matrix[0].count(1)

        if m == 1:
            return area

        if n == 1:
            for i in range(1, m):
                x = matrix[i][0]
                x += (-x & matrix[i-1][0])
                matrix[i][0] = x
                area = max(area, x)
            return area

        freq = [0] * 100000

        for i in range(1, m):

            for j in range(n):
                x = matrix[i][j]
                x += (-x & matrix[i-1][j])
                matrix[i][j] = x

            row = matrix[i]
            minH = i + 1
            maxH = 0

            for x in row:
                minH = min(minH, x)
                maxH = max(maxH, x)

            for k in range(maxH - minH + 1):
                freq[k] = 0

            for x in row:
                freq[x - minH] += 1

            acc = 0
            x = maxH - minH

            while acc < n and x >= 0:
                if freq[x] > 0:
                    acc += freq[x]
                    area = max(area, acc * (x + minH))
                x -= 1

        return area