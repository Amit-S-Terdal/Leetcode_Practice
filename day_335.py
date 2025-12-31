# 1970. Last Day Where You Can Still Cross

# There is a 1-based binary matrix where 0 represents land and 1 represents water. You are given integers row and col representing the number of rows and columns in the matrix, respectively.

# Initially on day 0, the entire matrix is land. However, each day a new cell becomes flooded with water. You are given a 1-based 2D array cells, where cells[i] = [ri, ci] represents that on the ith day, the cell on the rith row and cith column (1-based coordinates) will be covered with water (i.e., changed to 1).

# You want to find the last day that it is possible to walk from the top to the bottom by only walking on land cells. You can start from any cell in the top row and end at any cell in the bottom row. You can only travel in the four cardinal directions (left, right, up, and down).

# Return the last day where it is possible to walk from the top to the bottom by only walking on land cells.

 

# Example 1:


# Input: row = 2, col = 2, cells = [[1,1],[2,1],[1,2],[2,2]]
# Output: 2
# Explanation: The above image depicts how the matrix changes each day starting from day 0.
# The last day where it is possible to cross from top to bottom is on day 2.
# Example 2:


# Input: row = 2, col = 2, cells = [[1,1],[1,2],[2,1],[2,2]]
# Output: 1
# Explanation: The above image depicts how the matrix changes each day starting from day 0.
# The last day where it is possible to cross from top to bottom is on day 1.
# Example 3:


# Input: row = 3, col = 3, cells = [[1,2],[2,1],[3,3],[2,2],[1,1],[1,3],[2,3],[3,2],[3,1]]
# Output: 3
# Explanation: The above image depicts how the matrix changes each day starting from day 0.
# The last day where it is possible to cross from top to bottom is on day 3.
 

# Constraints:

# 2 <= row, col <= 2 * 10^4
# 4 <= row * col <= 2 * 10^4
# cells.length == row * col
# 1 <= ri <= row
# 1 <= ci <= col
# All the values of cells are unique.



# Solution: 


import heapq

class Solution(object):
    # Helper function to calculate the unique key for each cell
    def key(self, i, j, col):
        return (i - 1) * col + (j - 1)
    
    def latestDayToCross(self, row, col, cells):
        """
        :type row: int
        :type col: int
        :type cells: List[List[int]]
        :rtype: int
        """
        N = len(cells)
        water = [[0, 0, 0] for _ in range(N)]  # To store (d, i, j)
        c2idx = [[-1] * col for _ in range(row)]  # To store the index of each cell in cells
        water1 = []

        # Build water and find indexes for the cell=(i, j) with j=1 storing to water1
        for r in range(N):
            i, j = cells[r]
            water[r] = [r, i, j]
            c2idx[i - 1][j - 1] = r
            if j == 1:
                water1.append(r)

        # PriorityQueue to simulate a min-heap (smallest d comes first)
        pq = []
        visited = [False] * (row * col)

        # Start from all the cells where j=1
        for idx in water1:
            i, j = cells[idx]
            heapq.heappush(pq, [idx, i, j])
            visited[self.key(i, j, col)] = True

        max_d = float('-inf')

        # Process the queue
        while pq:
            top = heapq.heappop(pq)
            d, i, j = top
            max_d = max(max_d, d)

            if j == col:
                return max_d

            adj = [
                [i + 1, j - 1], [i + 1, j], [i + 1, j + 1],
                [i, j + 1], [i - 1, j + 1], [i - 1, j], [i - 1, j - 1], [i, j - 1]
            ]

            for a, b in adj:
                idx = self.key(a, b, col)

                if a <= 0 or a > row or b <= 0 or b > col or visited[idx]:
                    continue

                visited[idx] = True
                heapq.heappush(pq, [c2idx[a - 1][b - 1], a, b])

        return -1  # In case no path is found

