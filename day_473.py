# 1345. Jump Game IV

# Given an array of integers arr, you are initially positioned at the first index of the array.

# In one step you can jump from index i to index:

# i + 1 where: i + 1 < arr.length.
# i - 1 where: i - 1 >= 0.
# j where: arr[i] == arr[j] and i != j.
# Return the minimum number of steps to reach the last index of the array.

# Notice that you can not jump outside of the array at any time.

 

# Example 1:

# Input: arr = [100,-23,-23,404,100,23,23,23,3,404]
# Output: 3
# Explanation: You need three jumps from index 0 --> 4 --> 3 --> 9. Note that index 9 is the last index of the array.
# Example 2:

# Input: arr = [7]
# Output: 0
# Explanation: Start index is the last index. You do not need to jump.
# Example 3:

# Input: arr = [7,6,9,6,9,6,9,7]
# Output: 1
# Explanation: You can jump directly from index 0 to index 7 which is last index of the array.
 

# Constraints:

# 1 <= arr.length <= 5 * 10^4
# -10^8 <= arr[i] <= 10^8




# Solution:



class Solution(object):
    def minJumps(self, arr):
        """
        :type arr: List[int]
        :rtype: int
        """

        n = len(arr)

        vis = [False] * n
        q = [0] * n
        nxt = [-1] * n

        # value -> head index of linked list
        d = {}

        # Build linked lists of equal values
        for i in range(n):
            x = arr[i]

            if x not in d:
                nxt[i] = -1
            else:
                nxt[i] = d[x]

            d[x] = i

        front = 0
        back = 0

        q[back] = 0
        back += 1
        vis[0] = True

        step = 0

        while front < back:

            size = back - front

            while size > 0:
                size -= 1

                cur = q[front]
                front += 1

                if cur == n - 1:
                    return step

                # cur - 1
                if cur - 1 >= 0 and not vis[cur - 1]:
                    q[back] = cur - 1
                    back += 1
                    vis[cur - 1] = True

                # cur + 1
                if cur + 1 < n and not vis[cur + 1]:
                    q[back] = cur + 1
                    back += 1
                    vis[cur + 1] = True

                # same value jumps
                x = arr[cur]

                idx = d.get(x, -1)

                while idx != -1:
                    if not vis[idx]:
                        q[back] = idx
                        back += 1
                        vis[idx] = True

                    idx = nxt[idx]

                # critical optimization
                d[x] = -1

            step += 1

        return -1