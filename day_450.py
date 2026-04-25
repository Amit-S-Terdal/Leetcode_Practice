# 3464. Maximize the Distance Between Points on a Square

# You are given an integer side, representing the edge length of a square with corners at (0, 0), (0, side), (side, 0), and (side, side) on a Cartesian plane.

# You are also given a positive integer k and a 2D integer array points, where points[i] = [xi, yi] represents the coordinate of a point lying on the boundary of the square.

# You need to select k elements among points such that the minimum Manhattan distance between any two points is maximized.

# Return the maximum possible minimum Manhattan distance between the selected k points.

# The Manhattan Distance between two cells (xi, yi) and (xj, yj) is |xi - xj| + |yi - yj|.

 

# Example 1:

# Input: side = 2, points = [[0,2],[2,0],[2,2],[0,0]], k = 4

# Output: 2

# Explanation:



# Select all four points.

# Example 2:

# Input: side = 2, points = [[0,0],[1,2],[2,0],[2,2],[2,1]], k = 4

# Output: 1

# Explanation:



# Select the points (0, 0), (2, 0), (2, 2), and (2, 1).

# Example 3:

# Input: side = 2, points = [[0,0],[0,1],[0,2],[1,2],[2,0],[2,2],[2,1]], k = 5

# Output: 1

# Explanation:



# Select the points (0, 0), (0, 1), (0, 2), (1, 2), and (2, 2).

 

# Constraints:

# 1 <= side <= 10^9
# 4 <= points.length <= min(4 * side, 15 * 10^3)
# points[i] == [xi, yi]
# The input is generated such that:
# points[i] lies on the boundary of the square.
# All points[i] are unique.
# 4 <= k <= min(25, points.length)


# Solution:


class Solution(object):
    def maxDistance(self, side, points, k):
        """
        :type side: int
        :type points: List[List[int]]
        :type k: int
        :rtype: int
        """
        res = []

        # Map 2D boundary points to 1D perimeter
        for x, y in points:
            if x == 0:
                res.append(y)
            elif y == side:
                res.append(side + x)
            elif x == side:
                res.append(3 * side - y)
            else:
                res.append(4 * side - x)

        res.sort()
        m = len(res)

        # Binary search helper
        def lower_bound(arr, target):
            l, r = 0, len(arr)
            while l < r:
                mid = (l + r) // 2
                if arr[mid] >= target:
                    r = mid
                else:
                    l = mid + 1
            return l

        def check(n):
            idx = [0] * k
            curr = res[0]
            idx[0] = 0

            # Greedy pick
            for i in range(1, k):
                pos = lower_bound(res, curr + n)
                if pos == m:
                    return False
                idx[i] = pos
                curr = res[pos]

            # Circular condition
            if res[idx[-1]] - res[0] <= 4 * side - n:
                return True

            # Try shifting start
            start = 1
            while start < idx[1]:
                idx[0] = start
                for j in range(1, k):
                    while idx[j] < m and res[idx[j]] < res[idx[j - 1]] + n:
                        idx[j] += 1
                    if idx[j] == m:
                        return False

                if res[idx[-1]] - res[idx[0]] <= 4 * side - n:
                    return True

                start += 1

            return False

        left, right = 1, (4 * side) // k + 1

        while left + 1 < right:
            mid = (left + right) // 2
            if check(mid):
                left = mid
            else:
                right = mid

        return left