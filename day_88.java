// 1399. Count Largest Group

// You are given an integer n.

// Each number from 1 to n is grouped according to the sum of its digits.

// Return the number of groups that have the largest size.

 

// Example 1:

// Input: n = 13
// Output: 4
// Explanation: There are 9 groups in total, they are grouped according sum of its digits of numbers from 1 to 13:
// [1,10], [2,11], [3,12], [4,13], [5], [6], [7], [8], [9].
// There are 4 groups with largest size.
// Example 2:

// Input: n = 2
// Output: 2
// Explanation: There are 2 groups [1], [2] of size 1.
 

// Constraints:

// 1 <= n <= 10^4


// Solution:


class Solution {
    public int countLargestGroup(int n) {
        // Array to store counts of each digit sum (max sum of digits for n = 10000 is 36)
        int[] count = new int[37];
        int maxSize = 0;

        for (int i = 1; i <= n; i++) {
            int sum = digitSum(i);
            count[sum]++;
            maxSize = Math.max(maxSize, count[sum]);
        }

        int result = 0;
        for (int c : count) {
            if (c == maxSize) {
                result++;
            }
        }

        return result;
    }

    private int digitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
