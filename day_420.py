# 3548. Equal Sum Grid Partition II

# You are given an m x n matrix grid of positive integers. Your task is to determine if it is possible to make either one horizontal or one vertical cut on the grid such that:

# Each of the two resulting sections formed by the cut is non-empty.
# The sum of elements in both sections is equal, or can be made equal by discounting at most one single cell in total (from either section).
# If a cell is discounted, the rest of the section must remain connected.
# Return true if such a partition exists; otherwise, return false.

# Note: A section is connected if every cell in it can be reached from any other cell by moving up, down, left, or right through other cells in the section.

 

# Example 1:

# Input: grid = [[1,4],[2,3]]

# Output: true

# Explanation:



# A horizontal cut after the first row gives sums 1 + 4 = 5 and 2 + 3 = 5, which are equal. Thus, the answer is true.
# Example 2:

# Input: grid = [[1,2],[3,4]]

# Output: true

# Explanation:



# A vertical cut after the first column gives sums 1 + 3 = 4 and 2 + 4 = 6.
# By discounting 2 from the right section (6 - 2 = 4), both sections have equal sums and remain connected. Thus, the answer is true.
# Example 3:

# Input: grid = [[1,2,4],[2,3,5]]

# Output: false

# Explanation:



# A horizontal cut after the first row gives 1 + 2 + 4 = 7 and 2 + 3 + 5 = 10.
# By discounting 3 from the bottom section (10 - 3 = 7), both sections have equal sums, but they do not remain connected as it splits the bottom section into two parts ([2] and [5]). Thus, the answer is false.
# Example 4:

# Input: grid = [[4,1,8],[3,2,6]]

# Output: false

# Explanation:

# No valid cut exists, so the answer is false.

 

# Constraints:

# 1 <= m == grid.length <= 10^5
# 1 <= n == grid[i].length <= 10^5
# 2 <= m * n <= 10^5
# 1 <= grid[i][j] <= 10^5




# Solution: 



class Solution(object):
    def canPartitionGrid(self, grid):
        r, c = len(grid), len(grid[0])
        N = r * c

        # Flatten grid
        A = [0] * N
        idx = 0
        for row in grid:
            for x in row:
                A[idx] = x
                idx += 1

        Tsum = 0
        xMax = 0
        for x in A:
            Tsum += x
            if x > xMax:
                xMax = x

        seen = set()

        # Horizontal Cuts (Top → Bottom)
        top = 0
        for i in range(r - 1):
            for j in range(c):
                x = A[i * c + j]
                top += x
                seen.add(x)

            bot = Tsum - top
            if top == bot:
                return True

            d = top - bot
            if d > 0 and d <= xMax:
                if i > 0 and c > 1:
                    if d in seen:
                        return True
                else:
                    if A[0] == d or A[(i + 1) * c - 1] == d:
                        return True

        # Horizontal Cuts (Bottom → Top)
        seen.clear()
        bot = 0
        for i in range(r - 1, 0, -1):
            for j in range(c - 1, -1, -1):
                x = A[i * c + j]
                bot += x
                seen.add(x)

            topS = Tsum - bot
            d = bot - topS

            if d > 0 and d <= xMax:
                if (r - 1 - i) > 0 and c > 1:
                    if d in seen:
                        return True
                else:
                    if A[i * c] == d or A[N - 1] == d:
                        return True

        # Vertical Cuts (Left → Right)
        seen.clear()
        left = 0
        for j in range(c - 1):
            for i in range(r):
                x = A[i * c + j]
                left += x
                seen.add(x)

            right = Tsum - left
            if left == right:
                return True

            d = left - right
            if d > 0 and d <= xMax:
                if r > 1 and j > 0:
                    if d in seen:
                        return True
                else:
                    if A[0] == d or A[(r - 1) * c + j] == d:
                        return True

        # Vertical Cuts (Right → Left)
        seen.clear()
        right = 0
        for j in range(c - 1, 0, -1):
            for i in range(r):
                x = A[i * c + j]
                right += x
                seen.add(x)

            leftS = Tsum - right
            d = right - leftS

            if d > 0 and d <= xMax:
                if r > 1 and (c - 1 - j) > 0:
                    if d in seen:
                        return True
                else:
                    if A[j] == d or A[N - 1] == d:
                        return True

        return False