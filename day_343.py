# 1458. Max Dot Product of Two Subsequences

# Given two arrays nums1 and nums2.

# Return the maximum dot product between non-empty subsequences of nums1 and nums2 with the same length.

# A subsequence of a array is a new array which is formed from the original array by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, [2,3,5] is a subsequence of [1,2,3,4,5] while [1,5,3] is not).

 

# Example 1:

# Input: nums1 = [2,1,-2,5], nums2 = [3,0,-6]
# Output: 18
# Explanation: Take subsequence [2,-2] from nums1 and subsequence [3,-6] from nums2.
# Their dot product is (2*3 + (-2)*(-6)) = 18.
# Example 2:

# Input: nums1 = [3,-2], nums2 = [2,-6,7]
# Output: 21
# Explanation: Take subsequence [3] from nums1 and subsequence [7] from nums2.
# Their dot product is (3*7) = 21.
# Example 3:

# Input: nums1 = [-1,-1], nums2 = [1,1]
# Output: -1
# Explanation: Take subsequence [-1] from nums1 and subsequence [1] from nums2.
# Their dot product is -1.
 

# Constraints:

# 1 <= nums1.length, nums2.length <= 500
# -1000 <= nums1[i], nums2[i] <= 1000




# Solution:




class Solution(object):
    def maxDotProduct(self, nums1, nums2):
        """
        :type nums1: List[int]
        :type nums2: List[int]
        :rtype: int
        """
        n1, n2 = len(nums1), len(nums2)

        # Ensure nums1 is the longer array (space optimization)
        if n1 < n2:
            return self.maxDotProduct(nums2, nums1)

        NEG_INF = -10**18
        dp = [[NEG_INF] * (n2 + 1) for _ in range(2)]
        res = NEG_INF

        for i in range(n1 - 1, -1, -1):
            cur = i & 1
            nxt = (i + 1) & 1

            for j in range(n2 - 1, -1, -1):
                x = nums1[i] * nums2[j]

                tmp = dp[cur][j]
                tmp = max(tmp, x)
                tmp = max(tmp, x + (dp[nxt][j + 1] if i + 1 < n1 and j + 1 < n2 else 0))
                tmp = max(tmp, dp[cur][j + 1])

                dp[cur][j] = max(tmp, dp[nxt][j])
                res = max(res, dp[cur][j])

        return res
