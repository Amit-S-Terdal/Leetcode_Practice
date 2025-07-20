// 3202. Find the Maximum Length of Valid Subsequence II

// You are given an integer array nums and a positive integer k.
// A subsequence sub of nums with length x is called valid if it satisfies:

// (sub[0] + sub[1]) % k == (sub[1] + sub[2]) % k == ... == (sub[x - 2] + sub[x - 1]) % k.
// Return the length of the longest valid subsequence of nums.
 

// Example 1:

// Input: nums = [1,2,3,4,5], k = 2

// Output: 5

// Explanation:

// The longest valid subsequence is [1, 2, 3, 4, 5].

// Example 2:

// Input: nums = [1,4,2,3,1,4], k = 3

// Output: 4

// Explanation:

// The longest valid subsequence is [1, 4, 1, 4].

 

// Constraints:

// 2 <= nums.length <= 10^3
// 1 <= nums[i] <= 10^7
// 1 <= k <= 10^3


// Solution: 



class Solution {
    public:
        static int maximumLength(vector<int>& nums, int k) {
            //let dp[i] denote len for subsequence 
            short ans=0;
            vector<short> dp;
            for (int i=0; i<k; i++){
                dp.assign(k, 0);
                for (int x : nums){
                    int j=x%k;
                    dp[j]=max(dp[j], short(dp[(i+k-j)%k]+1));
                    ans=max(ans, dp[j]);
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