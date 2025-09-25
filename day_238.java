// 120. Triangle

// Given a triangle array, return the minimum path sum from top to bottom.

// For each step, you may move to an adjacent number of the row below. More formally, if you are on index i on the current row, you may move to either index i or index i + 1 on the next row.

 

// Example 1:

// Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
// Output: 11
// Explanation: The triangle looks like:
//    2
//   3 4
//  6 5 7
// 4 1 8 3
// The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11 (underlined above).
// Example 2:

// Input: triangle = [[-10]]
// Output: -10
 

// Constraints:

// 1 <= triangle.length <= 200
// triangle[0].length == 1
// triangle[i].length == triangle[i - 1].length + 1
// -10^4 <= triangle[i][j] <= 10^4


// Solution: 


import java.util.List;

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        List<Integer> dp = new ArrayList<>(Collections.nCopies(n, 0));

        dp.set(0, triangle.get(0).get(0));  // Initialize dp[0] with the first element

        for (int i = 1; i < n; i++) {
            dp.set(i, dp.get(i - 1) + triangle.get(i).get(i));  // Handle the rightmost element in row i
            for (int j = i - 1; j >= 1; j--) {
                dp.set(j, triangle.get(i).get(j) + Math.min(dp.get(j), dp.get(j - 1)));
            }
            dp.set(0, dp.get(0) + triangle.get(i).get(0));  // Handle the leftmost element in row i
        }

        return dp.stream().min(Integer::compare).get();  // Return the minimum value in dp
    }
}
