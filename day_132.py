# 386. Lexicographical Numbers

# Given an integer n, return all the numbers in the range [1, n] sorted in lexicographical order.

# You must write an algorithm that runs in O(n) time and uses O(1) extra space. 

 

# Example 1:

# Input: n = 13
# Output: [1,10,11,12,13,2,3,4,5,6,7,8,9]
# Example 2:

# Input: n = 2
# Output: [1,2]
 

# Constraints:

# 1 <= n <= 5 * 10^4


# Solution: 


class Solution(object):
    def lexicalOrder(self, n):
        """
        :type n: int
        :rtype: List[int]
        """
        def solve(prod, n, ans):
            if prod > n:
                return
            ans.append(prod)
            for j in range(10):
                next_val = prod * 10 + j
                if next_val > n:
                    return
                solve(next_val, n, ans)

        ans = []
        for i in range(1, 10):
            solve(i, n, ans)
        return ans
