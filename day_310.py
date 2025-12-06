# 3578. Count Partitions With Max-Min Difference at Most K

# You are given an integer array nums and an integer k. Your task is to partition nums into one or more non-empty contiguous segments such that in each segment, the difference between its maximum and minimum elements is at most k.

# Return the total number of ways to partition nums under this condition.

# Since the answer may be too large, return it modulo 109 + 7.

 

# Example 1:

# Input: nums = [9,4,1,3,7], k = 4

# Output: 6

# Explanation:

# There are 6 valid partitions where the difference between the maximum and minimum elements in each segment is at most k = 4:

# [[9], [4], [1], [3], [7]]
# [[9], [4], [1], [3, 7]]
# [[9], [4], [1, 3], [7]]
# [[9], [4, 1], [3], [7]]
# [[9], [4, 1], [3, 7]]
# [[9], [4, 1, 3], [7]]
# Example 2:

# Input: nums = [3,3,4], k = 0

# Output: 2

# Explanation:

# There are 2 valid partitions that satisfy the given conditions:

# [[3], [3], [4]]
# [[3, 3], [4]]
 

# Constraints:

# 2 <= nums.length <= 5 * 10^4
# 1 <= nums[i] <= 10^9
# 0 <= k <= 10^9



# Solution: 



class Solution(object):
    def countPartitions(self, nums, k):
        """
        :type nums: List[int]
        :type k: int
        :rtype: int
        """
        N = 50000
        MOD = 1000000007
        
        # Initialize monotonic queues
        qMax = [0] * N
        qMin = [0] * N
        frontX, backX, frontN, backN = 0, -1, 0, -1
        
        n = len(nums)
        cnt = 0
        
        # Initialize sum array for dynamic programming
        sum = [0] * (n + 2)
        sum[1] = 1  # Base case
        
        l = 0
        for r in range(n):
            x = nums[r]

            # max queue
            while backX >= frontX and qMax[backX] < x:
                backX -= 1
            backX += 1
            qMax[backX] = x

            # min queue
            while backN >= frontN and qMin[backN] > x:
                backN -= 1
            backN += 1
            qMin[backN] = x

            # shrink window
            while qMax[frontX] - qMin[frontN] > k:
                y = nums[l]
                if qMax[frontX] == y:
                    frontX += 1
                if qMin[frontN] == y:
                    frontN += 1
                l += 1

            cnt = (MOD + sum[r + 1] - sum[l]) % MOD
            sum[r + 2] = (sum[r + 1] + cnt) % MOD
        
        return cnt
