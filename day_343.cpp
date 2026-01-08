// 1458. Max Dot Product of Two Subsequences

// Given two arrays nums1 and nums2.

// Return the maximum dot product between non-empty subsequences of nums1 and nums2 with the same length.

// A subsequence of a array is a new array which is formed from the original array by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, [2,3,5] is a subsequence of [1,2,3,4,5] while [1,5,3] is not).

 

// Example 1:

// Input: nums1 = [2,1,-2,5], nums2 = [3,0,-6]
// Output: 18
// Explanation: Take subsequence [2,-2] from nums1 and subsequence [3,-6] from nums2.
// Their dot product is (2*3 + (-2)*(-6)) = 18.
// Example 2:

// Input: nums1 = [3,-2], nums2 = [2,-6,7]
// Output: 21
// Explanation: Take subsequence [3] from nums1 and subsequence [7] from nums2.
// Their dot product is (3*7) = 21.
// Example 3:

// Input: nums1 = [-1,-1], nums2 = [1,1]
// Output: -1
// Explanation: Take subsequence [-1] from nums1 and subsequence [1] from nums2.
// Their dot product is -1.
 

// Constraints:

// 1 <= nums1.length, nums2.length <= 500
// -1000 <= nums1[i], nums2[i] <= 1000




// Solution:




class Solution {
public:
    static int maxDotProduct(vector<int>& nums1, vector<int>& nums2) {
        const int n1=nums1.size(), n2=nums2.size();
        if (n1<n2) // for less space, interchange nums1 & nums2
            return maxDotProduct(nums2, nums1);
        int dp[2][501];
        fill(&dp[0][0], &dp[0][0]+2*501, INT_MIN);
        int res=INT_MIN;
        for(int i=n1-1; i>=0; i--){
            for(int j=n2-1; j>=0; j--){
                int x=nums1[i]*nums2[j], tmp=dp[i&1][j];
                tmp=max(tmp, x);
                tmp=max(tmp, x+(i+1<n1 & j+1<n2 ? dp[(i+1)&1][j+1]:0));
                tmp=max(tmp, dp[i&1][(j+1)]);
                dp[i&1][j]=max(tmp, dp[(i+1)&1][j]);
                res=max(res, dp[i&1][j]);
            }
        }
        return res;
    }
};
auto init = []()
{ 
    ios::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);
    return 'c';
}();