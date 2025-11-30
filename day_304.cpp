// 1590. Make Sum Divisible by P


// Given an array of positive integers nums, remove the smallest subarray (possibly empty) such that the sum of the remaining elements is divisible by p. It is not allowed to remove the whole array.

// Return the length of the smallest subarray that you need to remove, or -1 if it's impossible.

// A subarray is defined as a contiguous block of elements in the array.

 

// Example 1:

// Input: nums = [3,1,4,2], p = 6
// Output: 1
// Explanation: The sum of the elements in nums is 10, which is not divisible by 6. We can remove the subarray [4], and the sum of the remaining elements is 6, which is divisible by 6.
// Example 2:

// Input: nums = [6,3,5,2], p = 9
// Output: 2
// Explanation: We cannot remove a single element to get a sum divisible by 9. The best way is to remove the subarray [5,2], leaving us with [6,3] with sum 9.
// Example 3:

// Input: nums = [1,2,3], p = 3
// Output: 0
// Explanation: Here the sum is 6. which is already divisible by 3. Thus we do not need to remove anything.
 

// Constraints:

// 1 <= nums.length <= 10^5
// 1 <= nums[i] <= 10^9
// 1 <= p <= 10^9



// Solution: 



class Solution {
public:
    static int array_method(int n, int modP, vector<int>& nums, int p){
        int pos[100000];
        fill(pos, pos+p, INT_MAX);
        pos[0]=-1;
        int len=n, sumP=0;
        for(int i=0; i<n; i++){
            sumP=(sumP+nums[i])%p;
            int y=(sumP-modP+p)%p;
            if (pos[y]!=INT_MAX)
                len=min(len, i-pos[y]);
            pos[sumP]=i;
        }
        return (len==n)?-1:len;
    }
    static int minSubarray(vector<int>& nums, int p) {
        const int n=nums.size();
        long long modP=accumulate(nums.begin(), nums.end(), 0LL)%p;

    //    cout<<modP<<endl;
        if (modP==0) return 0;
        int sz=min(n, p);

        // hashmap version just comment the following line
        if (p<=n) return array_method(n, modP, nums, p);

        unordered_map<int, int> pos={{0, -1}};
        pos.reserve(sz);

        int len=n, sumP=0;
        for(int i=0; i<n; i++){
            sumP=(sumP+nums[i])%p;
            int y=(sumP-modP+p)%p;
            if (pos.count(y))
                len=min(len, i-pos[y]);
            pos[sumP]=i;
        }
        return (len==n)?-1:len;
    }
};


auto init = []() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    return 'c';
}();