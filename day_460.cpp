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



/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode() : val(0), next(nullptr) {}
 *     ListNode(int x) : val(x), next(nullptr) {}
 *     ListNode(int x, ListNode *next) : val(x), next(next) {}
 * };
 */
class Solution {
public:
    ListNode* rotateRight(ListNode* head, int k) {
        if (head==NULL) return NULL;
        if (k==0) return head;
        ListNode* cur=head, *first=head;
        int n=1;
        while(cur->next){
            cur=cur->next;
            n++;
        } 
        cur->next=first;
        int pos=n+(-k)%n;
        ListNode* ans=head, *prev;
        for(int i=0; i<pos; i++){
            prev=ans;
            ans=ans->next;
        }
        prev->next=NULL;
        return ans;    
    }
};