# 1751. Maximum Number of Events That Can Be Attended II

# You are given an array of events where events[i] = [startDayi, endDayi, valuei]. The ith event starts at startDayi and ends at endDayi, and if you attend this event, you will receive a value of valuei. You are also given an integer k which represents the maximum number of events you can attend.

# You can only attend one event at a time. If you choose to attend an event, you must attend the entire event. Note that the end day is inclusive: that is, you cannot attend two events where one of them starts and the other ends on the same day.

# Return the maximum sum of values that you can receive by attending events.

 

# Example 1:



# Input: events = [[1,2,4],[3,4,3],[2,3,1]], k = 2
# Output: 7
# Explanation: Choose the green events, 0 and 1 (0-indexed) for a total value of 4 + 3 = 7.
# Example 2:



# Input: events = [[1,2,4],[3,4,3],[2,3,10]], k = 2
# Output: 10
# Explanation: Choose event 2 for a total value of 10.
# Notice that you cannot attend any other event as they overlap, and that you do not have to attend k events.
# Example 3:



# Input: events = [[1,1,1],[2,2,2],[3,3,3],[4,4,4]], k = 3
# Output: 9
# Explanation: Although the events do not overlap, you can only attend 3 events. Pick the highest valued three.
 

# Constraints:

# 1 <= k <= events.length
# 1 <= k * events.length <= 10^6
# 1 <= startDayi <= endDayi <= 10^9
# 1 <= valuei <= 10^6


# Solution: 


import bisect

class Solution(object):
    def maxValue(self, events, k):
        """
        :type events: List[List[int]]
        :type k: int
        :rtype: int
        """
        # Sort events by end time
        events.sort(key=lambda x: x[1])
        n = len(events)

        # Initialize DP table
        dp = [[0] * (k + 1) for _ in range(n + 1)]

        # Extract start times for binary search
        end_times = [event[1] for event in events]

        for i in range(1, n + 1):
            start, end, value = events[i - 1]
            prev = self.binarySearch(events, start)

            for j in range(1, k + 1):
                dp[i][j] = max(dp[i - 1][j], dp[prev + 1][j - 1] + value)

        return dp[n][k]

    def binarySearch(self, events, currentStart):
        left, right = 0, len(events) - 1
        result = -1

        while left <= right:
            mid = (left + right) // 2
            if events[mid][1] < currentStart:
                result = mid
                left = mid + 1
            else:
                right = mid - 1

        return result
