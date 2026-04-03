# 3661. Maximum Walls Destroyed by Robots

# There is an endless straight line populated with some robots and walls. You are given integer arrays robots, distance, and walls:
# robots[i] is the position of the ith robot.
# distance[i] is the maximum distance the ith robot's bullet can travel.
# walls[j] is the position of the jth wall.
# Every robot has one bullet that can either fire to the left or the right at most distance[i] meters.

# A bullet destroys every wall in its path that lies within its range. Robots are fixed obstacles: if a bullet hits another robot before reaching a wall, it immediately stops at that robot and cannot continue.

# Return the maximum number of unique walls that can be destroyed by the robots.

# Notes:

# A wall and a robot may share the same position; the wall can be destroyed by the robot at that position.
# Robots are not destroyed by bullets.
 

# Example 1:

# Input: robots = [4], distance = [3], walls = [1,10]

# Output: 1

# Explanation:

# robots[0] = 4 fires left with distance[0] = 3, covering [1, 4] and destroys walls[0] = 1.
# Thus, the answer is 1.
# Example 2:

# Input: robots = [10,2], distance = [5,1], walls = [5,2,7]

# Output: 3

# Explanation:

# robots[0] = 10 fires left with distance[0] = 5, covering [5, 10] and destroys walls[0] = 5 and walls[2] = 7.
# robots[1] = 2 fires left with distance[1] = 1, covering [1, 2] and destroys walls[1] = 2.
# Thus, the answer is 3.
# Example 3:
# Input: robots = [1,2], distance = [100,1], walls = [10]

# Output: 0

# Explanation:

# In this example, only robots[0] can reach the wall, but its shot to the right is blocked by robots[1]; thus the answer is 0.

 

# Constraints:

# 1 <= robots.length == distance.length <= 10^5
# 1 <= walls.length <= 10^5
# 1 <= robots[i], walls[j] <= 10^9
# 1 <= distance[i] <= 10^5
# All values in robots are unique
# All values in walls are unique




# Solution:


import bisect

class Solution(object):
    def maxWalls(self, robots, distance, walls):
        """
        :type robots: List[int]
        :type distance: List[int]
        :type walls: List[int]
        :rtype: int
        """
        n = len(robots)

        # Store robots as (position, distance)
        x = [(robots[i], distance[i]) for i in range(n)]
        x.sort()

        # Sort walls
        walls.sort()

        # Dummy robot
        x.append((10**9, 0))

        # Function to count walls in [l, r]
        def query(l, r):
            if l > r:
                return 0
            left = bisect.bisect_left(walls, l)
            right = bisect.bisect_right(walls, r)
            return right - left

        # dp[i][0] = shoot LEFT
        # dp[i][1] = shoot RIGHT
        dp = [[0, 0] for _ in range(n)]

        # Base case
        dp[0][0] = query(x[0][0] - x[0][1], x[0][0])

        if n > 1:
            dp[0][1] = query(
                x[0][0],
                min(x[1][0] - 1, x[0][0] + x[0][1])
            )
        else:
            dp[0][1] = query(x[0][0], x[0][0] + x[0][1])

        # DP transitions
        for i in range(1, n):

            # Case 1: shoot RIGHT
            dp[i][1] = max(dp[i-1][0], dp[i-1][1]) + query(
                x[i][0],
                min(x[i+1][0] - 1, x[i][0] + x[i][1])
            )

            # Case 2: shoot LEFT (no overlap with previous LEFT)
            dp[i][0] = dp[i-1][0] + query(
                max(x[i][0] - x[i][1], x[i-1][0] + 1),
                x[i][0]
            )

            # Case 3: shoot LEFT but previous was RIGHT
            leftStart = max(x[i][0] - x[i][1], x[i-1][0] + 1)
            leftEnd = x[i][0]

            overlapStart = leftStart
            overlapEnd = min(x[i-1][0] + x[i-1][1], x[i][0] - 1)

            res = dp[i-1][1] + query(leftStart, leftEnd) - query(overlapStart, overlapEnd)

            dp[i][0] = max(dp[i][0], res)

        return max(dp[n-1][0], dp[n-1][1])