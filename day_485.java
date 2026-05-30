// 3161. Block Placement Queries

// There exists an infinite number line, with its origin at 0 and extending towards the positive x-axis.

// You are given a 2D array queries, which contains two types of queries:

// For a query of type 1, queries[i] = [1, x]. Build an obstacle at distance x from the origin. It is guaranteed that there is no obstacle at distance x when the query is asked.
// For a query of type 2, queries[i] = [2, x, sz]. Check if it is possible to place a block of size sz anywhere in the range [0, x] on the line, such that the block entirely lies in the range [0, x]. A block cannot be placed if it intersects with any obstacle, but it may touch it. Note that you do not actually place the block. Queries are separate.
// Return a boolean array results, where results[i] is true if you can place the block specified in the ith query of type 2, and false otherwise.

 

// Example 1:

// Input: queries = [[1,2],[2,3,3],[2,3,1],[2,2,2]]

// Output: [false,true,true]

// Explanation:



// For query 0, place an obstacle at x = 2. A block of size at most 2 can be placed before x = 3.

// Example 2:

// Input: queries = [[1,7],[2,7,6],[1,2],[2,7,5],[2,7,6]]

// Output: [true,true,false]

// Explanation:



// Place an obstacle at x = 7 for query 0. A block of size at most 7 can be placed before x = 7.
// Place an obstacle at x = 2 for query 2. Now, a block of size at most 5 can be placed before x = 7, and a block of size at most 2 before x = 2.
 

// Constraints:

// 1 <= queries.length <= 15 * 10^4
// 2 <= queries[i].length <= 3
// 1 <= queries[i][0] <= 2
// 1 <= x, sz <= min(5 * 10^4, 3 * queries.length)
// The input is generated such that for queries of type 1, no obstacle exists at distance x when the query is asked.
// The input is generated such that there is at least one query of type 2.




// Solution: 



import java.util.*;

class Solution {

    static class SegmentTree {
        int[] seg;
        int n;

        SegmentTree(int[] arr) {
            n = arr.length;
            seg = new int[4 * Math.max(1, n)];
            if (n > 0) {
                build(arr, 1, 0, n - 1);
            }
        }

        void build(int[] arr, int idx, int l, int r) {
            if (l == r) {
                seg[idx] = arr[l];
                return;
            }

            int m = (l + r) >> 1;

            build(arr, idx * 2, l, m);
            build(arr, idx * 2 + 1, m + 1, r);

            seg[idx] = Math.max(seg[idx * 2], seg[idx * 2 + 1]);
        }

        void update(int idx, int l, int r, int pos, int val) {
            if (l == r) {
                seg[idx] = val;
                return;
            }

            int m = (l + r) >> 1;

            if (pos <= m) {
                update(idx * 2, l, m, pos, val);
            } else {
                update(idx * 2 + 1, m + 1, r, pos, val);
            }

            seg[idx] = Math.max(seg[idx * 2], seg[idx * 2 + 1]);
        }

        int query(int idx, int l, int r, int ql, int qr) {
            if (ql > r || qr < l) return 0;

            if (ql <= l && r <= qr) {
                return seg[idx];
            }

            int m = (l + r) >> 1;

            return Math.max(
                query(idx * 2, l, m, ql, qr),
                query(idx * 2 + 1, m + 1, r, ql, qr)
            );
        }
    }

    public List<Boolean> getResults(int[][] queries) {

        TreeSet<Integer> obstacles = new TreeSet<>();
        obstacles.add(0); // sentinel

        int qz = queries.length;
        int answerSize = qz;
        int maxPos = 0;

        // Collect all obstacles
        for (int[] q : queries) {
            if (q[0] == 1) {
                answerSize--;
                obstacles.add(q[1]);
                maxPos = Math.max(maxPos, q[1]);
            }
        }

        List<Boolean> ans =
            new ArrayList<>(Collections.nCopies(answerSize, false));

        int[] arr = new int[maxPos + 1];

        int prev = 0;
        for (int x : obstacles) {
            if (x == 0) continue;

            arr[x] = x - prev;
            prev = x;
        }

        SegmentTree tree = new SegmentTree(arr);

        int j = answerSize - 1;

        for (int i = qz - 1; i >= 0; i--) {

            int[] q = queries[i];
            int type = q[0];
            int x = q[1];

            Integer it = obstacles.ceiling(x);

            if (type == 1) {

                // obstacle x must currently exist
                it = x;

                Integer nxt = obstacles.higher(it);
                Integer prv = obstacles.lower(it);

                if (nxt != null) {
                    tree.update(1, 0, maxPos, nxt, nxt - prv);
                }

                tree.update(1, 0, maxPos, it, 0);
                obstacles.remove(it);

            } else {

                int sz = q[2];

                Integer prv;

                if (it == null) {
                    // equivalent to prev(end()) in C++
                    prv = obstacles.last();
                } else {
                    prv = obstacles.lower(it);
                }

                boolean canPlace =
                    (x - prv >= sz) ||
                    (tree.query(1, 0, maxPos, 0, x) >= sz);

                ans.set(j--, canPlace);
            }
        }

        return ans;
    }
}