// 1200. Minimum Absolute Difference

// Given an array of distinct integers arr, find all pairs of elements with the minimum absolute difference of any two elements.

// Return a list of pairs in ascending order(with respect to pairs), each pair [a, b] follows

// a, b are from arr
// a < b
// b - a equals to the minimum absolute difference of any two elements in arr
 

// Example 1:

// Input: arr = [4,2,1,3]
// Output: [[1,2],[2,3],[3,4]]
// Explanation: The minimum absolute difference is 1. List all pairs with difference equal to 1 in ascending order.
// Example 2:

// Input: arr = [1,3,6,10,15]
// Output: [[1,3]]
// Example 3:

// Input: arr = [3,8,-10,23,19,-4,-14,27]
// Output: [[-14,-10],[19,23],[23,27]]
 

// Constraints:

// 2 <= arr.length <= 10^5
// -10^6 <= arr[i] <= 10^6



// Solution: 



static constexpr int N=2048, MASK=2047, bshift=11;
static int freq[N];

void radix_sort(int* nums, int n) {// 2 rounds
    int* buf=(int*)alloca(n * sizeof(int));

    const int bias=*min_element(nums, nums+n);
    // normalize! 
    // auto-vectorized (SIMD)
    for (int i=0; i<n; i++) nums[i]-=bias;

    int* in=nums;
    int* out=buf;

    //1st round
    memset(freq, 0, sizeof(freq));

    for (int i=0; i<n; i++) 
        freq[in[i] & MASK]++;

    partial_sum(freq, freq+N, freq);

    for (int i=n-1; i>=0; i--) {
        const int v=in[i];
        out[--freq[v & MASK]]=v;
    }

    // 2nd round
    memset(freq, 0, sizeof(freq));

    for (int i=0; i<n; i++)
        freq[(out[i]>>bshift) & MASK]++;

    partial_sum(freq, freq+N, freq);

    for (int i=n-1; i>= 0; i--) {
        const int v=out[i];
        in[--freq[(v>>bshift) & MASK]]=v;
    }

    // restore! auto-vectorized (SIMD)
    for (int i=0; i<n; i++) nums[i]+=bias;
}

class Solution {
public:
    static vector<vector<int>> minimumAbsDifference(vector<int>& arr){
        const int n=arr.size();
        int* a=arr.data();
        radix_sort(a, n);
        
        int minD=1e9+7;
        for(int i=1; i<n; i++){
            minD=min(minD, a[i]-a[i-1]);
        }
        vector<vector<int>> ans;
        for(int i=1; i<n; i++){
            if (a[i]-a[i-1]==minD)
                ans.push_back({a[i-1], a[i]});
        }
        return ans;
    }
};
auto init = []()
{ 
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();