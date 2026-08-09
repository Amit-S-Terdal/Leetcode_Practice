# 1140. Stone Game II

# Alice and Bob continue their games with piles of stones. There are a number of piles arranged in a row, and each pile has a positive integer number of stones piles[i]. The objective of the game is to end with the most stones.

# Alice and Bob take turns, with Alice starting first.

# On each player's turn, that player can take all the stones in the first X remaining piles, where 1 <= X <= 2M. Then, we set M = max(M, X). Initially, M = 1.

# The game continues until all the stones have been taken.

# Assuming Alice and Bob play optimally, return the maximum number of stones Alice can get.

 

# Example 1:

# Input: piles = [2,7,9,4,4]

# Output: 10

# Explanation:

# If Alice takes one pile at the beginning, Bob takes two piles, then Alice takes 2 piles again. Alice can get 2 + 4 + 4 = 10 stones in total.
# If Alice takes two piles at the beginning, then Bob can take all three piles left. In this case, Alice get 2 + 7 = 9 stones in total.
# So we return 10 since it's larger.

# Example 2:

# Input: piles = [1,2,3,4,5,100]

# Output: 104

 

# Constraints:

# 1 <= piles.length <= 100
# 1 <= piles[i] <= 10^4




# Solution:



class Solution(object):
    def stoneGameII(self, piles):
        """
        :type piles: List[int]
        :rtype: int
        """
        n = len(piles)

        dp = [[[-1] * (n + 1) for _ in range(n + 1)] for _ in range(2)]

        def alice(isBob, i, m):
            if i == n:
                return 0

            if dp[isBob][i][m] != -1:
                return dp[isBob][i][m]

            if isBob == 0:
                stones = 0
            else:
                stones = float('inf')

            total = 0
            xN = min(2 * m, n - i)

            for x in range(1, xN + 1):
                total += piles[i + x - 1]

                m2 = max(m, x)

                if isBob:
                    stones = min(
                        stones,
                        alice(0, i + x, m2)
                    )
                else:
                    stones = max(
                        stones,
                        total + alice(1, i + x, m2)
                    )

            dp[isBob][i][m] = stones
            return stones

        return alice(0, 0, 1)