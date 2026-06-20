// 1840. Maximum Building Height

// You want to build n new buildings in a city. The new buildings will be built in a line and are labeled from 1 to n.

// However, there are city restrictions on the heights of the new buildings:

// The height of each building must be a non-negative integer.
// The height of the first building must be 0.
// The height difference between any two adjacent buildings cannot exceed 1.
// Additionally, there are city restrictions on the maximum height of specific buildings. These restrictions are given as a 2D integer array restrictions where restrictions[i] = [idi, maxHeighti] indicates that building idi must have a height less than or equal to maxHeighti.

// It is guaranteed that each building will appear at most once in restrictions, and building 1 will not be in restrictions.

// Return the maximum possible height of the tallest building.

 

// Example 1:


// Input: n = 5, restrictions = [[2,1],[4,1]]
// Output: 2
// Explanation: The green area in the image indicates the maximum allowed height for each building.
// We can build the buildings with heights [0,1,2,1,2], and the tallest building has a height of 2.
// Example 2:


// Input: n = 6, restrictions = []
// Output: 5
// Explanation: The green area in the image indicates the maximum allowed height for each building.
// We can build the buildings with heights [0,1,2,3,4,5], and the tallest building has a height of 5.
// Example 3:


// Input: n = 10, restrictions = [[5,3],[2,5],[7,4],[10,3]]
// Output: 5
// Explanation: The green area in the image indicates the maximum allowed height for each building.
// We can build the buildings with heights [0,1,2,3,3,4,4,5,4,3], and the tallest building has a height of 5.
 

// Constraints:

// 2 <= n <= 10^9
// 0 <= restrictions.length <= min(n - 1, 10^5)
// 2 <= idi <= n
// idi is unique.
// 0 <= maxHeighti <= 10^9




// Solution:
 



using int2=pair<int, int>;
const int N=1e5+2;
static int2 R[N];
static constexpr int M=1<<10, mask=M-1, bshift=10;
int freq[M];

void radix_sort(int2* nums, int n) {
    int2* buffer=(int2*)alloca(n*sizeof(int2));  // buffer
    int2* in=nums;
    int2* out=buffer;

    // 3 rounds covers 30 bits 
    for (int round=0; round < 3; round++) {
        const int shift=round * bshift;
        memset(freq, 0, sizeof(freq));

        for (int i=0; i<n; i++)
            freq[(in[i].first >> shift) & mask]++;

        partial_sum(freq, freq+M, freq);

        for (int i = n - 1; i >= 0; i--) {
            int x = (in[i].first >> shift) & mask;
            out[--freq[x]] = in[i];
        }

        // Swap pointers for the next round
        swap(in, out);
    }
    //data is currently in buffer. We must copy it back to nums.
    if (in != nums) {
        memcpy(nums, in, n * sizeof(int2));
    }
}
class Solution {
public:
    static int maxBuilding(int n, vector<vector<int>>& restrictions) {
        int m=restrictions.size();
        for(int i=0; i<m; i++)
            R[i]={restrictions[i][0], restrictions[i][1]};
        R[m]={1, 0};
        R[m+1]={n, n-1};
        m+=2;
        radix_sort(R, m);
              
        for(int i=1; i<m; i++)
            R[i].second=min(R[i].second, R[i-1].second-R[i-1].first+R[i].first);
        
        for(int i=m-2; i>=1; i--)
            R[i].second=min(R[i].second, R[i+1].second+R[i+1].first-R[i].first); 

        int ans=0;
        for(int i=0; i<m-1; i++){ 
            int peak=(R[i].second+R[i+1].second+R[i+1].first-R[i].first)/2;
            ans=max(ans, peak);
        }
        return ans;
    }
};
auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();