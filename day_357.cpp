// 3507. Minimum Pair Removal to Sort Array I

// Given an array nums, you can perform the following operation any number of times:

// Select the adjacent pair with the minimum sum in nums. If multiple such pairs exist, choose the leftmost one.
// Replace the pair with their sum.
// Return the minimum number of operations needed to make the array non-decreasing.

// An array is said to be non-decreasing if each element is greater than or equal to its previous element (if it exists).

 

// Example 1:

// Input: nums = [5,2,3,1]

// Output: 2

// Explanation:

// The pair (3,1) has the minimum sum of 4. After replacement, nums = [5,2,4].
// The pair (2,4) has the minimum sum of 6. After replacement, nums = [5,6].
// The array nums became non-decreasing in two operations.

// Example 2:

// Input: nums = [1,2,2]

// Output: 0

// Explanation:

// The array nums is already sorted.

 

// Constraints:

// 1 <= nums.length <= 50
// -1000 <= nums[i] <= 1000



// Solution:



// C-array version
class Solution {
public:
    // check if nums is non-decreasing ignoring INT_MAX
    static bool isNonDec(int* nums, int n) {
        int prev=INT_MIN;
        for (int i=0; i<n; i++) {
            while (i<n && nums[i]==INT_MAX) i++;
            if (i==n) break;
            if (nums[i]<prev) return 0;
            prev=nums[i];
        }
        return 1;
    }

    static int minimumPairRemoval(vector<int>& _nums) {
        const int n=_nums.size();
        int* nums=_nums.data();// just consider the C-array
        int op=0;

        while (!isNonDec(nums, n)) {
            int minPair=INT_MAX, s=-1, t=-1;
            // Find pair with minimum sum where both values are not INT_MAX
            for (int i=0; i<n-1; i++) {
                if (nums[i]==INT_MAX) continue;
                // Find next valid j > i
                int j=i+1;
                while (j<n && nums[j]==INT_MAX) j++;

                if (j<n && nums[j]!=INT_MAX) {
                    int pairSum=nums[i]+nums[j];
                    if (pairSum<minPair) {
                        minPair=pairSum;
                        s=i;
                        t=j;
                    }
                }
            }

            // Merge t into s, mark t as removed
            if (s!=-1 && t!=-1) {
                nums[s]+=nums[t];
                nums[t]=INT_MAX;
                op++;
            } 
            else 
                break; // no valid pair found
        }

        return op;
    }
};

