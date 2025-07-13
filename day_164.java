// 3440. Reschedule Meetings for Maximum Free Time II

// You are given an integer eventTime denoting the duration of an event. You are also given two integer arrays startTime and endTime, each of length n.

// These represent the start and end times of n non-overlapping meetings that occur during the event between time t = 0 and time t = eventTime, where the ith meeting occurs during the time [startTime[i], endTime[i]].

// You can reschedule at most one meeting by moving its start time while maintaining the same duration, such that the meetings remain non-overlapping, to maximize the longest continuous period of free time during the event.

// Return the maximum amount of free time possible after rearranging the meetings.

// Note that the meetings can not be rescheduled to a time outside the event and they should remain non-overlapping.

// Note: In this version, it is valid for the relative ordering of the meetings to change after rescheduling one meeting.

 

// Example 1:

// Input: eventTime = 5, startTime = [1,3], endTime = [2,5]

// Output: 2

// Explanation:



// Reschedule the meeting at [1, 2] to [2, 3], leaving no meetings during the time [0, 2].

// Example 2:

// Input: eventTime = 10, startTime = [0,7,9], endTime = [1,8,10]

// Output: 7

// Explanation:



// Reschedule the meeting at [0, 1] to [8, 9], leaving no meetings during the time [0, 7].

// Example 3:

// Input: eventTime = 10, startTime = [0,3,7,9], endTime = [1,4,8,10]

// Output: 6

// Explanation:



// Reschedule the meeting at [3, 4] to [8, 9], leaving no meetings during the time [1, 7].

// Example 4:

// Input: eventTime = 5, startTime = [0,1,2,3,4], endTime = [1,2,3,4,5]

// Output: 0

// Explanation:

// There is no time during the event not occupied by meetings.

 

// Constraints:

// 1 <= eventTime <= 10^9
// n == startTime.length == endTime.length
// 2 <= n <= 10^5
// 0 <= startTime[i] < endTime[i] <= eventTime
// endTime[i] <= startTime[i + 1] where i lies in the range [0, n - 2].



// Solution:


class Solution {
    int[] gap = new int[3];
    int[] id = new int[]{0, 1, 2};
    
    private int jumpOrShift(int i, int[] startTime, int[] endTime) {
        int busy = endTime[i] - startTime[i];
        boolean pass = false;

        for (int k = 0; k < 3; k++) {
            if (gap[k] >= busy && (id[k] != i && id[k] != i + 1)) {
                pass = true;
                break;
            }
        }

        int end = (i == 0) ? 0 : endTime[i - 1];
        int jump = startTime[i + 1] - end;
        return pass ? jump : jump - busy;
    }

    public int maxFreeTime(int eventTime, int[] startTime, int[] endTime) {
        int n = startTime.length;

        // Extend arrays to include the dummy eventTime
        int[] newStartTime = new int[n + 1];
        int[] newEndTime = new int[n + 1];
        System.arraycopy(startTime, 0, newStartTime, 0, n);
        System.arraycopy(endTime, 0, newEndTime, 0, n);
        newStartTime[n] = eventTime; // dummy end time

        gap[0] = newStartTime[0];
        gap[1] = newStartTime[1] - newEndTime[0];
        gap[2] = newStartTime[2] - newEndTime[1];

        // Bubble sort for top 3 gaps
        if (gap[0] < gap[1]) { swap(gap, 0, 1); swap(id, 0, 1); }
        if (gap[1] < gap[2]) { swap(gap, 1, 2); swap(id, 1, 2); }
        if (gap[0] < gap[1]) { swap(gap, 0, 1); swap(id, 0, 1); }

        for (int i = 3; i <= n; i++) {
            int currGap = newStartTime[i] - newEndTime[i - 1];
            if (currGap >= gap[0]) {
                gap[2] = gap[1];
                gap[1] = gap[0];
                gap[0] = currGap;

                id[2] = id[1];
                id[1] = id[0];
                id[0] = i;
            } else if (currGap >= gap[1]) {
                gap[2] = gap[1];
                gap[1] = currGap;

                id[2] = id[1];
                id[1] = i;
            } else if (currGap > gap[2]) {
                gap[2] = currGap;
                id[2] = i;
            }
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, jumpOrShift(i, newStartTime, newEndTime));
        }

        return ans;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }
}
