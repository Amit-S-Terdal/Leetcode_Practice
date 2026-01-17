# 3047. Find the Largest Area of Square Inside Two Rectangles

# There exist n rectangles in a 2D plane with edges parallel to the x and y axis. You are given two 2D integer arrays bottomLeft and topRight where bottomLeft[i] = [a_i, b_i] and topRight[i] = [c_i, d_i] represent the bottom-left and top-right coordinates of the ith rectangle, respectively.

# You need to find the maximum area of a square that can fit inside the intersecting region of at least two rectangles. Return 0 if such a square does not exist.

 

# Example 1:


# Input: bottomLeft = [[1,1],[2,2],[3,1]], topRight = [[3,3],[4,4],[6,6]]

# Output: 1

# Explanation:

# A square with side length 1 can fit inside either the intersecting region of rectangles 0 and 1 or the intersecting region of rectangles 1 and 2. Hence the maximum area is 1. It can be shown that a square with a greater side length can not fit inside any intersecting region of two rectangles.

# Example 2:


# Input: bottomLeft = [[1,1],[1,3],[1,5]], topRight = [[5,5],[5,7],[5,9]]

# Output: 4

# Explanation:

# A square with side length 2 can fit inside either the intersecting region of rectangles 0 and 1 or the intersecting region of rectangles 1 and 2. Hence the maximum area is 2 * 2 = 4. It can be shown that a square with a greater side length can not fit inside any intersecting region of two rectangles.

# Example 3:

  
# Input: bottomLeft = [[1,1],[2,2],[1,2]], topRight = [[3,3],[4,4],[3,4]]

# Output: 1

# Explanation:

# A square with side length 1 can fit inside the intersecting region of any two rectangles. Also, no larger square can, so the maximum area is 1. Note that the region can be formed by the intersection of more than 2 rectangles.

# Example 4:

  
# Input: bottomLeft = [[1,1],[3,3],[3,1]], topRight = [[2,2],[4,4],[4,2]]

# Output: 0

# Explanation:

# No pair of rectangles intersect, hence, the answer is 0.

 

# Constraints:

# n == bottomLeft.length == topRight.length
# 2 <= n <= 103
# bottomLeft[i].length == topRight[i].length == 2
# 1 <= bottomLeft[i][0], bottomLeft[i][1] <= 10^7
# 1 <= topRight[i][0], topRight[i][1] <= 10^7
# bottomLeft[i][0] < topRight[i][0]
# bottomLeft[i][1] < topRight[i][1]




# Solution: 




class Solution(object):
    def largestSquareArea(self, bottomLeft, topRight):
        """
        :type bottomLeft: List[List[int]]
        :type topRight: List[List[int]]
        :rtype: int
        """
        n = len(bottomLeft)
        
        # Store rectangles as (x1, y1, x2, y2)
        rect = []
        for i in range(n):
            rect.append((
                bottomLeft[i][0],
                bottomLeft[i][1],
                topRight[i][0],
                topRight[i][1]
            ))
        
        # Lexicographical sort (same as tuple sort in C++)
        rect.sort()
        
        max_side = 0
        
        for r in range(1, n):
            a, b, c, d = rect[r]
            for l in range(r):
                aj, bj, cj, dj = rect[l]
                
                width = min(cj, c) - a
                height = min(dj, d) - max(bj, b)
                
                side = min(width, height)
                if side > max_side:
                    max_side = side
        
        return max_side * max_side
