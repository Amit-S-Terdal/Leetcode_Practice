// 3397. Maximum Number of Distinct Elements After Operations

// You are given an integer array nums and an integer k.

// You are allowed to perform the following operation on each element of the array at most once:

// Add an integer in the range [-k, k] to the element.
// Return the maximum possible number of distinct elements in nums after performing the operations.

 

// Example 1:

// Input: nums = [1,2,2,3,3,4], k = 2

// Output: 6

// Explanation:

// nums changes to [-1, 0, 1, 2, 3, 4] after performing operations on the first four elements.

// Example 2:

// Input: nums = [4,4,4,4], k = 1

// Output: 3

// Explanation:

// By adding -1 to nums[0] and 1 to nums[1], nums changes to [3, 5, 4, 4].

 

// Constraints:

// 1 <= nums.length <= 10^5
// 1 <= nums[i] <= 10^9
// 0 <= k <= 10^9



// Solution: 


const int N=256, mask=255, bshift=8;
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
            int val = in[i];
            int x = (val >> shift) & mask;
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
    static int maxDistinctElements(vector<int>& nums, int k) {
        const int n=nums.size();
        int* A=nums.data();
        radix_sort(A, n);
        int cnt=0, prev=-k;
        for(int i=0; i<n; i++){
            const int x=A[i], xm=x-k, xx=x+k;
            const int choice=min(max(xm, prev+1), xx);
            if (choice>prev){
                cnt++;
                prev=choice;
            }
        }
        return cnt;
    }
};