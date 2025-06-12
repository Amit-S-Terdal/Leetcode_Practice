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

using namespace std;
class Solution {
public:
    static int findKthNumber(int n, int K) {
        return solve(0, n, K);
    }

    static int solve(long long current, long long n, long long k) {
        if (k == 0)
            return (int)(current / 10);

        for (long long i = max(current, 1LL); i < current + 10; ++i) {
            long long count = numOfChildren(i, i + 1, n);
            if (count >= k)
                return solve(i * 10, n, k - 1);
            k -= count;
        }
        return -1;
    }

    static long long numOfChildren(long long current, long long neighbour, long long n) {
        if (neighbour > n) {
            if (current > n) return 0;
            return n - current + 1;
        }
        return neighbour - current + numOfChildren(current * 10, neighbour * 10, n);
    }
};