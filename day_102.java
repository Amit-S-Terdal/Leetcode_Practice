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


import java.util.*;

class Solution {

    static class State {
        int x, y, time;

        State(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }
    }

    public int minTimeToReach(int[][] moveTime) {
        int n = moveTime.length;
        int m = moveTime[0].length;
        int[][] dist = new int[n][m];

        // Initialize distances to a large value
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;

        // Min-heap (priority queue) based on time
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.time));
        pq.offer(new State(0, 0, 0));

        int[][] directions = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        while (!pq.isEmpty()) {
            State curr = pq.poll();
            int x = curr.x, y = curr.y, t = curr.time;

            // Early stop if we reached bottom-right
            if (x == n - 1 && y == m - 1) return t;

            if (t > dist[x][y]) continue;

            for (int[] dir : directions) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (nx >= 0 && ny >= 0 && nx < n && ny < m) {
                    int nextTime = Math.max(t, moveTime[nx][ny]) + 1;
                    if (nextTime < dist[nx][ny]) {
                        dist[nx][ny] = nextTime;
                        pq.offer(new State(nx, ny, nextTime));
                    }
                }
            }
        }

        return -1; // Should not happen with valid input
    }
}
