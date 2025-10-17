// 3003. Maximize the Number of Partitions After Operations

// You are given a string s and an integer k.

// First, you are allowed to change at most one index in s to another lowercase English letter.

// After that, do the following partitioning operation until s is empty:

// Choose the longest prefix of s containing at most k distinct characters.
// Delete the prefix from s and increase the number of partitions by one. The remaining characters (if any) in s maintain their initial order.
// Return an integer denoting the maximum number of resulting partitions after the operations by optimally choosing at most one index to change.

 

// Example 1:

// Input: s = "accca", k = 2

// Output: 3

// Explanation:

// The optimal way is to change s[2] to something other than a and c, for example, b. then it becomes "acbca".

// Then we perform the operations:

// The longest prefix containing at most 2 distinct characters is "ac", we remove it and s becomes "bca".
// Now The longest prefix containing at most 2 distinct characters is "bc", so we remove it and s becomes "a".
// Finally, we remove "a" and s becomes empty, so the procedure ends.
// Doing the operations, the string is divided into 3 partitions, so the answer is 3.

// Example 2:

// Input: s = "aabaab", k = 3

// Output: 1

// Explanation:

// Initially s contains 2 distinct characters, so whichever character we change, it will contain at most 3 distinct characters, so the longest prefix with at most 3 distinct characters would always be all of it, therefore the answer is 1.

// Example 3:

// Input: s = "xxyz", k = 1

// Output: 4

// Explanation:

// The optimal way is to change s[0] or s[1] to something other than characters in s, for example, to change s[0] to w.

// Then s becomes "wxyz", which consists of 4 distinct characters, so as k is 1, it will divide into 4 partitions.

 

// Constraints:

// 1 <= s.length <= 10^4
// s consists only of lowercase English letters.
// 1 <= k <= 26


// Solution: 



import java.util.BitSet;

class Data {
    int segCnt;
    BitSet hasC;
    int cnt;

    public Data() {
        this.segCnt = 0;
        this.hasC = new BitSet(26);
        this.cnt = 0;
    }

    public Data(int segCnt, BitSet hasC, int cnt) {
        this.segCnt = segCnt;
        this.hasC = (BitSet) hasC.clone();
        this.cnt = cnt;
    }
}

class Solution {
    public int maxPartitionsAfterOperations(String s, int k) {
        int n = s.length();
        Data[] pref = new Data[n];
        Data[] suff = new Data[n];

        for (int i = 0; i < n; i++) {
            pref[i] = new Data();
            suff[i] = new Data();
        }

        int seg = 0, cnt = 0;
        BitSet mask = new BitSet(26);

        for (int i = 0; i < n - 1; i++) {
            int idx = s.charAt(i) - 'a';
            if (!mask.get(idx)) {
                cnt++;
                if (cnt > k) {
                    seg++;
                    cnt = 1;
                    mask.clear();
                }
                mask.set(idx);
            }
            pref[i + 1] = new Data(seg, mask, cnt);
        }

        seg = 0;
        cnt = 0;
        mask = new BitSet(26);

        for (int i = n - 1; i > 0; i--) {
            int idx = s.charAt(i) - 'a';
            if (!mask.get(idx)) {
                cnt++;
                if (cnt > k) {
                    seg++;
                    cnt = 1;
                    mask.clear();
                }
                mask.set(idx);
            }
            suff[i - 1] = new Data(seg, mask, cnt);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            Data left = pref[i];
            Data right = suff[i];

            int segL = left.segCnt;
            BitSet Lmask = left.hasC;
            int Lcnt = left.cnt;

            int segR = right.segCnt;
            BitSet Rmask = right.hasC;
            int Rcnt = right.cnt;

            int totalSeg = segL + segR + 1;

            BitSet unionMask = (BitSet) Lmask.clone();
            unionMask.or(Rmask);
            int bitCount = unionMask.cardinality();

            int add;
            if (Math.min(bitCount + 1, 26) <= k) {
                add = 0;
            } else {
                add = (Lcnt == k && Rcnt == k && bitCount < 26) ? 2 : 1;
            }

            ans = Math.max(ans, totalSeg + add);
        }

        return ans;
    }
}
