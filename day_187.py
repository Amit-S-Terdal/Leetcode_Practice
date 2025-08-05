# 2561. Rearranging Fruits

# You have two fruit baskets containing n fruits each. You are given two 0-indexed integer arrays basket1 and basket2 representing the cost of fruit in each basket. You want to make both baskets equal. To do so, you can use the following operation as many times as you want:

# Chose two indices i and j, and swap the ith fruit of basket1 with the jth fruit of basket2.
# The cost of the swap is min(basket1[i],basket2[j]).
# Two baskets are considered equal if sorting them according to the fruit cost makes them exactly the same baskets.

# Return the minimum cost to make both the baskets equal or -1 if impossible.

 

# Example 1:

# Input: basket1 = [4,2,2,2], basket2 = [1,4,1,2]
# Output: 1
# Explanation: Swap index 1 of basket1 with index 0 of basket2, which has cost 1. Now basket1 = [4,1,2,2] and basket2 = [2,4,1,2]. Rearranging both the arrays makes them equal.
# Example 2:

# Input: basket1 = [2,3,4,1], basket2 = [3,2,5,1]
# Output: -1
# Explanation: It can be shown that it is impossible to make both the baskets equal.
 

# Constraints:

# basket1.length == basket2.length
# 1 <= basket1.length <= 10^5
# 1 <= basket1[i],basket2[i] <= 10^9



# Solution:


class Solution(object):
    def minCost(self, basket1, basket2):
        """
        :type basket1: List[int]
        :type basket2: List[int]
        :rtype: int
        """
        from collections import Counter

        n = len(basket1)
        freq = Counter()

        minX = float('inf')

        for i in range(n):
            freq[basket1[i]] += 1
            freq[basket2[i]] -= 1
            minX = min(minX, basket1[i], basket2[i])

        B1 = []
        B2 = []

        for x, f in freq.items():
            if f % 2 != 0:
                return -1
            if f > 0:
                B1.append([x, f // 2])
            elif f < 0:
                B2.append([x, -f // 2])

        B1.sort()  # sort by value ascending
        B2.sort(reverse=True)  # sort by value descending

        cost = 0
        i, j = 0, 0

        while i < len(B1) and j < len(B2):
            b1, f1 = B1[i]
            b2, f2 = B2[j]
            f0 = min(f1, f2)

            swapCost = min(min(b1, b2), 2 * minX)
            cost += swapCost * f0

            B1[i][1] -= f0
            B2[j][1] -= f0

            if B1[i][1] == 0:
                i += 1
            if B2[j][1] == 0:
                j += 1

        return cost
