// 1317. Convert Integer to the Sum of Two No-Zero Integers

// No-Zero integer is a positive integer that does not contain any 0 in its decimal representation.

// Given an integer n, return a list of two integers [a, b] where:

// a and b are No-Zero integers.
// a + b = n
// The test cases are generated so that there is at least one valid solution. If there are many valid solutions, you can return any of them.

 

// Example 1:

// Input: n = 2
// Output: [1,1]
// Explanation: Let a = 1 and b = 1.
// Both a and b are no-zero integers, and a + b = 2 = n.
// Example 2:

// Input: n = 11
// Output: [2,9]
// Explanation: Let a = 2 and b = 9.
// Both a and b are no-zero integers, and a + b = 11 = n.
// Note that there are other valid answers as [8, 3] that can be accepted.
 

// Constraints:

// 2 <= n <= 10^4


// Solution:



class Solution {
    public int[] getNoZeroIntegers(int n) {
        int a = 0, b = 0, tens = 1;
        java.util.Random rand = new java.util.Random();

        while (n > 0) {
            int d = n % 10;

            if (d == 0) {
                int x = rand.nextInt(9) + 1; // [1, 9]
                a += x * tens;
                b += (10 - x) * tens;
                n -= 10; // borrow from next digit
            } else if (d == 1 && n >= 10) {
                int x = rand.nextInt(8) + 2; // [2, 9]
                a += x * tens;
                b += (11 - x) * tens;
                n -= 10; // borrow from next digit
            } else {
                int x = (d == 1) ? 1 : rand.nextInt(d - 1) + 1; // [1, d-1]
                a += x * tens;
                b += (d - x) * tens;
            }

            n /= 10;
            tens *= 10;
        }

        return new int[]{a, b};
    }
}
