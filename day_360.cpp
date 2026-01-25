// 1984. Minimum Difference Between Highest and Lowest of K Scores

// You are given a 0-indexed integer array nums, where nums[i] represents the score of the ith student. You are also given an integer k.

// Pick the scores of any k students from the array so that the difference between the highest and the lowest of the k scores is minimized.

// Return the minimum possible difference.

 

// Example 1:

// Input: nums = [90], k = 1
// Output: 0
// Explanation: There is one way to pick score(s) of one student:
// - [90]. The difference between the highest and lowest score is 90 - 90 = 0.
// The minimum possible difference is 0.
// Example 2:

// Input: nums = [9,4,1,7], k = 2
// Output: 2
// Explanation: There are six ways to pick score(s) of two students:
// - [9,4,1,7]. The difference between the highest and lowest score is 9 - 4 = 5.
// - [9,4,1,7]. The difference between the highest and lowest score is 9 - 1 = 8.
// - [9,4,1,7]. The difference between the highest and lowest score is 9 - 7 = 2.
// - [9,4,1,7]. The difference between the highest and lowest score is 4 - 1 = 3.
// - [9,4,1,7]. The difference between the highest and lowest score is 7 - 4 = 3.
// - [9,4,1,7]. The difference between the highest and lowest score is 7 - 1 = 6.
// The minimum possible difference is 2.
 

// Constraints:

// 1 <= k <= nums.length <= 1000
// 0 <= nums[i] <= 10^5



// Solution: 



const int N=64, mask=63, bshift=6;
int freq[N];

void radix_sort(int* nums, int n) {
    int* output = (int*)alloca(n * sizeof(int));  // buffer
    unsigned M=*max_element(nums, nums+n);
    const int Mround = max(1, int(31-countl_zero(M)+bshift)/bshift);
    int* in=nums;
    int* out=output;
    bool swapped=0;

    for (int round=0; round<Mround; round++) {
        const int shift=round*bshift;
        memset(freq, 0, sizeof(freq));

        for (int i = 0; i < n; i++)
            freq[(in[i] >> shift) & mask]++;

        partial_sum(freq, freq+N, freq);

        for (int i = n - 1; i >= 0; i--) {
            uint64_t val = in[i];
            uint64_t x = (val >> shift) & mask;
            out[--freq[x]] = val;
        }

        swap(in, out);
        swapped = !swapped;
    }

    // If needed, copy back
    if (swapped)
        memcpy(nums, in, n * sizeof(int));
}
class Solution {
public:
    static int minimumDifference(vector<int>& nums, int k) {
        const int n=nums.size();
        if (k==1) return 0; 
        int* nums_=nums.data();
        radix_sort(nums_, n);
        int diff=INT_MAX;
        for(int l=0, r=k-1; r<n; r++, l++){
            diff=min(diff, nums_[r]-nums_[l]);
        }
        return diff;
    }
};