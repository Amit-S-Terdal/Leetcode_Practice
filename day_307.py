# 3625. Count Number of Trapezoids II

# You are given a 2D integer array points where points[i] = [xi, yi] represents the coordinates of the ith point on the Cartesian plane.

# Return the number of unique trapezoids that can be formed by choosing any four distinct points from points.

# A trapezoid is a convex quadrilateral with at least one pair of parallel sides. Two lines are parallel if and only if they have the same slope.

 

# Example 1:

# Input: points = [[-3,2],[3,0],[2,3],[3,2],[2,-3]]

# Output: 2

# Explanation:



# There are two distinct ways to pick four points that form a trapezoid:

# The points [-3,2], [2,3], [3,2], [2,-3] form one trapezoid.
# The points [2,3], [3,2], [3,0], [2,-3] form another trapezoid.
# Example 2:

# Input: points = [[0,0],[1,0],[0,1],[2,1]]

# Output: 1

# Explanation:



# There is only one trapezoid which can be formed.

 

# Constraints:

# 4 <= points.length <= 500
# –1000 <= xi, yi <= 1000
# All points are pairwise distinct.



# Solution:



class Solution(object):
    def countTrapezoids(self, points):
        """
        :type points: List[List[int]]
        :rtype: int
        """

        # custom gcd (works in all Python versions)
        def gcd(a, b):
            a = abs(a)
            b = abs(b)
            while b:
                a, b = b, a % b
            return a

        n = len(points)
        n2 = n * (n - 1) // 2

        slope_inter = []
        mid_slope = []

        # Build slope/intercept and midpoint/slope pairs
        for i in range(n - 1):
            x0, y0 = points[i]
            for j in range(i + 1, n):
                x1, y1 = points[j]

                a = y1 - y0
                b = x0 - x1
                c = y0 * x1 - y1 * x0

                # Normalize sign like C++
                if a == 0 and b < 0:
                    b = -b
                    c = -c
                elif a < 0:
                    a = -a
                    b = -b
                    c = -c

                gm = gcd(a, b)
                gc = gcd(gm, c)

                slope = (a // gm, b // gm)
                intercept = (c // gc, gm // gc)
                mid = (x0 + x1, y0 + y1)

                slope_inter.append((slope, intercept))
                mid_slope.append((mid, slope))

        # Sort like std::sort
        slope_inter.sort()
        mid_slope.sort()

        # Same helper as C++ version
        def cntShapes(total, groups):
            s = 0
            for x in groups:
                s += x * (total - x)
            return s

        cnt = 0

        # ---------- slope/intercept counting ----------
        B = []
        prev_slope, prev_inter = slope_inter[0]
        sameM = sameB = 1

        for i in range(1, n2):
            slope, inter = slope_inter[i]
            if slope == prev_slope:
                sameM += 1
                if inter == prev_inter:
                    sameB += 1
                else:
                    B.append(sameB)
                    sameB = 1
                    prev_inter = inter
            else:
                B.append(sameB)
                cnt += cntShapes(sameM, B) // 2
                B = []
                prev_slope, prev_inter = slope, inter
                sameM = sameB = 1

        B.append(sameB)
        cnt += cntShapes(sameM, B) // 2

        # ---------- midpoint/slope counting (subtract) ----------
        L = []
        prev_mid, prev_slope = mid_slope[0]
        sameMid = sameM = 1

        for i in range(1, n2):
            mid, slope = mid_slope[i]
            if mid == prev_mid:
                sameMid += 1
                if slope == prev_slope:
                    sameM += 1
                else:
                    L.append(sameM)
                    sameM = 1
                    prev_slope = slope
            else:
                L.append(sameM)
                cnt -= cntShapes(sameMid, L) // 2
                L = []
                prev_mid, prev_slope = mid, slope
                sameMid = sameM = 1

        L.append(sameM)
        cnt -= cntShapes(sameMid, L) // 2

        return cnt
