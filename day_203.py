# # 679. 24 Game

# You are given an integer array cards of length 4. You have four cards, each containing a number in the range [1, 9]. You should arrange the numbers on these cards in a mathematical expression using the operators ['+', '-', '*', '/'] and the parentheses '(' and ')' to get the value 24.

# You are restricted with the following rules:

# The division operator '/' represents real division, not integer division.
# For example, 4 / (1 - 2 / 3) = 4 / (1 / 3) = 12.
# Every operation done is between two numbers. In particular, we cannot use '-' as a unary operator.
# For example, if cards = [1, 1, 1, 1], the expression "-1 - 1 - 1 - 1" is not allowed.
# You cannot concatenate numbers together
# For example, if cards = [1, 2, 1, 2], the expression "12 + 12" is not valid.
# Return true if you can get such expression that evaluates to 24, and false otherwise.

 

# Example 1:

# Input: cards = [4,1,8,7]
# Output: true
# Explanation: (8-4) * (7-1) = 24
# Example 2:

# Input: cards = [1,2,1,2]
# Output: false
 

# Constraints:

# cards.length == 4
# 1 <= cards[i] <= 9

# Solution:


class Solution(object):
    def judgePoint24(self, cards):
        """
        :type cards: List[int]
        :rtype: bool
        """
        eps = 1e-3
        nums = [float(x) for x in cards]

        def dfs(arr):
            if len(arr) == 1:
                return abs(arr[0] - 24.0) < eps

            n = len(arr)
            for i in range(n):
                for j in range(i + 1, n):
                    a, b = arr[i], arr[j]
                    rest = [arr[k] for k in range(n) if k != i and k != j]

                    candidates = [a + b, a - b, b - a, a * b]
                    if abs(b) > eps:
                        candidates.append(a / b)
                    if abs(a) > eps:
                        candidates.append(b / a)

                    for v in candidates:
                        if dfs(rest + [v]):
                            return True
            return False

        return dfs(nums)
