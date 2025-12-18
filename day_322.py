# 3652. Best Time to Buy and Sell Stock using Strategy

# You are given two integer arrays prices and strategy, where:

# prices[i] is the price of a given stock on the ith day.
# strategy[i] represents a trading action on the ith day, where:
# -1 indicates buying one unit of the stock.
# 0 indicates holding the stock.
# 1 indicates selling one unit of the stock.
# You are also given an even integer k, and may perform at most one modification to strategy. A modification consists of:

# Selecting exactly k consecutive elements in strategy.
# Set the first k / 2 elements to 0 (hold).
# Set the last k / 2 elements to 1 (sell).
# The profit is defined as the sum of strategy[i] * prices[i] across all days.

# Return the maximum possible profit you can achieve.

# Note: There are no constraints on budget or stock ownership, so all buy and sell operations are feasible regardless of past actions.

 

# Example 1:

# Input: prices = [4,2,8], strategy = [-1,0,1], k = 2

# Output: 10

# Explanation:

# Modification	Strategy	Profit Calculation	Profit
# Original	[-1, 0, 1]	(-1 × 4) + (0 × 2) + (1 × 8) = -4 + 0 + 8	4
# Modify [0, 1]	[0, 1, 1]	(0 × 4) + (1 × 2) + (1 × 8) = 0 + 2 + 8	10
# Modify [1, 2]	[-1, 0, 1]	(-1 × 4) + (0 × 2) + (1 × 8) = -4 + 0 + 8	4
# Thus, the maximum possible profit is 10, which is achieved by modifying the subarray [0, 1]​​​​​​​.

# Example 2:

# Input: prices = [5,4,3], strategy = [1,1,0], k = 2

# Output: 9

# Explanation:

# Modification	Strategy	Profit Calculation	Profit
# Original	[1, 1, 0]	(1 × 5) + (1 × 4) + (0 × 3) = 5 + 4 + 0	9
# Modify [0, 1]	[0, 1, 0]	(0 × 5) + (1 × 4) + (0 × 3) = 0 + 4 + 0	4
# Modify [1, 2]	[1, 0, 1]	(1 × 5) + (0 × 4) + (1 × 3) = 5 + 0 + 3	8
# Thus, the maximum possible profit is 9, which is achieved without any modification.

 

# Constraints:

# 2 <= prices.length == strategy.length <= 10^5
# 1 <= prices[i] <= 10^5
# -1 <= strategy[i] <= 1
# 2 <= k <= prices.length
# k is even


# Solution: 




class Solution(object):
    def maxProfit(self, prices, strategy, k):
        """
        :type prices: List[int]
        :type strategy: List[int]
        :type k: int
        :rtype: int
        """
        n = len(prices)
        k2 = k // 2
        
        # Initialize a sum array similar to 'sum' in C++ and accumulate the values.
        sum_arr = [0] * (n + 1)
        for i in range(n):
            sum_arr[i + 1] = sum_arr[i] + strategy[i] * prices[i]
        
        # Use slicing and sum for Python equivalent of 'reduce' in C++
        modify = sum(prices[k2:k])
        
        # Calculate the initial profit
        profit = max(sum_arr[n], modify + sum_arr[n] - sum_arr[k])

        # Iterate over the range and calculate the maximum profit
        for i in range(1, n + 1 - k):
            modify += prices[i + k - 1] - prices[i + k2 - 1]
            profit = max(profit, modify + sum_arr[n] - sum_arr[i + k] + sum_arr[i])

        return profit
