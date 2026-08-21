# 3116. Kth Smallest Amount With Single Denomination Combination

# You are given an integer array coins representing coins of different denominations and an integer k.

# You have an infinite number of coins of each denomination. However, you are not allowed to combine coins of different denominations.

# Return the kth smallest amount that can be made using these coins.

 

# Example 1:

# Input: coins = [3,6,9], k = 3

# Output: 9

# Explanation: The given coins can make the following amounts:
# Coin 3 produces multiples of 3: 3, 6, 9, 12, 15, etc.
# Coin 6 produces multiples of 6: 6, 12, 18, 24, etc.
# Coin 9 produces multiples of 9: 9, 18, 27, 36, etc.
# All of the coins combined produce: 3, 6, 9, 12, 15, etc.

# Example 2:

# Input: coins = [5,2], k = 7

# Output: 12

# Explanation: The given coins can make the following amounts:
# Coin 5 produces multiples of 5: 5, 10, 15, 20, etc.
# Coin 2 produces multiples of 2: 2, 4, 6, 8, 10, 12, etc.
# All of the coins combined produce: 2, 4, 5, 6, 8, 10, 12, 14, 15, etc.

 

# Constraints:

# 1 <= coins.length <= 15
# 1 <= coins[i] <= 25
# 1 <= k <= 2 * 10^9
# coins contains pairwise distinct integers.




# Solution:



class Solution(object):

    def findKthSmallest(self, coins, k):
        """
        :type coins: List[int]
        :type k: int
        :rtype: int
        """

        def gcd(a, b):
            while b:
                a, b = b, a % b
            return a

        def popcount(x):
            cnt = 0
            while x:
                cnt += x & 1
                x >>= 1
            return cnt

        coins.sort()

        if coins[0] == 1:
            return k

        # Remove redundant coins.
        valid = [False] * 26

        for c in coins:
            valid[c] = True
            for r in range(2 * c, 26, c):
                valid[r] = False

        coins = [i for i in range(1, 26) if valid[i]]

        if len(coins) == 1:
            return coins[0] * k

        n = len(coins)
        bit_mask = (1 << n) - 1

        # LCM for every subset
        dp = [0] * (1 << n)

        for mask in range(1, bit_mask + 1):
            lcm = 1

            for i in range(n):
                if mask & (1 << i):
                    lcm = lcm // gcd(lcm, coins[i]) * coins[i]

            dp[mask] = lcm

        # Inclusion-exclusion
        def count(x):
            ans = 0

            for mask in range(1, bit_mask + 1):
                if popcount(mask) & 1:
                    ans += x // dp[mask]
                else:
                    ans -= x // dp[mask]

            return ans

        # Binary search
        left = k + 1
        right = coins[0] * k
        ans = right

        while left <= right:
            mid = left + (right - left) // 2

            if count(mid) >= k:
                ans = mid
                right = mid - 1
            else:
                left = mid + 1

        return ans