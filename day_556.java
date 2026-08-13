// 2213. Longest Substring of One Repeating Character

// You are given a 0-indexed string s. You are also given a 0-indexed string queryCharacters of length k and a 0-indexed array of integer indices queryIndices of length k, both of which are used to describe k queries.

// The ith query updates the character in s at index queryIndices[i] to the character queryCharacters[i].

// Return an array lengths of length k where lengths[i] is the length of the longest substring of s consisting of only one repeating character after the ith query is performed.

 

// Example 1:

// Input: s = "babacc", queryCharacters = "bcb", queryIndices = [1,3,3]
// Output: [3,3,4]
// Explanation: 
// - 1st query updates s = "bbbacc". The longest substring consisting of one repeating character is "bbb" with length 3.
// - 2nd query updates s = "bbbccc". 
//   The longest substring consisting of one repeating character can be "bbb" or "ccc" with length 3.
// - 3rd query updates s = "bbbbcc". The longest substring consisting of one repeating character is "bbbb" with length 4.
// Thus, we return [3,3,4].
// Example 2:

// Input: s = "abyzz", queryCharacters = "aa", queryIndices = [2,1]
// Output: [2,3]
// Explanation:
// - 1st query updates s = "abazz". The longest substring consisting of one repeating character is "zz" with length 2.
// - 2nd query updates s = "aaazz". The longest substring consisting of one repeating character is "aaa" with length 3.
// Thus, we return [2,3].
 

// Constraints:

// 1 <= s.length <= 10^5
// s consists of lowercase English letters.
// k == queryCharacters.length == queryIndices.length
// 1 <= k <= 10^5
// queryCharacters consists of lowercase English letters.
// 0 <= queryIndices[i] < s.length



// Solution: 




class Solution {
    static final int N = 1 << 18;

    static class Node {
        int len, longest, pref, suff;
        char left, right;

        Node() {
            len = longest = pref = suff = 0;
            left = right = 0;
        }

        Node(int len, int longest, int pref, int suff, char left, char right) {
            this.len = len;
            this.longest = longest;
            this.pref = pref;
            this.suff = suff;
            this.left = left;
            this.right = right;
        }
    }

    Node[] tree;
    int n2;

    Node merge(Node L, Node R) {
        int len = L.len + R.len;
        int longest = Math.max(L.longest, R.longest);

        boolean canPlus = L.right == R.left;

        if (canPlus) {
            longest = Math.max(longest, L.suff + R.pref);
        }

        int pref = L.pref;
        if (L.pref == L.len && canPlus) {
            pref += R.pref;
        }

        int suff = R.suff;
        if (R.suff == R.len && canPlus) {
            suff += L.suff;
        }

        return new Node(len, longest, pref, suff, L.left, R.right);
    }

    void build(String s) {
        int n = s.length();
        n2 = 1;

        while (n2 < n) {
            n2 <<= 1;
        }

        tree = new Node[2 * n2];

        for (int i = 0; i < 2 * n2; i++) {
            tree[i] = new Node();
        }

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            tree[i + n2] = new Node(1, 1, 1, 1, c, c);
        }

        for (int i = n2 - 1; i >= 1; i--) {
            tree[i] = merge(tree[i << 1], tree[(i << 1) | 1]);
        }
    }

    void update(int idx, char c) {
        idx += n2;
        tree[idx] = new Node(1, 1, 1, 1, c, c);

        idx >>= 1;
        while (idx >= 1) {
            tree[idx] = merge(tree[idx << 1], tree[(idx << 1) | 1]);
            idx >>= 1;
        }
    }

    public int[] longestRepeating(
            String s,
            String queryCharacters,
            int[] queryIndices) {

        build(s);

        int k = queryIndices.length;
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            update(queryIndices[i], queryCharacters.charAt(i));
            ans[i] = tree[1].longest;
        }

        return ans;
    }
}