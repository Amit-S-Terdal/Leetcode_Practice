# 3341. Find Minimum Time to Reach Last Room I

# There is a dungeon with n x m rooms arranged as a grid.

# You are given a 2D array moveTime of size n x m, where moveTime[i][j] represents the minimum time in seconds when you can start moving to that room. You start from the room (0, 0) at time t = 0 and can move to an adjacent room. Moving between adjacent rooms takes exactly one second.

# Return the minimum time to reach the room (n - 1, m - 1).

# Two rooms are adjacent if they share a common wall, either horizontally or vertically.

 

# Example 1:

# Input: moveTime = [[0,4],[4,4]]

# Output: 6

# Explanation:

# The minimum time required is 6 seconds.

# At time t == 4, move from room (0, 0) to room (1, 0) in one second.
# At time t == 5, move from room (1, 0) to room (1, 1) in one second.
# Example 2:

# Input: moveTime = [[0,0,0],[0,0,0]]

# Output: 3

# Explanation:

# The minimum time required is 3 seconds.

# At time t == 0, move from room (0, 0) to room (1, 0) in one second.
# At time t == 1, move from room (1, 0) to room (1, 1) in one second.
# At time t == 2, move from room (1, 1) to room (1, 2) in one second.
# Example 3:

# Input: moveTime = [[0,1],[1,2]]

# Output: 3

 

# Constraints:

# 2 <= n == moveTime.length <= 50
# 2 <= m == moveTime[i].length <= 50
# 0 <= moveTime[i][j] <= 10^9


# Solution:


import heapq

class Solution(object):
    def minTimeToReach(self, moveTime):
        """
        :type moveTime: List[List[int]]
        :rtype: int
        """
        n, m = len(moveTime), len(moveTime[0])
        INF = float('inf')
        
        # Distance array to store min time to reach each cell
        dist = [[INF] * m for _ in range(n)]
        dist[0][0] = 0
        
        # Min-heap for Dijkstra: (time, x, y)
        heap = [(0, 0, 0)]
        
        directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]

        while heap:
            time, x, y = heapq.heappop(heap)

            # Early stop if we reach bottom-right
            if x == n - 1 and y == m - 1:
                return time

            # Skip if this is not the shortest time to (x, y)
            if time > dist[x][y]:
                continue

            for dx, dy in directions:
                nx, ny = x + dx, y + dy

                if 0 <= nx < n and 0 <= ny < m:
                    # Must wait until moveTime[nx][ny], then move (1 sec)
                    next_time = max(time, moveTime[nx][ny]) + 1
                    if next_time < dist[nx][ny]:
                        dist[nx][ny] = next_time
                        heapq.heappush(heap, (next_time, nx, ny))

        return -1  # Not reachable (should not happen with valid input)
