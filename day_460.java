// 61. Rotate List

// Given the head of a linked list, rotate the list to the right by k places.

 

// Example 1:


// Input: head = [1,2,3,4,5], k = 2
// Output: [4,5,1,2,3]
// Example 2:


// Input: head = [0,1,2], k = 4
// Output: [2,0,1]
 

// Constraints:

// The number of nodes in the list is in the range [0, 500].
// -100 <= Node.val <= 100
// 0 <= k <= 2 * 10^9



// Solution: 



class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) return null;
        if (k == 0) return head;

        ListNode cur = head, first = head;
        int n = 1;

        // Find length
        while (cur.next != null) {
            cur = cur.next;
            n++;
        }

        // Make it circular
        cur.next = first;

        // Normalize k
        k = k % n;

        int pos = n - k;
        ListNode ans = head, prev = null;

        // Move to new head
        for (int i = 0; i < pos; i++) {
            prev = ans;
            ans = ans.next;
        }

        // Break the cycle
        prev.next = null;

        return ans;
    }
}