// 1320. Minimum Distance to Type a Word Using Two Fingers


// You have a keyboard layout as shown above in the X-Y plane, where each English uppercase letter is located at some coordinate.

// For example, the letter 'A' is located at coordinate (0, 0), the letter 'B' is located at coordinate (0, 1), the letter 'P' is located at coordinate (2, 3) and the letter 'Z' is located at coordinate (4, 1).
// Given the string word, return the minimum total distance to type such string using only two fingers.

// The distance between coordinates (x1, y1) and (x2, y2) is |x1 - x2| + |y1 - y2|.

// Note that the initial positions of your two fingers are considered free so do not count towards your total distance, also your two fingers do not have to start at the first letter or the first two letters.

 

// Example 1:

// Input: word = "CAKE"
// Output: 3
// Explanation: Using two fingers, one optimal way to type "CAKE" is: 
// Finger 1 on letter 'C' -> cost = 0 
// Finger 1 on letter 'A' -> cost = Distance from letter 'C' to letter 'A' = 2 
// Finger 2 on letter 'K' -> cost = 0 
// Finger 2 on letter 'E' -> cost = Distance from letter 'K' to letter 'E' = 1 
// Total distance = 3
// Example 2:

// Input: word = "HAPPY"
// Output: 6
// Explanation: Using two fingers, one optimal way to type "HAPPY" is:
// Finger 1 on letter 'H' -> cost = 0
// Finger 1 on letter 'A' -> cost = Distance from letter 'H' to letter 'A' = 2
// Finger 2 on letter 'P' -> cost = 0
// Finger 2 on letter 'P' -> cost = Distance from letter 'P' to letter 'P' = 0
// Finger 1 on letter 'Y' -> cost = Distance from letter 'A' to letter 'Y' = 4
// Total distance = 6
 

// Constraints:

// 2 <= word.length <= 300
// word consists of uppercase English letters.



// Solution:



class Solution {
    static final int INF = (int)1e9 + 7;

    static int dist(int x, int y) {
        if (x == 26 || y == 26) return 0;
        return Math.abs(x / 6 - y / 6) + Math.abs(x % 6 - y % 6);
    }

    public int minimumDistance(String word) {
        int n = word.length();
        int[][] dp = new int[2][27];

        // Initialize
        for (int i = 0; i < 27; i++) dp[0][i] = INF;

        // First character typed by finger1, finger2 at "null" (26)
        dp[0][26] = 0;
        int prev = word.charAt(0) - 'A';

        for (int i = 1; i < n; i++) {
            int cur = i & 1;
            int prv = cur ^ 1;
            int x = word.charAt(i) - 'A';

            // Reset current row
            for (int j = 0; j < 27; j++) dp[cur][j] = INF;

            for (int j = 0; j < 27; j++) {
                if (dp[prv][j] >= INF) continue;

                // Move the same finger
                dp[cur][j] = Math.min(dp[cur][j],
                        dp[prv][j] + dist(prev, x));

                // Move the other finger
                dp[cur][prev] = Math.min(dp[cur][prev],
                        dp[prv][j] + dist(j, x));
            }

            prev = x;
        }

        int last = (n - 1) & 1;
        int ans = INF;
        for (int j = 0; j < 27; j++) {
            ans = Math.min(ans, dp[last][j]);
        }
        return ans;
    }
}