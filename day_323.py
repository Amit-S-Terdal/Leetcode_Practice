# 2092. Find All People With Secret

# You are given an integer n indicating there are n people numbered from 0 to n - 1. You are also given a 0-indexed 2D integer array meetings where meetings[i] = [xi, yi, timei] indicates that person xi and person yi have a meeting at timei. A person may attend multiple meetings at the same time. Finally, you are given an integer firstPerson.

# Person 0 has a secret and initially shares the secret with a person firstPerson at time 0. This secret is then shared every time a meeting takes place with a person that has the secret. More formally, for every meeting, if a person xi has the secret at timei, then they will share the secret with person yi, and vice versa.

# The secrets are shared instantaneously. That is, a person may receive the secret and share it with people in other meetings within the same time frame.

# Return a list of all the people that have the secret after all the meetings have taken place. You may return the answer in any order.

 

# Example 1:

# Input: n = 6, meetings = [[1,2,5],[2,3,8],[1,5,10]], firstPerson = 1
# Output: [0,1,2,3,5]
# Explanation:
# At time 0, person 0 shares the secret with person 1.
# At time 5, person 1 shares the secret with person 2.
# At time 8, person 2 shares the secret with person 3.
# At time 10, person 1 shares the secret with person 5.​​​​
# Thus, people 0, 1, 2, 3, and 5 know the secret after all the meetings.
# Example 2:

# Input: n = 4, meetings = [[3,1,3],[1,2,2],[0,3,3]], firstPerson = 3
# Output: [0,1,3]
# Explanation:
# At time 0, person 0 shares the secret with person 3.
# At time 2, neither person 1 nor person 2 know the secret.
# At time 3, person 3 shares the secret with person 0 and person 1.
# Thus, people 0, 1, and 3 know the secret after all the meetings.
# Example 3:

# Input: n = 5, meetings = [[3,4,2],[1,2,1],[2,3,1]], firstPerson = 1
# Output: [0,1,2,3,4]
# Explanation:
# At time 0, person 0 shares the secret with person 1.
# At time 1, person 1 shares the secret with person 2, and person 2 shares the secret with person 3.
# Note that person 2 can share the secret at the same time as receiving it.
# At time 2, person 3 shares the secret with person 4.
# Thus, people 0, 1, 2, 3, and 4 know the secret after all the meetings.
 

# Constraints:

# 2 <= n <= 10^5
# 1 <= meetings.length <= 10^5
# meetings[i].length == 3
# 0 <= xi, yi <= n - 1
# xi != yi
# 1 <= timei <= 10^5
# 1 <= firstPerson <= n - 1



# Solution: 



class UnionFind:
    def __init__(self, n):
        self.root = list(range(n))
        self.rank = [1] * n

    def find(self, x):
        if x != self.root[x]:
            self.root[x] = self.find(self.root[x])  # Path compression
        return self.root[x]

    def union(self, x, y):
        rX = self.find(x)
        rY = self.find(y)
        if rX == rY:
            return
        if self.rank[rX] > self.rank[rY]:
            self.root[rY] = rX
        elif self.rank[rX] < self.rank[rY]:
            self.root[rX] = rY
        else:
            self.root[rY] = rX
            self.rank[rX] += 1

    def connected(self, x, y):
        return self.find(x) == self.find(y)

    def reset(self, x):
        self.root[x] = x
        self.rank[x] = 1


class Solution:
    def findAllPeople(self, n, meetings, firstPerson):
        """
        :type n: int
        :type meetings: List[List[int]]
        :type firstPerson: int
        :rtype: List[int]
        """
        # Using a time-based grouping for meetings
        meet_time = {}
        t_max = -1

        # Group meetings by time
        for meet in meetings:
            x, y, t = meet
            if t not in meet_time:
                meet_time[t] = []
            meet_time[t].append((x, y))
            t_max = max(t_max, t)

        # Initialize UnionFind
        uf = UnionFind(n)
        uf.union(0, firstPerson)  # Add edge (0, firstPerson)

        # Process meetings by time
        for t in range(t_max + 1):
            if t in meet_time:
                # Union all pairs for the current time
                for x, y in meet_time[t]:
                    uf.union(x, y)

                # After processing all pairs, reset the edges that don't connect to person 0
                for x, y in meet_time[t]:
                    if not uf.connected(0, x):
                        uf.reset(x)
                        uf.reset(y)

        # Collect the people connected to person 0
        result = [0]  # Add person 0 initially
        for i in range(1, n):
            if uf.connected(0, i):
                result.append(i)

        return result
