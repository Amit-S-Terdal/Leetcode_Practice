# 3161. Block Placement Queries

# There exists an infinite number line, with its origin at 0 and extending towards the positive x-axis.

# You are given a 2D array queries, which contains two types of queries:

# For a query of type 1, queries[i] = [1, x]. Build an obstacle at distance x from the origin. It is guaranteed that there is no obstacle at distance x when the query is asked.
# For a query of type 2, queries[i] = [2, x, sz]. Check if it is possible to place a block of size sz anywhere in the range [0, x] on the line, such that the block entirely lies in the range [0, x]. A block cannot be placed if it intersects with any obstacle, but it may touch it. Note that you do not actually place the block. Queries are separate.
# Return a boolean array results, where results[i] is true if you can place the block specified in the ith query of type 2, and false otherwise.

 

# Example 1:

# Input: queries = [[1,2],[2,3,3],[2,3,1],[2,2,2]]

# Output: [false,true,true]

# Explanation:



# For query 0, place an obstacle at x = 2. A block of size at most 2 can be placed before x = 3.

# Example 2:

# Input: queries = [[1,7],[2,7,6],[1,2],[2,7,5],[2,7,6]]

# Output: [true,true,false]

# Explanation:



# Place an obstacle at x = 7 for query 0. A block of size at most 7 can be placed before x = 7.
# Place an obstacle at x = 2 for query 2. Now, a block of size at most 5 can be placed before x = 7, and a block of size at most 2 before x = 2.
 

# Constraints:

# 1 <= queries.length <= 15 * 10^4
# 2 <= queries[i].length <= 3
# 1 <= queries[i][0] <= 2
# 1 <= x, sz <= min(5 * 10^4, 3 * queries.length)
# The input is generated such that for queries of type 1, no obstacle exists at distance x when the query is asked.
# The input is generated such that there is at least one query of type 2.




# Solution: 



from sortedcontainers import SortedSet

class Solution(object):
    def getResults(self, queries):
        """
        :type queries: List[List[int]]
        :rtype: List[bool]
        """

        obstacles = SortedSet([0])

        qz = len(queries)
        ans_size = qz
        M = 0

        # Collect all obstacles
        for q in queries:
            if q[0] == 1:
                ans_size -= 1
                obstacles.add(q[1])
                M = max(M, q[1])

        ans = [False] * ans_size

        # Build gap array
        A = [0] * (M + 1)

        prev = 0
        for x in obstacles:
            if x == 0:
                continue
            A[x] = x - prev
            prev = x

        # Segment Tree
        seg = [0] * (4 * max(1, M + 1))

        def build(idx, l, r):
            if l == r:
                seg[idx] = A[l]
                return

            m = (l + r) // 2
            build(idx * 2, l, m)
            build(idx * 2 + 1, m + 1, r)

            seg[idx] = max(seg[idx * 2], seg[idx * 2 + 1])

        def update(idx, l, r, pos, val):
            if l == r:
                seg[idx] = val
                return

            m = (l + r) // 2

            if pos <= m:
                update(idx * 2, l, m, pos, val)
            else:
                update(idx * 2 + 1, m + 1, r, pos, val)

            seg[idx] = max(seg[idx * 2], seg[idx * 2 + 1])

        def query(idx, l, r, ql, qr):
            if ql > r or qr < l:
                return 0

            if ql <= l and r <= qr:
                return seg[idx]

            m = (l + r) // 2

            return max(
                query(idx * 2, l, m, ql, qr),
                query(idx * 2 + 1, m + 1, r, ql, qr)
            )

        if M >= 0:
            build(1, 0, M)

        j = ans_size - 1

        # Process queries in reverse
        for i in range(qz - 1, -1, -1):
            q = queries[i]
            typ = q[0]
            x = q[1]

            if typ == 1:
                pos = obstacles.index(x)

                prv = obstacles[pos - 1]

                if pos + 1 < len(obstacles):
                    nxt = obstacles[pos + 1]
                    update(1, 0, M, nxt, nxt - prv)

                update(1, 0, M, x, 0)
                obstacles.remove(x)

            else:
                sz = q[2]

                pos = obstacles.bisect_left(x)

                if pos == len(obstacles):
                    prv = obstacles[-1]
                else:
                    prv = obstacles[pos - 1]

                ans[j] = (
                    x - prv >= sz or
                    query(1, 0, M, 0, x) >= sz
                )
                j -= 1

        return ans