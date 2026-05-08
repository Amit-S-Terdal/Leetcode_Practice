// 3629. Minimum Jumps to Reach End via Prime Teleportation

// You are given an integer array nums of length n.

// You start at index 0, and your goal is to reach index n - 1.

// From any index i, you may perform one of the following operations:

// Adjacent Step: Jump to index i + 1 or i - 1, if the index is within bounds.
// Prime Teleportation: If nums[i] is a prime number p, you may instantly jump to any index j != i such that nums[j] % p == 0.
// Return the minimum number of jumps required to reach index n - 1.

 

// Example 1:

// Input: nums = [1,2,4,6]

// Output: 2

// Explanation:

// One optimal sequence of jumps is:

// Start at index i = 0. Take an adjacent step to index 1.
// At index i = 1, nums[1] = 2 is a prime number. Therefore, we teleport to index i = 3 as nums[3] = 6 is divisible by 2.
// Thus, the answer is 2.

// Example 2:

// Input: nums = [2,3,4,7,9]

// Output: 2

// Explanation:

// One optimal sequence of jumps is:

// Start at index i = 0. Take an adjacent step to index i = 1.
// At index i = 1, nums[1] = 3 is a prime number. Therefore, we teleport to index i = 4 since nums[4] = 9 is divisible by 3.
// Thus, the answer is 2.

// Example 3:

// Input: nums = [4,6,5,8]

// Output: 3

// Explanation:

// Since no teleportation is possible, we move through 0 → 1 → 2 → 3. Thus, the answer is 3.
 

// Constraints:

// 1 <= n == nums.length <= 10^5
// 1 <= nums[i] <= 10^6




// Solution:



constexpr int M=1e6+1, N=1e5+1;
bitset<M> sieve;
int pIdx[M]={[0 ... (M-1)]=-1};
vector<int> primes;
int sz=0;
void Sieve(){
    if (sieve[0]) return;
    sieve[0]=sieve[1]=1;
    primes.reserve(80000);
    for(int i=2; i<=1000; i+=1+(i>2)){
        if(sieve[i]) continue;
        primes.push_back(i);
        pIdx[i]=sz++;
        for(int j=i*i; j<M; j+=i)
            sieve[j]=1;
    }
    for(int i=1001; i<M; i+=2){
        if(!sieve[i]){
            pIdx[i]=sz++;
            primes.push_back(i);
        }
    }
}
vector<int> mp[80000];
class Solution {
public:
    static void reset(const bitset<80000>& used, int maxPidx){
        for(int i=0; i<=maxPidx; i++){
            if(used[i]) mp[i].clear();
        }
    }
    static int minJumps(vector<int>& nums) {
        Sieve();
    //    cout<<sz<<endl;
        const int n=nums.size();
        bitset<80000> used;
        int maxPidx=-1;
        for(int i=0; i<n; i++){
            const int x=nums[i];
            if(!sieve[x]){
                int idx=pIdx[x];
                mp[idx].push_back(i);
                used[idx]=1;
                maxPidx=max(idx, maxPidx);
            }
        }
        bitset<N> vis;
        queue<int> q;
        q.push(n-1);
        vis[n-1]=1;
        for(int d=0; !q.empty(); d++){
            int qz=q.size();
            while(qz--){
                auto i=q.front();
                q.pop();
                if(i==0){
                    reset(used, maxPidx);
                    return d;
                }
                int x=nums[i];
                if(i>0 && !vis[i-1]){
                    vis[i-1]=1; 
                    q.push(i-1);
                }
                if(i<n-1 && !vis[i+1]){
                    vis[i+1]=1;
                    q.push(i+1);
                }
                
                for(int j=0; j<sz && x>1; j++){
                    int p=primes[j];
                    if (!sieve[x]) p=x;
                    if(x%p==0){
                        const int idx=pIdx[p];
                        while(x%p==0) x/=p;
                        for(auto& it : mp[idx]){
                            if(!vis[it]){ vis[it]=1; q.push(it); }
                        }
                        used[idx]=0;
                        mp[idx].clear();
                    }
                }
            }
        }
        return -1;
    }
};
auto init = []()
{ 
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();