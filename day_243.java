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



import java.util.*;

class Solution {
    static int[] inv = new int[10];
    
    // Triplet of exponent of 2, exponent of 5, remainder mod 10 (coprime to 2 and 5)
    static class Int3 {
        int exp2, exp5, rem;
        Int3(int a, int b, int c) {
            exp2 = a; exp5 = b; rem = c;
        }
    }

    static void invMod10() {
        if (inv[1] != 0) return;
        inv[1] = 1;
        inv[3] = 7;
        inv[7] = 3;
        inv[9] = 9;
    }

    static Int3 factor(int x) {
        int exp2 = Integer.numberOfTrailingZeros(x);
        x >>= exp2;
        int exp5 = 0;
        while (x % 5 == 0) {
            x /= 5;
            exp5++;
        }
        x %= 10;
        return new Int3(exp2, exp5, x);
    }

    static Int3 mul(Int3 x, Int3 y) {
        return new Int3(x.exp2 + y.exp2, x.exp5 + y.exp5, (x.rem * y.rem) % 10);
    }

    static Int3 div(Int3 x, Int3 y) {
        int invRem = inv[y.rem];
        return new Int3(x.exp2 - y.exp2, x.exp5 - y.exp5, (x.rem * invRem) % 10);
    }

    static int toInt(Int3 x) {
        if (x.exp2 > 0 && x.exp5 > 0) return 0;
        if (x.exp5 > 0) return (5 * x.rem) % 10;
        return (x.rem << x.exp2) % 10;
    }

    static int[] comb(int n) {
        Int3[] a = new Int3[n + 1];
        int[] A = new int[n + 1];
        a[0] = a[n] = new Int3(0, 0, 1);
        A[0] = A[n] = 1;

        for (int k = 1; k <= n / 2; k++) {
            Int3 num = factor(n - k + 1);
            Int3 den = factor(k);
            a[k] = a[n - k] = div(mul(a[k - 1], num), den);
            A[k] = A[n - k] = toInt(a[k]);
        }
        return A;
    }

    public int triangularSum(int[] nums) {
        int n = nums.length - 1;
        invMod10();
        int[] coeffs = comb(n);
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum = (sum + coeffs[i] * nums[i]) % 10;
        }
        return sum;
    }
}
