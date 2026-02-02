// 3013. Divide an Array Into Subarrays With Minimum Cost II

// You are given a 0-indexed array of integers nums of length n, and two positive integers k and dist.

// The cost of an array is the value of its first element. For example, the cost of [1,2,3] is 1 while the cost of [3,4,1] is 3.

// You need to divide nums into k disjoint contiguous subarrays, such that the difference between the starting index of the second subarray and the starting index of the kth subarray should be less than or equal to dist. In other words, if you divide nums into the subarrays nums[0..(i1 - 1)], nums[i1..(i2 - 1)], ..., nums[ik-1..(n - 1)], then ik-1 - i1 <= dist.

// Return the minimum possible sum of the cost of these subarrays.

 

// Example 1:

// Input: nums = [1,3,2,6,4,2], k = 3, dist = 3
// Output: 5
// Explanation: The best possible way to divide nums into 3 subarrays is: [1,3], [2,6,4], and [2]. This choice is valid because ik-1 - i1 is 5 - 2 = 3 which is equal to dist. The total cost is nums[0] + nums[2] + nums[5] which is 1 + 2 + 2 = 5.
// It can be shown that there is no possible way to divide nums into 3 subarrays at a cost lower than 5.
// Example 2:

// Input: nums = [10,1,2,2,2,1], k = 4, dist = 3
// Output: 15
// Explanation: The best possible way to divide nums into 4 subarrays is: [10], [1], [2], and [2,2,1]. This choice is valid because ik-1 - i1 is 3 - 1 = 2 which is less than dist. The total cost is nums[0] + nums[1] + nums[2] + nums[3] which is 10 + 1 + 2 + 2 = 15.
// The division [10], [1], [2,2,2], and [1] is not valid, because the difference between ik-1 and i1 is 5 - 1 = 4, which is greater than dist.
// It can be shown that there is no possible way to divide nums into 4 subarrays at a cost lower than 15.
// Example 3:

// Input: nums = [10,8,18,9], k = 3, dist = 1
// Output: 36
// Explanation: The best possible way to divide nums into 4 subarrays is: [10], [8], and [18,9]. This choice is valid because ik-1 - i1 is 2 - 1 = 1 which is equal to dist.The total cost is nums[0] + nums[1] + nums[2] which is 10 + 8 + 18 = 36.
// The division [10], [8,18], and [9] is not valid, because the difference between ik-1 and i1 is 3 - 1 = 2, which is greater than dist.
// It can be shown that there is no possible way to divide nums into 3 subarrays at a cost lower than 36.
 

// Constraints:

// 3 <= n <= 10^5
// 1 <= nums[i] <= 10^9
// 3 <= k <= n
// k - 2 <= dist <= n - 2




// Solution: 



//pmr multiset version
#include <memory_resource>
class Solution {
    long long Sum=0;
    std::pmr::unsynchronized_pool_resource pool;
    //Define the multisets using the PMR namespace
    std::pmr::multiset<int> small{&pool};
    std::pmr::multiset<int> large{&pool};

    void add(int x, int m) {
        small.insert(x);
        Sum+=x;
        if (small.size()>m) {// small should have at most m elements
            int toMove=*small.rbegin();
            Sum-=toMove;
            large.insert(toMove);
            small.erase(prev(small.end()));
        }
    }

    void remove(int x) {
        auto it=small.find(x);
        if (it!=small.end()) {
            Sum-=x;
            small.erase(it);
            if (!large.empty()) {
                auto lit=large.begin(); // Use iterator 
                int toMove=*lit;
                Sum+=toMove;
                small.insert(toMove);
                large.erase(lit); // removal by iterator
            }
        } 
        else {
            auto itL=large.find(x);
            large.erase(itL); //remove
        }
    }

public:
    long long minimumCost(vector<int>& nums, int k, int dist) {
        const int n=nums.size();
        k--; // Need k-1 more elements after nums[0]

        // Initialize the first window [1, dist+1]
        const int iN=dist+2;
        vector<int> win(nums.begin()+1, nums.begin()+iN);
    
        // initialize small & large in linear time
        // the (k-1)-th smallest element at its sorted position
        nth_element(win.begin(), win.begin()+k, win.end());// O(dist+2)
        small.insert(win.begin(), win.begin() + k);
        Sum=accumulate(win.begin(), win.begin()+k, 0LL);
        large.insert(win.begin()+k, win.end());
    
        long long minCost=nums[0]+Sum;

        // Slide the window starting from index 1.
        // Elements in window nums[l...r]
        for (int l=1, r=dist+2; r<n; l++, r++) {
            remove(nums[l]);
            add(nums[r], k);

            // Update the minCost
            minCost=min(minCost, nums[0]+Sum);
        }
        return minCost;
    }
};