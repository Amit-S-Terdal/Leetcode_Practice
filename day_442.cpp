// 3761. Minimum Absolute Distance Between Mirror Pairs

// You are given an integer array nums.

// A mirror pair is a pair of indices (i, j) such that:

// 0 <= i < j < nums.length, and
// reverse(nums[i]) == nums[j], where reverse(x) denotes the integer formed by reversing the digits of x. Leading zeros are omitted after reversing, for example reverse(120) = 21.
// Return the minimum absolute distance between the indices of any mirror pair. The absolute distance between indices i and j is abs(i - j).

// If no mirror pair exists, return -1.

 

// Example 1:

// Input: nums = [12,21,45,33,54]

// Output: 1

// Explanation:

// The mirror pairs are:

// (0, 1) since reverse(nums[0]) = reverse(12) = 21 = nums[1], giving an absolute distance abs(0 - 1) = 1.
// (2, 4) since reverse(nums[2]) = reverse(45) = 54 = nums[4], giving an absolute distance abs(2 - 4) = 2.
// The minimum absolute distance among all pairs is 1.

// Example 2:

// Input: nums = [120,21]

// Output: 1

// Explanation:

// There is only one mirror pair (0, 1) since reverse(nums[0]) = reverse(120) = 21 = nums[1].

// The minimum absolute distance is 1.

// Example 3:

// Input: nums = [21,120]

// Output: -1

// Explanation:

// There are no mirror pairs in the array.

 

// Constraints:

// 1 <= nums.length <= 10^5
// 1 <= nums[i] <= 10^9    



// Solution:



#include <memory_resource>
pmr::unsynchronized_pool_resource pool;
class Solution {
public:
    static int rev(int x){
        int ans=0;
        for(; x>0; x/=10){
            const int d=x%10;
            ans=10*ans+d;
        }
        return ans;
    }
    static int minMirrorPairDistance(vector<int>& nums) {
        const int n=nums.size();
        pmr::unordered_map<int, int> mp(&pool);
        mp.reserve(n);
        int dist=INT_MAX;
        for(int i=0; i<n; i++){
            const int x=nums[i], R=rev(x);
        //    cout<<i<<"->"<< x<<", Rev="<<R<<endl;
            auto it=mp.find(x);
            if (it!=mp.end())
                dist=min(dist, i-it->second);
            mp[R]=i;
        }
        return dist==INT_MAX?-1:dist;
    }
};