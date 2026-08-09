// 1140. Stone Game II

// Alice and Bob continue their games with piles of stones. There are a number of piles arranged in a row, and each pile has a positive integer number of stones piles[i]. The objective of the game is to end with the most stones.

// Alice and Bob take turns, with Alice starting first.

// On each player's turn, that player can take all the stones in the first X remaining piles, where 1 <= X <= 2M. Then, we set M = max(M, X). Initially, M = 1.

// The game continues until all the stones have been taken.

// Assuming Alice and Bob play optimally, return the maximum number of stones Alice can get.

 

// Example 1:

// Input: piles = [2,7,9,4,4]

// Output: 10

// Explanation:

// If Alice takes one pile at the beginning, Bob takes two piles, then Alice takes 2 piles again. Alice can get 2 + 4 + 4 = 10 stones in total.
// If Alice takes two piles at the beginning, then Bob can take all three piles left. In this case, Alice get 2 + 7 = 9 stones in total.
// So we return 10 since it's larger.

// Example 2:

// Input: piles = [1,2,3,4,5,100]

// Output: 104

 

// Constraints:

// 1 <= piles.length <= 100
// 1 <= piles[i] <= 10^4




// Solution:



class Solution {
public:
    int n;
    int dp[2][101][101];
    int alice(int isBob, int i, int m, vector<int>& piles){
        if (i == n) return 0;
        if (dp[isBob][i][m]!=-1) return dp[isBob][i][m];
        int stones =(isBob == 0)? 0: INT_MAX;
        int sum = 0, xN= min(2*m, n-i);
        for (int x = 1; x <= xN; x++) {
            sum += piles[i+x-1];
            int m2=max(m, x);
            if (isBob) 
                stones=min(stones, alice(0, i+x, m2, piles));
            else 
                stones=max(stones, sum+alice(1, i+x, m2, piles));                       
        }
        return dp[isBob][i][m]=stones;
    }

    int stoneGameII(vector<int>& piles) {
        n = piles.size();
        memset(dp, -1, sizeof(dp));
        return alice(0, 0, 1,  piles);
    }
};


auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();