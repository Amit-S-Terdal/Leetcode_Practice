// 3370. Smallest Number With All Set Bits
// Easy

// You are given a positive number n.

// Return the smallest number x greater than or equal to n, such that the binary representation of x contains only set bits

 

// Example 1:

// Input: n = 5

// Output: 7

// Explanation:

// The binary representation of 7 is "111".

// Example 2:

// Input: n = 10

// Output: 15

// Explanation:

// The binary representation of 15 is "1111".

// Example 3:

// Input: n = 3

// Output: 3

// Explanation:

// The binary representation of 3 is "11".

 

// Constraints:

// 1 <= n <= 1000


// Solution:


int A[11];// only 11 possiblities
class Solution {
public:
    static void buildA(){
        if (A[0]==1) return; // only once
        A[0]=1;
        for(int i=1; i<=10; i++)
            A[i]=(1<<i)-1;// 2^i-1
    }
    static int smallestNumber(int n) {
        buildA();
        return *lower_bound(A, A+11, n);
    }
};