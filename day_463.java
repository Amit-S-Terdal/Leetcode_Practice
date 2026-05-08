// 3629. Minimum Jumps to Reach End via Prime Teleportation

// You are given an integer array nums of length n.

// You start at index 0, and your goal is to reach index n - 1.

// From any index i, you may perform one of the following operations:

// Adjacent Step: Jump to index i + 1 or i - 1, if the index is within bounds.
// Prime Teleportation: If nums[i] is a prime number p, you may instantly jump to any index j != i such that nums[j] % p == 0.
// Return the minimum number of jumps required to reach index n - 1.

 

// Example 1:

// Input: nums = [1,2,4,6]

// Output: 2

// Explanation:

// One optimal sequence of jumps is:

// Start at index i = 0. Take an adjacent step to index 1.
// At index i = 1, nums[1] = 2 is a prime number. Therefore, we teleport to index i = 3 as nums[3] = 6 is divisible by 2.
// Thus, the answer is 2.

// Example 2:

// Input: nums = [2,3,4,7,9]

// Output: 2

// Explanation:

// One optimal sequence of jumps is:

// Start at index i = 0. Take an adjacent step to index i = 1.
// At index i = 1, nums[1] = 3 is a prime number. Therefore, we teleport to index i = 4 since nums[4] = 9 is divisible by 3.
// Thus, the answer is 2.

// Example 3:

// Input: nums = [4,6,5,8]

// Output: 3

// Explanation:

// Since no teleportation is possible, we move through 0 → 1 → 2 → 3. Thus, the answer is 3.
 

// Constraints:

// 1 <= n == nums.length <= 10^5
// 1 <= nums[i] <= 10^6




// Solution:



import java.util.*;

class Solution {

    static final int M = 1_000_001;
    static final int N = 100_001;

    static BitSet sieve = new BitSet(M);
    static int[] pIdx = new int[M];
    static ArrayList<Integer> primes = new ArrayList<>();
    static ArrayList<Integer>[] mp = new ArrayList[80000];

    static int sz = 0;
    static boolean sieveBuilt = false;

    static {
        Arrays.fill(pIdx, -1);
        for (int i = 0; i < 80000; i++) {
            mp[i] = new ArrayList<>();
        }
    }

    static void Sieve() {
        if (sieveBuilt) return;
        sieveBuilt = true;

        sieve.set(0);
        sieve.set(1);

        primes.ensureCapacity(80000);

        for (int i = 2; i <= 1000; i += 1 + (i > 2 ? 1 : 0)) {
            if (sieve.get(i)) continue;

            primes.add(i);
            pIdx[i] = sz++;

            for (int j = i * i; j < M; j += i) {
                sieve.set(j);
            }
        }

        for (int i = 1001; i < M; i += 2) {
            if (!sieve.get(i)) {
                pIdx[i] = sz++;
                primes.add(i);
            }
        }
    }

    static void reset(BitSet used, int maxPidx) {
        for (int i = 0; i <= maxPidx; i++) {
            if (used.get(i)) {
                mp[i].clear();
            }
        }
    }

    public int minJumps(int[] nums) {

        Sieve();

        int n = nums.length;

        BitSet used = new BitSet(80000);
        int maxPidx = -1;

        for (int i = 0; i < n; i++) {
            int x = nums[i];

            if (!sieve.get(x)) {
                int idx = pIdx[x];
                mp[idx].add(i);
                used.set(idx);
                maxPidx = Math.max(maxPidx, idx);
            }
        }

        BitSet vis = new BitSet(N);

        Queue<Integer> q = new ArrayDeque<>();
        q.offer(n - 1);
        vis.set(n - 1);

        int d = 0;

        while (!q.isEmpty()) {

            int qz = q.size();

            while (qz-- > 0) {

                int i = q.poll();

                if (i == 0) {
                    reset(used, maxPidx);
                    return d;
                }

                int x = nums[i];

                if (i > 0 && !vis.get(i - 1)) {
                    vis.set(i - 1);
                    q.offer(i - 1);
                }

                if (i < n - 1 && !vis.get(i + 1)) {
                    vis.set(i + 1);
                    q.offer(i + 1);
                }

                for (int j = 0; j < sz && x > 1; j++) {

                    int p = primes.get(j);

                    if (!sieve.get(x)) {
                        p = x;
                    }

                    if (x % p == 0) {

                        int idx = pIdx[p];

                        while (x % p == 0) {
                            x /= p;
                        }

                        for (int it : mp[idx]) {
                            if (!vis.get(it)) {
                                vis.set(it);
                                q.offer(it);
                            }
                        }

                        used.clear(idx);
                        mp[idx].clear();
                    }
                }
            }

            d++;
        }

        return -1;
    }
}