// 1345. Jump Game IV

// Given an array of integers arr, you are initially positioned at the first index of the array.

// In one step you can jump from index i to index:

// i + 1 where: i + 1 < arr.length.
// i - 1 where: i - 1 >= 0.
// j where: arr[i] == arr[j] and i != j.
// Return the minimum number of steps to reach the last index of the array.

// Notice that you can not jump outside of the array at any time.

 

// Example 1:

// Input: arr = [100,-23,-23,404,100,23,23,23,3,404]
// Output: 3
// Explanation: You need three jumps from index 0 --> 4 --> 3 --> 9. Note that index 9 is the last index of the array.
// Example 2:

// Input: arr = [7]
// Output: 0
// Explanation: Start index is the last index. You do not need to jump.
// Example 3:

// Input: arr = [7,6,9,6,9,6,9,7]
// Output: 1
// Explanation: You can jump directly from index 0 to index 7 which is last index of the array.
 

// Constraints:

// 1 <= arr.length <= 5 * 10^4
// -10^8 <= arr[i] <= 10^8




// Solution:



import java.util.*;

class Solution {
    static final int N = 50001;

    public int minJumps(int[] arr) {
        int n = arr.length;

        boolean[] vis = new boolean[N];
        int[] q = new int[N];
        int[] nxt = new int[N];

        // value -> head index of linked list
        HashMap<Integer, Integer> dict = new HashMap<>(n * 2);

        // Build linked lists of equal values
        for (int i = 0; i < n; i++) {
            int x = arr[i];

            if (!dict.containsKey(x)) {
                nxt[i] = -1;
            } else {
                nxt[i] = dict.get(x);
            }

            dict.put(x, i);
        }

        int front = 0, back = 0;

        q[back++] = 0;
        vis[0] = true;

        for (int step = 0; front < back; step++) {

            int size = back - front;

            while (size-- > 0) {

                int cur = q[front++];

                if (cur == n - 1) {
                    return step;
                }

                // cur - 1
                if (cur - 1 >= 0 && !vis[cur - 1]) {
                    q[back++] = cur - 1;
                    vis[cur - 1] = true;
                }

                // cur + 1
                if (cur + 1 < n && !vis[cur + 1]) {
                    q[back++] = cur + 1;
                    vis[cur + 1] = true;
                }

                // Same value jumps
                int x = arr[cur];

                int head = dict.getOrDefault(x, -1);

                for (int idx = head; idx != -1; idx = nxt[idx]) {
                    if (!vis[idx]) {
                        q[back++] = idx;
                        vis[idx] = true;
                    }
                }

                // Critical optimization:
                // prevent revisiting same-value chain again
                dict.put(x, -1);
            }
        }

        return -1;
    }
}