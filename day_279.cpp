// 3321. Find X-Sum of All K-Long Subarrays II

// You are given an array nums of n integers and two integers k and x.

// The x-sum of an array is calculated by the following procedure:

// Count the occurrences of all elements in the array.
// Keep only the occurrences of the top x most frequent elements. If two elements have the same number of occurrences, the element with the bigger value is considered more frequent.
// Calculate the sum of the resulting array.
// Note that if an array has less than x distinct elements, its x-sum is the sum of the array.

// Return an integer array answer of length n - k + 1 where answer[i] is the x-sum of the subarray nums[i..i + k - 1].

 

// Example 1:

// Input: nums = [1,1,2,2,3,4,2,3], k = 6, x = 2

// Output: [6,10,12]

// Explanation:

// For subarray [1, 1, 2, 2, 3, 4], only elements 1 and 2 will be kept in the resulting array. Hence, answer[0] = 1 + 1 + 2 + 2.
// For subarray [1, 2, 2, 3, 4, 2], only elements 2 and 4 will be kept in the resulting array. Hence, answer[1] = 2 + 2 + 2 + 4. Note that 4 is kept in the array since it is bigger than 3 and 1 which occur the same number of times.
// For subarray [2, 2, 3, 4, 2, 3], only elements 2 and 3 are kept in the resulting array. Hence, answer[2] = 2 + 2 + 2 + 3 + 3.
// Example 2:

// Input: nums = [3,8,7,8,7,5], k = 2, x = 2

// Output: [11,15,15,15,12]

// Explanation:

// Since k == x, answer[i] is equal to the sum of the subarray nums[i..i + k - 1].

 

// Constraints:

// nums.length == n
// 1 <= n <= 10^5
// 1 <= nums[i] <= 10^9
// 1 <= x <= k <= nums.length



// Solution:


class Solution {
public:
    using int2 = pair<int,int>; // (freq, value)
    unordered_map<int,int> freq;
    set<int2, greater<int2>> top, rest;
    long long curr_sum=0;
    inline void insert_val(int v, int x) {
        int& f=freq[v];
        if (f>0) {
            const int2 fv={f, v};
            auto it=top.find(fv);
            if (it!=top.end()) {
                top.erase(it);
                curr_sum-=1LL*f*v;
            }
            else rest.erase(fv);
        }

        top.insert({++f,v});
        curr_sum+=1LL*f*v;

        // too many in top, move smallest one
        if (top.size()>x) {
            auto it=prev(top.end());
            curr_sum-=1LL*it->first*it->second;
            rest.insert(*it);
            top.erase(it);
        }
    }

    inline void erase_val(int v, int x) {
        int& f=freq[v];
        if (f==0) return;
        const int2 fv={f, v};
        auto it=top.find(fv);
        if (it!=top.end()){
            top.erase(it);
            curr_sum-=1LL*f*v;
        }
        else rest.erase(fv);

        if (--f==0) 
            freq.erase(v);
        else 
            rest.insert({f,v});
            
        // Move head of rest to top if possible
        if (top.size()<x && !rest.empty()) {
            auto it=rest.begin();
            curr_sum+=1LL*it->first*it->second;
            top.insert(*it);
            rest.erase(it);
        }
    }

    vector<long long> findXSum(vector<int>& nums, int k, int x) {
        const int n=nums.size(), sz=n-k+1;
        vector<long long> ans(sz);
    
        freq.reserve(n);
        
        // 1st window
        for (int i=0;i<k;i++) insert_val(nums[i], x);
        ans[0]=curr_sum;

        // slide window
        for (int l=1, r=k; r<n; l++, r++) {
            erase_val(nums[l-1], x);
            insert_val(nums[r], x);
            ans[l]=curr_sum;
        }
        return ans;
    }
};
static const auto init = []() noexcept {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();