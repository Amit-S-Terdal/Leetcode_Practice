# 85. Maximal Rectangle

# Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

 

# Example 1:


# Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
# Output: 6
# Explanation: The maximal rectangle is shown in the above picture.
# Example 2:

# Input: matrix = [["0"]]
# Output: 0
# Example 3:

# Input: matrix = [["1"]]
# Output: 1
 

# Constraints:

# rows == matrix.length
# cols == matrix[i].length
# 1 <= rows, cols <= 200
# matrix[i][j] is '0' or '1'.


# Solution: 



class Solution(object):
    def maximalRectangle(self, matrix):
        """
        :type matrix: List[List[str]]
        :rtype: int
        """
        r, c = len(matrix), len(matrix[0])

        if r == 1 and c == 1:
            return 1 if matrix[0][0] == '1' else 0

        h = [0] * (c + 1)
        maxArea = 0

        for row in matrix:
            st = [-1]
            row.append('0')  # sentinel

            for j in range(len(row)):
                if row[j] == '1':
                    h[j] += 1
                else:
                    h[j] = 0

                while len(st) > 1 and (j == c or h[j] < h[st[-1]]):
                    m = st.pop()
                    w = j - st[-1] - 1
                    area = h[m] * w
                    if area > maxArea:
                        maxArea = area

                st.append(j)

            row.pop()  # restore row

        return maxArea
