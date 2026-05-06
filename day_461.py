# 1861. Rotating the Box

# You are given an m x n matrix of characters boxGrid representing a side-view of a box. Each cell of the box is one of the following:

# A stone '#'
# A stationary obstacle '*'
# Empty '.'
# The box is rotated 90 degrees clockwise, causing some of the stones to fall due to gravity. Each stone falls down until it lands on an obstacle, another stone, or the bottom of the box. Gravity does not affect the obstacles' positions, and the inertia from the box's rotation does not affect the stones' horizontal positions.

# It is guaranteed that each stone in boxGrid rests on an obstacle, another stone, or the bottom of the box.

# Return an n x m matrix representing the box after the rotation described above.

 

# Example 1:



# Input: boxGrid = [["#",".","#"]]
# Output: [["."],
#          ["#"],
#          ["#"]]
# Example 2:



# Input: boxGrid = [["#",".","*","."],
#               ["#","#","*","."]]
# Output: [["#","."],
#          ["#","#"],
#          ["*","*"],
#          [".","."]]
# Example 3:



# Input: boxGrid = [["#","#","*",".","*","."],
#               ["#","#","#","*",".","."],
#               ["#","#","#",".","#","."]]
# Output: [[".","#","#"],
#          [".","#","#"],
#          ["#","#","*"],
#          ["#","*","."],
#          ["#",".","*"],
#          ["#",".","."]]
 

# Constraints:

# m == boxGrid.length
# n == boxGrid[i].length
# 1 <= m, n <= 500
# boxGrid[i][j] is either '#', '*', or '.'.



# Solution: 




class Solution(object):
    def rotateTheBox(self, boxGrid):
        """
        :type boxGrid: List[List[str]]
        :rtype: List[List[str]]
        """
        r = len(boxGrid)
        c = len(boxGrid[0])

        # Initialize rotated box with '.'
        rotate = [['.' for _ in range(r)] for _ in range(c)]

        for i in range(r):
            bottom = c - 1
            for j in range(c - 1, -1, -1):
                if boxGrid[i][j] == '#':
                    rotate[bottom][r - 1 - i] = '#'
                    bottom -= 1
                elif boxGrid[i][j] == '*':
                    rotate[j][r - 1 - i] = '*'
                    bottom = j - 1

        return rotate