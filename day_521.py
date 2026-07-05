# 1301. Number of Paths with Max Score

# You are given a square board of characters. You can move on the board starting at the bottom right square marked with the character 'S'.

# You need to reach the top left square marked with the character 'E'. The rest of the squares are labeled either with a numeric character 1, 2, ..., 9 or with an obstacle 'X'. In one move you can go up, left or up-left (diagonally) only if there is no obstacle there.

# Return a list of two integers: the first integer is the maximum sum of numeric characters you can collect, and the second is the number of such paths that you can take to get that maximum sum, taken modulo 10^9 + 7.

# In case there is no path, return [0, 0].

 

# Example 1:

# Input: board = ["E23","2X2","12S"]
# Output: [7,1]
# Example 2:

# Input: board = ["E12","1X1","21S"]
# Output: [4,2]
# Example 3:

# Input: board = ["E11","XXX","11S"]
# Output: [0,0]
 

# Constraints:

# 2 <= board.length == board[i].length <= 100




# Solution: 




class Solution(object):
    def pathsWithMaxScore(self, board):
        """
        :type board: List[str]
        :rtype: List[int]
        """
        MOD = 10**9 + 7
        n = len(board)

        board = [list(row) for row in board]
        board[0][0] = '0'

        NONE = (0, -1)
        dp = [[NONE] * n for _ in range(2)]

        last = (n - 1) & 1
        dp[last][n - 1] = (0, 1)

        # Last row
        for j in range(n - 2, -1, -1):
            c = board[n - 1][j]
            if c == 'X':
                break
            s, _ = dp[last][j + 1]
            dp[last][j] = (s + int(c), 1)

        for i in range(n - 2, -1, -1):
            cur = i & 1
            prv = (i + 1) & 1

            c = board[i][n - 1]
            if c == 'X' or dp[prv][n - 1][1] == -1:
                dp[cur][n - 1] = NONE
            else:
                dp[cur][n - 1] = (dp[prv][n - 1][0] + int(c), 1)

            for j in range(n - 2, -1, -1):
                c = board[i][j]

                if c == 'X':
                    dp[cur][j] = NONE
                    continue

                r0, r1 = dp[cur][j + 1]
                d0, d1 = dp[prv][j]
                s0, s1 = dp[prv][j + 1]

                prevMax = -1
                if r1 > 0:
                    prevMax = max(prevMax, r0)
                if d1 > 0:
                    prevMax = max(prevMax, d0)
                if s1 > 0:
                    prevMax = max(prevMax, s0)

                if prevMax == -1:
                    dp[cur][j] = NONE
                    continue

                ways = 0
                if r1 > 0 and r0 == prevMax:
                    ways += r1
                if d1 > 0 and d0 == prevMax:
                    ways += d1
                if s1 > 0 and s0 == prevMax:
                    ways += s1

                dp[cur][j] = (prevMax + int(c), ways % MOD)

        score, ways = dp[0][0]
        return [0, 0] if ways <= 0 else [score, ways]