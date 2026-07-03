# 3620. Network Recovery Pathways

# You are given a directed acyclic graph of n nodes numbered from 0 to n − 1. This is represented by a 2D array edges of length m, where edges[i] = [ui, vi, costi] indicates a one‑way communication from node ui to node vi with a recovery cost of costi.

# Some nodes may be offline. You are given a boolean array online where online[i] = true means node i is online. Nodes 0 and n − 1 are always online.

# A path from 0 to n − 1 is valid if:

# All intermediate nodes on the path are online.
# The total recovery cost of all edges on the path does not exceed k.
# For each valid path, define its score as the minimum edge‑cost along that path.

# Return the maximum path score (i.e., the largest minimum-edge cost) among all valid paths. If no valid path exists, return -1.

 

# Example 1:

# Input: edges = [[0,1,5],[1,3,10],[0,2,3],[2,3,4]], online = [true,true,true,true], k = 10

# Output: 3

# Explanation:



# The graph has two possible routes from node 0 to node 3:

# Path 0 → 1 → 3

# Total cost = 5 + 10 = 15, which exceeds k (15 > 10), so this path is invalid.

# Path 0 → 2 → 3

# Total cost = 3 + 4 = 7 <= k, so this path is valid.

# The minimum edge‐cost along this path is min(3, 4) = 3.

# There are no other valid paths. Hence, the maximum among all valid path‐scores is 3.

# Example 2:

# Input: edges = [[0,1,7],[1,4,5],[0,2,6],[2,3,6],[3,4,2],[2,4,6]], online = [true,true,true,false,true], k = 12

# Output: 6

# Explanation:



# Node 3 is offline, so any path passing through 3 is invalid.

# Consider the remaining routes from 0 to 4:

# Path 0 → 1 → 4

# Total cost = 7 + 5 = 12 <= k, so this path is valid.

# The minimum edge‐cost along this path is min(7, 5) = 5.

# Path 0 → 2 → 3 → 4

# Node 3 is offline, so this path is invalid regardless of cost.

# Path 0 → 2 → 4

# Total cost = 6 + 6 = 12 <= k, so this path is valid.

# The minimum edge‐cost along this path is min(6, 6) = 6.

# Among the two valid paths, their scores are 5 and 6. Therefore, the answer is 6.

 

# Constraints:

# n == online.length
# 2 <= n <= 5 * 10^4
# 0 <= m == edges.length <= min(10^5, n * (n - 1) / 2)
# edges[i] = [ui, vi, costi]
# 0 <= ui, vi < n
# ui != vi
# 0 <= costi <= 10^9
# 0 <= k <= 5 * 10^13
# online[i] is either true or false, and both online[0] and online[n − 1] are true.
# The given graph is a directed acyclic graph.





# Solution:




import heapq

class Solution(object):
    def findMaxPathScore(self, edges, online, k):
        """
        :type edges: List[List[int]]
        :type online: List[bool]
        :type k: int
        :rtype: int
        """

        n = len(online)
        m = len(edges)

        to = [0] * m
        wt = [0] * m
        nxt = [-1] * m
        head = [-1] * n
        eIdx = [0]

        def addEdge(u, v, w):
            idx = eIdx[0]
            to[idx] = v
            wt[idx] = w
            nxt[idx] = head[u]
            head[u] = idx
            eIdx[0] += 1

        mnC = float("inf")
        mxC = -1

        for u, v, w in edges:
            if online[u] and online[v]:
                mnC = min(mnC, w)
                mxC = max(mxC, w)
                addEdge(u, v, w)

        if mxC == -1:
            return -1

        def dijkstra(minW):
            INF = 10 ** 30
            dist = [INF] * n
            pathMin = [0] * n

            dist[0] = 0
            pathMin[0] = INF

            pq = [(0, 0)]

            while pq:
                d, u = heapq.heappop(pq)

                if d > dist[u]:
                    continue
                if d > k:
                    return -1
                if u == n - 1:
                    return pathMin[u]

                idx = head[u]
                while idx != -1:
                    v = to[idx]
                    w = wt[idx]

                    if w >= minW:
                        d2 = d + w
                        curMin = min(pathMin[u], w)

                        if d2 < dist[v]:
                            dist[v] = d2
                            pathMin[v] = curMin
                            heapq.heappush(pq, (d2, v))
                        elif d2 == dist[v] and curMin > pathMin[v]:
                            pathMin[v] = curMin
                            heapq.heappush(pq, (d2, v))

                    idx = nxt[idx]

            return -1

        l, r = mnC, mxC
        ans = -1

        while l <= r:
            mid = (l + r) // 2
            aMin = dijkstra(mid)

            if aMin != -1:
                ans = max(ans, aMin)
                l = aMin + 1
            else:
                r = mid - 1

        return ans