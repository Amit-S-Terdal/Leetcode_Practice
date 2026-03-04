# 1582. Special Positions in a Binary Matrix

# Given an m x n binary matrix mat, return the number of special positions in mat.

# A position (i, j) is called special if mat[i][j] == 1 and all other elements in row i and column j are 0 (rows and columns are 0-indexed).

 

# Example 1:


# Input: mat = [[1,0,0],[0,0,1],[1,0,0]]
# Output: 1
# Explanation: (1, 2) is a special position because mat[1][2] == 1 and all other elements in row 1 and column 2 are 0.
# Example 2:


# Input: mat = [[1,0,0],[0,1,0],[0,0,1]]
# Output: 3
# Explanation: (0, 0), (1, 1) and (2, 2) are special positions.
 

# Constraints:

# m == mat.length
# n == mat[i].length
# 1 <= m, n <= 100
# mat[i][j] is either 0 or 1.



# Solution: 



class Solution(object):
    def numSpecial(self, mat):
        """
        :type mat: List[List[int]]
        :rtype: int
        """
        def check1(row):
            ans = []
            for i, x in enumerate(row):
                if x == 1:
                    ans.append(i)
            return ans
        
        m = len(mat)
        n = len(mat[0])
        ans = 0
        col = [False] * n
        
        for row in mat:
            idx = check1(row)
            
            if len(idx) == 1 and not col[idx[0]]:
                j = idx[0]
                col[j] = True
                count_col1 = 0
                
                for k in range(m):
                    count_col1 += mat[k][j]
                
                if count_col1 == 1:
                    ans += 1
            else:
                for j in idx:
                    col[j] = True   # fixed (was == in your original code)
        
        return ans