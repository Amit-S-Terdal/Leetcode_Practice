# 1733. Minimum Number of People to Teach

# On a social network consisting of m users and some friendships between users, two users can communicate with each other if they know a common language.

# You are given an integer n, an array languages, and an array friendships where:

# There are n languages numbered 1 through n,
# languages[i] is the set of languages the i​​​​​​th​​​​ user knows, and
# friendships[i] = [u​​​​​​i​​​, v​​​​​​i] denotes a friendship between the users u​​​​​​​​​​​i​​​​​ and vi.
# You can choose one language and teach it to some users so that all friends can communicate with each other. Return the minimum number of users you need to teach.

# Note that friendships are not transitive, meaning if x is a friend of y and y is a friend of z, this doesn't guarantee that x is a friend of z.
 

# Example 1:

# Input: n = 2, languages = [[1],[2],[1,2]], friendships = [[1,2],[1,3],[2,3]]
# Output: 1
# Explanation: You can either teach user 1 the second language or user 2 the first language.
# Example 2:

# Input: n = 3, languages = [[2],[1,3],[1,2],[3]], friendships = [[1,4],[1,2],[3,4],[2,3]]
# Output: 2
# Explanation: Teach the third language to users 1 and 3, yielding two users to teach.
 

# Constraints:

# 2 <= n <= 500
# languages.length == m
# 1 <= m <= 500
# 1 <= languages[i].length <= n
# 1 <= languages[i][j] <= n
# 1 <= u​​​​​​i < v​​​​​​i <= languages.length
# 1 <= friendships.length <= 500
# All tuples (u​​​​​i, v​​​​​​i) are unique
# languages[i] contains only unique values



# Solution: 


class Solution(object):
    def minimumTeachings(self, n, languages, friendships):
        """
        :type n: int
        :type languages: List[List[int]]
        :type friendships: List[List[int]]
        :rtype: int
        """
        m = len(languages)  # number of people

        # Known languages for each person
        know = [[False] * (n + 1) for _ in range(m)]
        for i in range(m):
            for l in languages[i]:
                know[i][l] = True

        # People who need to be taught
        need = [False] * m
        any_need_teaching = False  # Flag to track if anyone needs teaching

        for f in friendships:
            a, b = f[0] - 1, f[1] - 1  # Convert 1-based to 0-based index
            can_talk = False
            for lang in range(1, n + 1):  # Check if they share a language
                if know[a][lang] and know[b][lang]:
                    can_talk = True
                    break
            if not can_talk:
                need[a] = True
                need[b] = True
                any_need_teaching = True

        # If no one needs teaching, return 0
        if not any_need_teaching:
            return 0

        ans = float('inf')
        for lang in range(1, n + 1):
            cnt = 0
            for i in range(m):
                if need[i] and not know[i][lang]:
                    cnt += 1
            ans = min(ans, cnt)

        return ans
