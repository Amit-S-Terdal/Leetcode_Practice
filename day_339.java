// 1390. Four Divisors

// Given an integer array nums, return the sum of divisors of the integers in that array that have exactly four divisors. If there is no such integer in the array, return 0.

 

// Example 1:

// Input: nums = [21,4,7]
// Output: 32
// Explanation: 
// 21 has 4 divisors: 1, 3, 7, 21
// 4 has 3 divisors: 1, 2, 4
// 7 has 2 divisors: 1, 7
// The answer is the sum of divisors of 21 only.
// Example 2:

// Input: nums = [21,21]
// Output: 64
// Example 3:

// Input: nums = [1,2,3,4,5]
// Output: 0
 

// Constraints:

// 1 <= nums.length <= 10^4
// 1 <= nums[i] <= 10^5



// Solution: 



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    static final int N = 316;
    static boolean[] isPrime = new boolean[N + 1];
    static List<Integer> prime = new ArrayList<>();
    static int[] Div4 = new int[100001];

    public static void Sieve() {
        if (isPrime[2]) return;
        Arrays.fill(isPrime, true);
        Arrays.fill(Div4, -1);
        isPrime[0] = isPrime[1] = false;
        Div4[0] = Div4[1] = 0;
        int Nsqrt = (int) Math.sqrt(N - 1);
        for (int p = 2; p <= Nsqrt; p += 1 + (p & 1)) {
            if (isPrime[p]) {
                Div4[p] = 0; // prime has only 2 divisors
                prime.add(p);
                for (int i = p * p; i < N; i += p)
                    isPrime[i] = false;
            }
        }
        for (int i = Nsqrt + ((Nsqrt & 1) == 0 ? 1 : 0); i < N; i += 2) {
            if (isPrime[i])
                prime.add(i);
        }
    }

    public static int sum4Div(int x) {
        if (Div4[x] != -1) return Div4[x];
        int y = x, sum = 1 + x, cntPF = 0, xsqrt = (int) Math.sqrt(x);
        for (int p : prime) {
            if (p > xsqrt) break;
            if (y % p != 0) continue;
            int e = 0;
            while (y % p == 0) {
                y /= p;
                e++;
            }
            cntPF++;

            if (e == 3 && y == 1 && cntPF == 1)
                return Div4[x] = 1 + p + p * p + p * p * p;

            if (e > 1 || cntPF > 2) return Div4[x] = 0;
            sum += p;
        }
        if (y > 1) {
            cntPF++;
            sum += y;
        }
        return Div4[x] = (cntPF == 2) ? sum : 0;
    }

    public int sumFourDivisors(int[] nums) {
        Sieve();
        int ans = 0;
        for (int x : nums)
            ans += sum4Div(x);
        return ans;
    }
}
