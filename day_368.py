# 3013. Divide an Array Into Subarrays With Minimum Cost II

# You are given a 0-indexed array of integers nums of length n, and two positive integers k and dist.

# The cost of an array is the value of its first element. For example, the cost of [1,2,3] is 1 while the cost of [3,4,1] is 3.

# You need to divide nums into k disjoint contiguous subarrays, such that the difference between the starting index of the second subarray and the starting index of the kth subarray should be less than or equal to dist. In other words, if you divide nums into the subarrays nums[0..(i1 - 1)], nums[i1..(i2 - 1)], ..., nums[ik-1..(n - 1)], then ik-1 - i1 <= dist.

# Return the minimum possible sum of the cost of these subarrays.

 

# Example 1:

# Input: nums = [1,3,2,6,4,2], k = 3, dist = 3
# Output: 5
# Explanation: The best possible way to divide nums into 3 subarrays is: [1,3], [2,6,4], and [2]. This choice is valid because ik-1 - i1 is 5 - 2 = 3 which is equal to dist. The total cost is nums[0] + nums[2] + nums[5] which is 1 + 2 + 2 = 5.
# It can be shown that there is no possible way to divide nums into 3 subarrays at a cost lower than 5.
# Example 2:

# Input: nums = [10,1,2,2,2,1], k = 4, dist = 3
# Output: 15
# Explanation: The best possible way to divide nums into 4 subarrays is: [10], [1], [2], and [2,2,1]. This choice is valid because ik-1 - i1 is 3 - 1 = 2 which is less than dist. The total cost is nums[0] + nums[1] + nums[2] + nums[3] which is 10 + 1 + 2 + 2 = 15.
# The division [10], [1], [2,2,2], and [1] is not valid, because the difference between ik-1 and i1 is 5 - 1 = 4, which is greater than dist.
# It can be shown that there is no possible way to divide nums into 4 subarrays at a cost lower than 15.
# Example 3:

# Input: nums = [10,8,18,9], k = 3, dist = 1
# Output: 36
# Explanation: The best possible way to divide nums into 4 subarrays is: [10], [8], and [18,9]. This choice is valid because ik-1 - i1 is 2 - 1 = 1 which is equal to dist.The total cost is nums[0] + nums[1] + nums[2] which is 10 + 8 + 18 = 36.
# The division [10], [8,18], and [9] is not valid, because the difference between ik-1 and i1 is 3 - 1 = 2, which is greater than dist.
# It can be shown that there is no possible way to divide nums into 3 subarrays at a cost lower than 36.
 

# Constraints:

# 3 <= n <= 10^5
# 1 <= nums[i] <= 10^9
# 3 <= k <= n
# k - 2 <= dist <= n - 2




# Solution: 


import heapq

class Solution(object):
    def minimumCost(self, nums, k, dist):
        """
        :type nums: List[int]
        :type k: int
        :type dist: int
        :rtype: int
        """

        n = len(nums)
        minCostSum = float("inf")

        # MAX HEAP (store as negatives)
        maxHeap = []

        # Sum of smallest (k - 2) values on right
        minRightSum = 0

        # Set to track indices of chosen (k-2) elements
        indices = set()

        # MIN HEAP for future candidates
        minHeap = []

        # Initial window [2 .. dist+1]
        for i in range(2, min(dist + 2, n)):
            heapq.heappush(maxHeap, [-nums[i], -i, i])
            minRightSum += nums[i]
            indices.add(i)

            if len(indices) > k - 2:
                val, _, idx = heapq.heappop(maxHeap)
                heapq.heappush(minHeap, [-val, idx, idx])
                minRightSum -= -val
                indices.remove(idx)

        # Slide the second subarray start
        for i in range(1, n - (k - 2)):

            # Remove invalid entries
            while maxHeap and maxHeap[0][2] not in indices:
                heapq.heappop(maxHeap)

            while minHeap and minHeap[0][2] <= i:
                heapq.heappop(minHeap)

            # Update answer
            minCostSum = min(minCostSum, nums[0] + nums[i] + minRightSum)

            # Remove element at i+1 if it was selected
            if i + 1 in indices:
                minRightSum -= nums[i + 1]
                indices.remove(i + 1)

                if minHeap:
                    val, _, idx = heapq.heappop(minHeap)
                    heapq.heappush(maxHeap, [-val, -idx, idx])
                    indices.add(idx)
                    minRightSum += val

            # Add new element at i+1+dist
            new_idx = i + 1 + dist
            if new_idx < n:
                heapq.heappush(maxHeap, [-nums[new_idx], -new_idx, new_idx])
                minRightSum += nums[new_idx]
                indices.add(new_idx)

                if len(indices) > k - 2:
                    val, _, idx = heapq.heappop(maxHeap)
                    heapq.heappush(minHeap, [-val, idx, idx])
                    minRightSum -= -val
                    indices.remove(idx)

        return minCostSum
