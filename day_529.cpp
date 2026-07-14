// 3336. Find the Number of Subsequences With Equal GCD

// You are given an integer array nums.

// Your task is to find the number of pairs of non-empty subsequences (seq1, seq2) of nums that satisfy the following conditions:

// The subsequences seq1 and seq2 are disjoint, meaning no index of nums is common between them.
// The GCD of the elements of seq1 is equal to the GCD of the elements of seq2.
// Return the total number of such pairs.

// Since the answer may be very large, return it modulo 109 + 7.

 

// Example 1:

// Input: nums = [1,2,3,4]

// Output: 10

// Explanation:

// The subsequence pairs which have the GCD of their elements equal to 1 are:

// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// ([1, 2, 3, 4], [1, 2, 3, 4])
// Example 2:

// Input: nums = [10,20,30]

// Output: 2

// Explanation:

// The subsequence pairs which have the GCD of their elements equal to 10 are:

// ([10, 20, 30], [10, 20, 30])
// ([10, 20, 30], [10, 20, 30])
// Example 3:

// Input: nums = [1,1,1,1]

// Output: 50

 

// Constraints:

// 1 <= nums.length <= 200
// 1 <= nums[i] <= 200





// Solution:



const int N=201, mod=1e9+7;
int GCD[N][N];
int dp[2][N*N];
using ll=long long;
class Solution {
public:
    static void computeGCD(){
        if (GCD[0][1]==1) return;
        for(int x=0; x<N; x++) 
            GCD[x][x]=GCD[x][0]=GCD[0][x]=x;
        for(int x=1; x<N; x++){
            GCD[x][1]=GCD[1][x]=1;
            for(int y=2; y<x; y++){
                GCD[x][y]=GCD[y][x]=GCD[y][x-y];
            }
        }
    }
    static int subsequencePairCount(vector<int>& nums) {
        computeGCD();
        int M=0, qM=0;
        array<uint64_t, 4> Bit{}; // custom bitset
        
        Bit[0]|=1ULL; // set 0th bit 
        for (int x : nums) {
            M=max(M, x);
            const int q=x>>6, r=x%64;
            qM=max(q, qM);
            Bit[q]|=(1ULL<<r);
        }
        
        const int M2=(M+1)*(M+1);
        
        // reachable GCDs
        for (int x=0; x<= M; x++) {
            const int q=x>>6, r=x%64;
            if ((Bit[q]>>r)&1) continue;
            for (int y=2*x; y<= M; y+=x) {
                const int q2=y>>6, r2=y%64;
                if ((Bit[q2]>>r2)&1) {
                    Bit[q]|=(1ULL<<r);
                    qM=max(qM, q);
                    break;
                }
            }
        }

        memset(dp[0], 0, sizeof(int)*M2);
        dp[0][0]=1;
        const int n=nums.size();
        
        for (int i=0; i<n; i++) {
            const int x=nums[i];
            bool cur=i&1, nxt=!cur;
            memset(dp[nxt], 0, sizeof(int)*M2);

            for (int iq=0; iq<=qM; iq++){ 
                uint64_t ir=Bit[iq];
                for(; ir; ir&=(ir-1)) {
                    int r1=__builtin_ctzll(ir); // Find the bit index
                    int g1=(iq<<6)+r1;
                    for (int jq=0; jq<=qM; jq++){
                        uint64_t jr=Bit[jq];
                        for(;jr ; jr&=(jr-1)){
                            int r2=__builtin_ctzll(jr); // Find the bit index
                            int g2=(jq<<6)+r2;
                            
                            if (dp[cur][g1*(M+1)+g2]==0) continue;
                            ll curDp=dp[cur][g1*(M+1)+g2];

                            const int ng1=GCD[g1][x];
                            dp[nxt][ng1*(M+1)+g2]=(dp[nxt][ng1*(M+1)+g2]+curDp)%mod;

                            const int ng2=GCD[g2][x];
                            dp[nxt][g1*(M+1)+ng2]=(dp[nxt][g1*(M+1)+ng2]+curDp)% mod;

                            dp[nxt][g1*(M+1)+g2]=(dp[nxt][g1*(M+1)+g2]+curDp)% mod;
                        }
                    }
                }
            }
        }
        bool last=n&1;
        ll ans=0;
        Bit[0]^=1;// remove 0th bit
        for (int iq=0; iq<=qM; iq++){ 
            uint64_t ir=Bit[iq];
            for(; ir; ir&=(ir-1)) {
                int r1=__builtin_ctzll(ir); // Find the bit index
                int i=(iq<<6)+r1;
                ans+=dp[last][i*(M+2)];
            }
         }
        return ans%mod;
    }
};