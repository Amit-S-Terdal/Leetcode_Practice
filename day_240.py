# 812. Largest Triangle Area

# Given an array of points on the X-Y plane points where points[i] = [xi, yi], return the area of the largest triangle that can be formed by any three different points. Answers within 10-5 of the actual answer will be accepted.

 

# Example 1:


# Input: points = [[0,0],[0,1],[1,0],[0,2],[2,0]]
# Output: 2.00000
# Explanation: The five points are shown in the above figure. The red triangle is the largest.
# Example 2:

# Input: points = [[1,0],[0,0],[0,1]]
# Output: 0.50000
 

# Constraints:

# 3 <= points.length <= 50
# -50 <= xi, yi <= 50
# All the given points are unique.



# Solution:


class Solution(object):
    def largestTriangleArea(self, points):
        """
        :type points: List[List[int]]
        :rtype: float
        """
        n = len(points)
        maxA = 0
        for i in range(n - 2):
            a0, a1 = points[i]
            for j in range(i + 1, n - 1):
                b0, b1 = points[j]
                for k in range(j + 1, n):
                    c0, c1 = points[k]
                    area = (b0 - a0) * (c1 - a1) - (b1 - a1) * (c0 - a0)
                    maxA = max(maxA, abs(area))
        return 0.5 * maxA
# Time Complexity: O(n^3)
# Space Complexity: O(1)