# 1886. Determine Whether Matrix Can Be Obtained By Rotation

# Given two n x n binary matrices mat and target, return true if it is possible to make mat equal to target by rotating mat in 90-degree increments, or false otherwise.

 

# Example 1:


# Input: mat = [[0,1],[1,0]], target = [[1,0],[0,1]]
# Output: true
# Explanation: We can rotate mat 90 degrees clockwise to make mat equal target.
# Example 2:


# Input: mat = [[0,1],[1,1]], target = [[1,0],[0,1]]
# Output: false
# Explanation: It is impossible to make mat equal to target by rotating mat.
# Example 3:


# Input: mat = [[0,0,0],[0,1,0],[1,1,1]], target = [[1,1,1],[0,1,0],[0,0,0]]
# Output: true
# Explanation: We can rotate mat 90 degrees clockwise two times to make mat equal target.
 

# Constraints:

# n == mat.length == target.length
# n == mat[i].length == target[i].length
# 1 <= n <= 10
# mat[i][j] and target[i][j] are either 0 or 1.




# Solution:




class Solution(object):
    def findRotation(self, mat, target):
        """
        :type mat: List[List[int]]
        :type target: List[List[int]]
        :rtype: bool
        """
        n = len(mat)
        rot_mask = 15  # 1111 -> all 4 rotations possible

        for i in range(n):
            if rot_mask == 0:
                return False
            for j in range(n):
                if rot_mask == 0:
                    return False

                cur = 0

                if mat[i][j] == target[i][j]:
                    cur |= 1          # 0°
                if mat[j][n - 1 - i] == target[i][j]:
                    cur |= 2          # 90°
                if mat[n - 1 - i][n - 1 - j] == target[i][j]:
                    cur |= 4          # 180°
                if mat[n - 1 - j][i] == target[i][j]:
                    cur |= 8          # 270°

                rot_mask &= cur

        return rot_mask != 0