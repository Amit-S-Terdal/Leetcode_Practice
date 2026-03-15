// 1622. Fancy Sequence

// Write an API that generates fancy sequences using the append, addAll, and multAll operations.

// Implement the Fancy class:

// Fancy() Initializes the object with an empty sequence.
// void append(val) Appends an integer val to the end of the sequence.
// void addAll(inc) Increments all existing values in the sequence by an integer inc.
// void multAll(m) Multiplies all existing values in the sequence by an integer m.
// int getIndex(idx) Gets the current value at index idx (0-indexed) of the sequence modulo 109 + 7. If the index is greater or equal than the length of the sequence, return -1.
 

// Example 1:

// Input
// ["Fancy", "append", "addAll", "append", "multAll", "getIndex", "addAll", "append", "multAll", "getIndex", "getIndex", "getIndex"]
// [[], [2], [3], [7], [2], [0], [3], [10], [2], [0], [1], [2]]
// Output
// [null, null, null, null, null, 10, null, null, null, 26, 34, 20]

// Explanation
// Fancy fancy = new Fancy();
// fancy.append(2);   // fancy sequence: [2]
// fancy.addAll(3);   // fancy sequence: [2+3] -> [5]
// fancy.append(7);   // fancy sequence: [5, 7]
// fancy.multAll(2);  // fancy sequence: [5*2, 7*2] -> [10, 14]
// fancy.getIndex(0); // return 10
// fancy.addAll(3);   // fancy sequence: [10+3, 14+3] -> [13, 17]
// fancy.append(10);  // fancy sequence: [13, 17, 10]
// fancy.multAll(2);  // fancy sequence: [13*2, 17*2, 10*2] -> [26, 34, 20]
// fancy.getIndex(0); // return 26
// fancy.getIndex(1); // return 34
// fancy.getIndex(2); // return 20
 

// Constraints:

// 1 <= val, inc, m <= 100
// 0 <= idx <= 10^5
// At most 105 calls total will be made to append, addAll, multAll, and getIndex.





// Solution:




class Fancy {

    static final int N = 100000 + 1;
    static final long MOD = 1000000007L;

    static int[] nums = new int[N];
    static long[] add = new long[N];
    static long[] inv = new long[N];
    static long[] inv0 = new long[101];

    int n = 0;

    long curMul = 1;
    long curAdd = 0;
    long curInv = 1;

    public Fancy() {
        computeInv();
    }

    private static long inverse(long a) {
        long b = MOD;
        long x0 = 1, x1 = 0;

        while (b > 0) {
            long q = a / b;
            long r = a % b;

            long tmp = x1;
            x1 = x0 - q * x1;
            x0 = tmp;

            a = b;
            b = r;
        }

        if (x0 < 0) x0 += MOD;
        return x0;
    }

    private static void computeInv() {
        if (inv0[1] == 1) return;

        inv0[1] = 1;
        for (int m = 2; m <= 100; m++) {
            inv0[m] = inverse(m);
        }
    }

    public void append(int val) {
        nums[n] = val;
        add[n] = curAdd;
        inv[n] = curInv;
        n++;
    }

    public void addAll(int inc) {
        curAdd = (curAdd + inc) % MOD;
    }

    public void multAll(int m) {
        curMul = (curMul * m) % MOD;
        curAdd = (curAdd * m) % MOD;
        curInv = (curInv * inv0[m]) % MOD;
    }

    public int getIndex(int idx) {
        if (idx >= n) return -1;

        long val = ((nums[idx] - add[idx] + MOD) % MOD);
        val = (val * inv[idx]) % MOD;
        val = (val * curMul) % MOD;
        val = (val + curAdd) % MOD;

        return (int) val;
    }
}