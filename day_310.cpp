// 3578. Count Partitions With Max-Min Difference at Most K

// You are given an integer array nums and an integer k. Your task is to partition nums into one or more non-empty contiguous segments such that in each segment, the difference between its maximum and minimum elements is at most k.

// Return the total number of ways to partition nums under this condition.

// Since the answer may be too large, return it modulo 109 + 7.

 

// Example 1:

// Input: nums = [9,4,1,3,7], k = 4

// Output: 6

// Explanation:

// There are 6 valid partitions where the difference between the maximum and minimum elements in each segment is at most k = 4:

// [[9], [4], [1], [3], [7]]
// [[9], [4], [1], [3, 7]]
// [[9], [4], [1, 3], [7]]
// [[9], [4, 1], [3], [7]]
// [[9], [4, 1], [3, 7]]
// [[9], [4, 1, 3], [7]]
// Example 2:

// Input: nums = [3,3,4], k = 0

// Output: 2

// Explanation:

// There are 2 valid partitions that satisfy the given conditions:

// [[3], [3], [4]]
// [[3, 3], [4]]
 

// Constraints:

// 2 <= nums.length <= 5 * 10^4
// 1 <= nums[i] <= 10^9
// 0 <= k <= 10^9



// Solution: 



constexpr int N=5e4, mod=1e9+7;
int qMax[N], qMin[N];
int frontX, backX, frontN, backN;

class Solution {
public:
    static int countPartitions(vector<int>& nums, int k) {
        const int n=nums.size();

        //reset monotonic queue
        frontX=frontN=0;
        backX=backN=-1;

        long long cnt=0;
        int* sum=(int*)alloca((n+2)*sizeof(int));
        memset(sum, 0, sizeof(sum));
        sum[1]=1;

        for (int l=0, r=0; r< n; r++) {
            int x=nums[r];

            // max queue
            while (backX>=frontX && qMax[backX]<x) backX--;
            qMax[++backX]=x;

            // min queue
            while (backN>=frontN && qMin[backN]>x) backN--;
            qMin[++backN]=x;

            // shrink window
            while (qMax[frontX]-qMin[frontN]>k) {
                const int y=nums[l];
                frontX+=(qMax[frontX]==y);
                frontN+=(qMin[frontN]==y);
                l++;
            }

            cnt=(mod+sum[r+1]-sum[l])%mod;
            sum[r+2]=(sum[r+1]+cnt)%mod;
        }
        return cnt;
    }
};