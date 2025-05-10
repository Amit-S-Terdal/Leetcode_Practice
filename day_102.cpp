// 3341. Find Minimum Time to Reach Last Room I

// There is a dungeon with n x m rooms arranged as a grid.

// You are given a 2D array moveTime of size n x m, where moveTime[i][j] represents the minimum time in seconds when you can start moving to that room. You start from the room (0, 0) at time t = 0 and can move to an adjacent room. Moving between adjacent rooms takes exactly one second.

// Return the minimum time to reach the room (n - 1, m - 1).

// Two rooms are adjacent if they share a common wall, either horizontally or vertically.

 

// Example 1:

// Input: moveTime = [[0,4],[4,4]]

// Output: 6

// Explanation:

// The minimum time required is 6 seconds.

// At time t == 4, move from room (0, 0) to room (1, 0) in one second.
// At time t == 5, move from room (1, 0) to room (1, 1) in one second.
// Example 2:

// Input: moveTime = [[0,0,0],[0,0,0]]

// Output: 3

// Explanation:

// The minimum time required is 3 seconds.

// At time t == 0, move from room (0, 0) to room (1, 0) in one second.
// At time t == 1, move from room (1, 0) to room (1, 1) in one second.
// At time t == 2, move from room (1, 1) to room (1, 2) in one second.
// Example 3:

// Input: moveTime = [[0,1],[1,2]]

// Output: 3

 

// Constraints:

// 2 <= n == moveTime.length <= 50
// 2 <= m == moveTime[i].length <= 50
// 0 <= moveTime[i][j] <= 10^9


// Solution:


#include <vector>
#include <queue>
#include <tuple>
#include <algorithm>

using namespace std;

class State {
public:
    int x, y, time;
    State(int x, int y, int time) : x(x), y(y), time(time) {}
};

class Solution {
public:
    int minTimeToReach(vector<vector<int>>& moveTime) {
        int n = moveTime.size();
        int m = moveTime[0].size();
        const int INF = 0x3f3f3f3f;

        // Directions: right, left, down, up
        int dirs[4][2] = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // d[x][y] = minimum time to reach cell (x, y)
        vector<vector<int>> d(n, vector<int>(m, INF));
        d[0][0] = 0;

        // Min-heap based on time
        auto cmp = [](const State& a, const State& b) {
            return a.time > b.time;
        };
        priority_queue<State, vector<State>, decltype(cmp)> pq(cmp);
        pq.push(State(0, 0, 0));

        while (!pq.empty()) {
            State curr = pq.top();
            pq.pop();

            int x = curr.x, y = curr.y, currTime = curr.time;

            // If we reached the destination
            if (x == n - 1 && y == m - 1) return currTime;

            // Skip if we already found a faster way to this cell
            if (currTime > d[x][y]) continue;

            for (auto& dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue;

                int nextTime = max(currTime, moveTime[nx][ny]) + 1;
                if (nextTime < d[nx][ny]) {
                    d[nx][ny] = nextTime;
                    pq.push(State(nx, ny, nextTime));
                }
            }
        }

        return -1; // Not reachable (should not happen with valid input)
    }
};
