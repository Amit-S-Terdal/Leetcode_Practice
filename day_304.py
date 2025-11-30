# 1590. Make Sum Divisible by P


# Given an array of positive integers nums, remove the smallest subarray (possibly empty) such that the sum of the remaining elements is divisible by p. It is not allowed to remove the whole array.

# Return the length of the smallest subarray that you need to remove, or -1 if it's impossible.

# A subarray is defined as a contiguous block of elements in the array.

 

# Example 1:

# Input: nums = [3,1,4,2], p = 6
# Output: 1
# Explanation: The sum of the elements in nums is 10, which is not divisible by 6. We can remove the subarray [4], and the sum of the remaining elements is 6, which is divisible by 6.
# Example 2:

# Input: nums = [6,3,5,2], p = 9
# Output: 2
# Explanation: We cannot remove a single element to get a sum divisible by 9. The best way is to remove the subarray [5,2], leaving us with [6,3] with sum 9.
# Example 3:

# Input: nums = [1,2,3], p = 3
# Output: 0
# Explanation: Here the sum is 6. which is already divisible by 3. Thus we do not need to remove anything.
 

# Constraints:

# 1 <= nums.length <= 10^5
# 1 <= nums[i] <= 10^9
# 1 <= p <= 10^9



# Solution: 



class Solution(object):
    def minSubarray(self, nums, p):
        """
        :type nums: List[int]
        :type p: int
        :rtype: int
        """
        n = len(nums)
        modP = sum(nums) % p  # Compute the modulo of the sum of all elements in the list

        # If modP is 0, the answer is 0 because no subarray is needed.
        if modP == 0:
            return 0

        sz = min(n, p)

        # If p <= n, use the array method
        if p <= n:
            return self.array_method(n, modP, nums, p)

        # Hashmap version for the general case
        pos = {0: -1}
        len_subarray = n
        sumP = 0
        for i in range(n):
            sumP = (sumP + nums[i]) % p
            y = (sumP - modP + p) % p
            if y in pos:
                len_subarray = min(len_subarray, i - pos[y])
            pos[sumP] = i

        return -1 if len_subarray == n else len_subarray

    def array_method(self, n, modP, nums, p):
        pos = [float('inf')] * p
        pos[0] = -1
        len_subarray = n
        sumP = 0
        for i in range(n):
            sumP = (sumP + nums[i]) % p
            y = (sumP - modP + p) % p
            if pos[y] != float('inf'):
                len_subarray = min(len_subarray, i - pos[y])
            pos[sumP] = i
        return -1 if len_subarray == n else len_subarray
