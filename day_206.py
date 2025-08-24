# 1504. Count Submatrices With All Ones

# Given an m x n binary matrix mat, return the number of submatrices that have all ones.

 

# Example 1:


# Input: mat = [[1,0,1],[1,1,0],[1,1,0]]
# Output: 13
# Explanation: 
# There are 6 rectangles of side 1x1.
# There are 2 rectangles of side 1x2.
# There are 3 rectangles of side 2x1.
# There is 1 rectangle of side 2x2. 
# There is 1 rectangle of side 3x1.
# Total number of rectangles = 6 + 2 + 3 + 1 + 1 = 13.
# Example 2:


# Input: mat = [[0,1,1,0],[0,1,1,1],[1,1,1,0]]
# Output: 24
# Explanation: 
# There are 8 rectangles of side 1x1.
# There are 5 rectangles of side 1x2.
# There are 2 rectangles of side 1x3. 
# There are 4 rectangles of side 2x1.
# There are 2 rectangles of side 2x2. 
# There are 2 rectangles of side 3x1. 
# There is 1 rectangle of side 3x2. 
# Total number of rectangles = 8 + 5 + 2 + 4 + 2 + 2 + 1 = 24.
 

# Constraints:

# 1 <= m, n <= 150
# mat[i][j] is either 0 or 1.


# Solution:


class Solution(object):
    def numSubmat(self, mat):
        """
        :type mat: List[List[int]]
        :rtype: int
        """
        if not mat or not mat[0]:
            return 0

        r, c = len(mat), len(mat[0])
        ans = 0
        cnt = [0] * c     # dp: submatrices ending at column j for current row

        for i in range(r):
            h = mat[i]    # treat current row as heights and update in place
            st = []       # mono-increasing stack of column indices

            # reset dp for this row
            for j in range(c):
                cnt[j] = 0

            for j in range(c):
                if h[j] == 0:
                    # zero acts as a barrier; reset stack to just this index as sentinel
                    st = [j]
                    continue

                if i > 0:
                    h[j] += mat[i - 1][j]  # accumulate height

                while st and h[st[-1]] >= h[j]:
                    st.pop()

                left = st[-1] if st else -1
                base = cnt[left] if st else 0
                cnt[j] = base + h[j] * (j - left)
                ans += cnt[j]

                st.append(j)

        return ans
