# 3623. Count Number of Trapezoids I

# You are given a 2D integer array points, where points[i] = [xi, yi] represents the coordinates of the ith point on the Cartesian plane.

# A horizontal trapezoid is a convex quadrilateral with at least one pair of horizontal sides (i.e. parallel to the x-axis). Two lines are parallel if and only if they have the same slope.

# Return the number of unique horizontal trapezoids that can be formed by choosing any four distinct points from points.

# Since the answer may be very large, return it modulo 109 + 7.

 

# Example 1:

# Input: points = [[1,0],[2,0],[3,0],[2,2],[3,2]]

# Output: 3

# Explanation:



# There are three distinct ways to pick four points that form a horizontal trapezoid:

# Using points [1,0], [2,0], [3,2], and [2,2].
# Using points [2,0], [3,0], [3,2], and [2,2].
# Using points [1,0], [3,0], [3,2], and [2,2].
# Example 2:

# Input: points = [[0,0],[1,0],[0,1],[2,1]]

# Output: 1

# Explanation:



# There is only one horizontal trapezoid that can be formed.

 

# Constraints:

# 4 <= points.length <= 10^5
# –10^8 <= xi, yi <= 10^8
# All points are pairwise distinct.



# Solution: 



class Solution(object):
    def countTrapezoids(self, points):
        """
        :type points: List[List[int]]
        :rtype: int
        """
        mod = 10**9 + 7
        n = len(points)
        
        # Extract y-coordinates
        y = [p[1] for p in points]
        y.sort()
        
        sum_c = 0   # sum of combinations C(f,2)
        c2 = 0      # sum of squares of C(f,2)
        
        f = 1
        prev = y[0]
        
        for i in range(1, n):
            if y[i] == prev:
                f += 1
            else:
                c = f * (f - 1) // 2
                sum_c += c
                c2 += c * c
                f = 1
                prev = y[i]
        
        # Process last group
        c = f * (f - 1) // 2
        sum_c += c
        c2 += c * c
        
        ans = (sum_c * sum_c - c2) // 2
        ans %= mod
        
        return ans
