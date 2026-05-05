# 61. Rotate List

# Given the head of a linked list, rotate the list to the right by k places.

 

# Example 1:


# Input: head = [1,2,3,4,5], k = 2
# Output: [4,5,1,2,3]
# Example 2:


# Input: head = [0,1,2], k = 4
# Output: [2,0,1]
 

# Constraints:

# The number of nodes in the list is in the range [0, 500].
# -100 <= Node.val <= 100
# 0 <= k <= 2 * 10^9



# Solution: 



class Solution(object):
    def rotateRight(self, head, k):
        if not head or k == 0:
            return head

        cur = head
        n = 1

        # Find length
        while cur.next:
            cur = cur.next
            n += 1

        # Make it circular
        cur.next = head

        # Normalize k
        k = k % n
        pos = n - k

        ans = head
        prev = None

        # Move to new head
        for _ in range(pos):
            prev = ans
            ans = ans.next

        # Break the cycle
        prev.next = None

        return ans