// 611. Valid Triangle Number

// Given an integer array nums, return the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.

 

// Example 1:

// Input: nums = [2,2,3,4]
// Output: 3
// Explanation: Valid combinations are: 
// 2,3,4 (using the first 2)
// 2,3,4 (using the second 2)
// 2,2,3
// Example 2:

// Input: nums = [4,2,3,4]
// Output: 4
 

// Constraints:

// 1 <= nums.length <= 1000
// 0 <= nums[i] <= 1000


// Solution: 

#include <vector>
using namespace std;
class Solution {
public:
    int triangleNumber(vector<int>& nums) {
        int freq[1001]={0}, xMin=1001, xMax=0;
        for(int x: nums){
            freq[x]++;
            xMin=min(xMin, x);
            xMax=max(xMax, x);
        }
        for(int i=0, x=xMin; x<=xMax; x++){
            const int f=freq[x];
            if (f==0) continue;
            fill(nums.begin()+i, nums.begin()+i+f, x);
            i+=f;
        }
        int n=nums.size(), ans=0;
        for(int i=2; i<n; i++){
            int l=0, r=i-1;
            while(l<r){
                if(nums[l]+nums[r]>nums[i]){
                    ans+=r-l;
                    r--;
                }
                else l++;
            }
        }
        return ans;
    }
};