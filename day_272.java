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


class Solution {
    // Array A to store the precomputed values
    static int[] A = new int[11]; 

    // Method to build the array A with the values
    static void buildA() {
        if (A[0] == 1) return; // Only initialize once
        A[0] = 1;
        for (int i = 1; i <= 10; i++) {
            A[i] = (1 << i) - 1; // 2^i - 1
        }
    }

    // Method to find the smallest number greater than or equal to n
    public int smallestNumber(int n) {
        buildA(); // Build the array A
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= n) {
                return A[i];
            }
        }
        return -1; // In case no value is found (though the array is guaranteed to have one)
    }
}
