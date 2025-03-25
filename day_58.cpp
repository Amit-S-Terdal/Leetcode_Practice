// 3169. Count Days Without Meetings

// You are given a positive integer days representing the total number of days an employee is available for work (starting from day 1). You are also given a 2D array meetings of size n where, meetings[i] = [start_i, end_i] represents the starting and ending days of meeting i (inclusive).

// Return the count of days when the employee is available for work but no meetings are scheduled.

// Note: The meetings may overlap.

 

// Example 1:

// Input: days = 10, meetings = [[5,7],[1,3],[9,10]]

// Output: 2

// Explanation:

// There is no meeting scheduled on the 4th and 8th days.

// Example 2:

// Input: days = 5, meetings = [[2,4],[1,3]]

// Output: 1

// Explanation:

// There is no meeting scheduled on the 5th day.

// Example 3:

// Input: days = 6, meetings = [[1,6]]

// Output: 0

// Explanation:

// Meetings are scheduled for all working days.

 

// Constraints:

// 1 <= days <= 109
// 1 <= meetings.length <= 105
// meetings[i].length == 2
// 1 <= meetings[i][0] <= meetings[i][1] <= days

// Solution:

#include <vector>
#include <algorithm>

using namespace std;

class Solution {
public:
    int countDays(int days, vector<vector<int>>& meetings) {
        if (meetings.empty()) return days;
        
        // Sort meetings based on start time
        sort(meetings.begin(), meetings.end());
        
        vector<vector<int>> merged;
        merged.push_back(meetings[0]);
        
        for (size_t i = 1; i < meetings.size(); ++i) {
            vector<int>& last = merged.back();
            if (meetings[i][0] <= last[1] + 1) {
                // Overlapping or adjacent intervals, merge them
                last[1] = max(last[1], meetings[i][1]);
            } else {
                merged.push_back(meetings[i]);
            }
        }
        
        int meetingDays = 0;
        for (const auto& interval : merged) {
            meetingDays += interval[1] - interval[0] + 1;
        }
        
        return max(days - meetingDays, 0);
    }
};