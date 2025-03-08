// 2523. Closest Prime Numbers in Range


// Given two positive integers left and right, find the two integers num1 and num2 such that:

// left <= num1 < num2 <= right .
// Both num1 and num2 are prime numbers.
// num2 - num1 is the minimum amongst all other pairs satisfying the above conditions.
// Return the positive integer array ans = [num1, num2]. If there are multiple pairs satisfying these conditions, return the one with the smallest num1 value. If no such numbers exist, return [-1, -1].

 

// Example 1:

// Input: left = 10, right = 19
// Output: [11,13]
// Explanation: The prime numbers between 10 and 19 are 11, 13, 17, and 19.
// The closest gap between any pair is 2, which can be achieved by [11,13] or [17,19].
// Since 11 is smaller than 17, we return the first pair.
// Example 2:

// Input: left = 4, right = 6
// Output: [-1,-1]
// Explanation: There exists only one prime number in the given range, so the conditions cannot be satisfied.
 

// Constraints:

// 1 <= left <= right <= 106

// Solution:

import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[] closestPrimes(int left, int right) {
        // Generate all prime numbers in the range [left, right]
        boolean[] isPrime = new boolean[right + 1];
        for (int i = 2; i <= right; i++) {
            isPrime[i] = true;
        }
        
        for (int i = 2; i * i <= right; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= right; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        
        // Collect all prime numbers in the range [left, right]
        List<Integer> primes = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
        
        // If there are less than 2 primes, return [-1, -1]
        if (primes.size() < 2) {
            return new int[]{-1, -1};
        }
        
        // Find the pair of consecutive primes with the smallest difference
        int minDiff = Integer.MAX_VALUE;
        int[] result = new int[]{-1, -1};
        
        for (int i = 1; i < primes.size(); i++) {
            int diff = primes.get(i) - primes.get(i - 1);
            if (diff < minDiff) {
                minDiff = diff;
                result[0] = primes.get(i - 1);
                result[1] = primes.get(i);
            }
        }
        
        return result;
    }
}