// 1301. Number of Paths with Max Score

// You are given a square board of characters. You can move on the board starting at the bottom right square marked with the character 'S'.

// You need to reach the top left square marked with the character 'E'. The rest of the squares are labeled either with a numeric character 1, 2, ..., 9 or with an obstacle 'X'. In one move you can go up, left or up-left (diagonally) only if there is no obstacle there.

// Return a list of two integers: the first integer is the maximum sum of numeric characters you can collect, and the second is the number of such paths that you can take to get that maximum sum, taken modulo 10^9 + 7.

// In case there is no path, return [0, 0].

 

// Example 1:

// Input: board = ["E23","2X2","12S"]
// Output: [7,1]
// Example 2:

// Input: board = ["E12","1X1","21S"]
// Output: [4,2]
// Example 3:

// Input: board = ["E11","XXX","11S"]
// Output: [0,0]
 

// Constraints:

// 2 <= board.length == board[i].length <= 100




// Solution: 




class Solution {
    static final int MOD = 1_000_000_007;
    static final int N = 105;

    static class Pair {
        int sum;
        int ways;

        Pair(int sum, int ways) {
            this.sum = sum;
            this.ways = ways;
        }
    }

    static final Pair NONE = new Pair(0, -1);
    static Pair[][] dp = new Pair[2][N];

    public int[] pathsWithMaxScore(List<String> board) {
        int n = board.size();

        // Initialize DP
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < N; j++) {
                dp[i][j] = new Pair(0, -1);
            }
        }

        char[][] grid = new char[n][];
        for (int i = 0; i < n; i++) {
            grid[i] = board.get(i).toCharArray();
        }
        grid[0][0] = '0';

        int last = (n - 1) & 1;

        for (int j = 0; j < n; j++) {
            dp[last][j].sum = 0;
            dp[last][j].ways = -1;
        }

        dp[last][n - 1].sum = 0;
        dp[last][n - 1].ways = 1;

        // Last row
        for (int j = n - 2; j >= 0; j--) {
            char c = grid[n - 1][j];
            if (c == 'X') break;

            dp[last][j].sum = dp[last][j + 1].sum + (c - '0');
            dp[last][j].ways = 1;
        }

        for (int i = n - 2; i >= 0; i--) {
            int cur = i & 1;
            int prv = (i + 1) & 1;

            char cR = grid[i][n - 1];

            if (cR == 'X' || dp[prv][n - 1].ways == -1) {
                dp[cur][n - 1].sum = 0;
                dp[cur][n - 1].ways = -1;
            } else {
                dp[cur][n - 1].sum = dp[prv][n - 1].sum + (cR - '0');
                dp[cur][n - 1].ways = 1;
            }

            for (int j = n - 2; j >= 0; j--) {
                char c = grid[i][j];

                if (c == 'X') {
                    dp[cur][j].sum = 0;
                    dp[cur][j].ways = -1;
                    continue;
                }

                Pair right = dp[cur][j + 1];
                Pair down = dp[prv][j];
                Pair diag = dp[prv][j + 1];

                int prevMax = -1;
                if (right.ways > 0) prevMax = Math.max(prevMax, right.sum);
                if (down.ways > 0) prevMax = Math.max(prevMax, down.sum);
                if (diag.ways > 0) prevMax = Math.max(prevMax, diag.sum);

                if (prevMax == -1) {
                    dp[cur][j].sum = 0;
                    dp[cur][j].ways = -1;
                    continue;
                }

                long ways = 0;
                if (right.ways > 0 && right.sum == prevMax) ways += right.ways;
                if (down.ways > 0 && down.sum == prevMax) ways += down.ways;
                if (diag.ways > 0 && diag.sum == prevMax) ways += diag.ways;

                dp[cur][j].sum = prevMax + (c - '0');
                dp[cur][j].ways = (int) (ways % MOD);
            }
        }

        Pair ans = dp[0][0];
        if (ans.ways <= 0) return new int[]{0, 0};
        return new int[]{ans.sum, ans.ways};
    }
}