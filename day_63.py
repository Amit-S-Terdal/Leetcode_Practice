# 2818. Apply Operations to Maximize Score

# You are given an array nums of n positive integers and an integer k.

# Initially, you start with a score of 1. You have to maximize your score by applying the following operation at most k times:

# Choose any non-empty subarray nums[l, ..., r] that you haven't chosen previously.
# Choose an element x of nums[l, ..., r] with the highest prime score. If multiple such elements exist, choose the one with the smallest index.
# Multiply your score by x.
# Here, nums[l, ..., r] denotes the subarray of nums starting at index l and ending at the index r, both ends being inclusive.

# The prime score of an integer x is equal to the number of distinct prime factors of x. For example, the prime score of 300 is 3 since 300 = 2 * 2 * 3 * 5 * 5.

# Return the maximum possible score after applying at most k operations.

# Since the answer may be large, return it modulo 109 + 7.

 

# Example 1:

# Input: nums = [8,3,9,3,8], k = 2
# Output: 81
# Explanation: To get a score of 81, we can apply the following operations:
# - Choose subarray nums[2, ..., 2]. nums[2] is the only element in this subarray. Hence, we multiply the score by nums[2]. The score becomes 1 * 9 = 9.
# - Choose subarray nums[2, ..., 3]. Both nums[2] and nums[3] have a prime score of 1, but nums[2] has the smaller index. Hence, we multiply the score by nums[2]. The score becomes 9 * 9 = 81.
# It can be proven that 81 is the highest score one can obtain.
# Example 2:

# Input: nums = [19,12,14,6,10,18], k = 3
# Output: 4788
# Explanation: To get a score of 4788, we can apply the following operations: 
# - Choose subarray nums[0, ..., 0]. nums[0] is the only element in this subarray. Hence, we multiply the score by nums[0]. The score becomes 1 * 19 = 19.
# - Choose subarray nums[5, ..., 5]. nums[5] is the only element in this subarray. Hence, we multiply the score by nums[5]. The score becomes 19 * 18 = 342.
# - Choose subarray nums[2, ..., 3]. Both nums[2] and nums[3] have a prime score of 2, but nums[2] has the smaller index. Hence, we multipy the score by nums[2]. The score becomes 342 * 14 = 4788.
# It can be proven that 4788 is the highest score one can obtain.
 

# Constraints:

# 1 <= nums.length == n <= 10^5
# 1 <= nums[i] <= 10^5
# 1 <= k <= min(n * (n + 1) / 2, 10^9)

# Solution:

import heapq
import math

class Solution(object):
    MOD = 10**9 + 7

    def maximumScore(self, nums, k):
        """
        :type nums: List[int]
        :type k: int
        :rtype: int
        """
        n = len(nums)

        # Step 1: Compute prime scores
        def get_prime_score(num):
            score = 0
            for factor in range(2, int(math.sqrt(num)) + 1):
                if num % factor == 0:
                    score += 1
                    while num % factor == 0:
                        num //= factor
            if num > 1:
                score += 1
            return score

        prime_scores = [get_prime_score(num) for num in nums]

        # Step 2: Monotonic stack to get next and prev dominant indices
        next_dominant = [n] * n
        prev_dominant = [-1] * n
        stack = []

        for i in range(n):
            while stack and prime_scores[stack[-1]] < prime_scores[i]:
                top = stack.pop()
                next_dominant[top] = i
            if stack:
                prev_dominant[i] = stack[-1]
            stack.append(i)

        # Step 3: Calculate number of subarrays where element is dominant
        subarrays = [0] * n
        for i in range(n):
            left = i - prev_dominant[i]
            right = next_dominant[i] - i
            subarrays[i] = left * right

        # Step 4: Max heap for nums (using negative values since Python heap is min-heap)
        max_heap = [(-nums[i], i) for i in range(n)]
        heapq.heapify(max_heap)

        # Step 5: Binary exponentiation
        def mod_pow(base, exp):
            result = 1
            base %= self.MOD
            while exp > 0:
                if exp % 2 == 1:
                    result = (result * base) % self.MOD
                base = (base * base) % self.MOD
                exp //= 2
            return result

        score = 1
        while k > 0 and max_heap:
            num, i = heapq.heappop(max_heap)
            num = -num
            ops = min(k, subarrays[i])
            score = (score * mod_pow(num, ops)) % self.MOD
            k -= ops

        return score

