// 1861. Rotating the Box

// You are given an m x n matrix of characters boxGrid representing a side-view of a box. Each cell of the box is one of the following:

// A stone '#'
// A stationary obstacle '*'
// Empty '.'
// The box is rotated 90 degrees clockwise, causing some of the stones to fall due to gravity. Each stone falls down until it lands on an obstacle, another stone, or the bottom of the box. Gravity does not affect the obstacles' positions, and the inertia from the box's rotation does not affect the stones' horizontal positions.

// It is guaranteed that each stone in boxGrid rests on an obstacle, another stone, or the bottom of the box.

// Return an n x m matrix representing the box after the rotation described above.

 

// Example 1:



// Input: boxGrid = [["#",".","#"]]
// Output: [["."],
//          ["#"],
//          ["#"]]
// Example 2:



// Input: boxGrid = [["#",".","*","."],
//               ["#","#","*","."]]
// Output: [["#","."],
//          ["#","#"],
//          ["*","*"],
//          [".","."]]
// Example 3:



// Input: boxGrid = [["#","#","*",".","*","."],
//               ["#","#","#","*",".","."],
//               ["#","#","#",".","#","."]]
// Output: [[".","#","#"],
//          [".","#","#"],
//          ["#","#","*"],
//          ["#","*","."],
//          ["#",".","*"],
//          ["#",".","."]]
 

// Constraints:

// m == boxGrid.length
// n == boxGrid[i].length
// 1 <= m, n <= 500
// boxGrid[i][j] is either '#', '*', or '.'.



// Solution: 




class Solution {
    public char[][] rotateTheBox(char[][] boxGrid) {
        int r = boxGrid.length;
        int c = boxGrid[0].length;

        char[][] rotate = new char[c][r];

        // Fill with '.'
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++) {
                rotate[i][j] = '.';
            }
        }

        for (int i = 0; i < r; i++) {
            int bottom = c - 1;
            for (int j = c - 1; j >= 0; j--) {
                if (boxGrid[i][j] == '#') {
                    rotate[bottom][r - 1 - i] = '#';
                    bottom--;
                } else if (boxGrid[i][j] == '*') {
                    rotate[j][r - 1 - i] = '*';
                    bottom = j - 1;
                }
            }
        }

        return rotate;
    }
}