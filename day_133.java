// 440. K-th Smallest in Lexicographical Order

// Given two integers n and k, return the kth lexicographically smallest integer in the range [1, n].

 

// Example 1:

// Input: n = 13, k = 2
// Output: 10
// Explanation: The lexicographical order is [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9], so the second smallest number is 10.
// Example 2:

// Input: n = 1, k = 1
// Output: 1
 

// Constraints:

// 1 <= k <= n <= 10^9


// Solution:

class Solution {
    static public int findKthNumber(int n, int K) {
       long k = K;
       int ans = solve(0, n, k);
       return ans;
   }

   static int solve(long current, long n, long k) {
       if(k == 0) 
            return (int) (current / 10);

       for (long i = Math.max(current, 1); i < current + 10; i++) {
           long count = numOfChildren(i, i + 1, n);
           if (count >= k) 
               return solve(i * 10, n, k - 1);
           k-= count;
       }

       return -1;
   }

   static long numOfChildren(long current, long neighbour, long n) {
       if (neighbour > n) {
           if (current > n) return 0;
           return n - current + 1;
       }
       return neighbour - current + numOfChildren(current * 10, neighbour * 10, n);
   }
}