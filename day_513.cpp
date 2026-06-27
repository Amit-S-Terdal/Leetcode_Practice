// 3020. Find the Maximum Number of Elements in Subset

// You are given an array of positive integers nums.

// You need to select a subset of nums which satisfies the following condition:

// You can place the selected elements in a 0-indexed array such that it follows the pattern: [x, x2, x4, ..., xk/2, xk, xk/2, ..., x4, x2, x] (Note that k can be be any non-negative power of 2). For example, [2, 4, 16, 4, 2] and [3, 9, 3] follow the pattern while [2, 4, 8, 4, 2] does not.
// Return the maximum number of elements in a subset that satisfies these conditions.

 

// Example 1:

// Input: nums = [5,4,1,2,2]
// Output: 3
// Explanation: We can select the subset {4,2,2}, which can be placed in the array as [2,4,2] which follows the pattern and 22 == 4. Hence the answer is 3.
// Example 2:

// Input: nums = [1,3,2,4]
// Output: 1
// Explanation: We can select the subset {1}, which can be placed in the array as [1] which follows the pattern. Hence the answer is 1. Note that we could have also selected the subsets {2}, {3}, or {4}, there may be multiple subsets which provide the same answer. 
 

// Constraints:

// 2 <= nums.length <= 10^5
// 1 <= nums[i] <= 10^9


// Solution: 




using int2=pair<int, int>;
using ll=long long;
static int K[4]; // K[0]=sqrt(1e9), K[i+1]=sqrt(K[i])
static int freq[31623]; 
static bitset<31623> seenSq;// x*x>K[0] & seen in nums

class Solution {
public:
    static void findK(){
        if (K[0]) return;
        K[0]=sqrt(1e9);
        for(int i=1; i<4; i++){
            K[i]=sqrt(K[i-1]);
        }
    }
    static void reset(vector<int>& nums) {
        for(int x : nums) {
            if (x<=K[0]) freq[x]=0;
        }
        seenSq.reset();
    }

    static int maximumLength(vector<int>& nums) {
        findK();
        int xMax=0, n=nums.size();
        for (int x : nums) {
            if (x>K[0]) {// only perfect square matters the max len
                int rx=sqrt(x);
                if (rx*rx==x) seenSq[rx]=1;
            }
            else {
                freq[x]++;
                xMax=max(xMax, x);
            }
        }
        if (xMax==0) {
            reset(nums);
            return 1;
        }
        int ans=1; 
        if (freq[1]) { 
            const int f1=freq[1];
            ans=max(ans, f1-((f1 & 1)==0));
        }
        if (ans>=9) {
            reset(nums);
            return ans; 
        }
        int k=3;
        for (int x=2; x<=xMax; x++) {
            if (freq[x]==0) continue;
            while(x>K[k]) k--;
            int cnt=0;
            ll y=x;
            bool flag=0;
            
            while(y<=K[0] && freq[y]>=2) {
                cnt+=2;
                ll y2=y*y;
                if (y*y>K[0]) { 
                    flag=1;
                    cnt+=(seenSq[y]<<1)-1;
                    break;
                }
                y=y2;
            }
            
            if (!flag) {
                bool isIn=(y<=K[0]) && (freq[y]>=1);
                cnt+=(isIn<<1)-1;
            }
            ans=max(ans, cnt);
            if (ans==2*(k+1)+1) {
                reset(nums);
                return ans;
            }
        }
        
        reset(nums);
        return ans;
    }
};

auto init = []() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();