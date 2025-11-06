# 3607. Power Grid Maintenance

# You are given an integer c representing c power stations, each with a unique identifier id from 1 to c (1‑based indexing).

# These stations are interconnected via n bidirectional cables, represented by a 2D array connections, where each element connections[i] = [ui, vi] indicates a connection between station ui and station vi. Stations that are directly or indirectly connected form a power grid.

# Initially, all stations are online (operational).

# You are also given a 2D array queries, where each query is one of the following two types:

# [1, x]: A maintenance check is requested for station x. If station x is online, it resolves the check by itself. If station x is offline, the check is resolved by the operational station with the smallest id in the same power grid as x. If no operational station exists in that grid, return -1.

# [2, x]: Station x goes offline (i.e., it becomes non-operational).

# Return an array of integers representing the results of each query of type [1, x] in the order they appear.

# Note: The power grid preserves its structure; an offline (non‑operational) node remains part of its grid and taking it offline does not alter connectivity.

 

# Example 1:

# Input: c = 5, connections = [[1,2],[2,3],[3,4],[4,5]], queries = [[1,3],[2,1],[1,1],[2,2],[1,2]]

# Output: [3,2,3]

# Explanation:



# Initially, all stations {1, 2, 3, 4, 5} are online and form a single power grid.
# Query [1,3]: Station 3 is online, so the maintenance check is resolved by station 3.
# Query [2,1]: Station 1 goes offline. The remaining online stations are {2, 3, 4, 5}.
# Query [1,1]: Station 1 is offline, so the check is resolved by the operational station with the smallest id among {2, 3, 4, 5}, which is station 2.
# Query [2,2]: Station 2 goes offline. The remaining online stations are {3, 4, 5}.
# Query [1,2]: Station 2 is offline, so the check is resolved by the operational station with the smallest id among {3, 4, 5}, which is station 3.
# Example 2:

# Input: c = 3, connections = [], queries = [[1,1],[2,1],[1,1]]

# Output: [1,-1]

# Explanation:

# There are no connections, so each station is its own isolated grid.
# Query [1,1]: Station 1 is online in its isolated grid, so the maintenance check is resolved by station 1.
# Query [2,1]: Station 1 goes offline.
# Query [1,1]: Station 1 is offline and there are no other stations in its grid, so the result is -1.
 

# Constraints:

# 1 <= c <= 10^5
# 0 <= n == connections.length <= min(10^5, c * (c - 1) / 2)
# connections[i].length == 2
# 1 <= ui, vi <= c
# ui != vi
# 1 <= queries.length <= 2 * 10^5
# queries[i].length == 2
# queries[i][0] is either 1 or 2.
# 1 <= queries[i][1] <= c




# Solution: 


class Solution(object):
    def processQueries(self, c, connections, queries):
        """
        :type c: int
        :type connections: List[List[int]]
        :type queries: List[List[int]]
        :rtype: List[int]
        """

        class UnionFind:
            def __init__(self, n):
                self.root = [i for i in range(n + 1)]
                self.rank = [1] * (n + 1)

            def find(self, x):
                if self.root[x] != x:
                    self.root[x] = self.find(self.root[x])
                return self.root[x]

            def union(self, x, y):
                x, y = self.find(x), self.find(y)
                if x == y:
                    return False
                if self.rank[x] > self.rank[y]:
                    x, y = y, x
                self.root[x] = y
                if self.rank[x] == self.rank[y]:
                    self.rank[y] += 1
                return True

        uf = UnionFind(c)

        # Apply all connections
        for a, b in connections:
            uf.union(a, b)

        cntOff = [0] * (c + 1)
        minOn = [-1] * (c + 1)
        sz = 0

        # Count type-2 queries and determine result size
        for t, x in queries:
            if t == 2:
                cntOff[x] += 1
            else:
                sz += 1

        ans = [0] * sz

        # Compute initial minOn for each component
        for i in range(1, c + 1):
            if cntOff[i] == 0:
                rx = uf.find(i)
                if minOn[rx] == -1 or minOn[rx] > i:
                    minOn[rx] = i

        # Process queries in reverse
        j = sz - 1
        for t, x in reversed(queries):
            rx = uf.find(x)
            minS = minOn[rx]
            if t == 1:
                ans[j] = x if cntOff[x] == 0 else minS
                j -= 1
            else:
                cntOff[x] -= 1
                if cntOff[x] == 0:
                    if minOn[rx] == -1 or minOn[rx] > x:
                        minOn[rx] = x

        return ans
