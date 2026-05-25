// 1340. Jump Game V

// Given an array of integers arr and an integer d. In one step you can jump from index i to index:

// i + x where: i + x < arr.length and  0 < x <= d.
// i - x where: i - x >= 0 and  0 < x <= d.
// In addition, you can only jump from index i to index j if arr[i] > arr[j] and arr[i] > arr[k] for all indices k between i and j (More formally min(i, j) < k < max(i, j)).

// You can choose any index of the array and start jumping. Return the maximum number of indices you can visit.

// Notice that you can not jump outside of the array at any time.

 

// Example 1:


// Input: arr = [6,4,14,6,8,13,9,7,10,6,12], d = 2
// Output: 4
// Explanation: You can start at index 10. You can jump 10 --> 8 --> 6 --> 7 as shown.
// Note that if you start at index 6 you can only jump to index 7. You cannot jump to index 5 because 13 > 9. You cannot jump to index 4 because index 5 is between index 4 and 6 and 13 > 9.
// Similarly You cannot jump from index 3 to index 2 or index 1.
// Example 2:

// Input: arr = [3,3,3,3,3], d = 3
// Output: 1
// Explanation: You can start at any index. You always cannot jump to any index.
// Example 3:

// Input: arr = [7,6,5,4,3,2,1], d = 1
// Output: 7
// Explanation: Start at index 0. You can visit all the indicies. 
 

// Constraints:

// 1 <= arr.length <= 1000
// 1 <= arr[i] <= 10^5
// 1 <= d <= arr.length



// Solution:




class Solution {

    static class Edge {
        int to, nxt;

        Edge(int to, int nxt) {
            this.to = to;
            this.nxt = nxt;
        }
    }

    static final int V = 1000;
    static final int E = 1000 * 999 / 2;

    static Edge[] POOL = new Edge[E];
    static int idx;

    static int[] adj = new int[V];
    static int[] deg = new int[V];
    static int[] dp = new int[V];

    static int[] q = new int[V];
    static int front, back;

    static int[] stack = new int[V];
    static int top;

    static void addEdge(int u, int v) {
        POOL[idx] = new Edge(v, adj[u]);
        adj[u] = idx++;
        deg[v]++;
    }

    public int maxJumps(int[] arr, int d) {
        int n = arr.length;

        // reset
        idx = 0;

        for (int i = 0; i < n; i++) {
            adj[i] = -1;
            deg[i] = 0;
            dp[i] = 1;
        }

        // monotonic stack (left -> right)
        top = -1;

        for (int i = 0; i < n; i++) {
            int x = arr[i];

            while (top > -1 && arr[stack[top]] < x) {
                int j = stack[top--];

                if (i - j <= d) {
                    addEdge(j, i);
                }
            }

            stack[++top] = i;
        }

        // monotonic stack (right -> left)
        top = -1;

        for (int i = n - 1; i >= 0; i--) {
            int x = arr[i];

            while (top > -1 && arr[stack[top]] < x) {
                int j = stack[top--];

                if (j - i <= d) {
                    addEdge(j, i);
                }
            }

            stack[++top] = i;
        }

        // topological queue
        front = back = 0;

        for (int i = 0; i < n; i++) {
            if (deg[i] == 0) {
                q[back++] = i;
            }
        }

        while (front < back) {
            int u = q[front++];

            for (int e = adj[u]; e != -1; e = POOL[e].nxt) {
                int v = POOL[e].to;

                dp[v] = Math.max(dp[v], dp[u] + 1);

                if (--deg[v] == 0) {
                    q[back++] = v;
                }
            }
        }

        int ans = 1;

        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }
}