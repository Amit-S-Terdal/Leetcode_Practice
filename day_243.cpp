
// 2221. Find Triangular Sum of an Array

// You are given a 0-indexed integer array nums, where nums[i] is a digit between 0 and 9 (inclusive).

// The triangular sum of nums is the value of the only element present in nums after the following process terminates:

// Let nums comprise of n elements. If n == 1, end the process. Otherwise, create a new 0-indexed integer array newNums of length n - 1.
// For each index i, where 0 <= i < n - 1, assign the value of newNums[i] as (nums[i] + nums[i+1]) % 10, where % denotes modulo operator.
// Replace the array nums with newNums.
// Repeat the entire process starting from step 1.
// Return the triangular sum of nums.

 

// Example 1:


// Input: nums = [1,2,3,4,5]
// Output: 8
// Explanation:
// The above diagram depicts the process from which we obtain the triangular sum of the array.
// Example 2:

// Input: nums = [5]
// Output: 5
// Explanation:
// Since there is only one element in nums, the triangular sum is the value of that element itself.
 

// Constraints:

// 1 <= nums.length <= 1000
// 0 <= nums[i] <= 9



// Solution:

#include <array>
#include <vector>

// this variant version removes Pow
static std::array<int, 10> inv{};
static std::array<int, 10> inv{};
class Solution {
public:
    using int3=std::array<int, 3>; 
    using int3=std::array<int, 3>; 
    static void inv_mod10(){
        if (inv[1]==1) return;
        inv[1]=1, inv[3]=7, inv[7]=3, inv[9]=9;
    }
    static int3 factor(unsigned x){
        int exp2 = 0;
        while ((x & 1) == 0 && x != 0) {
            exp2++;
            x >>= 1;
        }
        x>>=exp2;
        int exp5=0;
        for (;x%5==0; x/=5) exp5++;
        x%=10;
        return {exp2, exp5, int(x)};
    }
    static int3 mul(const int3& x, const int3& y){
        return {x[0]+y[0], x[1]+y[1], x[2]*y[2]%10};
    }
    static int3 div(const int3& x, const int3& y){
        return {x[0]-y[0], x[1]-y[1], x[2]*inv[y[2]]%10};
    }
    static int toInt(const int3& x){
        if (x[0]>0 && x[1]>0) return 0;
        if (x[1]>0) return  5*x[2]%10;
        return  (x[2]<<x[0])%10;
    }

    static std::vector<int> Comb(int n){
        std::vector<int3> a(n+1, {0, 0, 0});
        std::vector<int> A(n+1);
        a[0]=a[n]={0, 0, 1};
        A[0]=A[n]=1;
        for(int k=1; k<=n/2; k++){
            a[k]=a[n-k]=div(mul(a[k-1], factor(n-k+1)), factor(k));
            A[k]=A[n-k]=toInt(a[k]);
        }
        return  A;
    }

    static int triangularSum(std::vector<int>& nums) {
        const int n=nums.size()-1;
        inv_mod10();
        auto A=Comb(n);
        int ans=0;
        for(int i=0; i<=n; i++){
            ans=(ans+A[i]*nums[i])%10;
        }
        return ans;
    }
};