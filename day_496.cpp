// 3691. Maximum Total Subarray Value II

// You are given an integer array nums of length n and an integer k.

// You must select exactly k distinct non-empty subarrays nums[l..r] of nums. Subarrays may overlap, but the exact same subarray (same l and r) cannot be chosen more than once.

// The value of a subarray nums[l..r] is defined as: max(nums[l..r]) - min(nums[l..r]).

// The total value is the sum of the values of all chosen subarrays.

// Return the maximum possible total value you can achieve.

 

// Example 1:

// Input: nums = [1,3,2], k = 2

// Output: 4

// Explanation:

// One optimal approach is:

// Choose nums[0..1] = [1, 3]. The maximum is 3 and the minimum is 1, giving a value of 3 - 1 = 2.
// Choose nums[0..2] = [1, 3, 2]. The maximum is still 3 and the minimum is still 1, so the value is also 3 - 1 = 2.
// Adding these gives 2 + 2 = 4.

// Example 2:

// Input: nums = [4,2,5,1], k = 3

// Output: 12

// Explanation:

// One optimal approach is:

// Choose nums[0..3] = [4, 2, 5, 1]. The maximum is 5 and the minimum is 1, giving a value of 5 - 1 = 4.
// Choose nums[1..3] = [2, 5, 1]. The maximum is 5 and the minimum is 1, so the value is also 4.
// Choose nums[2..3] = [5, 1]. The maximum is 5 and the minimum is 1, so the value is again 4.
// Adding these gives 4 + 4 + 4 = 12.

 

// Constraints:

// 1 <= n == nums.length <= 5 * 10^4
// 0 <= nums[i] <= 10^9
// 1 <= k <= min(10^5, n * (n + 1) / 2)


// Solution: 



// 1-based indexed segment tree defined iteratively
static constexpr int N=1<<17; // 2*bit_ceil(5*10^4)
int segMin[N], segMax[N]; 
using int2=pair<int, int>;
struct segTree {
    unsigned n;
    vector<int>& A;
    segTree(vector<int>& A) : n(A.size()), A(A) {
        for (int i=0; i<n; i++) segMin[i+n]=segMax[i+n]=A[i];
        for(int i=n-1; i>0; i--) {
            segMin[i]=min(segMin[i*2], segMin[i*2+1]);
            segMax[i]=max(segMax[i*2], segMax[i*2+1]);
        }
    }
    // Range Query minmax for [l, r]
    int2 qMinmax(int l, int r) {
        int mn=INT_MAX, mx=INT_MIN;
        for(l+=n, r+=n+1; l<r; l>>=1, r>>=1){
            if (l&1){
                mx=max(mx, segMax[l]);
                mn=min(mn, segMin[l++]);
            }
            if (r&1){
                mx=max(mx, segMax[--r]);
                mn=min(mn, segMin[r]);
            }
        }
        return {mn, mx};
    }
};

using int3=pair<int, pair<int, int>>;
using ll=long long;
class Solution {
public:
    static long long maxTotalValue(vector<int>& nums, int k) {
        const int n=nums.size();
        auto [it0, itM]=minmax_element(nums.begin(), nums.end());
        int v0=*itM-*it0;
        auto [l0, r0]=minmax(it0-nums.begin(), itM-nums.begin());
        ll ways=(ll)(l0+1)*(n-r0);// when l<=l0 and r>=r0  val(nums[l..r])=v0
        if (ways>=k) return (ll)k*v0;
        ll total=ways*v0;
        k-=ways;
        
        segTree tree(nums);
        priority_queue<int3> pq;
        
        for(int l=l0+1; l<n; l++){
            auto [mn, mx]=tree.qMinmax(l,n-1);
            pq.push({mx-mn, {l, n-1}});
        }
        for(int l=0; l<=l0; l++){
            auto [mn, mx]=tree.qMinmax(l,r0-1);
            pq.push({mx-mn, {l, r0-1}});
        }
       
        while(k--){
            auto [x, lr]=pq.top(); pq.pop();
            auto [l, r]=lr;
            if (x==0) break;
            total+=x;
            if (r>l) {
                auto [mn, mx]=tree.qMinmax(l,r-1);
                pq.push({mx-mn, {l, r-1}});
            }
        }
        return total;
    }
};