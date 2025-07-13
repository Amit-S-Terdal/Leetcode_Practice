# 3440. Reschedule Meetings for Maximum Free Time II

# You are given an integer eventTime denoting the duration of an event. You are also given two integer arrays startTime and endTime, each of length n.

# These represent the start and end times of n non-overlapping meetings that occur during the event between time t = 0 and time t = eventTime, where the ith meeting occurs during the time [startTime[i], endTime[i]].

# You can reschedule at most one meeting by moving its start time while maintaining the same duration, such that the meetings remain non-overlapping, to maximize the longest continuous period of free time during the event.

# Return the maximum amount of free time possible after rearranging the meetings.

# Note that the meetings can not be rescheduled to a time outside the event and they should remain non-overlapping.

# Note: In this version, it is valid for the relative ordering of the meetings to change after rescheduling one meeting.

 

# Example 1:

# Input: eventTime = 5, startTime = [1,3], endTime = [2,5]

# Output: 2

# Explanation:



# Reschedule the meeting at [1, 2] to [2, 3], leaving no meetings during the time [0, 2].

# Example 2:

# Input: eventTime = 10, startTime = [0,7,9], endTime = [1,8,10]

# Output: 7

# Explanation:



# Reschedule the meeting at [0, 1] to [8, 9], leaving no meetings during the time [0, 7].

# Example 3:

# Input: eventTime = 10, startTime = [0,3,7,9], endTime = [1,4,8,10]

# Output: 6

# Explanation:



# Reschedule the meeting at [3, 4] to [8, 9], leaving no meetings during the time [1, 7].

# Example 4:

# Input: eventTime = 5, startTime = [0,1,2,3,4], endTime = [1,2,3,4,5]

# Output: 0

# Explanation:

# There is no time during the event not occupied by meetings.

 

# Constraints:

# 1 <= eventTime <= 10^9
# n == startTime.length == endTime.length
# 2 <= n <= 10^5
# 0 <= startTime[i] < endTime[i] <= eventTime
# endTime[i] <= startTime[i + 1] where i lies in the range [0, n - 2].



# Solution:

class Solution(object):
    def maxFreeTime(self, eventTime, startTime, endTime):
        """
        :type eventTime: int
        :type startTime: List[int]
        :type endTime: List[int]
        :rtype: int
        """
        n = len(startTime)
        startTime = startTime + [eventTime]  # Add dummy event time
        endTime = endTime + [0]  # To avoid index errors (not actually used)

        # Compute initial gaps and IDs
        gap = [startTime[0], startTime[1] - endTime[0], startTime[2] - endTime[1]]
        id = [0, 1, 2]

        # Bubble sort for top 3 gaps
        def swap(i, j):
            gap[i], gap[j] = gap[j], gap[i]
            id[i], id[j] = id[j], id[i]

        if gap[0] < gap[1]: swap(0, 1)
        if gap[1] < gap[2]: swap(1, 2)
        if gap[0] < gap[1]: swap(0, 1)

        # Track top 3 largest gaps
        for i in range(3, n + 1):
            curr_gap = startTime[i] - endTime[i - 1]
            if curr_gap >= gap[0]:
                gap[2], gap[1] = gap[1], gap[0]
                gap[0] = curr_gap
                id[2], id[1] = id[1], id[0]
                id[0] = i
            elif curr_gap >= gap[1]:
                gap[2] = gap[1]
                gap[1] = curr_gap
                id[2] = id[1]
                id[1] = i
            elif curr_gap > gap[2]:
                gap[2] = curr_gap
                id[2] = i

        def jump_or_shift(i):
            busy = endTime[i] - startTime[i]
            pass_check = False
            for k in range(3):
                if gap[k] >= busy and (id[k] != i and id[k] != i + 1):
                    pass_check = True
                    break
            end = 0 if i == 0 else endTime[i - 1]
            jump = startTime[i + 1] - end
            return jump if pass_check else jump - busy

        ans = 0
        for i in range(n):
            ans = max(ans, jump_or_shift(i))
        return ans

