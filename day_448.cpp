// 2615. Sum of Distances

// You are given a 0-indexed integer array nums. There exists an array arr of length nums.length, where arr[i] is the sum of |i - j| over all j such that nums[j] == nums[i] and j != i. If there is no such j, set arr[i] to be 0.

// Return the array arr.

 

// Example 1:

// Input: nums = [1,3,1,1,2]
// Output: [5,0,3,4,0]
// Explanation: 
// When i = 0, nums[0] == nums[2] and nums[0] == nums[3]. Therefore, arr[0] = |0 - 2| + |0 - 3| = 5. 
// When i = 1, arr[1] = 0 because there is no other index with value 3.
// When i = 2, nums[2] == nums[0] and nums[2] == nums[3]. Therefore, arr[2] = |2 - 0| + |2 - 3| = 3. 
// When i = 3, nums[3] == nums[0] and nums[3] == nums[2]. Therefore, arr[3] = |3 - 0| + |3 - 2| = 4. 
// When i = 4, arr[4] = 0 because there is no other index with value 2. 

// Example 2:

// Input: nums = [0,5,3]
// Output: [0,0,0]
// Explanation: Since each element in nums is distinct, arr[i] = 0 for all i.
 

// Constraints:

// 1 <= nums.length <= 10^5
// 0 <= nums[i] <= 10^9




// Solution: 


#include <memory_resource>
pmr::unsynchronized_pool_resource pool;
constexpr int N=1e5+1;
long long nxt[N];
class Solution {
public:
    static void print(auto& c){
        for (auto x: c)cout<<x<<", ";
        cout<<endl;
    }
    static vector<long long> distance(vector<int>& nums) {
        pool.release();
        pmr::unordered_map<int, long long> idx(&pool);
        const int n=nums.size();
        for(int i=0; i<n; i++){
            const int x=nums[i];
            auto it=idx.find(x);
            if (it==idx.end()) idx[x]=i, nxt[i]=-1;
            else nxt[i]=idx[x], idx[x]=i;
        }
        vector<long long> ans(n, 0);
        // trasverse idx
        for(auto& [x, h]: idx){
        //    cout<<"x="<<x<<":"; print(v);
            if (nxt[h]==-1) continue;
            long long total=0, prefix=0;
            int vz=0;
            for(int j=h; j!=-1; j=nxt[j]){
                total+=j;
                vz++;
            }
            for(int i=vz-1, j=h; j!=-1; i--,j=nxt[j]){
                ans[j]=(2LL*i-vz+2)*j+2LL*prefix-total;
                prefix+=j;
            }
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