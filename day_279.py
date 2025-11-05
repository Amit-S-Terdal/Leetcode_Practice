# 3321. Find X-Sum of All K-Long Subarrays II

# You are given an array nums of n integers and two integers k and x.

# The x-sum of an array is calculated by the following procedure:

# Count the occurrences of all elements in the array.
# Keep only the occurrences of the top x most frequent elements. If two elements have the same number of occurrences, the element with the bigger value is considered more frequent.
# Calculate the sum of the resulting array.
# Note that if an array has less than x distinct elements, its x-sum is the sum of the array.

# Return an integer array answer of length n - k + 1 where answer[i] is the x-sum of the subarray nums[i..i + k - 1].

 

# Example 1:

# Input: nums = [1,1,2,2,3,4,2,3], k = 6, x = 2

# Output: [6,10,12]

# Explanation:

# For subarray [1, 1, 2, 2, 3, 4], only elements 1 and 2 will be kept in the resulting array. Hence, answer[0] = 1 + 1 + 2 + 2.
# For subarray [1, 2, 2, 3, 4, 2], only elements 2 and 4 will be kept in the resulting array. Hence, answer[1] = 2 + 2 + 2 + 4. Note that 4 is kept in the array since it is bigger than 3 and 1 which occur the same number of times.
# For subarray [2, 2, 3, 4, 2, 3], only elements 2 and 3 are kept in the resulting array. Hence, answer[2] = 2 + 2 + 2 + 3 + 3.
# Example 2:

# Input: nums = [3,8,7,8,7,5], k = 2, x = 2

# Output: [11,15,15,15,12]

# Explanation:

# Since k == x, answer[i] is equal to the sum of the subarray nums[i..i + k - 1].

 

# Constraints:

# nums.length == n
# 1 <= n <= 10^5
# 1 <= nums[i] <= 10^9
# 1 <= x <= k <= nums.length



# Solution:






class Solution(object):
    def findXSum(self, nums, k, x):
        """
        :type nums: List[int]
        :type k: int
        :type x: int
        :rtype: List[int]
        """

        res = []

        counts = defaultdict(int)
        for i in range(k):
            counts[nums[i]] += 1

        low = []

        for n, c in counts.items():
            heapq.heappush(low, (-c, -n))

        value = 0
        high = []
        high_nums = set()

        while len(high_nums) < x and low:
            c, n = heapq.heappop(low)
            heapq.heappush(high, (-c, -n))
            high_nums.add(-n)
            value += c * n

        res.append(value)

        def process_num(num):
            if num in high_nums:
                heapq.heappush(high, (counts[num], num))
            else:
                heapq.heappush(low, (-counts[num], -num))

        def clean_low():
            while low and (counts[-low[0][1]] != -low[0][0] or -low[0][1] in high_nums):
                heapq.heappop(low)

        def clean_high():
            while high and (counts[high[0][1]] != high[0][0] or high[0][1] not in high_nums):
                heapq.heappop(high)

        
        for i in range(k, len(nums)):
            leaving = nums[i - k]
            entering = nums[i]

            if leaving == entering:
                res.append(value)
                continue

            counts[leaving] -= 1
            counts[entering] += 1

            if leaving in high_nums:
                value -= leaving

            if entering in high_nums:
                value += entering

            process_num(leaving)
            process_num(entering)

            clean_low()
            clean_high()

            if low and high:
                if -low[0][0] > high[0][0] or (-low[0][0] >= high[0][0] and -low[0][1] > high[0][1]):
                    new_count, new_high = heapq.heappop(low)
                    old_count, old_high = heapq.heappop(high)
                    high_nums.remove(old_high)
                    high_nums.add(-new_high)

                    value -= (old_high * counts[old_high])
                    value += (-new_high * counts[-new_high])
                    heapq.heappush(high, (-new_count, -new_high))
                    heapq.heappush(low, (-old_count, -old_high))

            clean_low()

            if low and len(high_nums) < x:
                new_count, new_high = heapq.heappop(low)
                value += (-new_high * counts[-new_high])
                heapq.heappush(high, (-new_count, -new_high))
                high_nums.add(-new_high)

            res.append(value)

        return res