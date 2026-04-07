# 2069. Walking Robot Simulation II

# A width x height grid is on an XY-plane with the bottom-left cell at (0, 0) and the top-right cell at (width - 1, height - 1). The grid is aligned with the four cardinal directions ("North", "East", "South", and "West"). A robot is initially at cell (0, 0) facing direction "East".

# The robot can be instructed to move for a specific number of steps. For each step, it does the following.

# Attempts to move forward one cell in the direction it is facing.
# If the cell the robot is moving to is out of bounds, the robot instead turns 90 degrees counterclockwise and retries the step.
# After the robot finishes moving the number of steps required, it stops and awaits the next instruction.

# Implement the Robot class:

# Robot(int width, int height) Initializes the width x height grid with the robot at (0, 0) facing "East".
# void step(int num) Instructs the robot to move forward num steps.
# int[] getPos() Returns the current cell the robot is at, as an array of length 2, [x, y].
# String getDir() Returns the current direction of the robot, "North", "East", "South", or "West".
 

# Example 1:

# example-1
# Input
# ["Robot", "step", "step", "getPos", "getDir", "step", "step", "step", "getPos", "getDir"]
# [[6, 3], [2], [2], [], [], [2], [1], [4], [], []]
# Output
# [null, null, null, [4, 0], "East", null, null, null, [1, 2], "West"]

# Explanation
# Robot robot = new Robot(6, 3); // Initialize the grid and the robot at (0, 0) facing East.
# robot.step(2);  // It moves two steps East to (2, 0), and faces East.
# robot.step(2);  // It moves two steps East to (4, 0), and faces East.
# robot.getPos(); // return [4, 0]
# robot.getDir(); // return "East"
# robot.step(2);  // It moves one step East to (5, 0), and faces East.
#                 // Moving the next step East would be out of bounds, so it turns and faces North.
#                 // Then, it moves one step North to (5, 1), and faces North.
# robot.step(1);  // It moves one step North to (5, 2), and faces North (not West).
# robot.step(4);  // Moving the next step North would be out of bounds, so it turns and faces West.
#                 // Then, it moves four steps West to (1, 2), and faces West.
# robot.getPos(); // return [1, 2]
# robot.getDir(); // return "West"

 

# Constraints:

# 2 <= width, height <= 100
# 1 <= num <= 10^5
# At most 10^4 calls in total will be made to step, getPos, and getDir.





# Solution: 




class Robot(object):

    def __init__(self, width, height):
        self.w = width
        self.h = height
        self.face = 1  # East
        self.x = 0
        self.y = 0
        self.perimeter = 2 * (self.h + self.w - 2)
        self.dir = ["North", "East", "South", "West"]

    def move(self, num):
        if num >= self.perimeter:
            num %= self.perimeter
            if self.x == 0 and self.y == 0 and self.face == 1:  # edge case
                self.face = 2  # South

        if num == 0:
            return

        if self.face == 0:  # North
            d = min(num, self.h - 1 - self.y)
            self.y += d
            num -= d
            if self.y == self.h - 1 and num > 0:
                self.face = 3  # West

        elif self.face == 1:  # East
            d = min(num, self.w - 1 - self.x)
            self.x += d
            num -= d
            if self.x == self.w - 1 and num > 0:
                self.face = 0  # North

        elif self.face == 2:  # South
            d = min(num, self.y)
            self.y -= d
            num -= d
            if self.y == 0 and num > 0:
                self.face = 1  # East

        elif self.face == 3:  # West
            d = min(num, self.x)
            self.x -= d
            num -= d
            if self.x == 0 and num > 0:
                self.face = 2  # South

        self.move(num)

    def step(self, num):
        self.move(num)

    def getPos(self):
        return [self.x, self.y]

    def getDir(self):
        return self.dir[self.face]