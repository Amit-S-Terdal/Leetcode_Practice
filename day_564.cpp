// 3116. Kth Smallest Amount With Single Denomination Combination

// You are given an integer array coins representing coins of different denominations and an integer k.

// You have an infinite number of coins of each denomination. However, you are not allowed to combine coins of different denominations.

// Return the kth smallest amount that can be made using these coins.

 

// Example 1:

// Input: coins = [3,6,9], k = 3

// Output: 9

// Explanation: The given coins can make the following amounts:
// Coin 3 produces multiples of 3: 3, 6, 9, 12, 15, etc.
// Coin 6 produces multiples of 6: 6, 12, 18, 24, etc.
// Coin 9 produces multiples of 9: 9, 18, 27, 36, etc.
// All of the coins combined produce: 3, 6, 9, 12, 15, etc.

// Example 2:

// Input: coins = [5,2], k = 7

// Output: 12

// Explanation: The given coins can make the following amounts:
// Coin 5 produces multiples of 5: 5, 10, 15, 20, etc.
// Coin 2 produces multiples of 2: 2, 4, 6, 8, 10, 12, etc.
// All of the coins combined produce: 2, 4, 5, 6, 8, 10, 12, 14, 15, etc.

 

// Constraints:

// 1 <= coins.length <= 15
// 1 <= coins[i] <= 25
// 1 <= k <= 2 * 10^9
// coins contains pairwise distinct integers.




// Solution:



using ll=long long;
constexpr int N=1<<15;
ll dp[N];
class Solution {
public:
    static ll f(long long x, unsigned bitMask){
        ll cnt=0;
        for(unsigned i=1; i<=bitMask; i++)
            cnt+=(popcount(i)&1)?(x/dp[i]):(-x/dp[i]);
        return cnt;
    }

    static long long findKthSmallest(vector<int>& coins, int k) {
        sort(coins.begin(), coins.end(), greater<>());
        if (coins.back()==1) return k;
        bitset<26> valid_coin=0;
        for (int c: coins){
            valid_coin[c]=1;
            for(int r=2*c; r<26; r+=c) valid_coin[r]=0;
        }
        coins.clear();
        for(int i=1; i<=25; i++) 
            if(valid_coin[i]) coins.push_back(i);

        int sz=coins.size();
        if (sz==1) return (ll)coins[0]*k;

        unsigned bitMask=(1<<sz)-1;
        memset(dp, -1, (1<<sz)*sizeof(ll));

        // Loop through all possible subsets of coins
        for (int bMask=1; bMask<=bitMask; bMask++) {
            ll lcm=1;
            for (int i=0; i<sz; i++) {
                if (bMask & (1LL<<i)) {
                    lcm=lcm/gcd(lcm, coins[i])*coins[i] ; // compute LCM
                }
            }
            dp[bMask]=lcm;
        }
       
        ll l=k+1, r=(ll)coins[0]*k, mid, ans=r;

        while (l<=r) {
            mid=l+(r-l)/2;
            if (f(mid, bitMask)>=k){
                ans=mid;
                r=mid-1;
            }
            else
                l=mid+1;
        }
        return ans;
    }
};


auto init = []() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();