// 1510. Stone Game IV

// Alice and Bob take turns playing a game, with Alice starting first.

// Initially, there are n stones in a pile. On each player's turn, that player makes a move consisting of removing any non-zero square number of stones in the pile.

// Also, if a player cannot make a move, he/she loses the game.

// Given a positive integer n, return true if and only if Alice wins the game otherwise return false, assuming both players play optimally.

 

// Example 1:

// Input: n = 1
// Output: true
// Explanation: Alice can remove 1 stone winning the game because Bob doesn't have any moves.
// Example 2:

// Input: n = 2
// Output: false
// Explanation: Alice can only remove 1 stone, after that Bob removes the last one winning the game (2 -> 1 -> 0).
// Example 3:

// Input: n = 4
// Output: true
// Explanation: n is already a perfect square, Alice can win with one move, removing 4 stones (4 -> 0).
 

// Constraints:

// 1 <= n <= 10^5



// Solution:





constexpr int N=1e5+1;
bitset<N> dp;
class Solution {
public:
    static void pre_comp(){
        if (dp[1]) return;
        for (int i=1; i<N; i++) {
            for (int k=1; k*k<=i; k++) {
                if (dp[i-k*k]==0) {
                    dp[i]=1;
                    break;
                }
            }
        }
    }
    static bool winnerSquareGame(int n) {
        pre_comp();
        return dp[n];
    }
};



auto init = []()
{ 
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();