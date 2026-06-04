// 3751. Total Waviness of Numbers in Range I

// You are given two integers num1 and num2 representing an inclusive range [num1, num2].

// The waviness of a number is defined as the total count of its peaks and valleys:

// A digit is a peak if it is strictly greater than both of its immediate neighbors.
// A digit is a valley if it is strictly less than both of its immediate neighbors.
// The first and last digits of a number cannot be peaks or valleys.
// Any number with fewer than 3 digits has a waviness of 0.
// Return the total sum of waviness for all numbers in the range [num1, num2].
 

// Example 1:

// Input: num1 = 120, num2 = 130

// Output: 3

// Explanation:

// In the range [120, 130]:
// 120: middle digit 2 is a peak, waviness = 1.
// 121: middle digit 2 is a peak, waviness = 1.
// 130: middle digit 3 is a peak, waviness = 1.
// All other numbers in the range have a waviness of 0.
// Thus, total waviness is 1 + 1 + 1 = 3.

// Example 2:

// Input: num1 = 198, num2 = 202

// Output: 3

// Explanation:

// In the range [198, 202]:
// 198: middle digit 9 is a peak, waviness = 1.
// 201: middle digit 0 is a valley, waviness = 1.
// 202: middle digit 0 is a valley, waviness = 1.
// All other numbers in the range have a waviness of 0.
// Thus, total waviness is 1 + 1 + 1 = 3.

// Example 3:

// Input: num1 = 4848, num2 = 4848

// Output: 2

// Explanation:

// Number 4848: the second digit 8 is a peak, and the third digit 4 is a valley, giving a waviness of 2.

 

// Constraints:

// 1 <= num1 <= num2 <= 10^5



// Solution:




class Solution {
    static int[] D = new int[6]; // least significant digit first

    static int wav(int x) {
        int d = 0;

        while (x > 0) {
            D[d++] = x % 10;
            x /= 10;
        }

        int cnt = 0;
        int prv = D[0];
        int nxt = D[1];

        for (int i = 1; i < d - 1; i++) {
            int cur = nxt;      // equivalent to exchange(nxt, D[i + 1])
            nxt = D[i + 1];

            cnt += (((prv < cur) && (cur > nxt)) ||
                    ((prv > cur) && (cur < nxt))) ? 1 : 0;

            prv = cur;
        }

        return cnt;
    }

    public int totalWaviness(int num1, int num2) {
        num1 = Math.max(num1, 101);

        int ans = 0;
        for (int x = num1; x <= num2; x++) {
            ans += wav(x);
        }

        return ans;
    }
}