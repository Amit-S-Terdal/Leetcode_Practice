// 1751. Maximum Number of Events That Can Be Attended II

// You are given an array of events where events[i] = [startDayi, endDayi, valuei]. The ith event starts at startDayi and ends at endDayi, and if you attend this event, you will receive a value of valuei. You are also given an integer k which represents the maximum number of events you can attend.

// You can only attend one event at a time. If you choose to attend an event, you must attend the entire event. Note that the end day is inclusive: that is, you cannot attend two events where one of them starts and the other ends on the same day.

// Return the maximum sum of values that you can receive by attending events.

 

// Example 1:



// Input: events = [[1,2,4],[3,4,3],[2,3,1]], k = 2
// Output: 7
// Explanation: Choose the green events, 0 and 1 (0-indexed) for a total value of 4 + 3 = 7.
// Example 2:



// Input: events = [[1,2,4],[3,4,3],[2,3,10]], k = 2
// Output: 10
// Explanation: Choose event 2 for a total value of 10.
// Notice that you cannot attend any other event as they overlap, and that you do not have to attend k events.
// Example 3:



// Input: events = [[1,1,1],[2,2,2],[3,3,3],[4,4,4]], k = 3
// Output: 9
// Explanation: Although the events do not overlap, you can only attend 3 events. Pick the highest valued three.
 

// Constraints:

// 1 <= k <= events.length
// 1 <= k * events.length <= 10^6
// 1 <= startDayi <= endDayi <= 10^9
// 1 <= valuei <= 10^6


// Solution: 


class Solution {
    public:
        int maxValue(vector<vector<int>>& events, int k) {
            sort(events.begin(), events.end(), [](const vector<int>& a, const vector<int>& b) {
                return a[1] < b[1];
            });
    
            int n = events.size();
            vector<vector<int>> dp(n + 1, vector<int>(k + 1, 0));
    
            for (int i = 1; i <= n; ++i) {
                int prev = binarySearch(events, events[i - 1][0]);
    
                for (int j = 1; j <= k; ++j) {
                    dp[i][j] = max(dp[i - 1][j], dp[prev + 1][j - 1] + events[i - 1][2]);
                }
            }
    
            return dp[n][k];
        }
    
    private:
        int binarySearch(vector<vector<int>>& events, int currentStart) {
            int left = 0, right = events.size() - 1;
            int result = -1;
    
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (events[mid][1] < currentStart) {
                    result = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
    
            return result;
        }
    };