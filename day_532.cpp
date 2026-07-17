// 3312. Sorted GCD Pair Queries

// You are given an integer array nums of length n and an integer array queries.

// Let gcdPairs denote an array obtained by calculating the GCD of all possible pairs (nums[i], nums[j]), where 0 <= i < j < n, and then sorting these values in ascending order.

// For each query queries[i], you need to find the element at index queries[i] in gcdPairs.

// Return an integer array answer, where answer[i] is the value at gcdPairs[queries[i]] for each query.

// The term gcd(a, b) denotes the greatest common divisor of a and b.

 

// Example 1:

// Input: nums = [2,3,4], queries = [0,2,2]

// Output: [1,2,2]

// Explanation:

// gcdPairs = [gcd(nums[0], nums[1]), gcd(nums[0], nums[2]), gcd(nums[1], nums[2])] = [1, 2, 1].

// After sorting in ascending order, gcdPairs = [1, 1, 2].

// So, the answer is [gcdPairs[queries[0]], gcdPairs[queries[1]], gcdPairs[queries[2]]] = [1, 2, 2].

// Example 2:

// Input: nums = [4,4,2,1], queries = [5,3,1,0]

// Output: [4,2,1,1]

// Explanation:

// gcdPairs sorted in ascending order is [1, 1, 1, 2, 2, 4].

// Example 3:

// Input: nums = [2,2], queries = [0,0]

// Output: [2,2]

// Explanation:

// gcdPairs = [2].

 

// Constraints:

// 2 <= n == nums.length <= 10^5
// 1 <= nums[i] <= 5 * 10^4
// 1 <= queries.length <= 10^5
// 0 <= queries[i] < n * (n - 1) / 2




// Solution:




constexpr int Mx=5e4+1;
int8_t mu[Mx];
vector<int> prime, sq_free;
bitset<Mx> isPrime;
using ll=long long;
void Sieve_mu(){
    if (mu[1]==1) return; // compute exactly once
    memset(mu, 1, sizeof(mu));
    isPrime.set();
    isPrime[0]=isPrime[1]=0;
    prime.reserve((int)(Mx/log(Mx)));
    sq_free.reserve((int)(0.6*Mx));
    mu[1]=1;
    for(int i=2; i<Mx; i++){
        if(isPrime[i]){
            prime.push_back(i);
            mu[i]=-1;
            for(int j=i*2; j<Mx; j+=i){
                isPrime[j]=0;
                mu[j]=-mu[j];
            }
            ll i2=(ll)i*i;
            // not square-free
            for(ll j=i2; j<Mx; j+=i2) 
                mu[j]=0;
        }
    }
    for(int i=1; i<Mx; i++) 
        if (mu[i]!=0) sq_free.push_back(i);
}

// Div[i]= number of x's in nums divisible by i
ll Div[Mx];
ll freq[Mx];
class Solution {
public:
    static vector<int> gcdValues(vector<int>& nums, vector<long long>& q) {
        Sieve_mu();
        const int n=nums.size();
        int M=0;
        for (int x : nums){
            M=max(M, x);
            Div[x]++;// count x
        } 
        for(int x=1; x<=M; x++){
            for(int y=2*x; y<=M; y+=x)
                Div[x]+=Div[y];// add Div[y] to Div[x]
        }

        // compute GCD pair counts for each divisor
        for (int x=1; x<=M; x++) {
            long long cnt=Div[x];
            Div[x]=cnt*(cnt-1LL)/2; // Number of pairs with GCD = x
        }
        // Moebius inversion
        for (int x=1; x<=M; x++) {
            freq[x]=0;
            for (int k : sq_free) {// consider only square-free
                if (x*k>M) break; 
                freq[x]+=mu[k]*Div[x*k];
            }     
        }

        // Reuse freq for Prefix sum of GCD pair 
        partial_sum(freq, freq+(M+1), freq);
        
        //using upper_bound
        int qz=q.size();
        vector<int> ans(qz);
        for (int i=0; i<qz; i++) {
            ans[i]=upper_bound(freq, freq+M+1, q[i])-freq; 
        }
        // reset for the next testcase
        memset(Div, 0, (M+1)*sizeof(ll));
        return ans;
    }
};


auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();