// 3345. Smallest Divisible Digit Product I

// You are given two integers n and t. Return the smallest number greater than or equal to n such that the product of its digits is divisible by t.

 

// Example 1:

// Input: n = 10, t = 2

// Output: 10

// Explanation:

// The digit product of 10 is 0, which is divisible by 2, making it the smallest number greater than or equal to 10 that satisfies the condition.

// Example 2:

// Input: n = 15, t = 3

// Output: 16

// Explanation:

// The digit product of 16 is 6, which is divisible by 3, making it the smallest number greater than or equal to 15 that satisfies the condition.

 

// Constraints:

// 1 <= n <= 100
// 1 <= t <= 10



// Solution:




class Solution {
    public int product_digit(int x) {
        int ans = 1, d = 0;
        for (; x != 0; x /= 10) {
            d = x % 10;
            ans *= d;
        }
        return ans;
    }

    public int smallestNumber(int n, int t) {
        int[] P = {
            product_digit(n / 10),
            product_digit(n / 10 + 1)
        };

        int z0 = ((n / 10) + 1) * 10;

        for (int z = n; z < n + 10; z++) {
            int p = P[z >= z0 ? 1 : 0];
            int d = z % 10;

            if ((p * d) % t == 0) {
                return z;
            }
        }

        return 0;
    }
}