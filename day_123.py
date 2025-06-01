# 2359. Find Closest Node to Given Two Nodes

# You are given a directed graph of n nodes numbered from 0 to n - 1, where each node has at most one outgoing edge.

# The graph is represented with a given 0-indexed array edges of size n, indicating that there is a directed edge from node i to node edges[i]. If there is no outgoing edge from i, then edges[i] == -1.

# You are also given two integers node1 and node2.

# Return the index of the node that can be reached from both node1 and node2, such that the maximum between the distance from node1 to that node, and from node2 to that node is minimized. If there are multiple answers, return the node with the smallest index, and if no possible answer exists, return -1.

# Note that edges may contain cycles.

 

# Example 1:


# Input: edges = [2,2,3,-1], node1 = 0, node2 = 1
# Output: 2
# Explanation: The distance from node 0 to node 2 is 1, and the distance from node 1 to node 2 is 1.
# The maximum of those two distances is 1. It can be proven that we cannot get a node with a smaller maximum distance than 1, so we return node 2.
# Example 2:


# Input: edges = [1,2,-1], node1 = 0, node2 = 2
# Output: 2
# Explanation: The distance from node 0 to node 2 is 2, and the distance from node 2 to itself is 0.
# The maximum of those two distances is 2. It can be proven that we cannot get a node with a smaller maximum distance than 2, so we return node 2.
 

# Constraints:

# n == edges.length
# 2 <= n <= 10^5
# -1 <= edges[i] < n
# edges[i] != i
# 0 <= node1, node2 < n


# Solution:

class Solution(object):
    def dfs(self, current, distance, edges, distances):
        while current != -1 and distances[current] == -1:
            distances[current] = distance
            distance += 1
            current = edges[current]

    def closestMeetingNode(self, edges, start1, start2):
        res, Min_Of_Max, n = -1, float('inf'), len(edges)
        dist1 = [-1] * n
        dist2 = [-1] * n
        self.dfs(start1, 0, edges, dist1)
        self.dfs(start2, 0, edges, dist2)
        for i in range(n):
            if dist1[i] >= 0 and dist2[i] >= 0:
                maxDist = max(dist1[i], dist2[i])
                if maxDist < Min_Of_Max:
                    Min_Of_Max = maxDist
                    res = i
        return res
