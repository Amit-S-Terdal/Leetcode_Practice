// 3312. Sorted GCD Pair Queries

// You are given an integer array nums of length n and an integer array queries.

// Let gcdPairs denote an array obtained by calculating the GCD of all possible pairs (nums[i], nums[j]), where 0 <= i < j < n, and then sorting these values in ascending order.

// For each query queries[i], you need to find the element at index queries[i] in gcdPairs.

// Return an integer array answer, where answer[i] is the value at gcdPairs[queries[i]] for each query.

// The term gcd(a, b) denotes the greatest common divisor of a and b.

 

// Example 1:

// Input: nums = [2,3,4], queries = [0,2,2]

// Output: [1,2,2]

// Explanation:

// gcdPairs = [gcd(nums[0], nums[1]), gcd(nums[0], nums[2]), gcd(nums[1], nums[2])] = [1, 2, 1].

// After sorting in ascending order, gcdPairs = [1, 1, 2].

// So, the answer is [gcdPairs[queries[0]], gcdPairs[queries[1]], gcdPairs[queries[2]]] = [1, 2, 2].

// Example 2:

// Input: nums = [4,4,2,1], queries = [5,3,1,0]

// Output: [4,2,1,1]

// Explanation:

// gcdPairs sorted in ascending order is [1, 1, 1, 2, 2, 4].

// Example 3:

// Input: nums = [2,2], queries = [0,0]

// Output: [2,2]

// Explanation:

// gcdPairs = [2].

 

// Constraints:

// 2 <= n == nums.length <= 10^5
// 1 <= nums[i] <= 5 * 10^4
// 1 <= queries.length <= 10^5
// 0 <= queries[i] < n * (n - 1) / 2




// Solution:




import java.util.*;

class Solution {
    static final int MX = 50001;

    static byte[] mu = new byte[MX];
    static boolean[] isPrime = new boolean[MX];
    static ArrayList<Integer> primes = new ArrayList<>();
    static ArrayList<Integer> sqFree = new ArrayList<>();

    static long[] Div = new long[MX];
    static long[] freq = new long[MX];

    static {
        sieveMu();
    }

    static void sieveMu() {
        if (mu[1] == 1) return;

        Arrays.fill(mu, (byte) 1);
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;
        mu[1] = 1;

        for (int i = 2; i < MX; i++) {
            if (isPrime[i]) {
                primes.add(i);
                mu[i] = -1;

                for (int j = i * 2; j < MX; j += i) {
                    isPrime[j] = false;
                    mu[j] = (byte) (-mu[j]);
                }

                long sq = 1L * i * i;
                for (long j = sq; j < MX; j += sq) {
                    mu[(int) j] = 0;
                }
            }
        }

        for (int i = 1; i < MX; i++) {
            if (mu[i] != 0) {
                sqFree.add(i);
            }
        }
    }

    public int[] gcdValues(int[] nums, long[] queries) {
        int M = 0;

        for (int x : nums) {
            M = Math.max(M, x);
            Div[x]++;
        }

        // Div[i] = count of numbers divisible by i
        for (int x = 1; x <= M; x++) {
            for (int y = x + x; y <= M; y += x) {
                Div[x] += Div[y];
            }
        }

        // Number of pairs divisible by x
        for (int x = 1; x <= M; x++) {
            long cnt = Div[x];
            Div[x] = cnt * (cnt - 1L) / 2L;
        }

        // Möbius inversion
        for (int x = 1; x <= M; x++) {
            long sum = 0;
            for (int k : sqFree) {
                long mul = 1L * x * k;
                if (mul > M) break;
                sum += (long) mu[k] * Div[(int) mul];
            }
            freq[x] = sum;
        }

        // Prefix sums
        for (int i = 1; i <= M; i++) {
            freq[i] += freq[i - 1];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            ans[i] = upperBound(freq, M + 1, queries[i]);
        }

        // Reset arrays for next testcase
        Arrays.fill(Div, 0, M + 1, 0L);

        return ans;
    }

    // First index with value > target
    static int upperBound(long[] arr, int len, long target) {
        int l = 0, r = len;
        while (l < r) {
            int mid = (l + r) >>> 1;
            if (arr[mid] <= target)
                l = mid + 1;
            else
                r = mid;
        }
        return l;
    }
}