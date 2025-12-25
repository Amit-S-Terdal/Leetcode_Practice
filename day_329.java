// 3075. Maximize Happiness of Selected Children

// You are given an array happiness of length n, and a positive integer k.

// There are n children standing in a queue, where the ith child has happiness value happiness[i]. You want to select k children from these n children in k turns.

// In each turn, when you select a child, the happiness value of all the children that have not been selected till now decreases by 1. Note that the happiness value cannot become negative and gets decremented only if it is positive.

// Return the maximum sum of the happiness values of the selected children you can achieve by selecting k children.

 

// Example 1:

// Input: happiness = [1,2,3], k = 2
// Output: 4
// Explanation: We can pick 2 children in the following way:
// - Pick the child with the happiness value == 3. The happiness value of the remaining children becomes [0,1].
// - Pick the child with the happiness value == 1. The happiness value of the remaining child becomes [0]. Note that the happiness value cannot become less than 0.
// The sum of the happiness values of the selected children is 3 + 1 = 4.
// Example 2:

// Input: happiness = [1,1,1,1], k = 2
// Output: 1
// Explanation: We can pick 2 children in the following way:
// - Pick any child with the happiness value == 1. The happiness value of the remaining children becomes [0,0,0].
// - Pick the child with the happiness value == 0. The happiness value of the remaining child becomes [0,0].
// The sum of the happiness values of the selected children is 1 + 0 = 1.
// Example 3:

// Input: happiness = [2,3,4,5], k = 1
// Output: 5
// Explanation: We can pick 1 child in the following way:
// - Pick the child with the happiness value == 5. The happiness value of the remaining children becomes [1,2,3].
// The sum of the happiness values of the selected children is 5.
 

// Constraints:

// 1 <= n == happiness.length <= 2 * 10^5
// 1 <= happiness[i] <= 10^8
// 1 <= k <= n



// Solution: 




import java.util.*;

public class day_329 {
    private static final int N = 512, mask = 511, bshift = 9;
    private static int[] freq = new int[N];

    private static void radixSort(int[] nums, int n) {
        int[] output = new int[n];  // buffer
        int M = Arrays.stream(nums).max().getAsInt();
        int Mround = Math.max(1, (31 - Integer.numberOfLeadingZeros(M) + bshift) / bshift);
        int[] in = nums;
        int[] out = output;
        boolean swapped = false;

        for (int round = 0; round < Mround; round++) {
            int shift = round * bshift;
            Arrays.fill(freq, 0);

            for (int i = 0; i < n; i++)
                freq[(in[i] >> shift) & mask]++;

            for (int i = 1; i < N; i++) {
                freq[i] += freq[i - 1];
            }

            for (int i = n - 1; i >= 0; i--) {
                int val = in[i];
                int x = (val >> shift) & mask;
                out[--freq[x]] = val;
            }

            // Swap pointers
            int[] temp = in;
            in = out;
            out = temp;
            swapped = !swapped;
        }

        // If needed, copy back
        if (swapped)
            System.arraycopy(in, 0, nums, 0, n);
    }

    public long maximumHappinessSum(int[] happiness, int k) {
        int n = happiness.length;
        radixSort(happiness, n);
        
        long sum = 0;
        for (int i = 0; i < k; i++) {
            long x = Math.max(0, happiness[n - 1 - i] - i);
            sum += x;
        }
        return sum;
    }

    public static void main(String[] args) {
        day_329 sol = new day_329();
        int[] happiness = {1, 2, 3, 4, 5};
        int k = 3;
        System.out.println(sol.maximumHappinessSum(happiness, k)); // Example usage
    }
}
